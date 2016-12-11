package sa.rule.command;

import sa.Environment;
import sa.node.NonTerminalNode;
import sa.rule.RuleStrategy;

public class ExternalDeclarationCommand extends RuleStrategy {

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 1) {
            node.getChidlAt(0).visitNode(environment);
        } else {
            // loša produkcija
        }

    }
}
