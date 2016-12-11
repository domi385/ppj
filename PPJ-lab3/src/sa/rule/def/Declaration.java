package sa.rule.def;

import sa.Environment;
import sa.PropertyType;
import sa.node.NonTerminalNode;
import sa.rule.RuleStrategy;

public class Declaration extends RuleStrategy {

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 3) {
            node.getChidlAt(0).visitNode(environment);
            NonTerminalNode typeName = (NonTerminalNode) node.getChidlAt(0);
            NonTerminalNode initDeclarators = (NonTerminalNode) node.getChidlAt(1);
            initDeclarators.getProperty(PropertyType.N_TYPE).setValue(
                    typeName.getProperty(PropertyType.TYPE).getValue());
        } else {
            // loša produkcija
        }
    }

}
