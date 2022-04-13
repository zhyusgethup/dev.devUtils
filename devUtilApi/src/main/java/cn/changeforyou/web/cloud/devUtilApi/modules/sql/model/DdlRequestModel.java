package cn.changeforyou.web.cloud.devUtilApi.modules.sql.model;

import cn.changeforyou.web.cloud.devUtilApi.common.model.StringReqModel;
import cn.changeforyou.web.cloud.devUtilApi.modules.sql.impl.TimeType;
import lombok.Data;

/**
 * @author zhyu
 * @version 1.0
 * @date 2022/4/12 13:16
 */

@Data
public class DdlRequestModel extends StringReqModel {
    /**
     * 使用lombok
     */
    private boolean lombok;

    /**
     * 使用别名, 对ResultMap和select有影响
     */
    private TimeType timeType;
}
