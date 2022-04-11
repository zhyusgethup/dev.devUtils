package cn.changeforyou.web.cloud.devUtilApi.modules.sql.impl;

import cn.changeforyou.base.exception.DataExceptionEnum;
import cn.changeforyou.base.exception.ExceptionFactory;
import cn.changeforyou.utils.string.StringUtils;
import cn.changeforyou.web.cloud.devUtilApi.modules.sql.SqlService;
import cn.changeforyou.web.cloud.devUtilApi.parser.java.JavaEntity;
import cn.changeforyou.web.cloud.devUtilApi.parser.java.JavaEntityField;
import cn.changeforyou.web.utils.NameUtils;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.comments.JavadocComment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zhyu
 * @version 1.0
 * @date 2022/4/11 17:52
 */

@Service
@Slf4j
public class SqlServiceImpl implements SqlService {

    @Override
    public String ddl2Entity(String value) {
        JavaEntity javaEntity = null;
        try {
            javaEntity = JSqlParserUtil.parseDdl2JavaEntityObject(value);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.error("error", e);
            }
            throw ExceptionFactory.jsonException(DataExceptionEnum.PARAM_ERROR);
        }
        System.out.println(javaEntity);

        CompilationUnit compilationUnit = new CompilationUnit();
        compilationUnit.setPackageDeclaration("xx.xxx");
        ClassOrInterfaceDeclaration classDeclaration = compilationUnit.addClass(NameUtils.getFirstUpperName(javaEntity.getName()));
        String entityComment = javaEntity.getComment();
        if (StringUtils.isNotBlank(entityComment)) {
            JavadocComment javadocComment = new JavadocComment();
            javadocComment.setContent(entityComment);
            classDeclaration.setJavadocComment(javadocComment);
        }

        for (JavaEntityField entityField : javaEntity.getJavaEntityFields()) {
            String name = entityField.getName();
            Class javaClass = entityField.getJavaClass();
            String comment = entityField.getComment();

            FieldDeclaration fieldDeclaration = classDeclaration.addField(javaClass, name, Modifier.Keyword.PRIVATE);
            if (StringUtils.isNotBlank(comment)) {
                JavadocComment javadocComment = new JavadocComment();
                javadocComment.setContent(entityComment);
                fieldDeclaration.setJavadocComment(comment);
            }
        }

        try {
            Class.forName("lombok.Data");
            classDeclaration.addAnnotation(lombok.Data.class);
        } catch (ClassNotFoundException e) {

        }


        String result = compilationUnit.toString();
        return result;
    }
}
