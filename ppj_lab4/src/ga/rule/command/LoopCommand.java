package ga.rule.command;

import ga.Environment;
import ga.SemanticException;
import ga.Ulaz;
import ga.Types;
import ga.node.NonTerminalNode;
import ga.rule.RuleStrategy;
import ga.rule.RuleUtility;

public class LoopCommand extends RuleStrategy {

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 5) {
            node.getChidlAt(2).visitNode(environment);
            if (!RuleUtility.checkType((NonTerminalNode) node.getChidlAt(2), Types.INT)) {
                throw new SemanticException(node.toString());
            }
            node.getChidlAt(4).visitNode(environment);
        } else if (node.getChildNodeNumber() == 6) {
            node.getChidlAt(2).visitNode(environment);
            node.getChidlAt(3).visitNode(environment);
            if (!RuleUtility.checkType((NonTerminalNode) node.getChidlAt(3), Types.INT)) {
                throw new SemanticException(node.toString());
            }
            node.getChidlAt(5).visitNode(environment);

        } else if (node.getChildNodeNumber() == 7) {
            node.getChidlAt(2).visitNode(environment);
            node.getChidlAt(3).visitNode(environment);
            if (!RuleUtility.checkType((NonTerminalNode) node.getChidlAt(3), Types.INT)) {
                throw new SemanticException(node.toString());
            }
            node.getChidlAt(4).visitNode(environment);
            node.getChidlAt(6).visitNode(environment);
        } else {
            // neispravna produkcija
        }
    }

    @Override
    public void emit(NonTerminalNode node, Environment environment) {
        if(node.getChidlAt(0).getSymbol().getSymbol().equals("KR_WHILE")) {
            String first = "LABEL" + Ulaz.label++;
            String second = "LABEL" + Ulaz.label++;

            System.out.print(first + " ");
            node.getChidlAt(2).visitNode(environment);
            System.out.println("\t POP R0");
            System.out.println("\t CMP R0, 0");
            System.out.println("\t JP_Z " + second);

            node.getChidlAt(4).visitNode(environment);
            System.out.println("\t JP " + first);

            System.out.print(second + " ");
        }
    }
}
