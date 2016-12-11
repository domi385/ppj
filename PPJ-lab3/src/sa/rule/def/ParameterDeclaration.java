package sa.rule.def;

import sa.Environment;
import sa.PropertyType;
import sa.Types;
import sa.node.NonTerminalNode;
import sa.rule.RuleStrategy;
import sa.rule.RuleUtility;

public class ParameterDeclaration extends RuleStrategy {

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 2) {
            node.getChidlAt(0).visitNode(environment);
            RuleUtility.checkNotType(node.getChidlAt(0), Types.VOID);
            node.getProperty(PropertyType.TYPE).setValue(
                    ((NonTerminalNode) node.getChidlAt(0)).getProperty(PropertyType.TYPE)
                    .getValue());
            // TODO

        } else if (node.getChildNodeNumber() == 5) {
            // TODO
        } else {
            // loša produkcija
        }
    }

}
