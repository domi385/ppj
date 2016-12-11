package sa.rule.command;

import sa.Environment;
import sa.PropertyType;
import sa.Types;
import sa.node.NonTerminalNode;
import sa.rule.RuleStrategy;

public class ExpressionCommand extends RuleStrategy {

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 1) {
            node.getProperty(PropertyType.TYPE).setValue(Types.INT);
        } else if (node.getChildNodeNumber() == 2) {
            node.getChidlAt(0).visitNode(environment);
            node.getProperty(PropertyType.TYPE).setValue(
                    ((NonTerminalNode) node.getChidlAt(0)).getProperty(PropertyType.TYPE)
                    .getValue());
        } else {
            // neispravna produkcija
        }
    }

}
