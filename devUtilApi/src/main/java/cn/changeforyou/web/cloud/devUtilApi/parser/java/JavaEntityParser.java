package cn.changeforyou.web.cloud.devUtilApi.parser.java;

import cn.changeforyou.utils.string.StringUtils;
import cn.changeforyou.web.cloud.devUtilApi.modules.common.ProgressContext;
import cn.changeforyou.web.cloud.devUtilApi.modules.common.ProgressContextHolder;
import cn.changeforyou.web.cloud.devUtilApi.parser.java.visitors.*;
import cn.hutool.core.io.IoUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.javadoc.Javadoc;
import com.github.javaparser.javadoc.description.JavadocDescriptionElement;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import javax.persistence.Table;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 解析java文件为java实体对象
 */
@Slf4j
public class JavaEntityParser {
    /**
     * 解析java文件为java实体对象
     *
     * @param javaWords
     * @return
     */
    public static JavaEntity parse(String javaWords) {
        JavaEntity javaEntity = null;
        ProgressContext progressContext = ProgressContextHolder.get();
        if (progressContext.getSuperClassName() != null) {
            javaEntity = progressContext.getJavaEntity();
        } else {
            javaEntity = new JavaEntity();
        }

        parse_do(javaWords, javaEntity, progressContext);

        if (log.isDebugEnabled()) {
            log.debug("解析结果: {}", javaEntity);
        }

        return javaEntity;
    }

    private static void parse_do(String javaWords, JavaEntity javaEntity, ProgressContext progressContext) {
        ParserConfiguration parserConfiguration = new ParserConfiguration();
        parserConfiguration.setLanguageLevel(ParserConfiguration.LanguageLevel.JAVA_8);
        JavaParser javaParser = new JavaParser(parserConfiguration);
        CompilationUnit compilationUnit = javaParser.parse(javaWords);
        for (TypeDeclaration<?> type : compilationUnit.getTypes()) {
            if (type instanceof ClassOrInterfaceDeclaration) {
                ClassOrInterfaceDeclaration classOrInterfaceDeclaration = (ClassOrInterfaceDeclaration) type;
                String name = classOrInterfaceDeclaration.getName().asString();
                if(null != progressContext.getSuperClassName()) {
                    if(!name.equals(progressContext.getSuperClassName())) {
                        progressContext.setNextCode(ProgressContext.NEXT_CODE_ERROR);
                        progressContext.setMessage("需要上传的java类应该是:" + progressContext.getSuperClassName() + "");
                        return;
                    }
                    javaEntity.getFatherNames().add(name);
                }else {
                    //类名到手
                    javaEntity.setName(name);
                }

                dealDocComment(javaEntity, classOrInterfaceDeclaration);
                dealMybatisPlusClassAnnotation(classOrInterfaceDeclaration, javaEntity);
                dealJpaClassAnnotation(classOrInterfaceDeclaration, javaEntity);
                dealBlankClassAnnotation(classOrInterfaceDeclaration, javaEntity);

                PairsAnnotationVisitor pairsAnnotationVisitor = new PairsAnnotationVisitor();
                dealThisClassesFields(javaEntity, classOrInterfaceDeclaration, pairsAnnotationVisitor);

                NodeList<ClassOrInterfaceType> extendedTypes = classOrInterfaceDeclaration.getExtendedTypes();
                if (CollectionUtils.isNotEmpty(extendedTypes)) {
                    ClassOrInterfaceType classOrInterfaceType = extendedTypes.get(0);
                    String superClassName = classOrInterfaceType.getName().getIdentifier();
                    progressContext.setNextCode(ProgressContext.NEXT_CODE_WAIT_ANOTHER);
                    progressContext.setPercent(40);
                    progressContext.setSuperClassName(superClassName);
                    progressContext.setJavaEntity(javaEntity);
                    progressContext.setMessage("发现有父类, 请补充上传。 类名：" + superClassName);
                }else {
                    progressContext.setNextCode(ProgressContext.NEXT_CODE_FINISH);
                    progressContext.setPercent(100);
                    progressContext.setFinish(true);
                    progressContext.setMessage("成功");
                    progressContext.setJavaEntity(javaEntity);
                }
            }
        }
    }

