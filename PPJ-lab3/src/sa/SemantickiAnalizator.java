package sa;

import sa.node.Node;
import sa.node.NodeUtility;
import sa.rule.RuleUtility;

public class SemantickiAnalizator {

    public static void main(String[] args) {

        Node root = NodeUtility.buildTree();
        Environment environment = new Environment(null);
        try {
            root.visitNode(environment);
            RuleUtility.checkEnvironment(environment);
        } catch (SemanticException ex) {
            System.out.println(ex.getMessage());
        }

    }
}
