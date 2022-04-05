package cn.changeforyou.web.cloud.devUtilApi.parser.ast.model.sql.lang.definitionSegment;

import cn.changeforyou.web.cloud.devUtilApi.parser.ast.lang.SimpleLanguageDefinitionSegment;
import cn.changeforyou.web.cloud.devUtilApi.parser.ast.model.sql.segment.KeywordSegmentEnum;
import cn.changeforyou.web.cloud.devUtilApi.parser.ast.segment.IgnoreCaseKeyWordSegment;
import cn.changeforyou.web.cloud.devUtilApi.parser.ast.segment.LanguageSegment;

import java.util.Arrays;
import java.util.List;

public class DS_InsertInto implements SimpleLanguageDefinitionSegment {

    @Override
    public List<LanguageSegment> getSegments() {
        return Arrays.asList(new IgnoreCaseKeyWordSegment[]{KeywordSegmentEnum.S_insert, KeywordSegmentEnum.S_into});
    }

    @Override
    public boolean isMust() {
        return true;
    }

}
