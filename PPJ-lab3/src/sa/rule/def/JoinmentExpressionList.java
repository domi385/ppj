package sa.rule.def;

import java.util.ArrayList;
import java.util.List;

import sa.Environment;
import sa.Property;
import sa.PropertyType;
import sa.Types;
import sa.node.NonTerminalNode;
import sa.rule.RuleStrategy;

public class JoinmentExpressionList extends RuleStrategy {

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 1) {

            node.getChidlAt(0).visitNode(environment);
            List<Types> expressionsTypes = new ArrayList<Types>();
            expressionsTypes.add(((NonTerminalNode) node.getChidlAt(0)).getProperty(
                    PropertyType.TYPE).getValue());
            node.setProperty(PropertyType.TYPES, new Property(expressionsTypes));
            node.setProperty(PropertyType.NUM_ELEM, new Property(1));
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
            node.setProperty(PropertyType.TYPES, new Property(types));

            Integer expressionListElementNumber = ((NonTerminalNode) node.getChidlAt(0))
                    .getProperty(PropertyType.NUM_ELEM).getValue();
            node.setProperty(PropertyType.NUM_ELEM, new Property(expressionListElementNumber + 1));
        } else {
            // losa produkcija
        }
    }

}
