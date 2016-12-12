package sa.rule.expression;

import sa.Environment;
import sa.Property;
import sa.PropertyType;
import sa.SemanticException;
import sa.Types;
import sa.node.NonTerminalNode;
import sa.node.TerminalNode;
import sa.rule.RuleStrategy;

public class PrimaryExpression extends RuleStrategy {

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 1) {
            TerminalNode childNode = (TerminalNode) node.getChidlAt(0);
            if (childNode.getSymbol().getSymbol().equals("IDN")) {
                boolean declared = environment.isDeclared(childNode.getValue());
                if (!declared) {
                    throw new SemanticException(node.toString());
                }
                Types identificatorType = environment.getIdentificatorType(childNode.getValue());
                node.setProperty(PropertyType.TYPE, new Property(identificatorType));

                node.setProperty(PropertyType.L_EXPRESSION,
                        new Property(isLExpression(environment, childNode.getValue())));

            } else if (childNode.getSymbol().getSymbol().equals("BROJ")) {
                if (!checkIntValue(childNode.getValue())) {
                    throw new SemanticException(node.toString());
                }
                node.setProperty(PropertyType.TYPE, new Property(Types.INT));
                node.setProperty(PropertyType.L_EXPRESSION, new Property(0));
            } else if (childNode.getSymbol().getSymbol().equals("ZNAK")) {
                if (!checkCharValue(childNode.getValue())) {
                    throw new SemanticException(node.toString());
                }
                node.setProperty(PropertyType.TYPE, new Property(Types.CHAR));
                node.setProperty(PropertyType.L_EXPRESSION, new Property(0));
            } else if (childNode.getSymbol().getSymbol().equals("NIZ_ZNAKOVA")) {
                if (!checkConstCharArray(childNode.getValue())) {
                    throw new SemanticException(node.toString());
                }
                node.setProperty(PropertyType.TYPE, new Property(Types.ARRAY_CONST_CHAR));
                node.setProperty(PropertyType.L_EXPRESSION, new Property(0));
            } else {
                // loša produkcija
            }
        } else if (node.getChildNodeNumber() == 3) {
            node.getChidlAt(1).visitNode(environment);
            node.setProperty(PropertyType.TYPE,
                    ((NonTerminalNode) node.getChidlAt(1)).getProperty(PropertyType.TYPE));
            node.setProperty(PropertyType.L_EXPRESSION,
                    ((NonTerminalNode) node.getChidlAt(1)).getProperty(PropertyType.L_EXPRESSION));
        } else {
            // loša produkcija
        }
    }

    private Integer isLExpression(Environment environment, String identificatorName) {
        // TODO Auto-generated method stub
        return null;
    }

    public static boolean checkCharValue(String value) {
        return false;
        // TODO Auto-generated method stub

    }

    public static boolean checkConstCharArray(String value) {
        return false;
        // TODO Auto-generated method stub

    }

    public static boolean checkIntValue(String value) {
        return false;
        // TODO Auto-generated method stub

    }
}
