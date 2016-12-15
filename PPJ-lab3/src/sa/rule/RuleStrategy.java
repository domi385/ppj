package sa.rule;

import sa.Environment;
import sa.node.NonTerminalNode;

public abstract class RuleStrategy {

    public abstract void evaluate(NonTerminalNode node, Environment environment);

}
