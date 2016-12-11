package sa.rule.expression;

import sa.Environment;
import sa.PropertyType;
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
                    throw new RuntimeException();
                }
                node.getProperty(PropertyType.TYPE).setValue(
                        ((NonTerminalNode) node.getChidlAt(0)).getProperty(PropertyType.TYPE)
                        .getValue());
                node.getProperty(PropertyType.L_EXPRESSION).setValue(
                        ((NonTerminalNode) node.getChidlAt(0)).getProperty(
                                PropertyType.L_EXPRESSION).getValue());
            } else if (childNode.getSymbol().getSymbol().equals("BROJ")) {
                checkIntValue(childNode.getValue());
                node.getProperty(PropertyType.TYPE).setValue(Types.INT);
                node.getProperty(PropertyType.L_EXPRESSION).setValue("0");
            } else if (childNode.getSymbol().getSymbol().equals("ZNAK")) {
                checkCharValue(childNode.getValue());
                node.getProperty(PropertyType.TYPE).setValue(Types.CHAR);
                node.getProperty(PropertyType.L_EXPRESSION).setValue("0");
            } else if (childNode.getSymbol().getSymbol().equals("NIZ_ZNAKOVA")) {
                checkConstCharArray(childNode.getValue());
                node.getProperty(PropertyType.TYPE).setValue(Types.ARRAY_CONST_CHAR);
                node.getProperty(PropertyType.L_EXPRESSION).setValue("0");
            } else {
                // loša produkcija
            }
        } else if (node.getChildNodeNumber() == 3) {
            node.getChidlAt(1).visitNode(environment);
            node.getProperty(PropertyType.TYPE).setValue(
                    ((NonTerminalNode) node.getChidlAt(1)).getProperty(PropertyType.TYPE)
                    .getValue());
            node.getProperty(PropertyType.L_EXPRESSION).setValue(
                    ((NonTerminalNode) node.getChidlAt(1)).getProperty(PropertyType.L_EXPRESSION)
                    .getValue());
        } else {
            // loša produkcija
        }
    }

    public static void checkCharValue(String value) {
        // TODO Auto-generated method stub

    }

    public static void checkConstCharArray(String value) {
        // TODO Auto-generated method stub

    }

    public static void checkIntValue(String value) {
        // TODO Auto-generated method stub

    }
}
