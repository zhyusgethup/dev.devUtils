package cn.changeforyou.web.cloud.devUtilApi.parser.java;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * java实体类的字段
 */
@Data
@ToString
@EqualsAndHashCode
public class JavaEntityField {

    private String name;
    private String javaType;
    private Class javaClass;
    private String comment;
    private JavaFieldDbInfo javaFieldDbInfo;

    public JavaEntityField() {
        javaFieldDbInfo = new JavaFieldDbInfo();
    }
}
