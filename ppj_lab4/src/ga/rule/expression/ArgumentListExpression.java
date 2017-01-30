package ga.rule.expression;

import ga.Environment;
import ga.Property;
import ga.PropertyType;
import ga.Ulaz;
import ga.Types;
import ga.node.NonTerminalNode;
import ga.rule.RuleStrategy;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void emit(NonTerminalNode node, Environment environment) {
        if(node.getChildNodeNumber() == 1) {
            node.getChidlAt(0).visitNode(environment);
            Ulaz.numberOfParameters++;
        } else {
            node.getChidlAt(0).visitNode(environment);
            node.getChidlAt(2).visitNode(environment);
            Ulaz.numberOfParameters++;
        }
    }
}
