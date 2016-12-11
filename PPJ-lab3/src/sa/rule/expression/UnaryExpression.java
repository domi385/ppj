package sa.rule.expression;

import sa.Environment;
import sa.PropertyType;
import sa.Types;
import sa.node.Node;
import sa.node.NonTerminalNode;
import sa.rule.RuleStrategy;
import sa.rule.RuleUtility;

public class UnaryExpression extends RuleStrategy {

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
        } else if (node.getChildNodeNumber() == 2) {
            Node firstChild = node.getChidlAt(0);
            NonTerminalNode secondChild = (NonTerminalNode) node.getChidlAt(1);
            if (secondChild.getSymbol().getSymbol().equals("<unarni_izraz>")) {
                secondChild.visitNode(environment);
                RuleUtility.checkProperty(secondChild, PropertyType.TYPE, Types.INT);
                RuleUtility.checkProperty(secondChild, PropertyType.L_EXPRESSION, 1);

                node.getProperty(PropertyType.TYPE).setValue(Types.INT);
                node.getProperty(PropertyType.L_EXPRESSION).setValue(0);
            } else if (secondChild.getSymbol().getSymbol().equals("<cast_izraz>")) {
                secondChild.visitNode(environment);
                RuleUtility.checkType(secondChild, Types.INT);
                node.getProperty(PropertyType.TYPE).setValue(Types.INT);
                node.getProperty(PropertyType.L_EXPRESSION).setValue(0);
            } else {
                // loša produkcija
            }
        } else {
            // loša produkcija
        }
    }

}
