

public enum Types {
    VOID, CHAR, INT, CONST_T, CONST_CHAR, CONST_INT, ARRAY, ARRAY_CONST_CHAR, ARRAY_CONST_T, T, ARRAY_T, FUNCTION;

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

    public static Types getArrayType(Types value) {
        switch (value) {
        case CHAR:
            return ARRAY_CONST_CHAR;
        case INT:
            return ARRAY;
        case CONST_INT:
            return ARRAY;
        case CONST_CHAR:
            return ARRAY_CONST_CHAR;
        default:
            throw new IllegalArgumentException();
        }
    }

}
