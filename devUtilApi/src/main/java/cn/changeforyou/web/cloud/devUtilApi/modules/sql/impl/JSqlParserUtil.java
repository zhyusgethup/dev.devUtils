package cn.changeforyou.web.cloud.devUtilApi.modules.sql.impl;

import cn.changeforyou.utils.string.StringUtils;
import cn.changeforyou.web.cloud.devUtilApi.parser.java.JavaEntity;
import cn.changeforyou.web.cloud.devUtilApi.parser.java.JavaEntityDbInfo;
import cn.changeforyou.web.cloud.devUtilApi.parser.java.JavaEntityField;
import cn.changeforyou.web.utils.NameUtils;
import com.jn.sqlhelper.jsqlparser.sqlparser.JSqlParser;
import com.jn.sqlhelper.jsqlparser.sqlparser.JSqlParserStatementWrapper;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.StatementVisitorAdapter;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhyu
 * @version 1.0
 * @date 2022/4/11 17:56
 */

/**
 * JSqlParser这库的工具
 */
public class JSqlParserUtil {

    public static final String COMMENT = "COMMENT";

    /**
     * 从模型中获取表注释
     * @param createTable
     * @return
     */
    public static String getComment(CreateTable createTable) {
        List<?> tableOptionsStrings = createTable.getTableOptionsStrings();
        if (CollectionUtils.isNotEmpty(tableOptionsStrings)) {
            int commentContentIndex = -1;
            for (int i = 0; i < tableOptionsStrings.size(); i++) {
                Object item = tableOptionsStrings.get(i);
                if (item instanceof String) {
                    String str = (String) item;
                    if (COMMENT.equalsIgnoreCase(str)) {
                        commentContentIndex = i + 2;
                        break;
                    }
                }
            }
            if (commentContentIndex != -1) {
                return (String) tableOptionsStrings.get(commentContentIndex);
            }
        }
        return null;
    }

    /**
     * 从模型中获取字段注释
     * @param columnDefinition
     * @return
     */
    public static String getComment(ColumnDefinition columnDefinition) {
        List<String> columnSpecStrings = columnDefinition.getColumnSpecStrings();
        if(CollectionUtils.isNotEmpty(columnSpecStrings)) {
            int index = -1;
            for (int i = 0; i < columnSpecStrings.size(); i++) {
                if(COMMENT.equalsIgnoreCase(columnSpecStrings.get(i))){
                    index = i + 1;
                    break;
                }
            }
            if(index != -1){
                return columnSpecStrings.get(index);
            }
        }
        return null;
    }

    /**
     * 使用jsqlParser工具把ddl转换成javaEntity实例
     * @param ddl
     * @return
     */
    public static JavaEntity parseDdl2JavaEntityObject(String ddl) {
        JSqlParser jSqlParser = new JSqlParser();
        JSqlParserStatementWrapper parse = jSqlParser.parse(ddl);
        Statement statement = parse.get();
        final JavaEntity javaEntity = new JavaEntity();
        StatementVisitorAdapter visitor = new StatementVisitorAdapter(){
            public void visit(CreateTable createTable) {
                List<JavaEntityField> entityFields = new ArrayList<>();
                javaEntity.setJavaEntityFields(entityFields);

                String name = filterQuotation(createTable.getTable().getName());
                JavaEntityDbInfo dbInfo = new JavaEntityDbInfo();
                javaEntity.setName(NameUtils.getCamelName(name));
                dbInfo.setTableName(name);
                javaEntity.setDbInfo(dbInfo);
                javaEntity.setComment(filterQuotation(JSqlParserUtil.getComment(createTable)));

                for (ColumnDefinition columnDefinition : createTable.getColumnDefinitions()) {
                    JavaEntityField entityField = new JavaEntityField();
                    entityFields.add(entityField);
                    entityField.setName(NameUtils.getCamelName(filterQuotation(columnDefinition.getColumnName())));
                    entityField.setComment(filterQuotation(JSqlParserUtil.getComment(columnDefinition)));
                    entityField.setDataType(columnDefinition.getColDataType().getDataType());
                }
            }
        };
        statement.accept(visitor);
        return javaEntity;
    }

    public static String filterQuotation(String name) {
        return StringUtils.isBlank(name)?null:name.replaceAll("`|'","");
    }
}
