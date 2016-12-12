package sa.rule.expression;

import sa.Environment;
import sa.Property;
import sa.PropertyType;
import sa.Types;
import sa.node.NonTerminalNode;
import sa.rule.RuleStrategy;
import sa.rule.RuleUtility;

public class TypeNameExpression extends RuleStrategy {

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 1) {
            node.getChidlAt(0).visitNode(environment);
            node.setProperty(PropertyType.TYPE,
                    ((NonTerminalNode) node.getChidlAt(0)).getProperty(PropertyType.TYPE));
        } else if (node.getChildNodeNumber() == 2) {
            node.getChidlAt(0).visitNode(environment);
            RuleUtility.checkNotType(node.getChidlAt(0), Types.VOID);

            node.setProperty(
                    PropertyType.TYPE,
                    new Property(Types.getConstType(((NonTerminalNode) node.getChidlAt(0))
                            .getProperty(PropertyType.TYPE).getValue())));
        } else {
            // loša produkcija
        }
    }

}
