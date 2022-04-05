package cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.separatorContext;

import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.BodyChar;
import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.SeparatorChar;

public class DefaultSeparatorContext implements SeparatorContext {
    /**
     * 分隔符
     */
    protected SeparatorChar[] separatorChar;
    /**
     * 体符
     */
    private BodyChar[] bodyChars;
    /**
     * 空白符
     */
    private SeparatorChar[] blankChars;

    private SeparateRuntimeContext separateRuntimeContext;

    @Override
    public SeparatorChar[] getSeparatorChar() {
        return separatorChar;
    }

    @Override
    public void setSeparatorChar(SeparatorChar[] separatorChar) {
        this.separatorChar = separatorChar;
    }

    @Override
    public BodyChar[] getBodyChars() {
        return bodyChars;
    }

    @Override
    public void setBodyChars(BodyChar[] bodyChars) {
        this.bodyChars = bodyChars;
    }

    @Override
    public SeparatorChar[] getBlankChars() {
        return blankChars;
    }

    @Override
    public void setBlankChars(SeparatorChar[] blankChars) {
        this.blankChars = blankChars;
    }

    @Override
    public SeparateRuntimeContext getSeparateRuntimeContext() {
        return separateRuntimeContext;
    }

    @Override
    public void setSeparateRuntimeContext(SeparateRuntimeContext separateRuntimeContext) {
        this.separateRuntimeContext = separateRuntimeContext;
    }
}
