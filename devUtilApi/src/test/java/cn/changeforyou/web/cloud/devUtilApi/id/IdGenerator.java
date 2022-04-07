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

    @Test
    public void mdmBarCode(){
        System.out.println(buildCBarCode(690138211915L));

        System.out.println();



        System.out.println(buildXBarCode(1690138204319L));
    }

    private String buildCBarCode(Long cbarCode) {
        String cBarCode = "";
        Integer checkedCode = 0;
        Integer even = 0;
        Integer odd = 0;
        Integer sum = 0;
        Integer temp = 0;
        // 将12位流水码转为String类型
        cBarCode = String.valueOf(cbarCode);
        // 校验码生成
        // (1)获取偶数位和奇数位的和
        char[] charArr = cBarCode.toCharArray();
        for (int i = 0; i < charArr.length; i++) {
            if (i % 2 != 0) {
                // 偶数位数值
                even += (int) (charArr[i] - '0');
            } else {
                // 奇数和
                odd += (int) (charArr[i] - '0');
            }
        }
        // (2)奇数和+3*偶数和
        sum = even * 3;
        sum = sum + odd;
        // (3)(10-sum%10的余数)%10 = 校验码
        temp = sum % 10;
        temp = 10 - temp;
        checkedCode = temp % 10;

        //// 拼接条形码12位+1位校验码
        cBarCode = cBarCode + String.valueOf(checkedCode);

        return cBarCode;
    }

    private String buildXBarCode(Long xbarCode) {
        String xBarCode = "";
        Integer checkedCode = 0;
        Integer even = 0;
        Integer odd = 0;
        Integer sum = 0;
        Integer temp = 0;
        // 将13位流水码转为String类型
        xBarCode = String.valueOf(xbarCode);
        // 校验码生成
        // (1)获取偶数位和奇数位的和
        char[] charArr = xBarCode.toCharArray();
        for (int i = 0; i < charArr.length; i++) {
            if (i % 2 != 0) {
                // 奇数和
                odd += (int) (charArr[i] - '0');
            } else {
                // 偶数位数值
                even += (int) (charArr[i] - '0');
            }
        }
        // (2)奇数和+3*偶数和
        sum = even * 3;
        sum = sum + odd;
        // (3)(10-sum%10的余数)%10 = 校验码
        temp = sum % 10;
        temp = 10 - temp;
        checkedCode = temp % 10;
        // 拼接条形码13位+1位校验码
        xBarCode = xBarCode + String.valueOf(checkedCode);

        return xBarCode;
    }
}
