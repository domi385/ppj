package sa.node;

import sa.Environment;
import sa.Symbol;

public class TerminalNode extends Node {

    private Symbol.Terminal type;
    private int row;
    private String value;

    public TerminalNode(String value, int depth, Node parent) {
        super(value, depth, parent);
        String[] valueParts = value.split(" ");
        type = new Symbol.Terminal(valueParts[0]);
        row = Integer.parseInt(valueParts[1]);
        value = valueParts[2];
    }

    @Override
    public Symbol getSymbol() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public void visitNode(Environment environment) {
        // TODO Auto-generated method stub

    }

}
