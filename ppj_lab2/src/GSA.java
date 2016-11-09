import analizator.Action;

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

    public static void main(String[] args) {
        grammar = new GSAParser(readLines()).getGrammar().getAugmentedGrammar();

        first = computeFirst();
        canonical();

        defineActions();
        defineGoTos();
        System.out.println();
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

                checkShiftAction(items, terminal, i);

                checkReduceAction(items, terminal, i);

                if (terminal.equals(Symbol.Terminal.END_MARKER)) {
                    checkAcceptAction(items, i);
                }
            }
        }
    }

    private static void checkAcceptAction(Set<Item> items, int position) {
        boolean contains = items.stream().anyMatch(it -> it.getProduction().equals(grammar.getProduction(0)) &&
                                                         it.getTerminal().equals(Symbol.Terminal.END_MARKER) &&
                                                         it.dotAtEnd());
        if (contains) {
            actions.put(new Pair(position, Symbol.Terminal.END_MARKER), new Action(Action.ActionEnum.ACCEPT, 0));
        }
    }

    private static void checkShiftAction(Set<Item> items, Symbol.Terminal terminal, int position) {
        boolean contains = items.stream().anyMatch(it -> it.dotBefore(terminal) && !terminal.equals(it.getTerminal()));

        if (contains) {
            Set<Item> goTo = goTo(items, terminal);
            int index = canonical.indexOf(goTo);

            if (index == -1) {
                return;
            }

            actions.put(new Pair(position, terminal), new Action(Action.ActionEnum.SHIFT, index));
        }
    }

    private static void checkReduceAction(Set<Item> items, Symbol.Terminal terminal, int position) {
        Optional<Item> item = items.stream().filter((it -> it.dotAtEnd() && terminal.equals(it.getTerminal()) &&
                                                           !it.getProduction().left.equals(Grammar.augmentedStart)))
                .sorted((it1, ite2) -> Integer
                        .compare(grammar.indexOf(it1.getProduction()), grammar.indexOf(ite2.getProduction())))
                .findFirst();

        if (item.isPresent()) {
            actions.putIfAbsent(new Pair(position, terminal),
                    new Action(Action.ActionEnum.REDUCE, grammar.indexOf(item.get().getProduction())));
        }
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

    private static Map<Symbol, Set<Symbol>> computeFirst() {
        first.put(Symbol.Epsilon.getEpsilon(), Collections.singleton(Symbol.Epsilon.getEpsilon()));

        for (Symbol s : grammar.getSymbols()) {
            if (s instanceof Symbol.Terminal) {
                first.put(s, Collections.singleton(s));
                continue;
            } else if (s instanceof Symbol.Epsilon) {
                first.put(s, Collections.singleton(s));
                continue;
            }
            Set<Symbol> firstForNonterminal = getFirstNonterminal((Symbol.Nonterminal) s);
            first.put(s, firstForNonterminal);
        }

        return first;
    }

    private static Set<Symbol> getFirstNonterminal(Symbol.Nonterminal nonterminal) {
        Set<Symbol> f = new HashSet<>();
        Set<Production> productions = grammar.getProductions(nonterminal);

        for (Production p : productions) {
            if (p.isEpsilon()) {
                f.add(Symbol.Epsilon.getEpsilon());
                continue;
            }

            int size = p.size();
            for (int i = 0; i < size; i++) {
                Symbol s = p.getSymbol(i);

                Set<Symbol> firstForS = first.get(s);
                if (firstForS == null) {
                    Symbol.Nonterminal nt = (Symbol.Nonterminal) s;
                    firstForS = getFirstNonterminal(nt);
                }

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
        }

        return f;
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
                Symbol.Nonterminal nonTerminal = item.getNonterminal();
                List<Symbol> after = item.getAfterNonterminal();
                after.add(item.getTerminal());

                Set<Production> productions = grammar.getProductions(nonTerminal);

                for (Production production : productions) {
                    Set<Symbol.Terminal> t = getFirst(after).stream().filter(s -> s instanceof Symbol.Terminal)
                            .map(symbol -> (Symbol.Terminal) symbol).collect(Collectors.toSet());
                    for (Symbol.Terminal terminal : t) {
                        added = closure.add(new Item(production, 0, terminal));
                    }
                }
            }
        } while (added == true);

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
            List<Set<Item>> copy = new ArrayList<>(canonical);
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
