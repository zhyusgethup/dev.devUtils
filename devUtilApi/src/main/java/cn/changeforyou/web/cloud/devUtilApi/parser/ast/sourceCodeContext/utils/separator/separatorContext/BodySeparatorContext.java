package cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.separatorContext;

import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.BodyChar;
import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.SeparatorChar;

/**
 * 体符的监听器
 */
public class BodySeparatorContext implements SeparatorContext {

    private BodyChar[] bodyChars;
    private SeparateRuntimeContext separateRuntimeContext;


    @Override
    public SeparatorChar[] getSeparatorChar() {
        return new SeparatorChar[0];
    }

    @Override
    public void setSeparatorChar(SeparatorChar[] separatorChar) {

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
        return new SeparatorChar[0];
    }

    @Override
    public void setBlankChars(SeparatorChar[] blankChars) {

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