    private static void dealThisClassesFields(JavaEntity javaEntity, ClassOrInterfaceDeclaration classOrInterfaceDeclaration, PairsAnnotationVisitor pairsAnnotationVisitor) {
        for (FieldDeclaration field : classOrInterfaceDeclaration.getFields()) {
            dealOneField(javaEntity, pairsAnnotationVisitor, field);
        }

    }

    /**
     * 处理一个字段
     *
     * @param javaEntity
     * @param pairsAnnotationVisitor
     * @param field
     */
    private static void dealOneField(JavaEntity javaEntity, PairsAnnotationVisitor pairsAnnotationVisitor, FieldDeclaration field) {
        if (field.isStatic()) {
            return;
        }

        JavaEntityField entityField = new JavaEntityField();
        boolean shouldAdd = true;

        Optional<Javadoc> javadoc = field.getJavadoc();
        if (javadoc.isPresent()) {
            List<JavadocDescriptionElement> elements = javadoc.get().getDescription().getElements();
            if(!elements.isEmpty()) {
                String fieldComment = elements.get(0).toText();
                //获取字段注释
                entityField.setComment(fieldComment);
            }

        }
        //获取字段名称
        entityField.setJavaType(field.getElementType().asString());
        entityField.setName(field.getVariables().get(0).getNameAsString());

        shouldAdd = dealMybatisPlusTableFieldAnnotation(pairsAnnotationVisitor, field, entityField, shouldAdd);
        shouldAdd = shouldAdd | dealMybatisPlusTableIdAnnotation(javaEntity, field, entityField);

        if (shouldAdd && !javaEntity.getJavaEntityFields().contains(entityField)) {
            javaEntity.getJavaEntityFields().add(entityField);
        }
    }

    /**
     * 处理mybatis plus的TableId注解
     * @param javaEntity
     * @param field
     * @param entityField
     */
    private static boolean dealMybatisPlusTableIdAnnotation(JavaEntity javaEntity, FieldDeclaration field,
                                                      JavaEntityField entityField) {
        Optional<AnnotationExpr> annotation = field.getAnnotationByClass(TableId.class);
        if (annotation.isPresent()) {
            javaEntity.setPrimaryKey(entityField);
            AnnotationExpr annotationExpr = annotation.get();
            PairAnnotationVisitor<String> visitor = new PairAnnotationVisitor<>();
            String idColumn = annotationExpr.accept(visitor, new ReturnTypeParam(String.class, "value"));
            if(StringUtils.isNotBlank(idColumn)) {
                entityField.getJavaFieldDbInfo().setColumnName(idColumn);
            }else {
                entityField.getJavaFieldDbInfo().setColumnName(entityField.getName());
            }
            return true;
        }
        return false;
    }

    /**
     * 处理mybatis plus的TableField注解信息
     *
     * @param pairsAnnotationVisitor
     * @param field
     * @param entityField
     * @param shouldAdd              确认是否要添加该字段
     * @return
     */
    private static boolean dealMybatisPlusTableFieldAnnotation(PairsAnnotationVisitor pairsAnnotationVisitor, FieldDeclaration field, JavaEntityField entityField, boolean shouldAdd) {
        Optional<AnnotationExpr> annotation = field.getAnnotationByClass(TableField.class);
        if (annotation.isPresent()) {
            NormalAnnotationExpr normalAnnotationExpr = annotation.get().asNormalAnnotationExpr();
            List<ParamValue> params = new ArrayList<>(2);
            params.add(new ParamValue(boolean.class, "exist"));
            params.add(new ParamValue(String.class, "value"));
            List<ReturnValue> returnValues = pairsAnnotationVisitor.visit(normalAnnotationExpr, params);
            for (ReturnValue returnValue : returnValues) {
                if (returnValue.getParamName().equals("exist")) {
                    if (returnValue.getValue() != null) {
                        boolean value = (boolean) returnValue.getValue();
                        if (!value) {
                            //处理注解中exist字段
                            shouldAdd = false;
                            break;
                        }
                    }
                } else if (returnValue.getParamName().equals("value")) {
                    Object value = returnValue.getValue();
                    if (null != value) {
                        //处理注解中value字段
                        entityField.getJavaFieldDbInfo().setColumnName((String) value);
                    }
                }
            }
        }
        return shouldAdd;
    }


