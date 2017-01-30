package ga.node;

import ga.Environment;
import ga.Symbol;

public class TerminalNode extends Node {

    private Symbol.Terminal type;
    private final int row;
    private String value;

    public Symbol.Terminal getType() {
        return type;
    }

    public int getRow() {
        return row;
    }

    public TerminalNode(String value, int depth, Node parent) {
        super(value, depth, parent);
        String[] valueParts = value.split(" ", 3);
        type = new Symbol.Terminal(valueParts[0]);
        row = Integer.parseInt(valueParts[1]);
        this.value = valueParts[2];
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
        // ignore
    }

}
