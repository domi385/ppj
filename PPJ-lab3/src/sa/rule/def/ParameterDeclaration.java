package sa.rule.def;

import sa.Environment;
import sa.PropertyType;
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
            RuleUtility.checkNotType(node.getChidlAt(0), Types.VOID);
            node.getProperty(PropertyType.TYPE).setValue(
                    ((NonTerminalNode) node.getChidlAt(0)).getProperty(PropertyType.TYPE)
                            .getValue());
            node.getProperty(PropertyType.NAME).setValue(
                    ((TerminalNode) node.getChidlAt(0)).getValue());

        } else if (node.getChildNodeNumber() == 5) {
            node.getChidlAt(0).visitNode(environment);
            RuleUtility.checkNotType(node.getChidlAt(0), Types.VOID);
            node.getProperty(PropertyType.TYPE).setValue(
                    Types.getArrayType(((NonTerminalNode) node.getChidlAt(0)).getProperty(
                            PropertyType.TYPE).getValue()));
            node.getProperty(PropertyType.NAME).setValue(
                    ((TerminalNode) node.getChidlAt(0)).getValue());

        } else {
            // loša produkcija
        }
    }

}
