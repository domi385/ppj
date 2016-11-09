import analizator.Action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Stack;

/**
 * Created by Dominik on 8.11.2016..
 */
public class SA {
    public static void main(String[] args) {
        GSA.main(args);

        Stack<Object> stack = new Stack<>();
        stack.push(0);

        Node root = null;

        List<Token> tokens = getTokens(readLines());
        Iterator<Token> iterator = tokens.iterator();
        Token token = iterator.next();
        while(true) {
            int state = (int) stack.peek();
            Action action = GSA.actions.get(new Pair(state, token.getType()));
            if(action.getAction() == Action.ActionEnum.SHIFT) {
                Node node = new Node(token);
                stack.push(node);

                stack.push(action.getParameter());
                token = iterator.next();
            } else if (action.getAction() == Action.ActionEnum.REDUCE) {
                Production production = GSA.grammar.getProduction(action.getParameter());
                Node head = new Node(production.getLeft());
                if(production.isEpsilon()) {
                    head.addChild(new Node(Symbol.Epsilon.getEpsilon()));
                }
                root = head;
                int size = 2 * production.size();
                while(size > 0) {
                    Object poped = stack.pop();
                    if(poped instanceof Node) {
                        head.addChild((Node) poped);
                    }
                    size--;
                }
                state = (int) stack.peek();
                stack.push(head);
                stack.push(GSA.goTos.get(new Pair(state, production.getLeft())));
            } else if (action.getAction() == Action.ActionEnum.ACCEPT) {
                System.out.println("uspih");
                break;
            } else {
                throw new RuntimeException("Jebiga");
            }
        }

        root.visitNode(0);
    }

    private static List<Token> getTokens(List<String> lines) {
        List<Token> tokens = new ArrayList<>();

        for (String line : lines) {
            String[] data = line.split(" ", 3);

            Symbol.Terminal terminal =
                    GSA.grammar.getTerminals().stream().filter(t -> t.getSymbol().equals(data[0])).findFirst().get();
            int row = Integer.parseInt(data[1]);
            String string = data[2];

            tokens.add(new Token(terminal, row, string));
        }

        tokens.add(new Token(Symbol.Terminal.END_MARKER, -1, null));
        return tokens;
    }

    private static List<String> readLines() {
        Scanner sc = new Scanner(System.in);
        List<String> lines = new ArrayList<>();

        String line = sc.nextLine();
        while (!line.trim().isEmpty()) {
            lines.add(line);
            line = sc.nextLine();
        }

        return lines;
    }
}
