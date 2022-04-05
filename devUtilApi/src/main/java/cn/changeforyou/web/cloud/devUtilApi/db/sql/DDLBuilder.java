package cn.changeforyou.web.cloud.devUtilApi.db.sql;

import cn.changeforyou.utils.string.StringUtils;
import cn.changeforyou.web.cloud.devUtilApi.parser.java.JavaEntity;
import cn.changeforyou.web.cloud.devUtilApi.parser.java.JavaEntityField;

/**
 * DDL语句构建起
 *
 * @author zhyu
 * @version 1.0
 * @date 2022/2/15 16:01
 */
public class DDLBuilder {

    public static final String separator = "`";

    public static String buildDDL(JavaEntity javaEntity) {
/***
 * drop table if exists user;
 * CREATE TABLE `user`
 * (
 *     `id`               int NOT NULL AUTO_INCREMENT,
 *     `nickname`         varchar(50) COLLATE utf8mb4_general_ci                        DEFAULT NULL,
 *     `phone`            char(20) COLLATE utf8mb4_general_ci                           DEFAULT NULL,
 *     `type`             tinyint(1) DEFAULT NULL COMMENT '类型, 1:默认用户',
 *     `password`         varchar(255) COLLATE utf8mb4_general_ci                       DEFAULT NULL,
 *     `username`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL,
 *     `memo`             varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
 *     `ref_id`           varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '关联id(默认用户没有关联id)',
 *     `del`              bit(1)                                                        DEFAULT 0,
 *     `available`        bit(1)                                                        DEFAULT 1,
 *     `create_time`      datetime                                                      DEFAULT CURRENT_TIMESTAMP,
 *     `update_time`      datetime                                                      DEFAULT CURRENT_TIMESTAMP,
 *     `create_user_name` varchar(50) COLLATE utf8mb4_general_ci                        DEFAULT NULL,
 *     `create_user_id`   int                                                           DEFAULT NULL,
 *     `update_user_id`   int                                                           DEFAULT NULL,
 *     `update_user_name` varchar(50) COLLATE utf8mb4_general_ci                        DEFAULT NULL,
 *     PRIMARY KEY (`id`)
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci comment '用户表';
 */
        StringBuilder sb = new StringBuilder();
        String tableName = javaEntity.getDbInfo().getTableName();
        String comment = javaEntity.getComment();
        JavaEntityField primaryKey = javaEntity.getPrimaryKey();
        sb.append("drop table if exists ").append(tableName).append(";");
        sb.append("CREATE TABLE ").append(tableName).append("(");
        for (JavaEntityField javaEntityField : javaEntity.getJavaEntityFields()) {
            String fieldComment = javaEntityField.getComment();
            String columnName = javaEntityField.getJavaFieldDbInfo().getColumnName();
            if (StringUtils.isBlank(columnName)) {
                columnName = StringUtils.camelCase2UnderScoreCase(javaEntityField.getName());
            }
            String jdbcType = JdbcJavaTypeConvertor.javaType2JdbcType(javaEntityField.getJavaType());
            sb.append(columnName).append("    ").append(jdbcType).append("   ");
            if (javaEntityField == primaryKey) {
                sb.append("NOT NULL AUTO_INCREMENT");
            } else {
                if (columnName.equals("del") && jdbcType.equals("bit")) {
                    sb.append("DEFAULT 0");
                } else {
                    sb.append("DEFAULT NULL");
                }
            }

            if (StringUtils.isNotBlank(fieldComment)) {
                sb.append(" ").append("COMMENT ").append("\'").append(fieldComment).append("\'");
            }
            sb.append(",");
        }


        sb.append("PRIMARY KEY (").append(separator).append(StringUtils.camelCase2UnderScoreCase(primaryKey.getJavaFieldDbInfo().getColumnName())).append(separator).append(
                ")");
        sb.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci comment ").append("'").append(comment).append("'").append(";");

        return sb.toString();
    }
}
