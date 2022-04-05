package cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator;

import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.separatorContext.SeparatorContext;
import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.separatorContext.SeparatorContextListener;

/**
 * 分隔符
 */
public interface SeparatorChar {
    char getCharValue();
    SeparatorCharType getType();
    String toString();

    /**
     * 不是每个分隔符都有自己独特的分隔上下文, 返回null有环境提供默认的分隔上下文
     * @return
     */
    SeparatorContext getSeparatorContext();

    /**
     * 获取分隔上下文监听器
     * @return
     */
    SeparatorContextListener getSeparatorContextListener();

    /**
     * 是否是虚拟的
     * @return
     */
    boolean isVirtual();

}
