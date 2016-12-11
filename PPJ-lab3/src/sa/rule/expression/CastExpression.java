package sa.rule.expression;

import sa.Environment;
import sa.PropertyType;
import sa.Types;
import sa.node.NonTerminalNode;
import sa.rule.RuleStrategy;

public class CastExpression extends RuleStrategy {

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
        } else if (node.getChildNodeNumber() == 4) {
            node.getChidlAt(1).visitNode(environment);
            node.getChidlAt(3).visitNode(environment);

            Types originalType = (Types) ((NonTerminalNode) node.getChidlAt(3)).getProperty(
                    PropertyType.TYPE).getValue();
            Types castedType = (Types) ((NonTerminalNode) node.getChidlAt(1)).getProperty(
                    PropertyType.TYPE).getValue();

            isCastable(originalType, castedType);

            node.getProperty(PropertyType.TYPE).setValue(castedType);
            node.getProperty(PropertyType.L_EXPRESSION).setValue(0);

        } else {
            // loša produkcija
        }
    }

    public static void isCastable(Types originalType, Types castedType) {
        // TODO Auto-generated method stub

    }

}
