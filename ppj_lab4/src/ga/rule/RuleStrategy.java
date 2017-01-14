package ga.rule;

import ga.Environment;
import ga.node.NonTerminalNode;

public abstract class RuleStrategy {

    public abstract void evaluate(NonTerminalNode node, Environment environment);

    public void emit(NonTerminalNode node, Environment environment) {

    }
}
