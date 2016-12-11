package sa.rule.def;

import sa.Environment;
import sa.Property;
import sa.PropertyType;
import sa.Types;
import sa.node.Node;
import sa.node.NonTerminalNode;
import sa.node.TerminalNode;
import sa.rule.RuleStrategy;

public class DirectDeclarator extends RuleStrategy {

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 1) {
            TerminalNode identificator = (TerminalNode) node.getChidlAt(0);
            if (identificator.getSymbol().getSymbol().equals(Types.VOID.toString())
                    || environment.isDeclaredLocaly(identificator.getValue())) {
                throw new RuntimeException();
            }
            Types type = Types.getType(identificator.getSymbol().getSymbol());
            environment.declareIdentificator(identificator.getValue(), type);
            node.setProperty(PropertyType.TYPE, new Property(type));

        } else if (node.getChildNodeNumber() == 4) {
            Node thirdChildNode = node.getChidlAt(2);
            if (thirdChildNode.getSymbol().getSymbol().equals("BROJ")) {
                TerminalNode identificator = (TerminalNode) node.getChidlAt(0);
                if (identificator.getSymbol().getSymbol().equals(Types.VOID.toString())
                        || environment.isDeclaredLocaly(identificator.getValue())) {
                    throw new RuntimeException();
                }
                Integer numberValue = Integer.parseInt(((TerminalNode) node.getChidlAt(2))
                        .getValue());
                if (numberValue < 1 || numberValue > 1024) {
                    throw new RuntimeException();
                }
                Types type = Types.getArrayType(Types
                        .getType(identificator.getSymbol().getSymbol()));
                environment.declareIdentificator(identificator.getValue(), type);
                node.setProperty(PropertyType.TYPE, new Property(type));
                node.setProperty(PropertyType.NUM_ELEM, new Property(numberValue));
            } else if (thirdChildNode.getSymbol().getSymbol().equals("KR_VOID")) {
                // TODO
            } else if (thirdChildNode.getSymbol().getSymbol().equals("<lista_parametara>")) {
                // TODO
            } else {
                // loša produkcija
            }
        } else {
            // loša produkcija
        }
    }

}
