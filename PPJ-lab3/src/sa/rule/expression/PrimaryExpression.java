package sa.rule.expression;

import sa.Environment;
import sa.PropertyType;
import sa.Types;
import sa.node.NonTerminalNode;
import sa.node.TerminalNode;
import sa.rule.RuleStrategy;
import sa.rule.RuleUtility;

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
                RuleUtility.checkIntValue(childNode.getValue());
                node.getProperty(PropertyType.TYPE).setValue(Types.INT);
                node.getProperty(PropertyType.L_EXPRESSION).setValue("0");
            } else if (childNode.getSymbol().getSymbol().equals("ZNAK")) {
                RuleUtility.checkCharValue(childNode.getValue());
                node.getProperty(PropertyType.TYPE).setValue(Types.CHAR);
                node.getProperty(PropertyType.L_EXPRESSION).setValue("0");
            } else if (childNode.getSymbol().getSymbol().equals("NIZ_ZNAKOVA")) {
                RuleUtility.checkConstCharArray(childNode.getValue());
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
}
