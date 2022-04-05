package cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.dictionary;

import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.BodyChar;
import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.SeparatorChar;

import java.util.List;

public interface JustBodyCharDictionary extends SeparatorCharDictionary{
    default List<SeparatorCharDictionary> getFatherSeparatorCharDictionary() {
        return null;
    }

    default List<SeparatorChar> getSeparatorChars() {
        return null;
    }

    /**
     * 获取全部分隔符
     * @return
     */
    default SeparatorChar[] getAllSeparatorChars(){
        return new SeparatorChar[0];
    }

    /**
     * 获取全部body分隔符
     * @return
     */
    default BodyChar[] getAllBodyChars(){
        return new BodyChar[0];
    }

    /**
     * 获取空白符
     * @return
     */
    default SeparatorChar[] getAllBlankChars(){
        return new SeparatorChar[0];
    }
}
