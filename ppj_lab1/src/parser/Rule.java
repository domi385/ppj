package parser;

import java.util.Map;

/**
 * Razred koji predstavlja pravila za izgradnju leksičkog analizatora.
 *
 * @author Dominik
 */
public class Rule {
    /**
     * zadano stanje
     */
    private String state;
    /**
     * regularni izraz uz pravilo
     */
    private String regex;

    /**
     * leksička jedinka (ili REJECT u slučaju da odbacujemo)
     */
    private String token;

    /**
     * Mapa akcija za pravilo
     */
    private Map<Action, String> actions;

    public Rule(String state, String regex, String token, Map<Action, String> actions) {
        this.state = state;
        this.regex = regex;
        this.token = token;
        this.actions = actions;
    }

    public String getState() {
        return state;
    }

    public String getRegex() {
        return regex;
    }

    public String getToken() {
        return token;
    }

    public Map<Action, String> getActions() {
        return actions;
    }
}
