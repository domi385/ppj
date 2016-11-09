import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominik on 8.11.2016..
 */
public class Item implements Serializable {
    private static final long serialVersionUID = -7438596871526326674L;

    private Production production;
    private int dotPosition;
    private Symbol.Terminal terminal;

    public Item(Production production, int dotPosition, Symbol.Terminal terminal) {
        this.production = production;
        this.dotPosition = dotPosition;
        this.terminal = terminal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Item item = (Item) o;

        if (dotPosition != item.dotPosition) {
            return false;
        }
        if (!production.equals(item.production)) {
            return false;
        }
        return terminal.equals(item.terminal);

    }

    @Override
    public int hashCode() {
        int result = production.hashCode();
        result = 31 * result + dotPosition;
        result = 31 * result + terminal.hashCode();
        return result;
    }

    public boolean isBeforeNonterminal() {
        if (dotPosition == production.size()) {
            return false;
        }

        Symbol symbol = production.getSymbol(dotPosition);

        return symbol instanceof Symbol.Nonterminal;
    }

    public Symbol.Nonterminal getNonterminal() {
        if (!isBeforeNonterminal()) {
            throw new RuntimeException("Dot is not before nonterminal.");
        }

        return (Symbol.Nonterminal) production.getSymbol(dotPosition);
    }

    public List<Symbol> getAfterNonterminal() {
        List<Symbol> symbols = new ArrayList<>();
        int size = production.size();

        for (int i = dotPosition + 1; i < size; i++) {
            symbols.add(production.getSymbol(i));
        }

        return symbols;
    }

    public Symbol.Terminal getTerminal() {
        return terminal;
    }

    public boolean dotBefore(Symbol symbol) {
        if(production.isEpsilon() || dotPosition == production.size()) {
            return false;
        }

        return symbol.equals(production.getSymbol(dotPosition));
    }

    public Production getProduction() {
        return production;
    }

    public int getDotPosition() {
        return dotPosition;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[" + production.getLeft() + "->");
        int size = production.size();

        for(int i = 0; i <= size; i++) {
            if(dotPosition == i) {
                sb.append("*");
            }

            if(i < size) {
                sb.append(production.getSymbol(i));
            }
        }
        sb.append(", " + terminal + "]");


        return sb.toString();
    }

    public boolean dotAtEnd() {
        if(production.isEpsilon() || dotPosition == production.size()) {
            return true;
        }

        return false;
    }
}
