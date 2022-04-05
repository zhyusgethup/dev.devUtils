package cn.changeforyou.web.cloud.devUtilApi.modules.db.service;

import cn.changeforyou.web.cloud.devUtilApi.modules.db.entity.Java2RDBMSMap;
import cn.changeforyou.web.cloud.devUtilApi.modules.db.entity.Java2RDBMSMapCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author zhyu
 * @version 1.0
 * @date 2022/2/16 11:23
 */
public interface Java2RDBMSMapService {
    Page<Java2RDBMSMap> page(Pageable pageable, Java2RDBMSMapCondition condition);
}
