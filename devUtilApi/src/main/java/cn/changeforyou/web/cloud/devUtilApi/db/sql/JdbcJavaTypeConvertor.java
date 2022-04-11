package cn.changeforyou.web.cloud.devUtilApi.db.sql;

import cn.changeforyou.base.exception.ExceptionFactory;
import cn.changeforyou.web.cloud.devUtilApi.exception.DevApiExceptionEnum;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

/**
 * jdbc和java类型的转换器
 *
 * @author zhyu
 * @version 1.0
 * @date 2022/2/15 17:12
 */
@Slf4j
public class JdbcJavaTypeConvertor {
    private static Map<String, String> java2JdbcMap = new HashMap<>(32);
    private static Map<String, String> simpleName2FullNameMap = new HashMap<>(16);
    private static Map<String, Class> jdbc2JavaMap = new HashMap<>(32);

    static {
        java2JdbcMap.put("boolean", "bit");
        java2JdbcMap.put("int", "int");
        java2JdbcMap.put("short", "int");
        java2JdbcMap.put("long", "bigint");
        java2JdbcMap.put("float", "float");
        java2JdbcMap.put("double", "double");
        java2JdbcMap.put("char", "varchar(255)");
        java2JdbcMap.put("java.lang.String", "varchar(255)");
        java2JdbcMap.put("java.lang.Integer", "int");
        java2JdbcMap.put("java.lang.byte[]", "blob");
        java2JdbcMap.put("java.lang.Boolean", "bit");
        java2JdbcMap.put("java.lang.Long", "integer unsigned");
        java2JdbcMap.put("java.math.BigInteger", "bigint unsigned");
        java2JdbcMap.put("java.lang.Float", "float");
        java2JdbcMap.put("java.lang.Double", "float");
        java2JdbcMap.put("java.sql.Date", "datetime");
        java2JdbcMap.put("java.sql.Time", "time");
        java2JdbcMap.put("java.sql.Timestamp", "datetime");
        java2JdbcMap.put("java.time.LocalDateTime", "datetime");
        java2JdbcMap.put("java.util.Date", "datetime");
        java2JdbcMap.put("java.lang.Byte", "tinyint");

        jdbc2JavaMap.put("int",Integer.class);
        jdbc2JavaMap.put("mediumint",Integer.class);
        jdbc2JavaMap.put("tinyint",Integer.class);
        jdbc2JavaMap.put("bigint",Long.class);
        jdbc2JavaMap.put("char",String.class);
        jdbc2JavaMap.put("varchar",String.class);
        jdbc2JavaMap.put("longtext",String.class);
        jdbc2JavaMap.put("text",String.class);
        jdbc2JavaMap.put("bit",String.class);
        jdbc2JavaMap.put("text",Boolean.class);
        jdbc2JavaMap.put("blob",byte[].class);
        jdbc2JavaMap.put("time", LocalTime.class);
        jdbc2JavaMap.put("timestamp", LocalDateTime.class);
        jdbc2JavaMap.put("datetime",LocalDateTime.class);
        jdbc2JavaMap.put("date", LocalDate.class);


        simpleName2FullNameMap.put("Integer", "java.lang.Integer");
        simpleName2FullNameMap.put("String", "java.lang.String");
        simpleName2FullNameMap.put("Boolean", "java.lang.Boolean");
        simpleName2FullNameMap.put("LocalDateTime", "java.time.LocalDateTime");
    }

    public static String javaType2JdbcType(String javaType) {
        if (simpleName2FullNameMap.containsKey(javaType)) {
            javaType = simpleName2FullNameMap.get(javaType);
        }
        if (java2JdbcMap.containsKey(javaType)) {
            return java2JdbcMap.get(javaType);
        } else {
            log.error("javaType: {}", javaType);
            throw ExceptionFactory.jsonException(DevApiExceptionEnum.DESIGN_NOT_ENOUGH);
        }
    }

    public static Class jdbcType2JavaType(String jdbcType) {
        if (jdbc2JavaMap.containsKey(jdbcType)) {
            return jdbc2JavaMap.get(jdbcType);
        } else {
            log.error("jdbcType: {}", jdbcType);
            throw ExceptionFactory.jsonException(DevApiExceptionEnum.DESIGN_NOT_ENOUGH);
        }
    }
}
