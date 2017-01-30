package ga.rule.expression;

import ga.Environment;
import ga.Ulaz;
import ga.node.NonTerminalNode;
import ga.node.TerminalNode;
import ga.rule.RuleStrategy;

public class UnaryOperator extends RuleStrategy {

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        // ignore
    }

    @Override
    public void emit(NonTerminalNode node, Environment environment) {
        TerminalNode childNode = (TerminalNode) node.getChidlAt(0);

        if(childNode.getSymbol().getSymbol().equals("MINUS")) {
            if(!Ulaz.init) {
                System.out.println("\t POP R0");
                System.out.println("\t MOVE %D 0, R1");
                System.out.println("\t SUB R1, R0, R0");
                System.out.println("\t PUSH R0");
            } else {
                Ulaz.konst[0] = - Ulaz.konst[0];
            }
        }

        //TODO: ostalo
    }
}
