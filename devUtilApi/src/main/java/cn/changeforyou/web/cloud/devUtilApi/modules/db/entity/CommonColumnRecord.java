package cn.changeforyou.web.cloud.devUtilApi.modules.db.entity;

import lombok.Data;

/**
 * 常用行记录
 * @author zhyu
 * @version 1.0
 * @date 2022/2/16 11:10
 */
@Data
public class CommonColumnRecord {
    /**
     * 非空
     */
    private Boolean isNotNull;
    /**
     * 是否是主键
     */
    private Boolean isPrimaryKey;
    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 是否唯一
     */
    private Boolean isUnique;

    /**
     * 注释
     */
    private String comment;
}
