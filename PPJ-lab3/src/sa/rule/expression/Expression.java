package sa.rule.expression;

import sa.Environment;
import sa.Property;
import sa.PropertyType;
import sa.node.NonTerminalNode;
import sa.rule.RuleStrategy;

public class Expression extends RuleStrategy {

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 1) {
            node.getChidlAt(0).visitNode(environment);
            node.setProperty(PropertyType.TYPE,
                    ((NonTerminalNode) node.getChidlAt(0)).getProperty(PropertyType.TYPE));
            node.setProperty(PropertyType.L_EXPRESSION,
                    ((NonTerminalNode) node.getChidlAt(0)).getProperty(PropertyType.L_EXPRESSION));
        } else if (node.getChildNodeNumber() == 3) {
            node.getChidlAt(0).visitNode(environment);
            node.getChidlAt(2).visitNode(environment);

            node.setProperty(PropertyType.TYPE,
                    ((NonTerminalNode) node.getChidlAt(2)).getProperty(PropertyType.TYPE));

            node.setProperty(PropertyType.L_EXPRESSION, new Property(0));
        } else {
            // loša produkcija
        }

    }

}
