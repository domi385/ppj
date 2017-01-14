package ga;

import com.sun.org.apache.regexp.internal.RE;
import ga.node.Node;
import ga.node.NodeUtility;
import ga.rule.RuleUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class SemantickiAnalizator {
    public static Environment.TableEntry currentEntry;
    public static boolean init = false;
    public static int[] konst;

    private static List<Integer> big = new ArrayList<>();

    public static void main(String[] args) {

        Node root = NodeUtility.buildTree();
        Environment environment = new Environment(null);
        try {
            System.out.println("GLAVNI \t MOVE 40000, R7");
            System.out.println("\t CALL F_MAIN");
            System.out.println("\t HALT");

            root.visitNode(environment);
            //RuleUtility.checkEnvironment(environment);
            //declareGlobal(environment);
            printBig();
        } catch (SemanticException ex) {
            System.out.println(ex.getMessage());
        }

    }

    private static void printBig() {
        for(int i = 1, n = big.size(); i <= n; i++) {
            System.out.println("VEL" + i + "\t" + defineWord(1, new int[]{big.get(i - 1)}));
        }
    }

    public static String defineWord(int size, int[] word) {
        StringJoiner sj = new StringJoiner(", ", " DW ", "");
        for (int i = 0; i < size; i++) {
            if (i < word.length) {
                sj.add("%D " + Integer.toString(word[i]));
            } else {
                sj.add("%D 0");
            }
        }
        return sj.toString();
    }

    public static int addBig(int integer) {
        big.add(integer);
        return big.size();
    }

}
