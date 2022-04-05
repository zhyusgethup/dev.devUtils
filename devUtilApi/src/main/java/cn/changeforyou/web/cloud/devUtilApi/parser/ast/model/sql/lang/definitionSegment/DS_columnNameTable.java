package cn.changeforyou.web.cloud.devUtilApi.parser.ast.model.sql.lang.definitionSegment;

import cn.changeforyou.web.cloud.devUtilApi.parser.ast.lang.SimpleLanguageDefinitionSegment;
import cn.changeforyou.web.cloud.devUtilApi.parser.ast.segment.LanguageSegment;

import java.util.List;

public class DS_columnNameTable implements SimpleLanguageDefinitionSegment {

    @Override
    public List<LanguageSegment> getSegments() {
        return null;
    }

    @Override
    public boolean isMust() {
        return false;
    }
}
