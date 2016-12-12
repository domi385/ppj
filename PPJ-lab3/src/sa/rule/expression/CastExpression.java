package sa.rule.expression;

import sa.Environment;
import sa.Property;
import sa.PropertyType;
import sa.Types;
import sa.node.NonTerminalNode;
import sa.rule.RuleStrategy;

public class CastExpression extends RuleStrategy {

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 1) {
            node.getChidlAt(0).visitNode(environment);

            node.setProperty(PropertyType.TYPE,
                    ((NonTerminalNode) node.getChidlAt(0)).getProperty(PropertyType.TYPE));
            node.setProperty(PropertyType.L_EXPRESSION,
                    ((NonTerminalNode) node.getChidlAt(0)).getProperty(PropertyType.L_EXPRESSION));
        } else if (node.getChildNodeNumber() == 4) {
            node.getChidlAt(1).visitNode(environment);
            node.getChidlAt(3).visitNode(environment);

            Types originalType = (Types) ((NonTerminalNode) node.getChidlAt(3)).getProperty(
                    PropertyType.TYPE).getValue();
            Types castedType = (Types) ((NonTerminalNode) node.getChidlAt(1)).getProperty(
                    PropertyType.TYPE).getValue();

            isCastable(originalType, castedType);

            node.setProperty(PropertyType.TYPE, new Property(castedType));
            node.setProperty(PropertyType.L_EXPRESSION, new Property(0));

        } else {
            // loša produkcija
        }
    }

    public static void isCastable(Types originalType, Types castedType) {
        // TODO Auto-generated method stub

    }

}
