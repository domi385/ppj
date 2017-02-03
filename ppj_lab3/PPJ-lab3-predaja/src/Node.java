

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public abstract class Node {

    private int depth;
    private List<Node> childNodes;
    private Node parentNode;

    public Node(String value, int depth, Node parent) {
        childNodes = new ArrayList<>();
        this.depth = depth;
        this.parentNode = parent;
    }

    public List<Node> getChildNodes() {
        return childNodes;
    }

    public Node getParentNode() {
        return parentNode;
    }

    public void addChildNode(Node currNode) {
        childNodes.add(currNode);
    }

    public Node getChidlAt(int index) {
        return childNodes.get(index);
    }

    public int getChildNodeNumber() {
        return childNodes.size();
    }

    public int getDepth() {
        return depth;
    }

    public abstract Symbol getSymbol();

    public abstract void visitNode(Environment environment);

    public String treeString() {
        String value = (depth != 0 ? String.format("%" + depth + "c", ' ') : "")
                + getSymbol().getSymbol() + "\n";
        for (Node node : childNodes) {
            value += node.treeString();
        }
        return value;
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(" ");
        sj.add(getSymbol().getSymbol());
        sj.add("::=");
        for (Node childNode : childNodes) {
            if (childNode instanceof NonTerminalNode) {
                sj.add(childNode.getSymbol().getSymbol());
            } else {
                TerminalNode currNode = (TerminalNode) childNode;
                sj.add(currNode.getSymbol().getSymbol() + "(" + currNode.getRow() + ","
                        + currNode.getValue() + ")");
            }
        }
        return sj.toString() + "\n";
    }
}
