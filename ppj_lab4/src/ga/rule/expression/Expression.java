package ga.rule.expression;

import ga.Environment;
import ga.Property;
import ga.PropertyType;
import ga.node.NonTerminalNode;
import ga.rule.RuleStrategy;

public class Expression extends RuleStrategy {

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 1) {
            node.getChidlAt(0).visitNode(environment);
            node.setProperty(PropertyType.TYPE, ((NonTerminalNode) node.getChidlAt(0)).getProperty(PropertyType.TYPE));
            node.setProperty(PropertyType.L_EXPRESSION,
                    ((NonTerminalNode) node.getChidlAt(0)).getProperty(PropertyType.L_EXPRESSION));
        } else if (node.getChildNodeNumber() == 3) {
            node.getChidlAt(0).visitNode(environment);
            node.getChidlAt(2).visitNode(environment);

            node.setProperty(PropertyType.TYPE, ((NonTerminalNode) node.getChidlAt(2)).getProperty(PropertyType.TYPE));

            node.setProperty(PropertyType.L_EXPRESSION, new Property(0));
        } else {
            // losa produkcija
        }

    }

    @Override
    public void emit(NonTerminalNode node, Environment environment) {
        int childrenNumber = node.getChildNodeNumber();
        if (childrenNumber == 1) {
            node.getChidlAt(0).visitNode(environment);
        } else {
            node.getChidlAt(0).visitNode(environment);
            node.getChidlAt(2).visitNode(environment);
        }
    }
}
