package ga.rule.command;

import ga.Environment;
import ga.PropertyType;
import ga.Types;
import ga.node.NonTerminalNode;
import ga.rule.RuleStrategy;
import ga.rule.def.Declaration;
import ga.rule.def.DeclarationList;
import ga.rule.def.ParameterList;

import java.util.LinkedHashMap;
import java.util.List;

public class ComplexCommand extends RuleStrategy {

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        Environment localEnvironment = new Environment(environment);
        environment.addChildrenEvironment(localEnvironment);
        if (node.hasProperty(PropertyType.PARAMETER_NAMES) && node.hasProperty(PropertyType.PARAMETER_TYPES)) {
            List<String> parameterNames = node.getProperty(PropertyType.PARAMETER_NAMES).getValue();
            List<Types> parameterTypes = node.getProperty(PropertyType.PARAMETER_TYPES).getValue();
            for (int i = 0, end = parameterNames.size(); i < end; i++) {
                // localEnvironment.declareParameter(parameterNames.get(i), parameterTypes.get(i));
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

    @Override
    public void emit(NonTerminalNode node, Environment environment) {
        Environment localEnvironment = new Environment(environment);
        environment.addChildrenEvironment(localEnvironment);

        if(ParameterList.parameters != null) {
            for(String name : ParameterList.parameters) {
                localEnvironment.declareParameter(name, 1);
            }
            ParameterList.parameters = null;
        }

        System.out.println("\t PUSH R5");

        if (node.getChildNodeNumber() == 3) {
            //TODO
            System.out.println("\t MOVE R7, R5");
            node.getChidlAt(1).visitNode(localEnvironment);
            System.out.println("\t POP R5");
        } else {
            DeclarationList.params = new LinkedHashMap<>();
            node.getChidlAt(1).visitNode(localEnvironment);
            DeclarationList.params = null;
            System.out.println("\t MOVE R7, R5");

            //TODO
            node.getChidlAt(2).visitNode(localEnvironment);
            System.out.println("\t POP R5");
        }

        int offset = localEnvironment.totalLocalOffset();
        System.out.println("\t ADD R7, %D " + offset + ", R7");
    }
}
