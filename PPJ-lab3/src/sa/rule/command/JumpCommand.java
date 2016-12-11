package sa.rule.command;

import sa.Environment;
import sa.PropertyType;
import sa.Symbol;
import sa.node.NonTerminalNode;
import sa.rule.RuleStrategy;

public class JumpCommand extends RuleStrategy {

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 2) {
            if (node.getChidlAt(0).getSymbol().equals(new Symbol.Terminal("KR_RETURN"))) {
                checkParentFunction(node, "VOID");
            } else {
                checkParent(node, "<naredba_petlje>");
            }
        } else if (node.getChildNodeNumber() == 3) {
            node.getChidlAt(1).visitNode(environment);
            checkParentFunction(node,
                    ((NonTerminalNode) node.getChidlAt(1)).getProperty(PropertyType.TYPE)
                    .getValue());
        } else {
            // loša produkcija
        }
    }

    public static void checkParent(NonTerminalNode node, String string) {
        // TODO Auto-generated method stub

    }

    public static void checkParentFunction(NonTerminalNode node, String string) {
        // TODO Auto-generated method stub

    }

}
