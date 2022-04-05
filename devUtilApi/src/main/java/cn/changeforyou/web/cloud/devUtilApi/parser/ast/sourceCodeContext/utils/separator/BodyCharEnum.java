package cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator;

import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.separatorContext.DefaultSeparatorListener;
import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.separatorContext.SeparatorContext;
import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.separatorContext.SeparatorContextListener;

public enum BodyCharEnum implements BodyChar {

    left_brace('{', SeparatorCharType.RIGHT, '}'),
    right_brace('}', SeparatorCharType.LEFT, '{'),
    left_bracket('(', SeparatorCharType.RIGHT, ')'),
    right_bracket(')', SeparatorCharType.LEFT, '('),

    DOUBLE_QUOTE('"', SeparatorCharType.QUOTES, '"'),
    SINGLE_QUOTE('\'', SeparatorCharType.QUOTES, '\''),
    ;

    BodyCharEnum(char charValue, SeparatorCharType type, char oppositeBodyChar, char[] contextChars) {
        this(charValue, type, oppositeBodyChar, null, null,false, contextChars);
    }

    BodyCharEnum(char charValue, SeparatorCharType type, char oppositeBodyChar) {
        this(charValue, type, oppositeBodyChar, DefaultSeparatorListener.instance);
    }

    BodyCharEnum(char charValue, SeparatorCharType type, char oppositeBodyChar, SeparatorContextListener separatorContextListener) {
        this(charValue, type, oppositeBodyChar, null, separatorContextListener, false, null);
    }

    BodyCharEnum(char charValue, SeparatorCharType type, char oppositeBodyChar, SeparatorContext separatorContext, SeparatorContextListener separatorContextListener, boolean virtual, char[] contextChars) {
        this.charValue = charValue;
        this.type = type;
        this.oppositeBodyChar = oppositeBodyChar;
        this.separatorContext = separatorContext;
        this.separatorContextListener = separatorContextListener;
        this.virtual = virtual;
        this.contextChars = contextChars;
    }

    private char charValue;
    private SeparatorCharType type;
    private char oppositeBodyChar;
    private SeparatorContext separatorContext;
    private SeparatorContextListener separatorContextListener;
    private boolean virtual;
    private char[] contextChars;

    @Override
    public char getCharValue() {
        return charValue;
    }

    @Override
    public SeparatorCharType getType() {
        return type;
    }

    @Override
    public char[] getNoticedChars() {
        if(getOppositeChar() == getCharValue()) {
            return new char[]{getOppositeChar()};
        }else {
            return new char[]{getCharValue(), getOppositeChar()};
        }
    }

    @Override
    public char getOppositeChar() {
        return oppositeBodyChar;
    }

    @Override
    public BodyChar getOppositeBodyChar() {
        return getBodyChar(getOppositeChar());
    }

    public void setSeparatorContext(SeparatorContext separatorContext) {
        this.separatorContext = separatorContext;
    }

    /**
     * 随便体符
     *
     * @param v
     * @return
     */
    public static BodyChar getBodyChar(char v) {
        BodyCharEnum[] values = values();
        for (BodyCharEnum value : values) {
            if (value.getCharValue() == v) {
                return value;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "BodyCharEnum{" +
                "charValue=" + charValue +
                ", type=" + type +
                ", oppositeBodyChar=" + oppositeBodyChar +
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
