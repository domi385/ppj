package sa.rule.def;

import java.util.ArrayList;
import java.util.List;

import sa.Environment;
import sa.PropertyType;
import sa.Types;
import sa.node.NonTerminalNode;
import sa.node.TerminalNode;
import sa.rule.RuleStrategy;
import sa.rule.RuleUtility;

public class FunctionDefinition extends RuleStrategy {

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 5) {
            node.getChidlAt(0).visitNode(environment);
            RuleUtility.checkNotType(node.getChidlAt(0), Types.CONST_T);
            String functionName = ((TerminalNode) node.getChidlAt(1)).getValue();
            if (environment.isDefinedFunction(functionName)) {
                throw new RuntimeException();
            }
            Types functionReturnType = ((NonTerminalNode) node.getChidlAt(0)).getProperty(
                    PropertyType.TYPE).getValue();
            RuleUtility.checkFunctionsDeclarations(functionName, functionReturnType,
                    new ArrayList<Types>(), environment);
            environment.defineFunction(functionName, functionReturnType, new ArrayList<Types>());
            node.getChidlAt(5).visitNode(environment);
        } else if (node.getChildNodeNumber() == 6) {
            node.getChidlAt(0).visitNode(environment);
            RuleUtility.checkNotType(node.getChidlAt(0), Types.CONST_T);
            String functionName = ((TerminalNode) node.getChidlAt(1)).getValue();
            if (environment.isDefinedFunction(functionName)) {
                throw new RuntimeException();
            }
            node.getChidlAt(2).visitNode(environment);
            Types functionReturnType = ((NonTerminalNode) node.getChidlAt(0)).getProperty(
                    PropertyType.TYPE).getValue();
            List<Types> parameterTypes = ((NonTerminalNode) node.getChidlAt(2)).getProperty(
                    PropertyType.TYPES).getValue();
            RuleUtility.checkFunctionsDeclarations(functionName, functionReturnType,
                    parameterTypes, environment);
            environment.defineFunction(functionName, functionReturnType, parameterTypes);
            // TODO
            node.getChidlAt(5).visitNode(environment);
        } else {

        }

    }

}
