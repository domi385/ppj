package sa.node;

import java.util.ArrayList;
import java.util.List;

import sa.Environment;
import sa.Symbol;

public abstract class Node {

    private int depth;
    private List<Node> childNodes;
    private Node parentNode;

    public Node(String value, int depth, Node parent) {
        childNodes = new ArrayList<>();
        this.depth = depth;
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

    // public String treeString() {
    // String value = (depth != 0 ? String.format("%" + depth + "c", ' ') : "")
    // + tmpValue + "\n";
    // for (Node node : childNodes) {
    // value += node.toString();
    // }
    // return value;
    // }

}
