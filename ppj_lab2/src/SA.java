import analizator.Action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * Created by Dominik on 8.11.2016..
 */
public class SA {
    public static final String DATA_PATH = "src/analizator/data.ser";

    private static SAData data;
    private static List<Token> tokens;
    private static int currentToken ;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        currentToken = 0;
        data = new SAData(DATA_PATH);

        Stack<Object> stack = new Stack<>();
        //početno stanje parsera je stanje u kojem se nalazi živi prefiks [S' -> S, $]
        stack.push(0);

        //čvor za ispis
        Node root = null;

        tokens = getTokens(data, readLines());
        Token token = nextToken();
        while (true) {
            int state = (int) stack.peek();
            Action action = data.actions.get(new Pair(state, token.getType()));
            if (action.getAction() == Action.ActionEnum.SHIFT) {
                Node node = new Node(token);
                stack.push(node);

                stack.push(action.getParameter());
                token = nextToken();
            } else if (action.getAction() == Action.ActionEnum.REDUCE) {
                Production production = data.grammar.getProduction(action.getParameter());

                Node head = new Node(production.getLeft());
                if (production.isEpsilon()) {
                    head.addChild(new Node(Symbol.Epsilon.getEpsilon()));
                }
                root = head;

                int size = 2 * production.size();
                while (size > 0) {
                    Object poped = stack.pop();
                    if (poped instanceof Node) {
                        head.addChild((Node) poped);
                    }
                    size--;
                }
                state = (int) stack.peek();
                stack.push(head);
                stack.push(data.goTos.get(new Pair(state, production.getLeft())));
            } else if (action.getAction() == Action.ActionEnum.ACCEPT) {
                break;
            } else if (action.getAction() == Action.ActionEnum.ERROR) {
                printError(token, state);

                while (!token.getType().isSynchronizationSymbol) {
                    token = nextToken();
                }

                Action tempAction = data.actions.get(new Pair(state, token.getType()));
                if (tempAction.getAction() != Action.ActionEnum.ERROR) {
                    continue;
                }


                stack.pop();
                while (tempAction.getAction() == Action.ActionEnum.ERROR) {
                    Object top = stack.peek();
                    while (!(top instanceof Integer)) {
                        stack.pop();
                        top = stack.peek();
                    }

                    tempAction = data.actions.get(new Pair((int) top, token.getType()));
                }
            } else {
                throw new RuntimeException("Jebiga");
            }
        }

        root.visitNode(0);
    }

    private static void printError(Token token, int state) {
        System.err.println("Pogreška u retku broj: " + token.getRow());

        Set<Symbol> symbolSetForState = data.actions.entrySet().stream()
                .filter(e -> e.getKey().getState() == state && e.getValue().getAction() != Action.ActionEnum.ERROR)
                .map(e -> e.getKey().getSymbol()).collect(Collectors.toSet());

        StringJoiner sj = new StringJoiner(", ", "{", "}");
        for (Symbol symbol : symbolSetForState) {
            sj.add(symbol.toString());
        }

        System.err.println("Očekivani znakovi: " + sj.toString());
        System.err.println("Uniformni znak: " + token.getType() + ", znakovni prikaz: " + token.getValue());
    }

    private static List<Token> getTokens(SAData data, List<String> lines) {
        List<Token> tokens = new ArrayList<>();

        for (String line : lines) {
            String[] lineData = line.split(" ", 3);

            Symbol.Terminal terminal =
                    data.grammar.getTerminals().stream().filter(t -> t.getSymbol().equals(lineData[0])).findFirst()
                            .get();
            int row = Integer.parseInt(lineData[1]);
            String string = lineData[2];

            tokens.add(new Token(terminal, row, string));
        }

        tokens.add(new Token(Symbol.Terminal.END_MARKER, -1, null));
        return tokens;
    }

    private static List<String> readLines() {
        Scanner sc = new Scanner(System.in);
        List<String> lines = new ArrayList<>();

        while (sc.hasNextLine()) {
            lines.add(sc.nextLine());
        }

        return lines;
    }

    private static Token nextToken() {
        Token token = tokens.get(currentToken);
        currentToken++;
        return token;
    }
}
