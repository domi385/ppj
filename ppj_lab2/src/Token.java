import java.io.Serializable;

/**
 * Created by Dominik on 8.11.2016..
 */
public class Token implements Serializable {
    private static final long serialVersionUID = -2918507670494398991L;

    private Symbol.Terminal type;
    private int row;
    private String value;

    public Token(Symbol.Terminal type, int row, String value) {
        this.type = type;
        this.row = row;
        this.value = value;
    }

    public Symbol.Terminal getType() {
        return type;
    }

    public int getRow() {
        return row;
    }

    public String getValue() {
        return value;
    }
}
