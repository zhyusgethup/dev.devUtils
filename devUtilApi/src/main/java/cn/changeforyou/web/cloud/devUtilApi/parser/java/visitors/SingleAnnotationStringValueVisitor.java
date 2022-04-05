package cn.changeforyou.web.cloud.devUtilApi.parser.java.visitors;

import cn.changeforyou.utils.string.StringUtils;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;

/**
 * @author zhyu
 * @date 2021/2/9 13:25
 */
public class SingleAnnotationStringValueVisitor extends GenericVisitorAdapter<String, String> {
    
    @Override
    public String visit(SingleMemberAnnotationExpr n, String param) {
        return n.getMemberValue().asLiteralStringValueExpr().getValue();
    }

    @Override
    public String visit(final NormalAnnotationExpr n, String param) {
        NodeList<MemberValuePair> pairs = n.getPairs();
        if(StringUtils.isEmpty(param) && pairs.size() == 1) {
            return pairs.get(0).getValue().asLiteralStringValueExpr().getValue();
        }else {
            for (MemberValuePair pair : pairs) {
                if (pair.getName().getIdentifier().equalsIgnoreCase(param)) {
                    return pair.getValue().asLiteralStringValueExpr().getValue();
                }
            }
        }
        return null;
    }
}