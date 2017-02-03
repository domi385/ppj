package sa.rule.expression;

import sa.Environment;
import sa.Property;
import sa.PropertyType;
import sa.SemanticException;
import sa.Types;
import sa.node.NonTerminalNode;
import sa.rule.RuleStrategy;
import sa.rule.RuleUtility;

public class JoiningExpression extends RuleStrategy {

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

            if (!RuleUtility.checkProperty((NonTerminalNode) node.getChidlAt(0),
                    PropertyType.L_EXPRESSION, 1)) {

                throw new SemanticException(node.toString());
            }
            node.getChidlAt(2).visitNode(environment);

            Types joiningExpressionType = (Types) ((NonTerminalNode) node.getChidlAt(2))
                    .getProperty(PropertyType.TYPE).getValue();
            Types postfixExpressionType = (Types) ((NonTerminalNode) node.getChidlAt(0))
                    .getProperty(PropertyType.TYPE).getValue();

            if (!RuleUtility.checkType(joiningExpressionType, postfixExpressionType)) {

                throw new SemanticException(node.toString());
            }

            node.setProperty(PropertyType.TYPE,
                    ((NonTerminalNode) node.getChidlAt(2)).getProperty(PropertyType.TYPE));
            node.setProperty(PropertyType.L_EXPRESSION, new Property(0));
        } else {
            // losa produkcija
        }

    }

}
