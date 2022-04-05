package cn.changeforyou.web.cloud.devUtilApi.exception;

import cn.changeforyou.base.exception.ErrorInfo;
import cn.changeforyou.base.model.Module;

public enum DevApiExceptionEnum implements ErrorInfo {

    //这个在CommonResultEnum
//    TOKEN_NOT_EXIST(1, "token不存在"),
    DESIGN_NOT_ENOUGH(1, "遇到了未考虑到的情况, 请联系开发补充"),
    ;

    DevApiExceptionEnum(int unitCode, String message) {
        this.unitCode = unitCode;
        this.message = message;
    }

    private final int unitCode;
    private final String message;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Module getModule() {
        return Module.DEV_UTIL_API;
    }

    @Override
    public int unitCode() {
        return unitCode;
    }

}
