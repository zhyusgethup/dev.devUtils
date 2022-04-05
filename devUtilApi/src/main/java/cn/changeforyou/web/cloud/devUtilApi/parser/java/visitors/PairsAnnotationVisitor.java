package cn.changeforyou.web.cloud.devUtilApi.parser.java.visitors;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhyu
 * @date 2021/2/24 16:25
 */
@Slf4j
public class PairsAnnotationVisitor extends GenericVisitorAdapter<List<ReturnValue>, List<ParamValue>> {

    @Override
    public List<ReturnValue> visit(final NormalAnnotationExpr n, List<ParamValue> params) {
        NodeList<MemberValuePair> pairs = n.getPairs();
        List<ReturnValue> returnValues = new ArrayList<>();
        for (ParamValue param : params) {
            for (MemberValuePair pair : pairs) {
                String paramName = param.getParamName();
                if (pair.getName().getIdentifier().equals(paramName)) {
                    Class tClass = param.getTClass();
                    if (tClass == String.class) {
                        returnValues.add(new ReturnValue(paramName, pair.getValue().asLiteralStringValueExpr().getValue()));
                    } else if (tClass == boolean.class) {
                        returnValues.add(new ReturnValue(paramName, pair.getValue().asBooleanLiteralExpr().getValue()));
                    } else if (tClass == Boolean.class) {
                        returnValues.add(new ReturnValue(paramName, pair.getValue().asBooleanLiteralExpr().getValue()));
                    } else {
                        log.error("发现没有注册的枚举访问类型, return class 为{}, 表达式类型为:{}", tClass, pair.getValue().getClass().getCanonicalName());
                        throw new IllegalArgumentException();
                    }
                }

            }
        }
        return returnValues;
    }
}
