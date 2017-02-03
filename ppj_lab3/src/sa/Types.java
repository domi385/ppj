package sa;
public enum Types {
    VOID, CHAR, INT, CONST_T, CONST_CHAR, CONST_INT, ARRAY, ARRAY_CHAR, ARRAY_CONST_CHAR, ARRAY_CONST_T, T, ARRAY_T, FUNCTION;

    public static Types getConstType(Types value) {
        switch (value) {
        case CHAR:
            return CONST_CHAR;
        case INT:
            return CONST_INT;
        case ARRAY_CHAR:
            return ARRAY_CONST_CHAR;
        default:
            throw new IllegalArgumentException();
        }
    }

    public static Types getArrayType(Types value) {
        switch (value) {
        case CHAR:
            return ARRAY_CHAR;
        case INT:
            return ARRAY;
        case CONST_INT:
            return ARRAY_CONST_T;
        case CONST_CHAR:
            return ARRAY_CONST_CHAR;
        default:
            throw new IllegalArgumentException();
        }
    }

    public static Object getArrayTypeFrom(Types value) {
        switch (value) {
        case CHAR:
            return ARRAY_CHAR;
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
