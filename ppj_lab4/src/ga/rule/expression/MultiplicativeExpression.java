package ga.rule.expression;

import ga.Environment;
import ga.Property;
import ga.PropertyType;
import ga.SemanticException;
import ga.Ulaz;
import ga.Types;
import ga.node.NonTerminalNode;
import ga.rule.RuleStrategy;
import ga.rule.RuleUtility;

public class MultiplicativeExpression extends RuleStrategy {

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 1) {
            node.getChidlAt(0).visitNode(environment);
            node.setProperty(PropertyType.TYPE,
                    ((NonTerminalNode) node.getChidlAt(0)).getProperty(PropertyType.TYPE));
            node.setProperty(PropertyType.L_EXPRESSION,
                    ((NonTerminalNode) node.getChidlAt(0)).getProperty(PropertyType.L_EXPRESSION));
        } else if (node.getChildNodeNumber() == 3) {
            node.getChidlAt(0).visitNode(environment);
            if (!RuleUtility.checkType((NonTerminalNode) node.getChidlAt(0), Types.INT)) {
                throw new SemanticException(node.toString());
            }
            node.getChidlAt(2).visitNode(environment);
            if (!RuleUtility.checkType((NonTerminalNode) node.getChidlAt(2), Types.INT)) {
                throw new SemanticException(node.toString());
            }

            node.setProperty(PropertyType.TYPE, new Property(Types.INT));
            node.setProperty(PropertyType.L_EXPRESSION, new Property(0));
        } else {
            // losa produkcija
        }

    }

    @Override
    public void emit(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 1) {
            node.getChidlAt(0).visitNode(environment);
        } else {
            node.getChidlAt(0).visitNode(environment);
            node.getChidlAt(2).visitNode(environment);

            System.out.println("\t POP R1"); //y
            System.out.println("\t POP R0"); //x

            if(node.getChidlAt(1).getSymbol().getSymbol().equals("OP_MOD")) {
                mod();
            } else if (node.getChidlAt(1).getSymbol().getSymbol().equals("OP_PUTA")) {
                mul();
            } else {
                div();
            }
        }
    }

    private void div() {
        System.out.println("\t MOVE %D 0, R2");
        String first = "LABEL" + Ulaz.label++;
        System.out.println(first + "\t CMP R0, R1");

        String second = "LABEL" + Ulaz.label++;
        System.out.println("\tJP_SLT " + second);
        System.out.println("\t SUB R0, R1, R0");
        System.out.println("\t ADD R2, %D 1, R2");
        System.out.println("\t JP " + first);
        System.out.println(second + "\t PUSH R2");
    }

    private void mul() {
        System.out.println("\t MOVE %D 0, R2");
        String first = "LABEL" + Ulaz.label++;
        System.out.println(first + "\t CMP R1, 0");

        String second = "LABEL" + Ulaz.label++;
        System.out.println("\tJP_Z " + second);
        System.out.println("\t ADD R2, R0, R2");
        System.out.println("\t SUB R1, %D 1, R1");
        System.out.println("\t JP " + first);
        System.out.println(second + "\t PUSH R2");
    }

    private void mod() {
        String first = "LABEL" + Ulaz.label++;
        System.out.println(first + "\t CMP R0, R1");

        String second = "LABEL" + Ulaz.label++;
        System.out.println("\t JP_SLT " + second);
        System.out.println("\t SUB R0, R1, R0");
        System.out.println("\t JP " + first);
        System.out.println(second + "\t PUSH R0");
    }
}
