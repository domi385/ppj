package ga.rule.expression;

import ga.Environment;
import ga.Property;
import ga.PropertyType;
import ga.SemanticException;
import ga.Types;
import ga.node.NonTerminalNode;
import ga.rule.RuleStrategy;
import ga.rule.RuleUtility;

public class TypeNameExpression extends RuleStrategy {

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 1) {
            node.getChidlAt(0).visitNode(environment);
            node.setProperty(PropertyType.TYPE,
                    ((NonTerminalNode) node.getChidlAt(0)).getProperty(PropertyType.TYPE));

        } else if (node.getChildNodeNumber() == 2) {

            node.getChidlAt(1).visitNode(environment);

            if (!RuleUtility.checkNotType((NonTerminalNode) node.getChidlAt(1), Types.VOID)) {
                throw new SemanticException(node.toString());
            }

            node.setProperty(
                    PropertyType.TYPE,
                    new Property(Types.getConstType(((NonTerminalNode) node.getChidlAt(1))
                            .getProperty(PropertyType.TYPE).getValue())));
        } else {
            // losa produkcija
        }
    }

}
