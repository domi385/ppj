import analizator.Action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Dominik on 8.11.2016..
 */
public class GSA {
    public static Grammar grammar;
    public static Map<Symbol, Set<Symbol>> first = new HashMap<>();

    public static List<Set<Item>> canonical = new ArrayList<>();

    public static Map<Pair, Action> actions = new HashMap<>();
    public static Map<Pair, Integer> goTos = new HashMap<>();

    public static void main(String[] args) throws IOException {
        grammar = new GSAParser(readLines()).getGrammar().getAugmentedGrammar();

        computeFirst();
        canonical();

        defineActions();
        defineGoTos();

        SAData data = new SAData(grammar, first, actions, goTos);
        data.writeData("src/analizator/data.ser");
    }

    private static void defineGoTos() {
        int size = canonical.size();
        for (Symbol.Nonterminal nonterminal : grammar.getNonterminals()) {
            for (int i = 0; i < size; i++) {
                Set<Item> items = canonical.get(i);

                Set<Item> goTo = goTo(items, nonterminal);
                int index = canonical.indexOf(goTo);

                if (index == -1) {
                    continue;
                }

                goTos.put(new Pair(i, nonterminal), index);
            }
        }
    }

    private static void defineActions() {
        int size = canonical.size();
        for (Symbol.Terminal terminal : grammar.getTerminals()) {
            for (int i = 0; i < size; i++) {
                Set<Item> items = canonical.get(i);

                boolean isShift = checkShiftAction(items, terminal, i);

                boolean isReduce = checkReduceAction(items, terminal, i);
                if (isReduce) {
                    if (isShift) {
                        System.err
                                .println("Pomakni/reduciraj nejednoznačnost. Stanje: " + i + ", terminal: " + terminal);
                    }
                }
                if (isShift || isReduce) {
                    continue;
                }

                if (terminal.equals(Symbol.Terminal.END_MARKER)) {
                    boolean isAccepted = checkAcceptAction(items, i);
                    if (isAccepted) {
                        continue;
                    }
                }

                errorAction(terminal, i);
            }
        }
    }

    private static void errorAction(Symbol.Terminal terminal, int position) {
        actions.put(new Pair(position, terminal), new Action(Action.ActionEnum.ERROR, -1));
    }

    private static boolean checkAcceptAction(Set<Item> items, int position) {
        boolean contains = items.stream().anyMatch(it -> it.getProduction().equals(grammar.getProduction(0)) &&
                                                         it.getTerminal().equals(Symbol.Terminal.END_MARKER) &&
                                                         it.dotAtEnd());
        if (contains) {
            actions.put(new Pair(position, Symbol.Terminal.END_MARKER), new Action(Action.ActionEnum.ACCEPT, 0));

            return true;
        }

        return false;
    }

    private static boolean checkShiftAction(Set<Item> items, Symbol.Terminal terminal, int position) {
        boolean contains = items.stream().anyMatch(it -> it.dotBefore(terminal) && !terminal.equals(it.getTerminal()));

        if (contains) {
            Set<Item> goTo = goTo(items, terminal);
            int index = canonical.indexOf(goTo);

            if (index == -1) {
                return false;
            }

            actions.put(new Pair(position, terminal), new Action(Action.ActionEnum.SHIFT, index));
            return true;
        }

        return false;
    }

    private static boolean checkReduceAction(Set<Item> items, Symbol.Terminal terminal, int position) {
        Optional<Item> item = items.stream().filter((it -> it.dotAtEnd() && terminal.equals(it.getTerminal()) &&
                                                           !it.getProduction().left.equals(Grammar.augmentedStart)))
                .sorted((it1, ite2) -> Integer
                        .compare(grammar.indexOf(it1.getProduction()), grammar.indexOf(ite2.getProduction())))
                .findFirst();

        if (item.isPresent()) {
            actions.putIfAbsent(new Pair(position, terminal),
                    new Action(Action.ActionEnum.REDUCE, grammar.indexOf(item.get().getProduction())));

            return true;
        }

        return false;
    }

    private static List<String> readLines() {
        Scanner sc = new Scanner(System.in);
        List<String> lines = new ArrayList<>();

        String line = sc.nextLine();
        while (!line.trim().isEmpty()) {
            lines.add(line);
            line = sc.nextLine();
        }
        sc.nextLine();

        return lines;
    }

