package sa.rule.command;

import sa.Environment;
import sa.PropertyType;
import sa.SemanticException;
import sa.Symbol;
import sa.Types;
import sa.node.Node;
import sa.node.NonTerminalNode;
import sa.rule.RuleStrategy;
import sa.rule.RuleUtility;

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
            Types returnType = ((NonTerminalNode) node.getChidlAt(1))
                    .getProperty(PropertyType.TYPE).getValue();
            if (!checkParentFunction(node, returnType)) {
                throw new SemanticException(node.toString());
            }
        } else {
            // losa produkcija
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

                Types functionReturnType = currNode.getProperty(PropertyType.RETURN_TYPE)
                        .getValue();
                return RuleUtility.checkType(returnType, functionReturnType);

            }
            currNode = (NonTerminalNode) currNode.getParentNode();
        }
        return false;
    }

}
