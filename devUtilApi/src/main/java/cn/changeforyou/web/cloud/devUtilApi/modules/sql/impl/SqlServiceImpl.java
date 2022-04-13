package cn.changeforyou.web.cloud.devUtilApi.modules.sql.impl;

import cn.changeforyou.base.exception.DataExceptionEnum;
import cn.changeforyou.base.exception.ExceptionFactory;
import cn.changeforyou.web.cloud.devUtilApi.modules.sql.SqlService;
import cn.changeforyou.web.cloud.devUtilApi.modules.sql.model.DdlRequestModel;
import cn.changeforyou.web.cloud.devUtilApi.parser.java.JavaEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zhyu
 * @version 1.0
 * @date 2022/4/11 17:52
 */

@Service
@Slf4j
public class SqlServiceImpl implements SqlService {

    @Override
    public String ddl2Entity(DdlRequestModel model) {
        JavaEntity javaEntity = null;
        try {
            javaEntity = JSqlParserUtil.parseDdl2JavaEntityObject(model.getValue());
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.error("error", e);
            }
            throw ExceptionFactory.jsonException(DataExceptionEnum.PARAM_ERROR);
        }

        WriteJavaEntityContext context = new WriteJavaEntityContext();
        context.setLombok(model.isLombok());
        context.setTimeType(model.getTimeType());
        String result = JavaParserUtil.writeJavaEntityString(javaEntity, context);
        return result;
    }


}
