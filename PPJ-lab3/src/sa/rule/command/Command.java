package sa.rule.command;



public class Command extends RuleStrategy {

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 1) {
            node.getChidlAt(0).visitNode(environment);
        } else {
            // neispravna produkcija
        }
    }

}
