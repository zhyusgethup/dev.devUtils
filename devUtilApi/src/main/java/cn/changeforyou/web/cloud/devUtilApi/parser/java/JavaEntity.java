package cn.changeforyou.web.cloud.devUtilApi.parser.java;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * java的entity对象
 */
@Data
@ToString
public class JavaEntity {
    public JavaEntity() {
        this.dbInfo = new JavaEntityDbInfo();
        this.javaEntityFields = new ArrayList<>();
        this.fatherNames = new ArrayList<>(6);
    }

    private String name;
    private List<String> fatherNames;
    private String comment;
    private JavaEntityDbInfo dbInfo;
    private List<JavaEntityField> javaEntityFields;
    private JavaEntityField primaryKey;
}

