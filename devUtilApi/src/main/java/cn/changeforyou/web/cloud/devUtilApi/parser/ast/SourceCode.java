package cn.changeforyou.web.cloud.devUtilApi.parser.ast;

/**
 * 表示源代码,
 * 获取源代码, 获取在父结构, 全文本中的位置
 */
public interface SourceCode {
    /**
     * 获取源代码
     * @return
     */
    String getSourceCode();

    /**
     * 获取在父结构中的索引
     * @return
     */
    int getParentIndex();

    /**
     * 获取在全文中索引
     * @return
     */
    int getGlobalIndex();

    /**
     * 获取在全文中的行数
     * @return
     */
    int getRow();

    /**
     * 获取在全文中的列数
     * @return
     */
    int getCol();

    /**
     * 设置源代码
     * @param sourceCode
     */
    void setSourceCode(String sourceCode);

    /**
     * 设置在父结构中的索引
     * @param parentIndex
     */
    void setParentIndex(int parentIndex);

    /**
     * 设置在全文中索引
     * @param globalIndex
     */
    void setGlobalIndex(int globalIndex);

    /**
     * 设置在全文中的行数
     * @param row
     */
    void setRow(int row);

    /**
     * 设置在全文中的列数
     * @param col
     */
    void setCol(int col);
}
