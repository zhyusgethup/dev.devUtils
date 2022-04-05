package cn.changeforyou.web.cloud.devUtilApi.parser.ast.astNode;

import java.util.List;

/**
 * ast 语法树节点
 */
public interface AstNode {

    /**
     * 获取源代码
     * @return
     */
    String getSourceCode();

    /**
     * 获取父节点, 如果节点自己就是根节点, 那么返回null
     * @return
     */
    AstNode getParent();

    /***
     * 获取根节点, 如果节点自己就是根节点, 那么返回this
     * @return
     */
    AstNode getRoot();

    /**
     * 获取节点在父节点中的索引, 索引从1开始, 同一个节点的子节点必须是有序的, 如果该节点是根节点,那么返回0
     * @return
     */
    int getNum();

    /**
     * 获取节点在全树中的位置 位置用数字和_表示, 数字是在父节点的位置,从根节点的Num开始,_连接每层的Num,知道该节点的Num为止
     * 比如0_2_3_5: 表示该节点是根节点的2儿子的3儿子的5儿子
     * @return
     */
    String getTreeNum();

    /**
     * 获取哥哥, 如果他就是大哥,返回null
     * @return
     */
    AstNode getElderBrother();

    /**
     * 获取弟弟, 如果他就是幺弟,返回null
     * @return
     */
    AstNode getYoungerBrother();

    /**
     * 获取所有儿子, 如果不是概念节点, 那么将返回null
     * @return
     */
    List<AstNode> getChildren();
}
