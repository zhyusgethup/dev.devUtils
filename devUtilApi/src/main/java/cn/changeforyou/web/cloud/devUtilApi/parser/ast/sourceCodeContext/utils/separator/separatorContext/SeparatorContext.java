package cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.separatorContext;

import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.BodyChar;
import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.SeparatorChar;

/**
 * 分隔符上下文
 */
public interface SeparatorContext {

    SeparatorChar[] getSeparatorChar();

    void setSeparatorChar(SeparatorChar[] separatorChar);

    BodyChar[] getBodyChars();

    void setBodyChars(BodyChar[] bodyChars);

    SeparatorChar[] getBlankChars();

    void setBlankChars(SeparatorChar[] blankChars);

    SeparateRuntimeContext getSeparateRuntimeContext();

    void setSeparateRuntimeContext(SeparateRuntimeContext separateRuntimeContext);
}
