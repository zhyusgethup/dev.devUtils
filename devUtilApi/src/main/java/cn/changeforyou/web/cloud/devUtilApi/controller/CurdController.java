package cn.changeforyou.web.cloud.devUtilApi.controller;

import cn.changeforyou.web.cloud.webBase.common.model.Result;
import cn.changeforyou.web.cloud.devUtilApi.modules.db.entity.Java2RDBMSMap;
import cn.changeforyou.web.cloud.devUtilApi.modules.db.entity.Java2RDBMSMapCondition;
import cn.changeforyou.web.cloud.devUtilApi.modules.db.service.Java2RDBMSMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhyu
 * @version 1.0
 * @date 2022/2/16 10:26
 */
@RestController
@RequestMapping("crud")
public class CurdController {

    @Autowired
    private Java2RDBMSMapService java2RDBMSMapService;

    @GetMapping("list")
    public Result<Page<Java2RDBMSMap>> getList(Java2RDBMSMapCondition condition, @PageableDefault(value = 10, page =
            1, sort = {"create_time"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return Result.success(java2RDBMSMapService.page(pageable, condition));
    }

}
