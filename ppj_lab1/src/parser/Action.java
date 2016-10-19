package parser;

/**
 * Created by Dominik on 19.10.2016..
 */
public enum Action {

    NOVI_REDAK("dodati akciju", false),

    UDJI_U_STANJE("dodati akciju", true),

    VRATI_SE("dodati akciju", true);

    private String action;
    private boolean arg;

    Action(String action, boolean arg) {
        this.action = action;
        this.arg = arg;
    }

    public String generateAction(String arg) {
        return action + (arg != null ? arg : "");
    }

    public boolean isArg() {
        return arg;
    }
}
