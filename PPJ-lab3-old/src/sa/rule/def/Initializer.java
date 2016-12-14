package sa.rule.def;

import java.util.ArrayList;
import java.util.List;

import sa.Environment;
import sa.Property;
import sa.PropertyType;
import sa.Types;
import sa.node.Node;
import sa.node.NonTerminalNode;
import sa.node.TerminalNode;
import sa.rule.RuleStrategy;

public class Initializer extends RuleStrategy {

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 1) {

            node.getChidlAt(0).visitNode(environment);
            NonTerminalNode child = (NonTerminalNode) node.getChidlAt(0);

            if (child.getProperty(PropertyType.TYPE).getValue() == Types.ARRAY_CONST_CHAR) {

                // TODO ne zaboravi +1 !!!!

                int length = getCharArrayLength(node) + 1;
                node.setProperty(PropertyType.NUM_ELEM, new Property(length));
                List<Types> types = new ArrayList<>();
                for (int i = 0; i < length; i++) {
                    types.add(Types.CONST_CHAR);
                }
                node.setProperty(PropertyType.TYPE, new Property(Types.ARRAY_CONST_CHAR));
                node.setProperty(PropertyType.TYPES, new Property(types));
            } else {

                node.setProperty(PropertyType.TYPE, child.getProperty(PropertyType.TYPE));
            }

        } else if (node.getChildNodeNumber() == 3) {
            node.getChidlAt(1).visitNode(environment);

            @SuppressWarnings("unchecked")
            List<Types> expressionListTypes = (List<Types>) ((NonTerminalNode) node.getChidlAt(1))
            .getProperty(PropertyType.TYPES).getValue();

            node.setProperty(PropertyType.TYPES, new Property(expressionListTypes));

            Integer expressionListElementNumber = ((NonTerminalNode) node.getChidlAt(1))
                    .getProperty(PropertyType.NUM_ELEM).getValue();
            node.setProperty(PropertyType.NUM_ELEM, new Property(expressionListElementNumber));
        } else {
            // losa produkcija
        }
    }

    private int getCharArrayLength(NonTerminalNode node) {
        Node currNode = node;
        while (!currNode.getSymbol().getSymbol().equals("NIZ_ZNAKOVA") && currNode != null
                && currNode.getChildNodeNumber() > 0) {

            currNode = currNode.getChidlAt(0);
        }
        if (currNode == null) {
            return -1;
        }

        return ((TerminalNode) currNode).getValue().length() - 2;

    }

}
