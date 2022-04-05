package cn.changeforyou.web.cloud.devUtilApi.controller;

import cn.changeforyou.web.cloud.webBase.platform.PlatformEnum;
import cn.changeforyou.web.cloud.webBase.platform.PlatformStringUtils;
import cn.changeforyou.web.cloud.webBase.platform.PlatformUtils;
import cn.changeforyou.web.cloud.devUtilApi.db.sql.DDLBuilder;
import cn.changeforyou.web.cloud.devUtilApi.db.sql.SqlFormatUtil;
import cn.changeforyou.web.cloud.devUtilApi.modules.common.EasyProgressResult;
import cn.changeforyou.web.cloud.devUtilApi.modules.common.ProgressContext;
import cn.changeforyou.web.cloud.devUtilApi.modules.common.ProgressContextHolder;
import cn.changeforyou.web.cloud.devUtilApi.parser.java.JavaEntityParser;
import cn.hutool.core.codec.Base64;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("java")
@Slf4j
public class JavaController {

    @Autowired
    private SqlFormatUtil sqlFormatUtil;

    /**
     * @param javaFile   文件
     * @param progressId 进度id
     * @return
     */
    @PostMapping("generateSqlByJavaFile")
    @ResponseBody
    public EasyProgressResult generateSqlByJavaFile(MultipartFile javaFile, String progressId) {
        log.info("上传信息 文件名:{}, progressId:{}", javaFile.getName(), progressId);
        ProgressContextHolder.searchOrCreateProgress(progressId);
        try {
            JavaEntityParser.parse(javaFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EasyProgressResult<Object> result = new EasyProgressResult<>();
        ProgressContext progressContext = ProgressContextHolder.get();
        boolean startBuildDDL = false;
        switch (progressContext.getNextCode()) {
            case ProgressContext.NEXT_CODE_FINISH:
                ProgressContextHolder.finishProgress();
                startBuildDDL = true;
            case ProgressContext.NEXT_CODE_ERROR:
            case ProgressContext.NEXT_CODE_WAIT_ANOTHER:
                result.setFinish(progressContext.isFinish());
                result.setNextCode(progressContext.getNextCode());
                result.setProgressId(progressContext.getProgressId());
                result.setPercent(progressContext.getPercent());
                result.setMessage(progressContext.getMessage());
                ProgressContextHolder.save();
                break;
        }

        if (startBuildDDL) {
            if (log.isDebugEnabled()) {
                log.debug("解析出来的实体信息: {}", JSONUtil.toJsonPrettyStr(progressContext.getJavaEntity()));
            }
            String ddl = DDLBuilder.buildDDL(progressContext.getJavaEntity());
            ddl = sqlFormatUtil.formatDDL(ddl);
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            PlatformEnum platform = PlatformUtils.getPlatform(request);
            ddl = PlatformStringUtils.getStringAdaptPlatform(platform, ddl);
            if (log.isDebugEnabled()) {
                log.debug("ddl: {}", ddl);
            }
            ddl = Base64.encode(ddl);
            result.setData(ddl);
        }
        log.info("响应信息: {}", JSONUtil.toJsonPrettyStr(result));
        return result;
    }
}
