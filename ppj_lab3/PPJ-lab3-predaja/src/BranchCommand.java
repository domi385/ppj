


public class BranchCommand extends RuleStrategy {

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 5 || node.getChildNodeNumber() == 7) {
            node.getChidlAt(2).visitNode(environment);
            if (!RuleUtility.checkType((NonTerminalNode) node.getChidlAt(2), Types.INT)) {
                throw new SemanticException(node.toString());
            }
            node.getChidlAt(4).visitNode(environment);
        }
        if (node.getChildNodeNumber() == 7) {
            node.getChidlAt(6).visitNode(environment);
        } else {
            // neispravna produkcija
        }
    }
}
