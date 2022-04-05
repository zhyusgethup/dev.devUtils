package cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.dictionary;

import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.BodyChar;
import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.SeparatorChar;

import java.util.ArrayList;
import java.util.List;

/**
 * 分隔符字典适配器
 */
public class SeparatorDictionaryAdaptor extends AbstractSeparatorDictionary{

    public SeparatorDictionaryAdaptor() {
        super();
    }

    @Override
    public List<SeparatorCharDictionary> getFatherSeparatorCharDictionary() {
        return new ArrayList<>(16);
    }

    @Override
    public List<SeparatorChar> getSeparatorChars() {
        return new ArrayList<>(16);
    }

    @Override
    public List<BodyChar> getBodyChars() {
        return new ArrayList<>(16);
    }
}
