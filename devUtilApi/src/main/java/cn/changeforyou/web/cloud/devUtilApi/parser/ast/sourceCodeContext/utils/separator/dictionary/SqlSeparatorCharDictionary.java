package cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.dictionary;

import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.BodyChar;
import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.SeparatorChar;

import java.util.Arrays;
import java.util.List;

import static cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.BodyCharEnum.*;
import static cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.SeparatorCharEnum.*;

/**
 * sql分隔符字典
 */
public class SqlSeparatorCharDictionary extends SeparatorDictionaryAdaptor {

    private SqlSeparatorCharDictionary() {
    }

    public static final SqlSeparatorCharDictionary instance = new SqlSeparatorCharDictionary();

    @Override
    public List<SeparatorCharDictionary> getFatherSeparatorCharDictionary() {
        return Arrays.asList(BlankSeparatorCharDictionary.instance);
    }

    @Override
    public List<SeparatorChar> getSeparatorChars() {
        return Arrays.asList(semicolon, comma);
    }

    @Override
    public List<BodyChar> getBodyChars() {
        return Arrays.asList(left_bracket, right_bracket, DOUBLE_QUOTE, SINGLE_QUOTE);
    }
}
