package cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.separatorContext;

import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.BodyChar;
import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.SeparatorChar;
import lombok.Data;

/**
 * 分隔上下文
 * 分隔上下文不同于分隔符上下文,  分隔上下文和分隔符无关
 */
@Data
public class SeparateRuntimeContext {

    /**
     * 风格起点
     */
    int start;
    /**
     * 已记录长度
     */
    int length;

    /**
     * 当前索引
     */
    int index;

    /**
     * 分隔结果
     */
    private SeparateResultTypeEnum resultType;

    /**
     * 是否重置索引
     */
    boolean resetIndex;

    /**
     * 待分隔的字符数组
     */
    private char[] chars;

    /**
     * 当期产生效果的体符
     */
    private BodyChar validBodyChar;

    /**
     * 本次监听到的分隔符
     */
    private SeparatorChar listenedSeparatorChar;

    /**
     * 体符深度 处理 {{ 的问题, 第一个}返回后不能算结束, 必须和开始符号出现的个数一致才算结束
     */
    private int bodyDeep;

    /**
     * 是否取到值
     * @return
     */
    public boolean catchValue() {
        return length > 0;
    }
}
