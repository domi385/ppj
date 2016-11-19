import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Razred koji predstavlja produkciju
 */
public class Production implements Serializable {
    private static final long serialVersionUID = -1405079230500381873L;

    protected Symbol.Nonterminal left;
    protected List<Symbol> right;

    public Production(Symbol.Nonterminal left, List<Symbol> right) {
        this.left = left;
        this.right = right;
    }

    public Symbol.Nonterminal getLeft() {
        return left;
    }

    public boolean isEpsilon() {
        return right.contains(Symbol.Epsilon.getEpsilon());
    }

    public int size() {
        return isEpsilon() ? 0 : right.size();
    }

    public Symbol getSymbol(int index) {
        if (isEpsilon() || index < 0 || index >= right.size()) {
            throw new IllegalArgumentException("Illegal position");
        }

        return right.get(index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Production that = (Production) o;

        return left.equals(that.left) && right.equals(that.right);

    }

    @Override
    public int hashCode() {
        int result = left.hashCode();
        result = 31 * result + right.hashCode();
        return result;
    }

    @Override
    public String toString() {
        String start = left + "->";
        StringBuilder sb = new StringBuilder(start);
        right.forEach(sb::append);

        return sb.toString();
    }

    public Set<Item> getItems(Set<Symbol.Terminal> terminals) {
        Set<Item> items = new HashSet<>();

        int size = size();
        for (int i = 0; i <= size; i++) {
            for (Symbol.Terminal terminal : terminals) {
                items.add(new Item(this, i, terminal));
            }
        }

        return items;
    }
}
