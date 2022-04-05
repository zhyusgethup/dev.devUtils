package cn.changeforyou.web.cloud.devUtilApi.parser.java.visitors;

import lombok.Data;

/**
 * @author zhyu
 * @date 2022/02/07
 */
@Data
public class ReturnValue {

    private String paramName;

    private Object value;

    public ReturnValue(String paramName, Object value) {
        this.paramName = paramName;
        this.value = value;
    }
}
