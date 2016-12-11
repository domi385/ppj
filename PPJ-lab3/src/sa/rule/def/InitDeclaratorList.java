package sa.rule.def;

import sa.Environment;
import sa.PropertyType;
import sa.node.NonTerminalNode;
import sa.rule.RuleStrategy;

public class InitDeclaratorList extends RuleStrategy {

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 1) {
            NonTerminalNode initDeclarator = (NonTerminalNode) node.getChidlAt(0);
            initDeclarator.getProperty(PropertyType.N_TYPE).setValue(
                    node.getProperty(PropertyType.N_TYPE).getValue());
            initDeclarator.visitNode(environment);
        } else if (node.getChildNodeNumber() == 3) {
            NonTerminalNode listInitDeclarator = (NonTerminalNode) node.getChidlAt(0);
            listInitDeclarator.getProperty(PropertyType.N_TYPE).setValue(
                    node.getProperty(PropertyType.N_TYPE).getValue());
            listInitDeclarator.visitNode(environment);

            NonTerminalNode initDeclarator = (NonTerminalNode) node.getChidlAt(2);
            initDeclarator.getProperty(PropertyType.N_TYPE).setValue(
                    node.getProperty(PropertyType.N_TYPE).getValue());
            initDeclarator.visitNode(environment);
        } else {
            // loša produkcija
        }
    }

}
