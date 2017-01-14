package ga.rule.expression;

import ga.Environment;
import ga.Property;
import ga.PropertyType;
import ga.SemanticException;
import ga.SemantickiAnalizator;
import ga.Types;
import ga.node.NonTerminalNode;
import ga.rule.RuleStrategy;
import ga.rule.RuleUtility;

public class BinaryXorExpression extends RuleStrategy {

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 1) {
            node.getChidlAt(0).visitNode(environment);
            node.setProperty(PropertyType.TYPE, ((NonTerminalNode) node.getChidlAt(0)).getProperty(PropertyType.TYPE));
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

            if (!SemantickiAnalizator.init) {
                System.out.println("\t POP R1");
                System.out.println("\t POP R0");
                System.out.println("\t XOR R0, R1, R0");
                System.out.println("\t PUSH R0");
            } else {
                //TODO
            }
        }
    }
}
