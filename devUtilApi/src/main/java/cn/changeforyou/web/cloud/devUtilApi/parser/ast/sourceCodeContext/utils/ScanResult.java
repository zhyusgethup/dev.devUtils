package cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils;

import lombok.Data;

/**
 * 扫描的返回值
 */
@Data
public class ScanResult {

    private String word;

    private boolean canSeparate;

    private int index;
}
