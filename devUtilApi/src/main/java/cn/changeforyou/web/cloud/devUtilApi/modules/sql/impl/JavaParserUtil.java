package cn.changeforyou.web.cloud.devUtilApi.modules.sql.impl;

import cn.changeforyou.utils.string.StringUtils;
import cn.changeforyou.web.cloud.devUtilApi.db.sql.JdbcJavaTypeConvertor;
import cn.changeforyou.web.cloud.devUtilApi.parser.java.JavaEntity;
import cn.changeforyou.web.cloud.devUtilApi.parser.java.JavaEntityField;
import cn.changeforyou.web.utils.NameUtils;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.ReceiverParameter;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;

/**
 * javaParser lib的工具类
 * @author zhyu
 * @version 1.0
 * @date 2022/4/12 9:37
 */

public class JavaParserUtil {

    public static final WriteJavaEntityContext default_writeJavaEntityContext = new WriteJavaEntityContext();
    static {
        default_writeJavaEntityContext.setTimeType(TimeType.java8TimeApi);
        default_writeJavaEntityContext.setLombok(false);
    }
    /**
     *
     * 把javaEntity写成字符串
     * @param javaEntity
     * @param context
     * @return
     */
    public static String writeJavaEntityString(JavaEntity javaEntity, WriteJavaEntityContext context) {
        if(null == context){
            context = default_writeJavaEntityContext;
        }
        CompilationUnit compilationUnit = new CompilationUnit();
        compilationUnit.setPackageDeclaration("xx.xxx");
        ClassOrInterfaceDeclaration classDeclaration = compilationUnit.addClass(NameUtils.getFirstUpperName(javaEntity.getName()));
        String entityComment = javaEntity.getComment();

        if(context.isLombok()){
            //lombok打包不进去
            classDeclaration.addAnnotation(lombok.Data.class);
        }

        if (StringUtils.isNotBlank(entityComment)) {
            JavadocComment javadocComment = new JavadocComment();
            javadocComment.setContent(entityComment);
            classDeclaration.setJavadocComment(javadocComment);
        }

        for (JavaEntityField entityField : javaEntity.getJavaEntityFields()) {
            String name = entityField.getName();
            Class javaClass = entityField.getJavaClass();
            if(null == javaClass) {
                javaClass = JdbcJavaTypeConvertor.getJavaTypeByDataTypeAndWriteEntityContext(entityField.getDataType(), context);
                entityField.setJavaClass(javaClass);
            }
            String comment = entityField.getComment();

            FieldDeclaration fieldDeclaration = classDeclaration.addField(javaClass, name, Modifier.Keyword.PRIVATE);
            if (StringUtils.isNotBlank(comment)) {
                JavadocComment javadocComment = new JavadocComment();
                javadocComment.setContent(entityComment);
                fieldDeclaration.setJavadocComment(comment);
            }
        }

        if(!context.isLombok()) {
            for (JavaEntityField entityField : javaEntity.getJavaEntityFields()) {
                String name = entityField.getName();
                Class javaClass = entityField.getJavaClass();
                MethodDeclaration getMethod = classDeclaration.addMethod("get" + NameUtils.getFirstUpperName(name), Modifier.Keyword.PUBLIC);
                ReturnStmt returnStmt = new ReturnStmt(new NameExpr("this." + name));
                getMethod.setBody(new BlockStmt().addStatement(returnStmt));
                getMethod.setType(javaClass);

                MethodDeclaration setMethod = classDeclaration.addMethod("set" + NameUtils.getFirstUpperName(name), Modifier.Keyword.PUBLIC);
                ReceiverParameter receiverParameter = new ReceiverParameter();
                receiverParameter.setType(javaClass);
                setMethod.setReceiverParameter(receiverParameter);

                setMethod.setBody(new BlockStmt().addStatement("this." + name  + " = " + name + ";"));
            }
        }


        String result = compilationUnit.toString();
        return result;
    }
}
