package ga.rule.def;

import ga.Environment;
import ga.Property;
import ga.PropertyType;
import ga.SemanticException;
import ga.SemantickiAnalizator;
import ga.Symbol;
import ga.Types;
import ga.node.NonTerminalNode;
import ga.node.TerminalNode;
import ga.rule.RuleStrategy;
import ga.rule.RuleUtility;

public class ParameterDeclaration extends RuleStrategy {

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 2) {

            node.getChidlAt(0).visitNode(environment);

            if (RuleUtility.checkNotType((NonTerminalNode) node.getChidlAt(0), Types.VOID)) {

                node.setProperty(PropertyType.TYPE,
                        ((NonTerminalNode) node.getChidlAt(0)).getProperty(PropertyType.TYPE));
                node.setProperty(PropertyType.NAME, new Property(
                        ((TerminalNode) node.getChidlAt(1)).getValue()));

            } else {
                throw new SemanticException(node.toString());
            }

        } else if (node.getChildNodeNumber() == 4) {

            node.getChidlAt(0).visitNode(environment);
            if (!RuleUtility.checkNotType((NonTerminalNode) node.getChidlAt(0), Types.VOID)) {
                throw new SemanticException(node.toString());
            }

            node.setProperty(
                    PropertyType.TYPE,
                    new Property(Types.getArrayTypeFrom(((NonTerminalNode) node.getChidlAt(0))
                            .getProperty(PropertyType.TYPE).getValue())));

            node.setProperty(PropertyType.NAME,
                    new Property(((TerminalNode) node.getChidlAt(1)).getValue()));

        } else {
            // losa produkcija
        }
    }

    @Override
    public void emit(NonTerminalNode node, Environment environment) {
        String name = ((TerminalNode) node.getChidlAt(1)).getValue();
        environment.declareParameter(name, 1);
    }
}
