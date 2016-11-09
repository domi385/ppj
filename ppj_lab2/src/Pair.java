import analizator.Action;

import java.io.Serializable;

/**
 * Created by Dominik on 8.11.2016..
 */
public class Pair implements Serializable {
    private static final long serialVersionUID = 3495444939768020274L;

    private int state;
    private Symbol symbol;

    public Pair(int state, Symbol symbol) {
        this.state = state;
        this.symbol = symbol;
    }

    public int getState() {
        return state;
    }

    public Symbol getSymbol() {
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

        Pair pair = (Pair) o;

        if (state != pair.state) {
            return false;
        }
        return symbol.equals(pair.symbol);

    }

    @Override
    public int hashCode() {
        int result = state;
        result = 31 * result + symbol.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return Integer.toString(state) + symbol;
    }
}
