package cn.changeforyou.web.cloud.devUtilApi.parser.ast.model.sql.lang.definitionSegment;

import cn.changeforyou.web.cloud.devUtilApi.parser.ast.lang.SimpleLanguageDefinitionSegment;
import cn.changeforyou.web.cloud.devUtilApi.parser.ast.model.sql.segment.TableNameSegment;
import cn.changeforyou.web.cloud.devUtilApi.parser.ast.segment.LanguageSegment;

import java.util.Arrays;
import java.util.List;

public class DS_tableName implements SimpleLanguageDefinitionSegment {

    private TableNameSegment tableNameSegment;
    @Override
    public List<LanguageSegment> getSegments() {
        return Arrays.asList(tableNameSegment);
    }

    @Override
    public boolean isMust() {
        return true;
    }

}
