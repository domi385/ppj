package sa;

public enum Types {
    VOID, CHAR, INT, CONST_T, CONST_CHAR, CONST_INT, ARRAY, ARRAY_CONST_CHAR;

    public static Types getConstType(Types value) {
        switch (value) {
        case CHAR:
            return CONST_CHAR;
        case INT:
            return CONST_INT;
        default:
            throw new IllegalArgumentException();
        }
    }
}
