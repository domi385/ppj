package parser;

/**
 * Created by Dominik on 19.10.2016..
 */
public enum Action {

    NOVI_REDAK("nextLineNum = lineNum + 1;", false),

    UDJI_U_STANJE("nextState = LexerState.", true),

    VRATI_SE("nextCurrIndex = currentIndex + ", true);

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
