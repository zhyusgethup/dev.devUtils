package cn.changeforyou.web.cloud.devUtilApi.modules.db.service.impl;

import cn.changeforyou.web.cloud.devUtilApi.modules.db.dao.Java2RDBMSMapDao;
import cn.changeforyou.web.cloud.devUtilApi.modules.db.entity.Java2RDBMSMap;
import cn.changeforyou.web.cloud.devUtilApi.modules.db.entity.Java2RDBMSMapCondition;
import cn.changeforyou.web.cloud.devUtilApi.modules.db.service.Java2RDBMSMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author zhyu
 * @version 1.0
 * @date 2022/2/16 11:23
 */
@Service
public class Java2RDBMSMapServiceImpl implements Java2RDBMSMapService {

    @Autowired
    private Java2RDBMSMapDao java2RDBMSMapDao;

    @Override
    public Page<Java2RDBMSMap> page(Pageable pageable, Java2RDBMSMapCondition condition) {
        return null;
    }
}
