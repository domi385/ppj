package sa.rule.expression;

import sa.Environment;
import sa.PropertyType;
import sa.Types;
import sa.node.NonTerminalNode;
import sa.node.TerminalNode;
import sa.rule.RuleStrategy;

public class TypeSpecificatorExpression extends RuleStrategy {

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 1) {
            TerminalNode childNode = (TerminalNode) node.getChidlAt(0);
            if (childNode.getSymbol().getSymbol().equals("KR_VOID")) {
                node.getProperty(PropertyType.TYPE).setValue(Types.VOID);

            } else if (childNode.getSymbol().getSymbol().equals("KR_CHAR")) {
                node.getProperty(PropertyType.TYPE).setValue(Types.CHAR);

            } else if (childNode.getSymbol().getSymbol().equals("KR_INT")) {
                node.getProperty(PropertyType.TYPE).setValue(Types.INT);

            } else {
                // loša produkcija
            }
        } else {
            // loša produkcija
        }
    }

}
