package ga.rule.def;

import ga.Environment;
import ga.node.NonTerminalNode;
import ga.rule.RuleStrategy;

import java.util.LinkedHashMap;
import java.util.Map;

public class DeclarationList extends RuleStrategy {

    public static Map<Integer, int[]> params;

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 1) {
            node.getChidlAt(0).visitNode(environment);
        } else if (node.getChildNodeNumber() == 2) {
            node.getChidlAt(0).visitNode(environment);
            node.getChidlAt(1).visitNode(environment);
        } else {
            // losa produkcija
        }
    }

    @Override
    public void emit(NonTerminalNode node, Environment environment) {
        params = new LinkedHashMap<>();
        if (node.getChildNodeNumber() == 1) {
            node.getChidlAt(0).visitNode(environment);
        } else if (node.getChildNodeNumber() == 2) {
            node.getChidlAt(0).visitNode(environment);
            node.getChidlAt(1).visitNode(environment);
        } else {
            // losa produkcija
        }

        for(Map.Entry<Integer, int[]> entry : params.entrySet()) {
            String val = Integer.toString(entry.getValue()[0]);
            System.out.println("\t MOVE %D " + val + ", R2");
            System.out.println("\t PUSH R2");
        }

        params = null;
    }
}
