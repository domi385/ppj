import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominik on 9.11.2016..
 */
public class Node {
    private List<Node> children = new ArrayList<>();

    private String value;

    public Node (Symbol symbol) {
        value = symbol.toString();
    }

    public Node (Token token) {
        value = token.toString();
    }

    public void addChild(Node node) {
        children.add(0, node);
    }

    public void visitNode(int level) {
        String print = (level != 0 ? String.format("%" + level + "c", ' ') : "") + value + "\n";
        System.out.print(print);
        for(Node child : children) {
            child.visitNode(level + 1);
        }
    }
}
