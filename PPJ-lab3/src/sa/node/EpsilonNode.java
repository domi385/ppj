package sa.node;



public class EpsilonNode extends Node {

    private Symbol.Epsilon symbol = Symbol.Epsilon.getEpsilon();

    public EpsilonNode(String value, int depth, Node parent) {
        super(value, depth, parent);

    }

    @Override
    public Symbol getSymbol() {
        return symbol;
    }

    @Override
    public void visitNode(Environment environment) {
        // ignore
    }
}
