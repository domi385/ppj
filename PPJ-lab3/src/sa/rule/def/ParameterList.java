package sa.rule.def;

import java.util.ArrayList;
import java.util.List;

import sa.Environment;
import sa.Property;
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
            List<Types> parameterTypes = new ArrayList<Types>();
            parameterTypes.add(((NonTerminalNode) node.getChidlAt(0))
                    .getProperty(PropertyType.TYPE).getValue());

            node.setProperty(PropertyType.TYPES, new Property(parameterTypes));

            List<String> parameterNames = new ArrayList<String>();
            parameterNames.add(((NonTerminalNode) node.getChidlAt(0))
                    .getProperty(PropertyType.NAME).getValue());
            node.setProperty(PropertyType.NAMES, new Property(parameterNames));

        } else if (node.getChildNodeNumber() == 3) {
            node.getChidlAt(0).visitNode(environment);
            node.getChidlAt(2).visitNode(environment);

            List<Types> parameterListTypes = (List<Types>) ((NonTerminalNode) node.getChidlAt(0))
                    .getProperty(PropertyType.TYPES).getValue();
            Types parameterDeclarationType = ((NonTerminalNode) node.getChidlAt(2)).getProperty(
                    PropertyType.TYPE).getValue();
            List<String> parameterListNames = (List<String>) ((NonTerminalNode) node.getChidlAt(0))
                    .getProperty(PropertyType.NAMES).getValue();
            String parameterDeclarationName = ((NonTerminalNode) node.getChidlAt(2)).getProperty(
                    PropertyType.NAME).getValue();

            if (parameterListNames.contains(parameterDeclarationName)) {
                throw new RuntimeException();
            }

            List<Types> types = new ArrayList<>();
            types.addAll(parameterListTypes);
            types.add(parameterDeclarationType);
            node.setProperty(PropertyType.TYPES, new Property(types));

            List<String> names = new ArrayList<>();
            names.addAll(parameterListNames);
            names.add(parameterDeclarationName);
            node.setProperty(PropertyType.NAMES, new Property(names));
        } else {
            // loša produkcija
        }
    }

}
