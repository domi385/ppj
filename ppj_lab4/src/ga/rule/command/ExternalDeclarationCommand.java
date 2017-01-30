package ga.rule.command;

import ga.Environment;
import ga.node.NonTerminalNode;
import ga.rule.RuleStrategy;

public class ExternalDeclarationCommand extends RuleStrategy {

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 1) {
            node.getChidlAt(0).visitNode(environment);
        } else {
            // losa produkcija
        }

    }

    @Override
    public void emit(NonTerminalNode node, Environment environment) {
        node.getChidlAt(0).visitNode(environment);
    }
}
