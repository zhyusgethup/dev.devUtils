package cn.changeforyou.web.cloud.devUtilApi.common.model;

import cn.changeforyou.web.cloud.webBase.common.model.Result;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhyu
 * @version 1.0
 * @date 2022/4/6 20:59
 */

public class ResultWithEncoded extends Result<String> {

    public static String DEFAULT_ENCODED = "base64";

    @Setter@Getter
    private boolean encode;

    @Getter
    @Setter
    private String arithmetic;

    public ResultWithEncoded(int code, String message, String data, String arithmetic, boolean encode) {
        super(code, message, data);
        if (arithmetic == null) {
            this.arithmetic = DEFAULT_ENCODED;
        } else {
            this.arithmetic = arithmetic;
        }
        this.encode = encode;
    }

    public static ResultWithEncoded success(String data) {
        return new ResultWithEncoded(0, "成功", data, null, false);
    }

    public static ResultWithEncoded success(String data, String arithmetic) {
        return new ResultWithEncoded(0, "成功", data, arithmetic, false);
    }

    public static ResultWithEncoded success(String data, String arithmetic, boolean encode) {
        return new ResultWithEncoded(0, "成功", data, arithmetic, encode);
    }

}
