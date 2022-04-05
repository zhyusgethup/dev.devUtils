package cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.dictionary;

import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.BodyChar;
import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.SeparatorChar;
import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.SeparatorCharEnum;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 字典抽象模板类
 */
public abstract class AbstractSeparatorDictionary implements SeparatorCharDictionary {

    @Override
    public SeparatorChar[] getAllSeparatorChars() {
        List<SeparatorChar> separatorChars = new ArrayList<>(16);
        addFatherSeparatorChars(this, separatorChars);
        return separatorChars.toArray(new SeparatorChar[separatorChars.size()]);
    }

    @Override
    public BodyChar[] getAllBodyChars() {
        List<BodyChar> bodyChars = new ArrayList<>(16);
        addFatherBodyChars(this, bodyChars);
        return bodyChars.toArray(new BodyChar[bodyChars.size()]);
    }

    private void addFatherBodyChars(SeparatorCharDictionary father, List<BodyChar> bodyChars) {
        if (null == father) {
            return;
        }
        List<BodyChar> fathers = father.getBodyChars();
        if (CollectionUtils.isNotEmpty(fathers)) {
            bodyChars.addAll(fathers);
        }
        List<SeparatorCharDictionary> fatherSeparatorCharDictionary = father.getFatherSeparatorCharDictionary();
        if (null != fatherSeparatorCharDictionary) {
            for (SeparatorCharDictionary dictionary : fatherSeparatorCharDictionary) {
                addFatherBodyChars(dictionary, bodyChars);
            }
        }
    }

    private void addFatherSeparatorChars(SeparatorCharDictionary father, List<SeparatorChar> separatorChars) {
        if (null == father) {
            return;
        }
        List<SeparatorChar> fathers = father.getSeparatorChars();
        if (CollectionUtils.isNotEmpty(fathers)) {
            separatorChars.addAll(fathers);
        }
        List<SeparatorCharDictionary> fatherSeparatorCharDictionary = father.getFatherSeparatorCharDictionary();
        if (null != fatherSeparatorCharDictionary) {
            for (SeparatorCharDictionary dictionary : fatherSeparatorCharDictionary) {
                addFatherSeparatorChars(dictionary, separatorChars);
            }
        }
    }

    @Override
    public SeparatorChar[] getAllBlankChars() {
        SeparatorChar[] separatorChars = getAllSeparatorChars();
        List<SeparatorChar> list = new ArrayList<>(separatorChars.length);
        for (SeparatorChar separatorChar : separatorChars) {
            if (separatorChar == SeparatorCharEnum.r || separatorChar == SeparatorCharEnum.n || separatorChar == SeparatorCharEnum.t ||
                    separatorChar == SeparatorCharEnum.s) {
                list.add(separatorChar);
            }
        }
        return list.toArray(new SeparatorChar[list.size()]);
    }
}
