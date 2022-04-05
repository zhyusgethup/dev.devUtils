package cn.changeforyou.web.cloud.devUtilApi.parser.ast.astNode;

import java.util.List;

/**
 * AstNode实现
 */
public abstract class AbstractAstNode implements AstNode {

    protected String sourceCode;
    protected AstNode parent;
    protected AstNode root;
    protected int num;
    protected String treeNum;
    protected AstNode elderBrother;
    protected AstNode youngerBrother;
    protected List<AstNode> children;

    @Override
    public String getSourceCode() {
        return sourceCode;
    }

    @Override
    public AstNode getParent() {
        return parent;
    }

    @Override
    public AstNode getRoot() {
        return root;
    }

    @Override
    public int getNum() {
        return num;
    }

    @Override
    public String getTreeNum() {
        return treeNum;
    }

    @Override
    public AstNode getElderBrother() {
        return elderBrother;
    }

    @Override
    public AstNode getYoungerBrother() {
        return youngerBrother;
    }

    @Override
    public List<AstNode> getChildren() {
        return children;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public void setParent(AstNode parent) {
        this.parent = parent;
    }

    public void setRoot(AstNode root) {
        this.root = root;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setTreeNum(String treeNum) {
        this.treeNum = treeNum;
    }

    public void setElderBrother(AstNode elderBrother) {
        this.elderBrother = elderBrother;
    }

    public void setYoungerBrother(AstNode youngerBrother) {
        this.youngerBrother = youngerBrother;
    }

    public void setChildren(List<AstNode> children) {
        this.children = children;
    }

}