    /**
     * 处理空白情况
     *
     * @param classOrInterfaceDeclaration
     * @param javaEntity
     */
    private static void dealBlankClassAnnotation(ClassOrInterfaceDeclaration classOrInterfaceDeclaration, JavaEntity javaEntity) {
        if (StringUtils.isEmpty(javaEntity.getDbInfo().getTableName())) {
            javaEntity.getDbInfo().setTableName(StringUtils.camelCase2UnderScoreCase(javaEntity.getName()));
        }
    }

    /**
     * 处理javaDoc注释
     *
     * @param javaEntity
     * @param classOrInterfaceDeclaration
     */
    private static void dealDocComment(JavaEntity javaEntity, ClassOrInterfaceDeclaration classOrInterfaceDeclaration) {
        Optional<Comment> comment = classOrInterfaceDeclaration.getComment();
        if (comment.isPresent()) {
            List<JavadocDescriptionElement> elements = comment.get().asJavadocComment().parse().getDescription().getElements();
            if (CollectionUtils.isNotEmpty(elements)) {
                JavadocDescriptionElement descriptionElement = elements.get(0);
                String text = descriptionElement.toText();
                //类注释的javadoc到手
                javaEntity.setComment(text);
            }
        }
    }

    /**
     * 处理jpa的类注解
     *
     * @param classOrInterfaceDeclaration
     * @param javaEntity
     */
    private static void dealJpaClassAnnotation(ClassOrInterfaceDeclaration classOrInterfaceDeclaration, JavaEntity javaEntity) {
        Optional<AnnotationExpr> annotationByClass = classOrInterfaceDeclaration.getAnnotationByClass(Table.class);
        if (annotationByClass.isPresent()) {
            AnnotationExpr annotationExpr = annotationByClass.get();
            PairAnnotationVisitor<String> visitor = new PairAnnotationVisitor<>();
            String value = annotationExpr.accept(visitor, new ReturnTypeParam(String.class, "name"));
            //从jpa的注解获取表信息
            javaEntity.getDbInfo().setTableName(value);
        }
    }

    /**
     * 处理mybatis plus的类注解
     *
     * @param classOrInterfaceDeclaration
     * @param javaEntity
     */
    private static void dealMybatisPlusClassAnnotation(ClassOrInterfaceDeclaration classOrInterfaceDeclaration, JavaEntity javaEntity) {
        Optional<AnnotationExpr> annotationByClass = classOrInterfaceDeclaration.getAnnotationByClass(TableName.class);
        if (annotationByClass.isPresent()) {
            AnnotationExpr annotationExpr = annotationByClass.get();
            PairAnnotationVisitor<String> visitor = new PairAnnotationVisitor<>();
            String value = annotationExpr.accept(visitor, new ReturnTypeParam(String.class, "value"));
            //从mybatis plus的注解获取表信息
            if(null == value) {
                SingleAnnotationStringValueVisitor singleAnnotationStringValueVisitor =
                        new SingleAnnotationStringValueVisitor();
                value = annotationExpr.accept(singleAnnotationStringValueVisitor, null);
            }
            javaEntity.getDbInfo().setTableName(value);
        }
    }

    public static JavaEntity parse(InputStream inputStream) {
        String javaWords = IoUtil.read(inputStream, Charset.forName("UTF-8"));
        return parse(javaWords);
    }
}
