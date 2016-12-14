

public class Property {

    private Object value;

    public Property(Object value) {
        this.value = value;
    }

    @SuppressWarnings("unchecked")
    public <T> T getValue() {
        return (T) value;
    }

    public <T> void setValue(T value) {
        this.value = value;
    }

}
