package cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.dictionary;

import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.SeparatorChar;

import java.util.Arrays;
import java.util.List;

import static cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.SeparatorCharEnum.*;

/**
 * ngl表达式字典
 */
public class NglSeparatorCharDictionary extends SeparatorDictionaryAdaptor {
    private NglSeparatorCharDictionary() {
    }

    public static final NglSeparatorCharDictionary instance = new NglSeparatorCharDictionary();

    @Override
    public List<SeparatorChar> getSeparatorChars() {
        return Arrays.asList(NGL_1, NGL_2);
    }
}
