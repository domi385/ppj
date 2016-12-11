package sa.rule.def;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sa.Environment;
import sa.PropertyType;
import sa.Types;
import sa.node.NonTerminalNode;
import sa.rule.RuleStrategy;

public class ParameterList extends RuleStrategy {

    @SuppressWarnings("unchecked")
    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 1) {
            node.getChidlAt(0).visitNode(environment);
            node.getProperty(PropertyType.TYPES).setValue(
                    new ArrayList<Types>(Arrays.asList(((NonTerminalNode) node.getChidlAt(0))
                            .getProperty(PropertyType.TYPE).getValue())));
            node.getProperty(PropertyType.NAMES).setValue(
                    new ArrayList<Types>(Arrays.asList(((NonTerminalNode) node.getChidlAt(0))
                            .getProperty(PropertyType.NAME).getValue())));

        } else if (node.getChildNodeNumber() == 3) {
            node.getChidlAt(0).visitNode(environment);
            node.getChidlAt(2).visitNode(environment);

            List<Types> parameterListTypes = (List<Types>) ((NonTerminalNode) node.getChidlAt(0))
                    .getProperty(PropertyType.TYPES).getValue();
            Types parameterDeclarationType = ((NonTerminalNode) node.getChidlAt(2)).getProperty(
                    PropertyType.TYPE).getValue();
            List<Types> parameterListNames = (List<Types>) ((NonTerminalNode) node.getChidlAt(0))
                    .getProperty(PropertyType.NAMES).getValue();
            Types parameterDeclarationName = ((NonTerminalNode) node.getChidlAt(2)).getProperty(
                    PropertyType.NAME).getValue();

            if (parameterListNames.contains(parameterDeclarationName)) {
                throw new RuntimeException();
            }

            List<Types> types = new ArrayList<>();
            types.addAll(parameterListTypes);
            types.add(parameterDeclarationType);
            node.getProperty(PropertyType.TYPES).setValue(types);

            List<Types> names = new ArrayList<>();
            names.addAll(parameterListNames);
            names.add(parameterDeclarationName);
            node.getProperty(PropertyType.NAMES).setValue(names);
        } else {
            // loša produkcija
        }
    }

}
