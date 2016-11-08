import java.io.Serializable;

/**
 * Znakovi gramatike - završni i nezavršni
 *
 * @author Dominik Stanojevic
 */
public abstract class Symbol implements Serializable{
    private static final long serialVersionUID = -6221509207717815993L;

    private String symbol;

    public Symbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Symbol symbol1 = (Symbol) o;

        return symbol.equals(symbol1.symbol);

    }

    @Override
    public int hashCode() {
        return symbol.hashCode();
    }

    public static class Nonterminal extends Symbol {
        private static final long serialVersionUID = 2806461921546415986L;

        public Nonterminal(String symbol) {
            super(symbol);
        }
    }

    @Override
    public String toString() {
        return symbol;
    }

    public static class Terminal extends Symbol {
        private static final long serialVersionUID = -1278880143337778970L;

        public boolean isSynchronizationSymbol;

        public Terminal(String symbol) {
            super(symbol);
        }
    }
}
