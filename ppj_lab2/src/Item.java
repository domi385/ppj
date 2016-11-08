import java.io.Serializable;

/**
 * Created by Dominik on 8.11.2016..
 */
public class Item implements Serializable {
    private static final long serialVersionUID = -7438596871526326674L;

    private Production production;
    private int dotPosition;

    public Item(Production production, int dotPosition) {
        this.production = production;
        this.dotPosition = dotPosition;
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
        return production.equals(item.production);

    }

    @Override
    public int hashCode() {
        int result = production.hashCode();
        result = 31 * result + dotPosition;
        return result;
    }
}
