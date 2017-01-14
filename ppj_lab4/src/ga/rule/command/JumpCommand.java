package ga.rule.command;

import ga.Environment;
import ga.PropertyType;
import ga.SemanticException;
import ga.Symbol;
import ga.Types;
import ga.node.Node;
import ga.node.NonTerminalNode;
import ga.rule.RuleStrategy;
import ga.rule.RuleUtility;

public class JumpCommand extends RuleStrategy {

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 2) {
            if (node.getChidlAt(0).getSymbol().equals(new Symbol.Terminal("KR_RETURN"))) {
                if (!checkParentFunction(node, Types.VOID)) {
                    throw new SemanticException(node.toString());
                }
            } else {
                if (!checkParent(node, "<naredba_petlje>")) {
                    throw new SemanticException(node.toString());
                }

            }
        } else if (node.getChildNodeNumber() == 3) {
            node.getChidlAt(1).visitNode(environment);
            Types returnType = ((NonTerminalNode) node.getChidlAt(1)).getProperty(PropertyType.TYPE).getValue();
            if (!checkParentFunction(node, returnType)) {
                throw new SemanticException(node.toString());
            }
        } else {
            // losa produkcija
        }
    }

    @Override
    public void emit(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 2) {
            if (node.getChidlAt(0).getSymbol().equals(new Symbol.Terminal("KR_RETURN"))) {
                if (!checkParentFunction(node, Types.VOID)) {
                    throw new SemanticException(node.toString());
                }
            } else {
                if (!checkParent(node, "<naredba_petlje>")) {
                    throw new SemanticException(node.toString());
                }

            }
        } else if (node.getChildNodeNumber() == 3) {
            node.getChidlAt(1).visitNode(environment);
            System.out.println("\t POP R6");

            long local = environment.numberOfLocalVariables();
            String offset = Long.toHexString(local * 4).toUpperCase();
            System.out.println("\t ADD R7, " + offset + ", R7");

            System.out.println("\t RET");
        }
    }

    public static boolean checkParent(NonTerminalNode node, String parentNonTerminalNodeName) {
        Node currNode = node.getParentNode();
        while (currNode != null) {

            if (currNode.getSymbol().getSymbol().equals(parentNonTerminalNodeName)) {
                return true;
            }
            currNode = currNode.getParentNode();
        }
        return false;

    }

    public static boolean checkParentFunction(NonTerminalNode node, Types returnType) {

        NonTerminalNode currNode = node;
        while (currNode != null) {

            if (currNode.hasProperty(PropertyType.RETURN_TYPE)) {

                Types functionReturnType = currNode.getProperty(PropertyType.RETURN_TYPE).getValue();
                return RuleUtility.checkType(returnType, functionReturnType);

            }
            currNode = (NonTerminalNode) currNode.getParentNode();
        }
        return false;
    }

}
