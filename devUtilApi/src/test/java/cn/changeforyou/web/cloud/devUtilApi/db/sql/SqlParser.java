package cn.changeforyou.web.cloud.devUtilApi.db.sql;

import com.jn.sqlhelper.jsqlparser.sqlparser.JSqlParser;
import com.jn.sqlhelper.jsqlparser.sqlparser.JSqlParserStatementWrapper;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.StatementVisitorAdapter;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import org.junit.jupiter.api.Test;

/**
 * @author zhyu
 * @version 1.0
 * @date 2022/4/11 15:41
 */

public class SqlParser {
    @Test
    public void test(){
        JSqlParser jSqlParser = new JSqlParser();
        JSqlParserStatementWrapper parse = jSqlParser.parse("CREATE TABLE `mdg_material_wastage_rate` (\n" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `maktl` varchar(255) DEFAULT NULL,\n" +
                "  `wuliangye_rate` decimal(3,2) DEFAULT NULL,\n" +
                "  `xiliejiu_rate` decimal(3,2) DEFAULT NULL,\n" +
                "  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,\n" +
                "  `update_time` datetime DEFAULT NULL,\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=493 DEFAULT CHARSET=utf8 COMMENT = 'mdgMaterials的损耗率表';");
        Statement statement = parse.get();
        StatementVisitorAdapter visitor = new StatementVisitorAdapter(){
            public void visit(CreateTable createTable) {
                for (ColumnDefinition columnDefinition : createTable.getColumnDefinitions()) {
                    System.out.println(columnDefinition);
                }
                Table table = createTable.getTable();
                System.out.println(table);
                System.out.println(createTable.getTableOptionsStrings());
            }
        };
        statement.accept(visitor);
    }
}
