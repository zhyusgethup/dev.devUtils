package cn.changeforyou.web.cloud.devUtilApi.modules.common;

import cn.changeforyou.web.cloud.devUtilApi.parser.java.JavaEntity;
import lombok.Data;

/**
 * 进度条上下文
 * @author zhyu
 * @version 1.0
 * @date 2022/2/7 11:15
 */
@Data
public class ProgressContext {
    /**
     * 父类名称
     */
    private String superClassName;

    private JavaEntity javaEntity;

    /**
     * 是否完成
     */
    private boolean finish;

    /**
     * 进度 0~100
     */
    private int percent;

    /**
     * 进度id
     */
    private String progressId;

    /**
     * 信息
     */
    private String message;

    /**
     * 下一步指令 0:开始 1:等待下一个文件 2:结束 -1:异常
     */
    private int nextCode;

    public static final int NEXT_CODE_START = 0;
    public static final int NEXT_CODE_WAIT_ANOTHER = 1;
    public static final int NEXT_CODE_FINISH = 2;
    public static final int NEXT_CODE_ERROR = -1;
}
