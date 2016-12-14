


public class TypeSpecificatorExpression extends RuleStrategy {

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 1) {
            TerminalNode childNode = (TerminalNode) node.getChidlAt(0);
            if (childNode.getSymbol().getSymbol().equals("KR_VOID")) {
                node.setProperty(PropertyType.TYPE, new Property(Types.VOID));
            } else if (childNode.getSymbol().getSymbol().equals("KR_CHAR")) {
                node.setProperty(PropertyType.TYPE, new Property(Types.CHAR));
            } else if (childNode.getSymbol().getSymbol().equals("KR_INT")) {
                node.setProperty(PropertyType.TYPE, new Property(Types.INT));
            } else {
                // losa produkcija
            }
        } else {
            // losa produkcija
        }
    }

}
