package sa.rule.command;

import java.util.List;

import sa.Environment;
import sa.PropertyType;
import sa.Types;
import sa.node.NonTerminalNode;
import sa.rule.RuleStrategy;

public class ComplexCommand extends RuleStrategy {

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        Environment localEnvironment = new Environment(environment);
        environment.addChildrenEvironment(localEnvironment);
        if (node.hasProperty(PropertyType.PARAMETER_NAMES)
                && node.hasProperty(PropertyType.PARAMETER_TYPES)) {
            List<String> parameterNames = node.getProperty(PropertyType.PARAMETER_NAMES).getValue();
            List<Types> parameterTypes = node.getProperty(PropertyType.PARAMETER_TYPES).getValue();
            for (int i = 0, end = parameterNames.size(); i < end; i++) {
                localEnvironment.declareIdentificator(parameterNames.get(i), parameterTypes.get(i));
            }
        }

        if (node.getChildNodeNumber() == 3) {
            node.getChidlAt(1).visitNode(localEnvironment);
        } else if (node.getChildNodeNumber() == 4) {
            node.getChidlAt(1).visitNode(localEnvironment);
            node.getChidlAt(2).visitNode(localEnvironment);
        } else {
            // exception
        }

    }

}
