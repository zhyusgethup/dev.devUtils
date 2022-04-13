package cn.changeforyou.web.cloud.devUtilApi.modules.sql.impl;

import lombok.Data;

/**
 * @author zhyu
 * @version 1.0
 * @date 2022/4/13 10:35
 */

@Data
public class WriteJavaEntityContext {
    /**
     * 使用lombok
     */
    private boolean lombok;

    /**
     * 使用别名, 对ResultMap和select有影响
     */
    private TimeType timeType;
}
