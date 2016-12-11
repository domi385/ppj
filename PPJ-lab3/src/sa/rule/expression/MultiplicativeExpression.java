package sa.rule.expression;

import sa.Environment;
import sa.PropertyType;
import sa.Types;
import sa.node.NonTerminalNode;
import sa.rule.RuleStrategy;
import sa.rule.RuleUtility;

public class MultiplicativeExpression extends RuleStrategy {

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 1) {
            node.getChidlAt(0).visitNode(environment);
            node.getProperty(PropertyType.TYPE).setValue(
                    ((NonTerminalNode) node.getChidlAt(0)).getProperty(PropertyType.TYPE)
                    .getValue());
            node.getProperty(PropertyType.L_EXPRESSION).setValue(
                    ((NonTerminalNode) node.getChidlAt(0)).getProperty(PropertyType.L_EXPRESSION)
                    .getValue());
        } else if (node.getChildNodeNumber() == 3) {
            node.getChidlAt(0).visitNode(environment);
            RuleUtility.checkType(node.getChidlAt(0), Types.INT);
            node.getChidlAt(2).visitNode(environment);
            RuleUtility.checkType(node.getChidlAt(2), Types.INT);

            node.getProperty(PropertyType.TYPE).setValue(Types.INT);
            node.getProperty(PropertyType.L_EXPRESSION).setValue(0);
        } else {
            // loša produkcija
        }

    }

}
