package cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator;

/***
 * 分隔符类型
 */
public enum SeparatorCharType {
    /**
     * 不在任何一边
     */
    NONE,
    /**
     * 在左边
     */
    LEFT,
    /**
     * 在右边
     */
    RIGHT,
    /**
     * 在中间
     */
    MIDDLE,
    /**
     * 引号
     */
    QUOTES,
    /**
     * NGL表达式的开头
     */
    NGL,
}
