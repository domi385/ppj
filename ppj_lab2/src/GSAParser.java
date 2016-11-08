import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Dominik on 8.11.2016..
 */
public class GSAParser {
    private Grammar grammar;

    private Set<Symbol.Nonterminal> nonterminals;
    private Symbol.Nonterminal startSymbol;
    private Set<Symbol.Terminal> terminals;
    private Set<Symbol.Terminal> syncronizationSymbols;

    private int lineNum;

    public GSAParser(List<String> lines) {
        grammar = parse(lines);
    }

    private Grammar parse(List<String> lines) {
        lineNum = 0;

        getNonterminals(lines.get(lineNum));
        lineNum++;

        getTerminals(lines.get(lineNum));
        lineNum++;

        getSyncs(lines.get(lineNum));
        lineNum++;

        Grammar g = new Grammar(terminals, nonterminals, startSymbol);

        getProductions(g, lines);

        return g;
    }

    private void getProductions(Grammar g, List<String> lines) {

        for (int i = lineNum; i < lines.size(); i++) {
            String s = lines.get(i);
            if (s.startsWith("<")) {
                Symbol.Nonterminal left = new Symbol.Nonterminal(s.trim());
                List<Symbol> right = null;

                i++;
                if (i >= lines.size()) {
                    break;
                }
                s = lines.get(i);
                while (s.startsWith(" ")) {

                    right = getSymbols(s);
                    Production p = new Production(left, right);
                    g.addProduction(p);

                    i++;
                    if (i >= lines.size()) {
                        break;
                    }
                    s = lines.get(i);
                }
                i--;
            }
        }

    }

    private List<Symbol> getSymbols(String s) {
        List<Symbol> symbols = new ArrayList<>();

        String[] symbolStrings = s.trim().split(" ");
        for (String symbol : symbolStrings) {
            if (symbol.equals("$")) {
                return Collections.singletonList(Symbol.Epsilon.getEpsilon());
            }
            if (symbol.startsWith("<")) {
                symbols.add(new Symbol.Nonterminal(symbol.trim()));
            } else {
                symbols.add(new Symbol.Terminal(symbol.trim()));
            }
        }

        return symbols;
    }

    private void getSyncs(String s) {
        s = s.replace("%Syn ", "");

        String[] syncSymbols = s.split(" ");
        syncronizationSymbols = new HashSet<>();

        for (String symbol : syncSymbols) {
            terminals.stream().filter(t -> t.getSymbol().equals(symbol))
                    .forEach(terminal -> terminal.isSynchronizationSymbol = true);
        }

    }

    private void getTerminals(String s) {
        s = s.replace("%T ", "");

        String[] terminalSymbols = s.split(" ");
        terminals = new HashSet<>();

        for (String symbol : terminalSymbols) {
            terminals.add(new Symbol.Terminal(symbol.trim()));
        }
    }

    private void getNonterminals(String s) {
        s = s.replace("%V ", "");

        String[] nonterminalSymbols = s.split(" ");
        nonterminals = new HashSet<>();
        startSymbol = new Symbol.Nonterminal(nonterminalSymbols[0]);

        for (String symbol : nonterminalSymbols) {
            nonterminals.add(new Symbol.Nonterminal(symbol.trim()));
        }

    }

    public Grammar getGrammar() {
        return grammar;
    }
}
