package cn.changeforyou.web.cloud.devUtilApi.parser.ast.segment;

/**
 * 关键词语言片段
 */
public interface KeyWordSegment extends LanguageSegment{

    String getKeyWord();

    boolean ignoreCase();
}
