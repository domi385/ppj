


public class ExpressionCommand extends RuleStrategy {

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 1) {
            node.setProperty(PropertyType.TYPE, new Property(Types.INT));
        } else if (node.getChildNodeNumber() == 2) {
            node.getChidlAt(0).visitNode(environment);
            node.setProperty(PropertyType.TYPE,
                    ((NonTerminalNode) node.getChidlAt(0)).getProperty(PropertyType.TYPE));
        } else {
            // neispravna produkcija
        }
    }

}
