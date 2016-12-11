package sa.rule.command;

import sa.Environment;
import sa.Types;
import sa.node.NonTerminalNode;
import sa.rule.RuleStrategy;
import sa.rule.RuleUtility;

public class LoopCommand extends RuleStrategy {

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 5) {
            node.getChidlAt(2).visitNode(environment);
            RuleUtility.checkType(node.getChidlAt(2), Types.INT);
            node.getChidlAt(4).visitNode(environment);
        } else if (node.getChildNodeNumber() == 6) {
            node.getChidlAt(2).visitNode(environment);
            node.getChidlAt(3).visitNode(environment);
            RuleUtility.checkType(node.getChidlAt(3), Types.INT);
            node.getChidlAt(5).visitNode(environment);

        } else if (node.getChildNodeNumber() == 7) {
            node.getChidlAt(2).visitNode(environment);
            node.getChidlAt(3).visitNode(environment);
            RuleUtility.checkType(node.getChidlAt(3), Types.INT);
            node.getChidlAt(4).visitNode(environment);
            node.getChidlAt(6).visitNode(environment);
        } else {
            // neispravna produkcija
        }
    }

}
