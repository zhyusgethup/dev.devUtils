package cn.changeforyou.web.cloud.devUtilApi.db.sql;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author zhyu
 * @version 1.0
 * @date 2022/2/15 21:06
 */
class SqlFormatUtilTest {

    @Test
    void formatDDL() {
        String ddl = "drop table if exists org_info;CREATE TABLE `org_info`(`org_code`    varchar(255)   DEFAULT NULL" +
                " COMMENT `组织编码`,`org_type`    bit   DEFAULT NULL COMMENT `组织类型, 1:系统类型`,`org_name`    varchar(255)   DEFAULT NULL COMMENT `组织名`,`parent_org_id`    int   DEFAULT NULL COMMENT `父层级id`,`id`    int   NOT NULL,`del`    bit   DEFAULT NULL,`available`    bit   DEFAULT NULL,`create_time`    datetime   DEFAULT NULL,`update_time`    datetime   DEFAULT NULL,`create_user_name`    varchar(255)   DEFAULT NULL,`create_user_id`    int   DEFAULT NULL,`update_user_id`    int   DEFAULT NULL,`update_user_name`    varchar(255)   DEFAULT NULL,PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci comment `组织架构表`;";

        SqlFormatUtil sqlFormatUtil = new SqlFormatUtil();
        String s = sqlFormatUtil.formatDDL(ddl);
        System.out.println(s);
    }
}