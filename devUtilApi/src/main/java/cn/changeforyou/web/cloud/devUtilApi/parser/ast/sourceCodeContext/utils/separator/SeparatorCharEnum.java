package cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator;

import cn.changeforyou.web.cloud.devUtilApi.parser.ast.ParseCodeException;
import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.separatorContext.DefaultSeparatorListener;
import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.separatorContext.SeparatorContext;
import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.separatorContext.SeparatorContextListener;

/**
 * 记录分隔符和他们的分隔规则
 */
public enum SeparatorCharEnum implements SeparatorChar {

    r('\r', SeparatorCharType.NONE),
    n('\n', SeparatorCharType.NONE),
    t('\t', SeparatorCharType.NONE),
    s(' ', SeparatorCharType.NONE),

    semicolon(';', SeparatorCharType.LEFT),
    comma(',', SeparatorCharType.MIDDLE),

    NGL_1('#', SeparatorCharType.RIGHT),
    NGL_2('$', SeparatorCharType.RIGHT),

    /**
     * 用于标识其他头符号
     */
    VIRTUAL(SeparatorCharType.RIGHT, true),
    ;

    private char charValue;
    private SeparatorCharType type;
    private SeparatorContext separatorContext;
    private SeparatorContextListener separatorContextListener;
    private boolean virtual;

    SeparatorCharEnum(SeparatorCharType type, boolean virtual) {
        this('1', type, null, DefaultSeparatorListener.instance, virtual);
    }


    SeparatorCharEnum(char charValue, SeparatorCharType type) {
        this(charValue, type, null, DefaultSeparatorListener.instance, false);
    }

    SeparatorCharEnum(char charValue, SeparatorCharType type, SeparatorContext separatorContext) {
        this(charValue, type, separatorContext, DefaultSeparatorListener.instance, false);
    }

    SeparatorCharEnum(char charValue, SeparatorCharType type, SeparatorContextListener separatorContextListener) {
        this.charValue = charValue;
        this.type = type;
        this.separatorContextListener = separatorContextListener;
    }

    SeparatorCharEnum(char charValue, SeparatorCharType type, SeparatorContext separatorContext, SeparatorContextListener separatorContextListener, boolean virtual) {
        this.charValue = charValue;
        this.type = type;
        this.separatorContext = separatorContext;
        this.separatorContextListener = separatorContextListener;
        this.virtual = virtual;
    }

    public char getCharValue() {
        if (virtual) {
            throw new ParseCodeException("禁止获取虚拟分隔符的值");
        }
        return charValue;
    }

    public SeparatorCharType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "SeparatorCharEnum{" +
                "charValue=" + charValue +
                ", type=" + type +
                '}';
    }

    @Override
    public SeparatorContext getSeparatorContext() {
        return separatorContext;
    }

    @Override
    public SeparatorContextListener getSeparatorContextListener() {
        return separatorContextListener;
    }

    @Override
    public boolean isVirtual() {
        return virtual;
    }
}
