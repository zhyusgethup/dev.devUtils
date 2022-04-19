package cn.changeforyou.web.cloud.devUtilApi.encode;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * @author zhyu
 * @version 1.0
 * @date 2022/4/19 17:43
 */
@Slf4j
public class EncodeTest {

    @Test
    void test() {
        InputStream inputStream = EncodeTest.class.getClassLoader().getResourceAsStream("a.html");
        try {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);

            String gbk = new String(bytes, "gbk");
            System.out.println(gbk);
            String utf = new String(bytes, "UTF-8");
            System.out.println(utf);

            byte[] b1 = gbk.getBytes("gbk");

            byte[] b2 = utf.getBytes("UTF-8");
            System.out.println(Arrays.equals(b2, bytes));

            log.info("src: {}", Arrays.toString(bytes));
            log.info("b1: {}", Arrays.toString(b1));
            log.info("b2: {}", Arrays.toString(b2));

            System.out.println(new String(b2));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
