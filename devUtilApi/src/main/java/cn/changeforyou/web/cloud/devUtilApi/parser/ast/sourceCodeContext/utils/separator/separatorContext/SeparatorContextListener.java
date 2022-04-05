package cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.separatorContext;

/**
 * 分隔上下文监听器, 给引号,ngl表达式等使用
 */
public interface SeparatorContextListener {

    /**
     *
     * @return -1 表示不分隔, 别的代表重置的index位置
     */
    SeparateResultTypeEnum apply(SeparatorContextManager context);
}
