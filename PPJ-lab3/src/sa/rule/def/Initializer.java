package sa.rule.def;

import java.util.ArrayList;
import java.util.List;

import sa.Environment;
import sa.Property;
import sa.PropertyType;
import sa.Types;
import sa.node.NonTerminalNode;
import sa.rule.RuleStrategy;

public class Initializer extends RuleStrategy {

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 1) {

            node.getChidlAt(0).visitNode(environment);
            NonTerminalNode child = (NonTerminalNode) node.getChidlAt(0);

            if (child.getProperty(PropertyType.TYPE).getValue() == Types.ARRAY_CONST_CHAR) {

                // TODO ne zaboravi +1 !!!!

                // int length =
                //
                // node.setProperty(PropertyType.NUM_ELEM, new
                // Property(length));
                // List<Types> types = new ArrayList<>();
                // for (int i=0; i<length; i++){
                // types.add(Types.CONST_CHAR);
                // }
                // node.setProperty(PropertyType.TYPE, new
                // Property(Types.ARRAY_CONST_CHAR));
                // node.setProperty(PropertyType.TYPES, new Property(types));

            } else {

                node.setProperty(PropertyType.TYPE, child.getProperty(PropertyType.TYPE));
            }

        } else if (node.getChildNodeNumber() == 3) {
            node.getChidlAt(1).visitNode(environment);

            @SuppressWarnings("unchecked")
            List<Types> expressionListTypes = (List<Types>) ((NonTerminalNode) node.getChidlAt(1))
            .getProperty(PropertyType.TYPES).getValue();

            node.getProperty(PropertyType.TYPES).setValue(new ArrayList<>(expressionListTypes));

            Integer expressionListElementNumber = ((NonTerminalNode) node.getChidlAt(1))
                    .getProperty(PropertyType.NUM_ELEM).getValue();
            node.setProperty(PropertyType.NUM_ELEM, new Property(expressionListElementNumber));
        } else {
            // loša produkcija
        }
    }

}
