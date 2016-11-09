import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Razred koji predstavlja gramatiku.
 */
public class Grammar implements Serializable {

    public static final Symbol.Nonterminal augmentedStart = new Symbol.Nonterminal("AS");
    private static final long serialVersionUID = -5503090513562391586L;

    private Set<Symbol.Terminal> terminals;
    private Set<Symbol.Nonterminal> nonterminals;
    private Symbol.Nonterminal startSymbol;
    private List<Production> productions = new ArrayList<>();

    public Grammar(
            Set<Symbol.Terminal> terminals, Set<Symbol.Nonterminal> nonterminals, Symbol.Nonterminal startSymbol) {
        this.terminals = terminals;
        terminals.add(Symbol.Terminal.END_MARKER);

        this.nonterminals = nonterminals;
        this.startSymbol = startSymbol;
    }

    public Grammar getAugmentedGrammar() {
        Production production = new Production(augmentedStart, Collections.singletonList(startSymbol));
        nonterminals.add(augmentedStart);

        ArrayList<Production> newProductions = new ArrayList<>(productions);
        newProductions.add(0, production);

        Grammar augmentedGrammar = new Grammar(terminals, nonterminals, augmentedStart);
        augmentedGrammar.productions = newProductions;

        return augmentedGrammar;
    }

    public boolean addProduction(Production production) {
        if (productions.contains(production)) {
            return false;
        }

        productions.add(production);
        return true;
    }

    private Set<Item> getItems() {
        Set<Item> items = new HashSet<>();
        productions.forEach(production -> items.addAll(production.getItems(terminals)));

        return items;
    }

    public Set<Production> getProductions(Symbol.Nonterminal nonterminal) {
        return productions.stream().filter(p -> p.getLeft().equals(nonterminal))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public Set<Symbol.Terminal> getTerminals() {
        return new HashSet<>(terminals);
    }

    public Set<Symbol> getSymbols() {
        Set<Symbol> symbols = new LinkedHashSet<>(terminals);
        symbols.addAll(nonterminals);
        return symbols;
    }

    public Production getProduction(int index) {
        return productions.get(index);
    }

    public int indexOf(Production production) {
        return productions.indexOf(production);
    }

    public Set<Symbol.Nonterminal> getNonterminals() {
        return new HashSet<>(nonterminals);
    }
}
