package cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.separatorContext;

import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.BodyChar;
import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.BodyCharEnum;
import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.SeparatorChar;
import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.SeparatorCharType;

/**
 * 默认的分隔环境监听器实现
 */
public class DefaultSeparatorListener implements SeparatorContextListener {

    public static final DefaultSeparatorListener instance = new DefaultSeparatorListener();

    @Override
    public SeparateResultTypeEnum apply(SeparatorContextManager manager) {
        SeparatorContext context = manager.getContext();
        boolean catchValue = context.getSeparateRuntimeContext().catchValue();
        SeparateRuntimeContext runtime = context.getSeparateRuntimeContext();
        SeparatorChar separatorChar = runtime.getListenedSeparatorChar();
        SeparatorCharType type = separatorChar.getType();
        boolean apply = false;
        if (catchValue) {
            apply = dealCaughtValue(runtime, separatorChar, apply);
        } else {
            apply = dealCatchingValue(runtime, separatorChar, manager);
        }
        return dealLength(catchValue, runtime, type, apply);
    }

    protected SeparateResultTypeEnum dealLength(boolean catchValue, SeparateRuntimeContext runtime, SeparatorCharType type, boolean apply) {
        if (apply) {
            int length = runtime.getLength();
            if (catchValue) {
                //后的情况
                switch (type) {
                    case QUOTES:
                        runtime.setLength(length + 1);
                        return SeparateResultTypeEnum.end;
                    case MIDDLE:
                        //有抓扑值且居中, 比如 123 , 123 在要逗号的分隔环境时的逗号
                        return SeparateResultTypeEnum.end;
                    case LEFT:
                        runtime.setLength(length + 1);
                        return SeparateResultTypeEnum.end;
                    case RIGHT:
                        return SeparateResultTypeEnum.end;
                    case NONE:
                        //不归己
                        return SeparateResultTypeEnum.end;
                    case NGL:
                        runtime.setLength(length + 1);
                        return SeparateResultTypeEnum.ignore;
                }
            } else {
                //前的情况 bvcxed
                switch (type) {
                    case QUOTES:
                        runtime.setLength(length + 1);
                        return SeparateResultTypeEnum.start;
                    case MIDDLE:
                        runtime.setLength(length + 1);
                        return SeparateResultTypeEnum.unit;
                    case LEFT:
                        runtime.setLength(length + 1);
                        return SeparateResultTypeEnum.start;
                    case RIGHT:
                        //语法上不应出现
                    case NONE:
                        //不应在监听器环节处理
                    case NGL:
                        char nextChar = runtime.getChars()[runtime.getIndex() + 1];
                        if (nextChar == BodyCharEnum.left_brace.getCharValue()) {
                            runtime.setLength(2);
                            runtime.setResetIndex(true);
                            runtime.setValidBodyChar(BodyCharEnum.left_brace);
                            return SeparateResultTypeEnum.start;
                        } else {
                            runtime.setLength(length + 1);
                            return SeparateResultTypeEnum.start;
                        }
                }
            }
        }
        return SeparateResultTypeEnum.ignore;
    }

    protected boolean dealCaughtValue(SeparateRuntimeContext runtime, SeparatorChar separatorChar, boolean apply) {
        BodyChar validBodyChar = runtime.getValidBodyChar();
        if (null != validBodyChar) {
            apply = dealCaughtValueWhenInBodyChar(runtime, separatorChar, apply, validBodyChar);
        } else {
            //其他上下文允许的分隔符
            apply = true;
        }
        return apply;
    }

    protected boolean dealCatchingValue(SeparateRuntimeContext runtime, SeparatorChar separatorChar, SeparatorContextManager manager) {
        if (separatorChar instanceof BodyChar) {
            BodyChar bodyChar = (BodyChar) separatorChar;
            runtime.setValidBodyChar(bodyChar);
            manager.switchBodyCharContext(bodyChar);
        }
        return true;
    }

    /**
     * 处理体符
     *
     * @param runtime
     * @param separatorChar
     * @param apply
     * @param validBodyChar
     * @return
     */
    protected boolean dealCaughtValueWhenInBodyChar(SeparateRuntimeContext runtime, SeparatorChar separatorChar, boolean apply, BodyChar validBodyChar) {
        if (validBodyChar.getOppositeChar() == separatorChar.getCharValue()) {
            int bodyDeep = runtime.getBodyDeep();
            if (0 == bodyDeep) {
                apply = true;
            } else {
                runtime.setBodyDeep(bodyDeep - 1);
            }
        } else if (validBodyChar == separatorChar) {
            runtime.setBodyDeep(runtime.getBodyDeep() + 1);
        }
        return apply;
    }
}
