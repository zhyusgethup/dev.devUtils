package cn.changeforyou.web.cloud.devUtilApi.parser.ast.lang;

import cn.changeforyou.web.cloud.devUtilApi.parser.ast.segment.LanguageSegment;
import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.SeparatorChar;

import java.util.List;

/**
 * 语言定义片段
 */
public interface LanguageDefinitionSegment{

    /**s
     * 语言片段
     */
     List<LanguageSegment> getSegments();

    /**
     * 是否必须
     */
    boolean isMust();

    /**
     * 是否可重复
     */
    boolean isRepetition();

    /**
     * 获取重复时的分隔符
     * @return
     */
    SeparatorChar getRepetitionSeparatorChar();




}
