package sa.rule.command;

import sa.Environment;
import sa.node.NonTerminalNode;
import sa.rule.RuleStrategy;

public class TranslationUnitExpressionCommand extends RuleStrategy {

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 1) {
            node.getChidlAt(0).visitNode(environment);
        } else if (node.getChildNodeNumber() == 2) {
            node.getChidlAt(0).visitNode(environment);
            node.getChidlAt(1).visitNode(environment);
        } else {
            // Losa produkcija
        }
    }

}
