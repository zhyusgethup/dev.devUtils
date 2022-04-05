package cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils;

import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.BodyChar;
import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.BodyCharEnum;
import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.SeparatorChar;
import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.dictionary.SeparatorCharDictionary;
import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.separatorContext.*;

import java.util.Arrays;

/**
 * 单文本分割器
 * 作用是配上字典后, 根据设置的不同的分隔属性, 分隔出不同的文本段
 */
public class SingleContextScanner {

    private char[] chars;
    int pos;
    private final SeparatorChar[] separatorChars;
    private final BodyChar[] bodyChars;
    private final SeparatorChar[] blanks;
    final SeparatorContextManager manager = new SeparatorContextManager();

    public SingleContextScanner(SeparatorCharDictionary dictionary) {
        this(null, dictionary);
    }

    public SingleContextScanner(String sourceCode, SeparatorCharDictionary dictionary) {
        if (null != sourceCode) {
            this.chars = sourceCode.toCharArray();
        }
        separatorChars = dictionary.getAllSeparatorChars();
        bodyChars = dictionary.getAllBodyChars();
        blanks = dictionary.getAllBlankChars();
        manager.initDefaultSeparatorContext(separatorChars, bodyChars, blanks);
    }


    public void scan(String sourceCode) {
        this.chars = sourceCode.toCharArray();
        pos = 0;
    }

    /**
     * 返回是否还有下一个
     *
     * @return
     */
    public boolean hasNext() {
        if (null == chars) {
            return false;
        }
        if (pos == chars.length) {
            return false;
        }
        for (int i = pos; i < chars.length; i++) {
            char v = chars[i];
            if (!isBlack(v)) {
                return true;
            }
        }
        return false;
    }

    public ScanResult generateResult(SeparateRuntimeContext runtimeContext) {
        String word = new String(chars, runtimeContext.getStart(), runtimeContext.getLength());
        ScanResult scanResult = new ScanResult();
        scanResult.setIndex(runtimeContext.getStart());
        BodyChar validBodyChar = runtimeContext.getValidBodyChar();
        if (null != validBodyChar && (validBodyChar != BodyCharEnum.DOUBLE_QUOTE || validBodyChar != BodyCharEnum.SINGLE_QUOTE)) {
            scanResult.setCanSeparate(true);
        }
        scanResult.setWord(word);
        return scanResult;
    }

    /**
     * 获取下一个下一级的语素
     *
     * @return
     */
    public ScanResult next() {
        SeparateRuntimeContext runtimeContext = resetRuntimeContext();
        SeparatorContext currentContext = manager.getAndSetDefaultSeparatorContext();
        currentContext.setSeparateRuntimeContext(runtimeContext);
        for (int i = runtimeContext.getStart(); i < chars.length; i++) {
            if (runtimeContext.isResetIndex()) {
                i = runtimeContext.getIndex();
                runtimeContext.setResetIndex(false);
            }
            char c = chars[i];

            runtimeContext.setIndex(i);
            if (manager.isNeedDeal(c)) {
                SeparatorChar separatorChar = runtimeContext.getListenedSeparatorChar();
                SeparatorContextListener listener = separatorChar.getSeparatorContextListener();
                SeparateResultTypeEnum resultTypeEnum = listener.apply(manager);
                switch (resultTypeEnum) {
                    case end:
                        pos = runtimeContext.getStart() + runtimeContext.getLength();
                        return generateResult(runtimeContext);
                    case unit:
                        pos++;
                        return generateResult(runtimeContext);
                    case start:
                    case ignore:
                }
            } else {
                if (manager.catchValue()) {
                    runtimeContext.setLength(runtimeContext.getLength() + 1);
                } else {
                    runtimeContext.setStart(runtimeContext.getStart() + 1);
                }
            }
        }

        if (manager.catchValue()) {
            pos = chars.length;
            return generateResult(runtimeContext);
        }
        return null;
    }

    /**
     * 重置分隔环境
     */
    private SeparateRuntimeContext resetRuntimeContext() {
        SeparateRuntimeContext runtimeContext = new SeparateRuntimeContext();
        runtimeContext.setLength(0);
        runtimeContext.setStart(pos);
        runtimeContext.setBodyDeep(0);
        runtimeContext.setChars(chars);
        return runtimeContext;
    }

    /**
     * 是否是空白符
     *
     * @param c
     * @return
     */
    private boolean isBlack(char c) {
        for (SeparatorChar blank : blanks) {
            if (c == blank.getCharValue()) {
                return true;
            }
        }
        return false;
    }

    public void printSetting() {
        System.out.println("注册的空字符:");
        System.out.println(Arrays.toString(this.blanks));
        System.out.println("注册的body字符:");
        System.out.println(Arrays.toString(this.bodyChars));
        System.out.println("注册的普通分隔字符:");
        System.out.println(Arrays.toString(separatorChars));
    }

    /**
     * 打印分割后的文本
     */
    public void printWordsWithSeparate() {
        while (hasNext()) {
            System.out.println(this.next());
        }
    }

}