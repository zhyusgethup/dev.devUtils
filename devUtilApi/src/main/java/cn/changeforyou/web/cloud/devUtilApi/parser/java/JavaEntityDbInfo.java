package cn.changeforyou.web.cloud.devUtilApi.parser.java;

import lombok.Data;
import lombok.ToString;

/**
 * java实体类的数据库信息
 */
@Data
@ToString
public class JavaEntityDbInfo {

    /**
     *  表名
     */
    private String tableName;
}
