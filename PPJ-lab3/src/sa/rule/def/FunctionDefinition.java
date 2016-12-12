package sa.rule.def;

import java.util.ArrayList;
import java.util.List;

import sa.Environment;
import sa.Property;
import sa.PropertyType;
import sa.SemanticException;
import sa.Types;
import sa.node.NonTerminalNode;
import sa.node.TerminalNode;
import sa.rule.RuleStrategy;
import sa.rule.RuleUtility;

public class FunctionDefinition extends RuleStrategy {

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 6
                && node.getChidlAt(3).getSymbol().getSymbol().equals("KR_VOID")) {
            node.getChidlAt(0).visitNode(environment);
            if (!RuleUtility.checkNotType((NonTerminalNode) node.getChidlAt(0), Types.CONST_T)) {
                throw new SemanticException(node.toString());
            }
            String functionName = ((TerminalNode) node.getChidlAt(1)).getValue();
            if (Environment.getGlobalEnvironment(environment).isDefinedFunction(functionName)) {
                throw new SemanticException(node.toString());
            }
            Types functionReturnType = ((NonTerminalNode) node.getChidlAt(0)).getProperty(
                    PropertyType.TYPE).getValue();
            if (!checkFunctionsDeclarations(functionName, functionReturnType,
                    new ArrayList<Types>(), environment)) {
                throw new SemanticException(node.toString());
            }
            Environment.getGlobalEnvironment(environment).defineFunction(functionName,
                    functionReturnType, new ArrayList<Types>());
            node.getChidlAt(5).visitNode(environment);
        } else if (node.getChildNodeNumber() == 6) {
            node.getChidlAt(0).visitNode(environment);
            if (!RuleUtility.checkNotType((NonTerminalNode) node.getChidlAt(0), Types.CONST_T)) {
                throw new SemanticException(node.toString());
            }
            String functionName = ((TerminalNode) node.getChidlAt(1)).getValue();
            if (environment.isDefinedFunction(functionName)) {
                throw new SemanticException(node.toString());
            }

            Types functionReturnType = ((NonTerminalNode) node.getChidlAt(0)).getProperty(
                    PropertyType.TYPE).getValue();
            List<Types> parameterTypes = ((NonTerminalNode) node.getChidlAt(3)).getProperty(
                    PropertyType.TYPES).getValue();
            List<String> parameterNames = ((NonTerminalNode) node.getChidlAt(3)).getProperty(
                    PropertyType.NAMES).getValue();
            if (!checkFunctionsDeclarations(functionName, functionReturnType, parameterTypes,
                    environment)) {
                throw new SemanticException(node.toString());
            }
            Environment.getGlobalEnvironment(environment).defineFunction(functionName,
                    functionReturnType, parameterTypes);

            node.setProperty(PropertyType.PARAMETER_NAMES, new Property(parameterNames));
            node.setProperty(PropertyType.PARAMETER_TYPES, new Property(parameterTypes));

            node.getChidlAt(5).visitNode(environment);
        } else {

        }

    }

    public static boolean checkFunctionsDeclarations(String functionName, Types functionReturnType,
            List<Types> parameterTypes, Environment environment2) {
        return true;
        // TODO Auto-generated method stub

    }

}
