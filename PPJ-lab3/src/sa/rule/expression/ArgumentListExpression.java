package sa.rule.expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import sa.Environment;
import sa.PropertyType;
import sa.Types;
import sa.node.NonTerminalNode;
import sa.rule.RuleStrategy;

public class ArgumentListExpression extends RuleStrategy {

    @SuppressWarnings("unchecked")
    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 1) {
            node.getChidlAt(0).visitNode(environment);
            node.getProperty(PropertyType.TYPES).setValue(
                    new ArrayList<Types>(Arrays.asList(((NonTerminalNode) node.getChidlAt(0))
                            .getProperty(PropertyType.TYPE).getValue())));

        } else if (node.getChildNodeNumber() == 3) {
            node.getChidlAt(0).visitNode(environment);
            node.getChidlAt(2).visitNode(environment);
            List<Types> types = new ArrayList<>();
            types.addAll((Collection<Types>) ((NonTerminalNode) node.getChidlAt(0)).getProperty(
                    PropertyType.TYPES).getValue());
            types.add(((NonTerminalNode) node.getChidlAt(2)).getProperty(PropertyType.TYPE)
                    .getValue());
            node.getProperty(PropertyType.TYPES).setValue(types);
        } else {
            // loša produkcija
        }
    }

}
