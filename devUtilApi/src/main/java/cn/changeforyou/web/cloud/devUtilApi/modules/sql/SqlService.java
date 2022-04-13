package cn.changeforyou.web.cloud.devUtilApi.modules.sql;

import cn.changeforyou.web.cloud.devUtilApi.modules.sql.model.DdlRequestModel;

public interface SqlService {
    String ddl2Entity(DdlRequestModel value);

}
