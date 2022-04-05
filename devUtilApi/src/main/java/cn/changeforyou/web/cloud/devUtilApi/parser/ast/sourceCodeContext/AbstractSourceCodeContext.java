package cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext;

import cn.changeforyou.web.cloud.devUtilApi.parser.ast.astNode.AstNode;
import cn.changeforyou.web.cloud.devUtilApi.parser.ast.ParseCodeException;
import cn.changeforyou.web.cloud.devUtilApi.parser.ast.astNode.DefaultAstNode;

/**
 * SourceCodeContext的抽象类
 * 实现了SourceContext接口的一般性方法
 */
public abstract class AbstractSourceCodeContext implements SourceCodeContext {

    /**
     * 源代码字符串
     */
    protected String sourceCode;
    /**
     * 在父文本中的索引
     * 比如code是 b  父文本是 let b = 3;  那么parentIndex=4
     */
    protected int parentIndex;
    /**
     * 在全局文本中的索引
     */
    protected int globalIndex;
    /**
     * 在全文本中的行数
     */
    protected int row;
    /**
     * 在全文本中的列数
     */
    protected int col;

    /**
     * 全局文本
     */
    protected SourceCodeContext globalSourceCodeContext;

    /**
     * 父文本
     */
    protected SourceCodeContext parentSourceCodeContext;

    /**
     * 解析状态
     */
    protected volatile ParseStatus parseStatus;

    /***
     * 文本对应的节点
     */
    protected AstNode astNode;

    public AbstractSourceCodeContext() {
        this.parseStatus = ParseStatus.have_not_start_parsing;
    }


    @Override
    public String getSourceCode() {
        return sourceCode;
    }

    @Override
    public int getParentIndex() {
        return parentIndex;
    }

    @Override
    public int getGlobalIndex() {
        return globalIndex;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getCol() {
        return col;
    }

    @Override
    public void setSourceCode(String sourceCode) {
        sourceCode = sourceCode.replaceAll("\r\n|\r|\n", " ").replaceAll(" +", " ");
        this.sourceCode = sourceCode.trim();
    }

    @Override
    public void setParentIndex(int parentIndex) {
        this.parentIndex = parentIndex;
    }

    @Override
    public void setGlobalIndex(int globalIndex) {
        this.globalIndex = globalIndex;
    }

    @Override
    public void setRow(int row) {
        this.row = row;
    }

    @Override
    public void setCol(int col) {
        this.col = col;
    }

    @Override
    public ParseStatus getParseStatus() {
        return parseStatus;
    }

    @Override
    public SourceCodeContext getParentSourceCodeContext() {
        return parentSourceCodeContext;
    }

    @Override
    public SourceCodeContext getGlobalSourceCodeContext() {
        return globalSourceCodeContext;
    }

    @Override
    public AstNode loadAsAstNode() {
        if(null == sourceCode) {
            throw new ParseCodeException("清闲设置源代码");
        }
        if(null == this.astNode){
            this.astNode = genAstNode();
        }
        if(this.parseStatus == ParseStatus.have_not_start_parsing) {
            loadAsAstNode_do();
        }
        return this.astNode;
    }

    /**
     * 生成AstNode
     * @return
     */
    protected AstNode genAstNode(){
        DefaultAstNode defaultAstNode = new DefaultAstNode();
        defaultAstNode.setSourceCode(sourceCode);
        if(null != this.parentSourceCodeContext) {
            AstNode parentNode = this.parentSourceCodeContext.loadAsAstNode();
            AstNode root = parentNode.getRoot();
            defaultAstNode.setParent(parentNode);
            defaultAstNode.setRoot(root);
        }
        return defaultAstNode;
    }

    /**
     * 去_do的实际执行方法
     * @return
     */
    protected abstract AstNode loadAsAstNode_do();
}
