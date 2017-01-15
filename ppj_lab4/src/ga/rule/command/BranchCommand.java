package ga.rule.command;

import ga.Environment;
import ga.SemanticException;
import ga.Ulaz;
import ga.Types;
import ga.node.NonTerminalNode;
import ga.rule.RuleStrategy;
import ga.rule.RuleUtility;

public class BranchCommand extends RuleStrategy {

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 5 || node.getChildNodeNumber() == 7) {
            node.getChidlAt(2).visitNode(environment);
            if (!RuleUtility.checkType((NonTerminalNode) node.getChidlAt(2), Types.INT)) {
                throw new SemanticException(node.toString());
            }
            node.getChidlAt(4).visitNode(environment);
        }
        if (node.getChildNodeNumber() == 7) {
            node.getChidlAt(6).visitNode(environment);
        } else {
            // neispravna produkcija
        }
    }

    @Override
    public void emit(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 5) {
            node.getChidlAt(2).visitNode(environment);

            String label = "LABEL" + Ulaz.label++ + " ";

            System.out.println("\t POP R0");
            System.out.println("\t CMP R0, 0");
            System.out.println("\t JP_Z " + label);

            node.getChidlAt(4).visitNode(environment);
            System.out.print(label);
        } else {
            node.getChidlAt(2).visitNode(environment);

            String first = "LABEL" + Ulaz.label++ + " ";

            System.out.println("\t POP R0");
            System.out.println("\t CMP R0, 0");
            System.out.println("\t JP_Z " + first);

            node.getChidlAt(4).visitNode(environment);
            String second = "LABEL" + Ulaz.label++ + " ";
            System.out.println("\t JP " + second);

            System.out.print(first);
            node.getChidlAt(6).visitNode(environment);

            System.out.print(second);
        }
    }
}
