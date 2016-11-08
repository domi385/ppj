import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Dominik on 8.11.2016..
 */
public class GSA {
    private static Grammar grammar;
    private static Map<Symbol, Set<Symbol>> first = new HashMap<>();

    public static void main(String[] args) {
        grammar = new GSAParser(readLines()).getGrammar().getAugmentedGrammar();

        first = computeFirst();
        Set<Set<Item>> canonical = new LinkedHashSet<>();
        canonical(canonical);
        System.out.println();
    }

    private static List<String> readLines() {
        Scanner sc = new Scanner(System.in);
        List<String> lines = new ArrayList<>();

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.trim().isEmpty()) {
                break;
            }

            lines.add(line);
        }

        return lines;
    }

    private static Map<Symbol, Set<Symbol>> computeFirst() {
        first.put(Symbol.Epsilon.getEpsilon(), Collections.singleton(Symbol.Epsilon.getEpsilon()));
        first.put(Symbol.Terminal.END_MARKER, Collections.singleton(Symbol.Terminal.END_MARKER));

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

    private static void canonical(Set<Set<Item>> canonical) {
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
                        added = canonical.add(goTo);
                    }
                }
            }
        } while (added);
    }
}
