package cn.changeforyou.web.cloud.devUtilApi.parser.ast.lang;

import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.SeparatorChar;

/**
 * 语言定义片段
 */
public interface SimpleLanguageDefinitionSegment extends LanguageDefinitionSegment{

    /**
     * 是否可重复
     */
    default boolean isRepetition(){return false;};

    /**
     * 获取重复时的分隔符
     * @return
     */
    default SeparatorChar getRepetitionSeparatorChar() {return null;};




}
