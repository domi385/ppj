package sa.rule.def;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sa.Environment;
import sa.PropertyType;
import sa.Types;
import sa.node.NonTerminalNode;
import sa.rule.RuleStrategy;

public class JoinmentExpressionList extends RuleStrategy {

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 1) {

            node.getChidlAt(0).visitNode(environment);
            node.getProperty(PropertyType.TYPES).setValue(
                    new ArrayList<Types>(Arrays.asList(((NonTerminalNode) node.getChidlAt(0))
                            .getProperty(PropertyType.TYPE).getValue())));
            node.getProperty(PropertyType.NUM_ELEM).setValue(1);
        } else if (node.getChildNodeNumber() == 3) {
            node.getChidlAt(0).visitNode(environment);
            node.getChidlAt(2).visitNode(environment);

            @SuppressWarnings("unchecked")
            List<Types> expressionListTypes = (List<Types>) ((NonTerminalNode) node.getChidlAt(0))
            .getProperty(PropertyType.TYPES).getValue();
            Types expressionType = ((NonTerminalNode) node.getChidlAt(2)).getProperty(
                    PropertyType.TYPE).getValue();

            List<Types> types = new ArrayList<>();
            types.addAll(expressionListTypes);
            types.add(expressionType);
            node.getProperty(PropertyType.TYPES).setValue(types);

            Integer expressionListElementNumber = ((NonTerminalNode) node.getChidlAt(0))
                    .getProperty(PropertyType.NUM_ELEM).getValue();
            node.getProperty(PropertyType.NUM_ELEM).setValue(expressionListElementNumber + 1);
        } else {
            // loša produkcija
        }
    }

}
