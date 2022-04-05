package cn.changeforyou.web.cloud.devUtilApi.parser.java.visitors;

import lombok.Data;

/**
 * @author zhyu
 * @date 2021/2/24 16:35
 */
@Data
public class ReturnTypeParam<T> {

    private Class<T> tClass;
    
    private String paramName;
    
    private T value;

    public ReturnTypeParam(Class<T> tClass, String paramName) {
        this.tClass = tClass;
        this.paramName = paramName;
    }

    public ReturnTypeParam(String paramName, T value) {
        this.paramName = paramName;
        this.value = value;
    }
}
