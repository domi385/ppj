package sa.rule.expression;

import sa.Environment;
import sa.PropertyType;
import sa.node.NonTerminalNode;
import sa.rule.RuleStrategy;

public class Expression extends RuleStrategy {

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
            node.getChidlAt(2).visitNode(environment);

            node.getProperty(PropertyType.TYPE).setValue(
                    ((NonTerminalNode) node.getChidlAt(2)).getProperty(PropertyType.TYPE)
                    .getValue());

            node.getProperty(PropertyType.L_EXPRESSION).setValue(0);
        } else {
            // loša produkcija
        }

    }

}
