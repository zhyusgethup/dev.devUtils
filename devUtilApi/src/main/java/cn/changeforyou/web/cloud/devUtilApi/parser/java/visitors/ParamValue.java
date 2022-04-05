package cn.changeforyou.web.cloud.devUtilApi.parser.java.visitors;

import lombok.Data;

/**
 * @author zhyu
 * @date 2021/2/24 16:35
 */
@Data
public class ParamValue {

    private Class tClass;

    private String paramName;

    private Object value;

    public ParamValue(Class tClass, String paramName) {
        this.tClass = tClass;
        this.paramName = paramName;
    }

    public ParamValue(String paramName, Object value) {
        this.paramName = paramName;
        this.value = value;
    }
}
