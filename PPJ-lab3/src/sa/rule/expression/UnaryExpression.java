package sa.rule.expression;

import sa.Environment;
import sa.Property;
import sa.PropertyType;
import sa.SemanticException;
import sa.Types;
import sa.node.NonTerminalNode;
import sa.rule.RuleStrategy;
import sa.rule.RuleUtility;

public class UnaryExpression extends RuleStrategy {

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 1) {
            node.getChidlAt(0).visitNode(environment);
            node.setProperty(PropertyType.TYPE,
                    ((NonTerminalNode) node.getChidlAt(0)).getProperty(PropertyType.TYPE));
            node.setProperty(PropertyType.L_EXPRESSION,
                    ((NonTerminalNode) node.getChidlAt(0)).getProperty(PropertyType.L_EXPRESSION));
        } else if (node.getChildNodeNumber() == 2) {
            NonTerminalNode secondChild = (NonTerminalNode) node.getChidlAt(1);
            if (secondChild.getSymbol().getSymbol().equals("<unarni_izraz>")) {
                secondChild.visitNode(environment);
                if (!checkProperty(secondChild, PropertyType.TYPE, Types.INT)
                        || !checkProperty(secondChild, PropertyType.L_EXPRESSION, 1)) {
                    throw new SemanticException(node.toString());
                }

                node.getProperty(PropertyType.TYPE).setValue(Types.INT);
                node.getProperty(PropertyType.L_EXPRESSION).setValue(0);
            } else if (secondChild.getSymbol().getSymbol().equals("<cast_izraz>")) {
                secondChild.visitNode(environment);
                if (!RuleUtility.checkType(secondChild, Types.INT)) {
                    throw new SemanticException(node.toString());
                }
                node.setProperty(PropertyType.TYPE, new Property(Types.INT));
                node.setProperty(PropertyType.L_EXPRESSION, new Property(0));
            } else {
                // loša produkcija
            }
        } else {
            // loša produkcija
        }
    }

    public static boolean checkProperty(NonTerminalNode node, PropertyType type, Object value) {
        Property property = node.getProperty(type);
        return property.getValue().equals(value);
    }
}
