package cn.changeforyou.web.cloud.devUtilApi.modules.db.entity;

import cn.changeforyou.web.cloud.webBase.model.BaseEntity;
import lombok.Data;

/**
 * java对关系型数据库映射表
 * @author zhyu
 * @version 1.0
 * @date 2022/2/16 10:37
 */
@Data
public class Java2RDBMSMapCondition extends BaseEntity {
    /**
     * java类型
     */
    private String javaType;

    /**
     * 数据库类型
     */
    private String dbType;

    /**
     * 简单类名
     */
    private String simpleClassName;

    /**
     * 数据库信息
     */
    private int dbCode;

    private String dbVersion;
}
