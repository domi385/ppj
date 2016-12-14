package sa.rule.expression;

import java.util.ArrayList;
import java.util.List;

import sa.Environment;
import sa.Property;
import sa.PropertyType;
import sa.Types;
import sa.node.NonTerminalNode;
import sa.rule.RuleStrategy;

public class ArgumentListExpression extends RuleStrategy {

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 1) {
            node.getChidlAt(0).visitNode(environment);
            List<Types> types = new ArrayList<>();
            types.add(((NonTerminalNode) node.getChidlAt(0)).getProperty(PropertyType.TYPE)
                    .getValue());
            node.setProperty(PropertyType.TYPES, new Property(types));

        } else if (node.getChildNodeNumber() == 3) {
            node.getChidlAt(0).visitNode(environment);
            node.getChidlAt(2).visitNode(environment);
            List<Types> types = new ArrayList<>();
            List<Types> argumentListTypes = ((NonTerminalNode) node.getChidlAt(0)).getProperty(
                    PropertyType.TYPES).getValue();
            for (Types argumentType : argumentListTypes) {
                types.add(argumentType);
            }
            Types argumentType = ((NonTerminalNode) node.getChidlAt(2)).getProperty(
                    PropertyType.TYPE).getValue();
            types.add(argumentType);
            node.setProperty(PropertyType.TYPES, new Property(types));
        } else {
            // losa produkcija
        }
    }

}
