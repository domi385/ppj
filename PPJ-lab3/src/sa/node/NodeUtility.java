package sa.node;

import java.util.Scanner;
import java.util.Stack;

public class NodeUtility {

    public static Node buildTree() {
        Scanner sc = new Scanner(System.in);
        String line = null;
        Node root = nodeFactory(sc.nextLine().trim(), 0, null);
        Stack<Node> stack = new Stack<>();
        stack.add(root);
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            int depth = calcDepth(line);

            while (stack.peek().getDepth() >= depth) {
                stack.pop();
            }
            Node currNode = nodeFactory(line.trim(), depth, stack.peek());
            stack.peek().addChildNode(currNode);
            stack.add(currNode);
        }
        sc.close();
        return root;

    }

    private static int calcDepth(String line) {
        int index = 0;
        while (line.charAt(index) == ' ') {
            index++;
        }
        return index;
    }

    private static Node nodeFactory(String nodeString, int depth, Node parent) {
        if (nodeString.equals("$")) {
            return new EpsilonNode(nodeString, depth, parent);
        } else if (nodeString.startsWith("<")) {
            return new NonTerminalNode(nodeString, depth, parent);
        } else {
            return new TerminalNode(nodeString, depth, parent);
        }
    }
}
