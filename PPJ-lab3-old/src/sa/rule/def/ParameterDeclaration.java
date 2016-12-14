package sa.rule.def;

import sa.Environment;
import sa.Property;
import sa.PropertyType;
import sa.SemanticException;
import sa.Types;
import sa.node.NonTerminalNode;
import sa.node.TerminalNode;
import sa.rule.RuleStrategy;
import sa.rule.RuleUtility;

public class ParameterDeclaration extends RuleStrategy {

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 2) {
            node.getChidlAt(0).visitNode(environment);
            RuleUtility.checkNotType((NonTerminalNode) node.getChidlAt(0), Types.VOID);
            node.setProperty(PropertyType.TYPE,
                    ((NonTerminalNode) node.getChidlAt(0)).getProperty(PropertyType.TYPE));
            node.setProperty(PropertyType.NAME,
                    new Property(((TerminalNode) node.getChidlAt(1)).getValue()));

        } else if (node.getChildNodeNumber() == 4) {
            node.getChidlAt(0).visitNode(environment);
            if (!RuleUtility.checkNotType((NonTerminalNode) node.getChidlAt(0), Types.VOID)) {
                throw new SemanticException(node.toString());
            }
            node.setProperty(
                    PropertyType.TYPE,
                    new Property(Types.getArrayType(((NonTerminalNode) node.getChidlAt(0))
                            .getProperty(PropertyType.TYPE).getValue())));
            node.setProperty(PropertyType.NAME,
                    new Property(((TerminalNode) node.getChidlAt(1)).getValue()));

        } else {
            // losa produkcija
        }
    }

}
