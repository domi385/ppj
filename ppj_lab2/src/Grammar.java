import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Razred koji predstavlja gramatiku.
 */
class Grammar implements Serializable{


    private static final String AUGMENTED_START_NAME = "AS";
    private static final long serialVersionUID = -5503090513562391586L;

    private Set<Symbol.Terminal> terminals;
    private Set<Symbol.Nonterminal> nonterminals;
    private Symbol.Nonterminal startSymbol;
    private List<Production> productions = new ArrayList<>();

    public Grammar(
            Set<Symbol.Terminal> terminals, Set<Symbol.Nonterminal> nonterminals, Symbol.Nonterminal startSymbol) {
        this.terminals = terminals;
        this.nonterminals = nonterminals;
        this.startSymbol = startSymbol;
    }

    public Grammar getAugmentedGrammar() {
        Symbol.Nonterminal augmentedStart = new Symbol.Nonterminal(AUGMENTED_START_NAME);
        Production production = new Production(augmentedStart, Collections.singletonList(startSymbol));

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
}
