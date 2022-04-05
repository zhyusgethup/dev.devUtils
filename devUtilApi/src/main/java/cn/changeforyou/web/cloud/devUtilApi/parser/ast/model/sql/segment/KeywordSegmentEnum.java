package cn.changeforyou.web.cloud.devUtilApi.parser.ast.model.sql.segment;

import cn.changeforyou.web.cloud.devUtilApi.parser.ast.segment.IgnoreCaseKeyWordSegment;

public interface KeywordSegmentEnum {

    IgnoreCaseKeyWordSegment S_insert = new IgnoreCaseKeyWordSegment("insert");
    IgnoreCaseKeyWordSegment S_into = new IgnoreCaseKeyWordSegment("into");
}
