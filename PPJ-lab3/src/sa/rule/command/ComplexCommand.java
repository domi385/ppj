package sa.rule.command;

import sa.Environment;
import sa.node.NonTerminalNode;
import sa.rule.RuleStrategy;

public class ComplexCommand extends RuleStrategy {

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        Environment localEnvironment = new Environment(environment);
        if (node.getChildNodeNumber() == 3) {
            node.getChidlAt(1).visitNode(localEnvironment);
        } else if (node.getChildNodeNumber() == 4) {
            node.getChidlAt(1).visitNode(localEnvironment);
            node.getChidlAt(2).visitNode(localEnvironment);
        } else {
            // exception
        }

    }

}
