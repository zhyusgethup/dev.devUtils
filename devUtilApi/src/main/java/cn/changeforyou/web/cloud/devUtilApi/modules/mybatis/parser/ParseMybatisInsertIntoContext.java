package cn.changeforyou.web.cloud.devUtilApi.modules.mybatis.parser;

import lombok.Data;

import java.util.Scanner;

/**
 * 解析mybatis的上下文
 */
@Data
public class ParseMybatisInsertIntoContext {
    /**
     * 0:头, 1:表名, 2:(字段名,), 3: VALUES 等关键字, 4:(插入值)
     */
    private int state;
    private String head;
    private String tableName;
    private String fields;
    private String values;

    private Scanner sc;
    private String src;

    public enum InsertIntoSegment {
        HEAD, TABLE_NAME, FIELDS, VALUES
    }

    public void add(String words, InsertIntoSegment segment) {
        switch (segment) {
            case HEAD:
                if(null == this.head) {
                    head = words;
                }else {
                    head = " " + words;
                }
                break;
            case TABLE_NAME:
                tableName = words;
                break;
            case FIELDS:

        }
    }


}
