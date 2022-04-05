package cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.dictionary;

import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.SeparatorChar;
import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.SeparatorCharEnum;

import java.util.Arrays;
import java.util.List;

/**
 * 空白符字典
 */
public class BlankSeparatorCharDictionary extends SeparatorDictionaryAdaptor {

    public static final BlankSeparatorCharDictionary instance = new BlankSeparatorCharDictionary();

    private BlankSeparatorCharDictionary() {
    }

    @Override
    public List<SeparatorChar> getSeparatorChars() {
        return Arrays.asList(
                SeparatorCharEnum.r,
                SeparatorCharEnum.n,
                SeparatorCharEnum.t,
                SeparatorCharEnum.s
        );
    }
}
