package cn.changeforyou.web.cloud.devUtilApi.id;

import org.junit.jupiter.api.Test;

/**
 * @author zhyu
 * @version 1.0
 * @date 2022/3/29 16:19
 */


public class IdGenerator {

    @Test
    public void test(){
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker();
        for (int i = 0; i < 332; i++) {
            String s = idWorker.nextUUID(null);
            System.out.println(s);
        }
    }
}
