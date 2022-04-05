package cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext;

/**
 * 解析状态
 */
public enum ParseStatus {

    /**
     *  还没开始解析
     */
    have_not_start_parsing,

    /**
     * 解析结束
     */
    parse_end,

    /**
     * 不需要解析
     */
    not_need_parse,

}
