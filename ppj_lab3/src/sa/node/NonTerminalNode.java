package sa.node;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import sa.Environment;
import sa.Property;
import sa.PropertyType;
import sa.Symbol;
import sa.rule.RuleStrategy;
import sa.rule.RuleUtility;

public class NonTerminalNode extends Node {

    Symbol.Nonterminal symbol;

    private RuleStrategy evaluationStrategy;
    private Map<PropertyType, Property> properties;

    public NonTerminalNode(String value, int depth, Node parent) {
        super(value, depth, parent);
        symbol = new Symbol.Nonterminal(value);
        evaluationStrategy = RuleUtility.getRule(value);
        properties = new HashMap<PropertyType, Property>();
    }

    public Property getProperty(PropertyType tip) {
        return properties.get(tip);
    }

    public void setProperty(PropertyType propertyType, Property property) {
        properties.put(propertyType, property);
    }

    public Set<PropertyType> getPropertyTypes() {

        return properties.keySet();
    }

    @Override
    public Symbol getSymbol() {
        return symbol;
    }

    @Override
    public void visitNode(Environment environment) {
        evaluationStrategy.evaluate(this, environment);
    }

    public boolean hasProperty(PropertyType propertyType) {
        return properties.containsKey(propertyType);
    }

}
