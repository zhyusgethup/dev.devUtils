package cn.changeforyou.web.cloud.devUtilApi.modules.db.entity;

import cn.changeforyou.web.cloud.webBase.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * java对关系型数据库映射表
 * @author zhyu
 * @version 1.0
 * @date 2022/2/16 10:37
 */
@Data
@TableName("Java_2_rdbms_map")
public class Java2RDBMSMap extends BaseEntity {
    /**
     * java类型
     */
    private String javaType;

    /**
     * 数据库类型
     */
    private String dbType;

    /**
     * 数据库类型长度
     */
    private Integer dbTypeLength;

    /**
     * 数据库类型小数点长度
     */
    private String dbTypeRadixPointLength;

    /**
     * 简单类名
     */
    private String simpleClassName;

    /**
     * 数据库信息
     */
    private int dbCode;
    /**
     * 数据库版本号
     */
    private String dbVersion;
}
