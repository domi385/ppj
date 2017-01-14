package ga.rule.expression;

import com.sun.xml.internal.bind.v2.TODO;
import ga.Environment;
import ga.Property;
import ga.PropertyType;
import ga.SemanticException;
import ga.SemantickiAnalizator;
import ga.Types;
import ga.node.NonTerminalNode;
import ga.rule.RuleStrategy;
import ga.rule.RuleUtility;

import java.util.ArrayList;
import java.util.List;

public class PostfixExpression extends RuleStrategy {

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {

        if (node.getChildNodeNumber() == 1) {

            node.getChidlAt(0).visitNode(environment);

            node.setProperty(PropertyType.TYPE, ((NonTerminalNode) node.getChidlAt(0)).getProperty(PropertyType.TYPE));

            node.setProperty(PropertyType.L_EXPRESSION,
                    ((NonTerminalNode) node.getChidlAt(0)).getProperty(PropertyType.L_EXPRESSION));

            if (((NonTerminalNode) node.getChidlAt(0)).getProperty(PropertyType.TYPE).getValue()
                    .equals(Types.FUNCTION)) {
                node.setProperty(PropertyType.FUNCTION_NAME,
                        ((NonTerminalNode) node.getChidlAt(0)).getProperty(PropertyType.FUNCTION_NAME));
            }

        } else if (node.getChildNodeNumber() == 2) {

            node.getChidlAt(0).visitNode(environment);

            if (!RuleUtility.checkProperty((NonTerminalNode) node.getChidlAt(0), PropertyType.L_EXPRESSION, 1) ||
                !RuleUtility.checkProperty((NonTerminalNode) node.getChidlAt(0), PropertyType.TYPE, Types.INT)) {

                throw new SemanticException(node.toString());
            }

            node.setProperty(PropertyType.TYPE, new Property(Types.INT));
            node.setProperty(PropertyType.L_EXPRESSION, new Property(0));

        } else if (node.getChildNodeNumber() == 3) {

            node.getChidlAt(0).visitNode(environment);
            NonTerminalNode postfixExpression = (NonTerminalNode) node.getChidlAt(0);

            if (!RuleUtility.checkProperty(postfixExpression, PropertyType.TYPE, Types.FUNCTION)) {
                throw new SemanticException(node.toString());
            }
            String functionName = postfixExpression.getProperty(PropertyType.FUNCTION_NAME).getValue();
            Types functionReturnType = environment.getFunctionReturnType(functionName);
            List<Types> parametersType = new ArrayList<>();
            if (!environment.isFunctionDeclared(functionName, functionReturnType, parametersType)) {
                throw new SemanticException(node.toString());
            }

            node.setProperty(PropertyType.TYPE, new Property(functionReturnType));
            node.setProperty(PropertyType.L_EXPRESSION, new Property(0));

        } else if (node.getChildNodeNumber() == 4) {

            if (node.getChidlAt(1).getSymbol().getSymbol().equals("L_UGL_ZAGRADA")) {
                node.getChidlAt(0).visitNode(environment);
                // TODO check checkType
                if (!RuleUtility.checkType((NonTerminalNode) node.getChidlAt(0), Types.ARRAY)) {

                    if (!RuleUtility.checkType((NonTerminalNode) node.getChidlAt(0), Types.ARRAY_CHAR)) {

                        throw new SemanticException(node.toString());
                    }

                    node.getChidlAt(2).visitNode(environment);

                    if (!RuleUtility.checkType((NonTerminalNode) node.getChidlAt(2), Types.INT)) {
                        throw new SemanticException(node.toString());
                    }
                    // da li postaviti na char ili const_char
                    node.setProperty(PropertyType.TYPE, new Property(Types.CHAR));

                    node.setProperty(PropertyType.L_EXPRESSION, new Property(1));

                } else {

                    node.getChidlAt(2).visitNode(environment);

                    if (!RuleUtility.checkType((NonTerminalNode) node.getChidlAt(2), Types.INT)) {
                        throw new SemanticException(node.toString());
                    }

                    Types postfixExpressionType = getBasicTypeFromArray(
                            ((NonTerminalNode) node.getChidlAt(0)).getProperty(PropertyType.TYPE).getValue());
                    node.setProperty(PropertyType.TYPE, new Property(postfixExpressionType));

                    node.setProperty(PropertyType.L_EXPRESSION, new Property(checkLExpression(postfixExpressionType)));
                }

            } else if (node.getChidlAt(1).getSymbol().getSymbol().equals("L_ZAGRADA")) {

                node.getChidlAt(0).visitNode(environment);
                node.getChidlAt(2).visitNode(environment);

                NonTerminalNode postfixExpression = (NonTerminalNode) node.getChidlAt(0);

                if (!RuleUtility.checkProperty(postfixExpression, PropertyType.TYPE, Types.FUNCTION)) {
                    // System.err.println("1");
                    throw new SemanticException(node.toString());
                }

                String functionName = postfixExpression.getProperty(PropertyType.FUNCTION_NAME).getValue();

                Types functionReturnType = environment.getFunctionReturnType(functionName);
                List<Types> parametersType = environment.getFunctionParameters(functionName);
                List<Types> argumentType =
                        ((NonTerminalNode) node.getChidlAt(2)).getProperty(PropertyType.TYPES).getValue();

                if (parametersType.size() != argumentType.size()) {
                    // System.err.println("2");
                    throw new SemanticException(node.toString());
                }
                for (int i = 0, end = parametersType.size(); i < end; i++) {
                    if (!RuleUtility.checkType(argumentType.get(i), parametersType.get(i))) {
                        // System.err.println(argumentType.get(i));
                        // System.err.println(parametersType.get(i));
                        // System.err.println("3");
                        throw new SemanticException(node.toString());
                    }
                }

                node.setProperty(PropertyType.TYPE, new Property(functionReturnType));
            } else {
                // losa produkcija
            }
        } else {
            // losa produkcija
        }

    }

    @Override
    public void emit(NonTerminalNode node, Environment environment) {
        int children = node.getChildNodeNumber();
        if (children == 1) {
            node.getChidlAt(0).visitNode(environment);
        } else if (children == 2) {
            //TODO
        } else if (children == 3) {
            SemantickiAnalizator.functionCall = true;
            node.getChidlAt(0).visitNode(environment);
            SemantickiAnalizator.functionCall = false;
        } else if (children == 4) {
            if (node.getChidlAt(2).getSymbol().getSymbol().equals("<lista_argumenata>")) {
                SemantickiAnalizator.functionCall = true;
                node.getChidlAt(2).visitNode(environment);
                node.getChidlAt(0).visitNode(environment);
                SemantickiAnalizator.functionCall = false;
            } else {
                //TODO
            }
        }
    }

    private Types getBasicTypeFromArray(Types type) {
        if (type.equals(Types.ARRAY)) {
            return Types.INT;
        } else if (type.equals(Types.ARRAY_CONST_CHAR)) {
            return Types.CONST_CHAR;
        } else if (type.equals(Types.ARRAY_CHAR)) {
            return Types.CHAR;
        } else if (type.equals(Types.ARRAY_CONST_T)) {
            return Types.CONST_T;
        }
        return null;
    }

    private Integer checkLExpression(Types postfixExpressionType) {
        if (postfixExpressionType.equals(Types.CHAR) || postfixExpressionType.equals(Types.INT) ||
            postfixExpressionType.equals(Types.T)) {
            return 1;
        }
        return 0;
    }
}
