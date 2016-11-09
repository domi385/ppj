package analizator;

import java.io.Serializable;

/**
 * Created by Dominik on 8.11.2016..
 */
public class Action implements Serializable {
    private static final long serialVersionUID = 3658876873381141516L;

    public enum ActionEnum {
        SHIFT,
        REDUCE,
        ACCEPT,
        ERROR
    }

    private ActionEnum action;
    private int parameter;

    public Action(ActionEnum action, int parameter) {
        this.action = action;
        this.parameter = parameter;
    }

    public ActionEnum getAction() {
        return action;
    }

    public int getParameter() {
        return parameter;
    }
}
