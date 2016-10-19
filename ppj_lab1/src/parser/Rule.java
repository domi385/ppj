package parser;

import java.util.Map;

/**
 * Created by Dominik on 19.10.2016..
 */
public class Rule {
    private String state;
    private String regex;
    private String token;
    private Map<Action, String> actions;

    public Rule(String state, String regex, String token, Map<Action, String> actions) {
        this.state = state;
        this.regex = regex;
        this.token = token;
        this.actions = actions;
    }
}