    private static void computeFirst() {
        grammar.getSymbols().forEach(s -> first.put(s, new HashSet<>()));

        boolean added = false;
        do {
            added = false;
            for (Symbol symbol : grammar.getSymbols()) {
                boolean tempAdded = false;
                if (symbol instanceof Symbol.Terminal) {
                    tempAdded = first.get(symbol).add(symbol);
                } else {
                    tempAdded = firstForNonterminal((Symbol.Nonterminal) symbol);
                }

                if (!added && tempAdded) {
                    added = tempAdded;
                }
            }
        } while (added);
    }

    private static boolean firstForNonterminal(Symbol.Nonterminal symbol) {
        boolean added = false;

        Set<Symbol> fX = first.get(symbol);

        Set<Production> productions = grammar.getProductions(symbol);
        for (Production production : productions) {
            if (production.isEpsilon()) {
                added = first.get(symbol).add(Symbol.Epsilon.getEpsilon());
                continue;
            }

            int size = production.size();
            for (int i = 0; i < size; i++) {
                Set<Symbol> fY = first.get(production.getSymbol(i));

                for (Symbol s : fY) {
                    if (s.equals(Symbol.Epsilon.getEpsilon())) {
                        continue;
                    }

                    boolean tempBoolean = fX.add(s);
                    if (!added && tempBoolean) {
                        added = true;
                    }
                }

                if (!fY.contains(Symbol.Epsilon.getEpsilon())) {
                    break;
                }

                if (i == size - 1 && !fX.contains(Symbol.Epsilon.getEpsilon())) {
                    added = fX.add(Symbol.Epsilon.getEpsilon());
                }
            }
        }

        return added;
    }

    private static Set<Symbol> getFirst(List<Symbol> symbols) {
        Set<Symbol> f = new HashSet<>();
        int size = symbols.size();

        for (int i = 0; i < size; i++) {
            Symbol s = symbols.get(i);
            Set<Symbol> firstForS = first.get(s);

            firstForS.forEach(symbol -> {
                if (!symbol.equals(Symbol.Epsilon.getEpsilon())) {
                    f.add(symbol);
                }
            });

            if (!firstForS.contains(Symbol.Epsilon.getEpsilon())) {
                break;
            }

            if (i == size - 1) {
                f.add(Symbol.Epsilon.getEpsilon());
            }
        }

        return f;
    }

    private static Set<Item> closure(Set<Item> items) {
        Set<Item> closure = new HashSet<>(items);

        boolean added;
        do {
            added = false;
            Set<Item> temp = closure.stream().filter(i -> i.isBeforeNonterminal()).collect(Collectors.toSet());
            for (Item item : temp) {
                boolean tempAdded;

                Symbol.Nonterminal nonTerminal = item.getNonterminal();
                List<Symbol> after = item.getAfterNonterminal();
                after.add(item.getTerminal());

                Set<Production> productions = grammar.getProductions(nonTerminal);

                for (Production production : productions) {
                    Set<Symbol.Terminal> t = getFirst(after).stream().filter(s -> s instanceof Symbol.Terminal)
                            .map(symbol -> (Symbol.Terminal) symbol).collect(Collectors.toSet());
                    for (Symbol.Terminal terminal : t) {
                        tempAdded = closure.add(new Item(production, 0, terminal));

                        if (!added && tempAdded) {
                            added = tempAdded;
                        }
                    }
                }
            }
        } while (added);

        return closure;
    }

    private static Set<Item> goTo(Set<Item> items, Symbol symbol) {
        Set<Item> result = new HashSet<>();
        items = items.stream().filter(i -> i.dotBefore(symbol)).collect(Collectors.toSet());
        for (Item item : items) {
            result.add(new Item(item.getProduction(), item.getDotPosition() + 1, item.getTerminal()));
        }

        return closure(result);
    }

    private static void canonical() {
        Set<Item> closure =
                closure(Collections.singleton(new Item(grammar.getProduction(0), 0, Symbol.Terminal.END_MARKER)));
        canonical.add(closure);

        boolean added;
        do {
            added = false;
            Set<Set<Item>> copy = new HashSet<>(canonical);
            for (Set<Item> c : copy) {
                for (Symbol s : grammar.getSymbols()) {
                    Set<Item> goTo = goTo(c, s);
                    if (!goTo.isEmpty() && !canonical.contains(goTo)) {
                        canonical.add(goTo);
                        added = true;
                    }
                }
            }
        } while (added);
    }
}
