package cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext;

import cn.changeforyou.web.cloud.devUtilApi.parser.ast.SourceCode;
import cn.changeforyou.web.cloud.devUtilApi.parser.ast.astNode.AstNode;

/***
 * 源代码上下文外部api, 提供上下文统一的外部表现, 提供一个
 * 源代码上下文, 每一个源代码上下文必须对应一个AstNode, 对应不上就是源代码不完整(该上下文有问题), 或者没现实对应的解析器,
 *
 * 上下文是解析器解析源代码的工具, 为不同的语素, 语句, 体, 语法, 语言文件等提供相同的上下文环境
 * 上下文可以分解成子上下文, 正如Ast有儿子,有父亲,有兄弟一样, 上下文旨在提供一种快速的,节约的拆解完整源代码的方式
 */
public interface SourceCodeContext extends SourceCode {

    /**
     * 该上下文环境装在出一个AstNode
     */
    AstNode loadAsAstNode();

    /**
     * 获取解析状态
     * @return
     */
    ParseStatus getParseStatus();

    /**
     * 获取父文本
     * @return
     */
    SourceCodeContext getParentSourceCodeContext();

    /**
     * 获取全局文本
     * @return
     */
    SourceCodeContext getGlobalSourceCodeContext();
}
