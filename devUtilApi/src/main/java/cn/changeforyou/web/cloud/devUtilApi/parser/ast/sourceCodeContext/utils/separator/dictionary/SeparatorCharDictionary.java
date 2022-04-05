package cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.dictionary;

import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.BodyChar;
import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.SeparatorChar;

import java.util.List;

/**
 * 分隔符字典
 */
public interface SeparatorCharDictionary {

    List<SeparatorCharDictionary> getFatherSeparatorCharDictionary();

    List<SeparatorChar> getSeparatorChars();

    List<BodyChar> getBodyChars();

    /**
     * 获取全部分隔符
     * @return
     */
    SeparatorChar[] getAllSeparatorChars();

    /**
     * 获取全部body分隔符
     * @return
     */
    BodyChar[] getAllBodyChars();

    /**
     * 获取空白符
     * @return
     */
    SeparatorChar[] getAllBlankChars();

}
