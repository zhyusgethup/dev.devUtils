package cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.separatorContext;

import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.BodyChar;
import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.BodyCharEnum;
import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.SeparatorChar;
import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.SeparatorCharEnum;

/**
 * 分页上下文管理器
 */
public class SeparatorContextManager {

    private SeparatorContext defaultContext;

    private SeparatorContext context;

    private BodySeparatorContext bodyCharContext = new BodySeparatorContext();

    public SeparatorContext getContext(){
        return context;
    }

    public void switchBodyCharContext(BodyChar bodyChar){
        char[] noticedChars = bodyChar.getNoticedChars();
        BodyChar[] bodyChars = new BodyChar[noticedChars.length];
        for (int i = 0; i < noticedChars.length; i++) {
            bodyChars[i] = BodyCharEnum.getBodyChar(noticedChars[i]);
        }
        bodyCharContext.setBodyChars(bodyChars);
        bodyCharContext.setSeparateRuntimeContext(context.getSeparateRuntimeContext());
        context = bodyCharContext;
    }

    public SeparatorContext getAndSetDefaultSeparatorContext(){
        context = defaultContext;
        return defaultContext;
    }

    public void initDefaultSeparatorContext(SeparatorChar[] separatorChars, BodyChar[] bodyChars, SeparatorChar[] blanks) {
        defaultContext = new DefaultSeparatorContext();
        defaultContext.setSeparatorChar(separatorChars);
        defaultContext.setBodyChars(bodyChars);
        defaultContext.setBlankChars(blanks);
    }

    public boolean catchValue() {
        return context.getSeparateRuntimeContext().catchValue();
    }

    /**
     * 改方法只负责向上推荐, 该字符是否值得注意
     *
     * @return
     */
    public boolean isNeedDeal(char c) {
        context.getSeparateRuntimeContext().setListenedSeparatorChar(null);
        SeparatorChar separator = null;
        if (catchValue()) {
            separator = getSeparator(c);
            if (null != separator) {
                context.getSeparateRuntimeContext().setListenedSeparatorChar(separator);
                return true;
            } else if ((separator = getBody(c)) != null) {
                context.getSeparateRuntimeContext().setListenedSeparatorChar(separator);
                return true;
            }
        } else {
            separator = getBlack(c);
            if (null == separator) {
                if((separator = getSeparator(c)) != null) {
                    context.getSeparateRuntimeContext().setListenedSeparatorChar(separator);
                }else if ((separator = getBody(c)) != null) {
                    context.getSeparateRuntimeContext().setListenedSeparatorChar(separator);
                }else{
                    context.getSeparateRuntimeContext().setListenedSeparatorChar(SeparatorCharEnum.VIRTUAL);
                }
                return true;
            }
        }
        return false;
    }


    public SeparatorChar getBlack(char c) {
        return getSeparator(c, context.getBlankChars());
    }


    public SeparatorChar getSeparator(char c) {
        SeparatorChar separator = getSeparator(c, context.getSeparatorChar());
        if(null == separator){
            return getBody(c);
        }
        return separator;
    }

    public SeparatorChar getBody(char c) {
        return getSeparator(c, context.getBodyChars());
    }

    /**
     * 是否在该分隔符里
     *
     * @param c
     * @param separatorChars
     * @return
     */
    private boolean isIn(char c, SeparatorChar[] separatorChars) {
        for (int i = 0; i < separatorChars.length; i++) {
            if (c == separatorChars[i].getCharValue()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 返回分隔符
     *
     * @param c
     * @param separatorChars
     * @return
     */
    public SeparatorChar getSeparator(char c, SeparatorChar[] separatorChars) {
        if(separatorChars == null) {
            return null;
        }
        for (int i = 0; i < separatorChars.length; i++) {
            if (c == separatorChars[i].getCharValue()) {
                return separatorChars[i];
            }
        }
        return null;
    }
}
