package ga.rule.expression;

import ga.Environment;
import ga.Property;
import ga.PropertyType;
import ga.SemanticException;
import ga.Types;
import ga.node.NonTerminalNode;
import ga.rule.RuleStrategy;
import ga.rule.RuleUtility;

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
                if (!RuleUtility.checkProperty(secondChild, PropertyType.TYPE, Types.INT)
                        || !RuleUtility.checkProperty(secondChild, PropertyType.L_EXPRESSION, 1)) {
                    throw new SemanticException(node.toString());
                }

                node.setProperty(PropertyType.TYPE, new Property(Types.INT));
                node.setProperty(PropertyType.L_EXPRESSION, new Property(0));

            } else if (secondChild.getSymbol().getSymbol().equals("<cast_izraz>")) {
                secondChild.visitNode(environment);
                if (!RuleUtility.checkType(secondChild, Types.INT)) {
                    throw new SemanticException(node.toString());
                }
                node.setProperty(PropertyType.TYPE, new Property(Types.INT));
                node.setProperty(PropertyType.L_EXPRESSION, new Property(0));
            } else {
                // losa produkcija
            }
        } else {
            // losa produkcija
        }
    }

    @Override
    public void emit(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 1) {
            node.getChidlAt(0).visitNode(environment);
        } else {
            if(node.getChidlAt(0).getSymbol().getSymbol().equals("<unarni_operator>")) {
                node.getChidlAt(1).visitNode(environment);
                node.getChidlAt(0).visitNode(environment);
            } else if(node.getChidlAt(0).getSymbol().getSymbol().equals("OP_INC")) {
                node.getChidlAt(1).visitNode(environment);

                System.out.println("\t POP R0");
                System.out.println("\t SUB R0, 1, R0");
                System.out.println("\t PUSH R0");
            } else {
                node.getChidlAt(1).visitNode(environment);

                System.out.println("\t POP R0");
                System.out.println("\t SUB R0, 1, R0");
                System.out.println("\t PUSH R0");
            }
        }
    }
}
