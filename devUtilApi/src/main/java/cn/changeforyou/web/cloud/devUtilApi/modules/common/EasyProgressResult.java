package cn.changeforyou.web.cloud.devUtilApi.modules.common;

import cn.changeforyou.web.cloud.webBase.common.model.Result;
import cn.changeforyou.base.model.ResultInfo;
import lombok.ToString;

/**
 * 简单进度条模型
 * @see ProgressContext
 * @author zhyu
 * @version 1.0
 * @date 2022/2/7 11:06
 */
@ToString(callSuper = true)
public class EasyProgressResult<T> extends Result<T> {
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
     * 下一步指令
     */
    private int nextCode;


    public EasyProgressResult() {
        super(ResultInfo.SUCCESS_CODE, ResultInfo.SUCCESS_MESSAGE);
    }

    public EasyProgressResult(int code, String message) {
        super(code, message);
    }

    public EasyProgressResult(int code, String message, T data) {
        super(code, message, data);
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public String getProgressId() {
        return progressId;
    }

    public void setProgressId(String progressId) {
        this.progressId = progressId;
    }

    public int getNextCode() {
        return nextCode;
    }

    public void setNextCode(int nextCode) {
        this.nextCode = nextCode;
    }
}
