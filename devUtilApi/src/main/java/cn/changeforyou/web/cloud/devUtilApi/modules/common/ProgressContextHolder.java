package cn.changeforyou.web.cloud.devUtilApi.modules.common;

import cn.changeforyou.utils.string.StringUtils;
import cn.hutool.core.lang.generator.UUIDGenerator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.concurrent.TimeUnit;

/**
 * 进度条上下文管理器
 *
 * @author zhyu
 * @version 1.0
 * @date 2022/2/7 11:15
 */
@Data
@Component
@Slf4j
public class ProgressContextHolder implements ApplicationContextAware {
    static final String KEY_PREFIX = "progress_";
    static UUIDGenerator uuidGenerator = new UUIDGenerator();
    static RedisTemplate<String, Object> redisTemplate;
    static ThreadLocal<ProgressContext> threadLocal = new ThreadLocal<>();
    static int EXPIRE_MINUTES = 5;

    /**
     * 保存上下文
     */
    public static void save() {
        ProgressContext progressContext = threadLocal.get();
        redisTemplate.boundValueOps(getKey(progressContext)).set(progressContext, EXPIRE_MINUTES, TimeUnit.MINUTES);
        threadLocal.set(null);
    }

    /**
     * 获取上下文
     * @return
     */
    public static ProgressContext get() {
        return threadLocal.get();
    }

    /**
     * 结束进度条
     */
    public static void finishProgress() {
        String key = getKey(threadLocal.get());
        Boolean result = redisTemplate.delete(key);
        if (null == result || !result) {
            log.info("在redis中清除数据失败 key:{}" + key);
        }
    }

    /**
     * 搜索或者创建进度条上下文
     * 根据进度条id去缓存中搜索上下文, 如果没有就创建一个,  如果传入的进度条id为空, 也要创建一个
     *
     * @param progressId
     */
    public static void searchOrCreateProgress(@Nullable String progressId) {
        ProgressContext progressContext = null;
        if (StringUtils.isEmpty(progressId)) {
            progressContext = new ProgressContext();
            progressContext.setProgressId(uuidGenerator.next());
        } else {
            Object value = redisTemplate.boundValueOps(getKey(progressId)).get();
            if (null != value) {
                progressContext = (ProgressContext) value;
            }
        }
        threadLocal.set(progressContext);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        redisTemplate = applicationContext.getBean("redisTemplateStringKey", RedisTemplate.class);
    }

    private static String getKey(ProgressContext progressContext) {
        return getKey(progressContext.getProgressId());
    }

    private static String getKey(String progressId) {
        return KEY_PREFIX + progressId;
    }
}
