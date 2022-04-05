package cn.changeforyou.web.cloud.devUtilApi.parser.java.visitors;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhyu
 * @date 2021/2/24 16:25
 */
@Slf4j
public class PairAnnotationVisitor<R>  extends GenericVisitorAdapter<R, ReturnTypeParam<R>> {

    @Override
    public R visit(final NormalAnnotationExpr n, ReturnTypeParam<R> param) {
        NodeList<MemberValuePair> pairs = n.getPairs();
        R value = param.getValue();
        if (value != null) {
            MemberValuePair pair = new MemberValuePair();
            pairs.add(pair);
            pair.setName(param.getParamName());
            Class<R> tClass = param.getTClass();
            if (value instanceof String) {
                pair.setValue(new StringLiteralExpr((String)value));
            } else if (value instanceof Boolean) {
               pair.setValue(new BooleanLiteralExpr((Boolean) value));
            } else {
                log.error("发现没有注册的枚举写入类型, return class 为{}, 表达式类型为:{}", tClass, pair.getValue().getClass().getCanonicalName());
                throw new IllegalArgumentException();
            }
            return value;
        }else {
            if (null == param && pairs.size() == 1) {
                return (R) pairs.get(0).getValue().asLiteralStringValueExpr().getValue();
            } else {
                String name = param.getParamName();
                Class<R> tClass = param.getTClass();
                for (MemberValuePair pair : pairs) {
                    if (pair.getName().getIdentifier().equalsIgnoreCase(name)) {
                        if (tClass == String.class) {
                            return (R) pair.getValue().asLiteralStringValueExpr().getValue();
                        } else if (tClass == boolean.class) {
                            return (R) new Boolean(pair.getValue().asBooleanLiteralExpr().getValue());
                        } else if (tClass == Boolean.class) {
                            return (R) new Boolean(pair.getValue().asBooleanLiteralExpr().getValue());
                        } else {
                            log.error("发现没有注册的枚举访问类型, return class 为{}, 表达式类型为:{}", tClass, pair.getValue().getClass().getCanonicalName());
                            throw new IllegalArgumentException();
                        }
                    }
                }
            }
        }
        return null;
    }
}
