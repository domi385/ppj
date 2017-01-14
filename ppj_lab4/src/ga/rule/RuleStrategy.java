package ga.rule;

import ga.Environment;
import ga.node.NonTerminalNode;

import java.util.Objects;

public abstract class RuleStrategy {

    public abstract void evaluate(NonTerminalNode node, Environment environment);

    public void emit(NonTerminalNode node, Environment environment) {
    }

}
