package ga.rule;

import ga.Environment;
import ga.Property;
import ga.PropertyType;
import ga.SemanticException;
import ga.Types;
import ga.node.NonTerminalNode;
import ga.rule.command.BranchCommand;
import ga.rule.command.Command;
import ga.rule.command.CommandList;
import ga.rule.command.ComplexCommand;
import ga.rule.command.ExpressionCommand;
import ga.rule.command.ExternalDeclarationCommand;
import ga.rule.command.JumpCommand;
import ga.rule.command.LoopCommand;
import ga.rule.command.TranslationUnitExpressionCommand;
import ga.rule.def.Declaration;
import ga.rule.def.DeclarationList;
import ga.rule.def.DirectDeclarator;
import ga.rule.def.FunctionDefinition;
import ga.rule.def.InitDeclarator;
import ga.rule.def.InitDeclaratorList;
import ga.rule.def.Initializer;
import ga.rule.def.JoinmentExpressionList;
import ga.rule.def.ParameterDeclaration;
import ga.rule.def.ParameterList;
import ga.rule.expression.AditiveExpression;
import ga.rule.expression.ArgumentListExpression;
import ga.rule.expression.BinaryAndExpression;
import ga.rule.expression.BinaryOrExpression;
import ga.rule.expression.BinaryXorExpression;
import ga.rule.expression.CastExpression;
import ga.rule.expression.EqualitiyExpression;
import ga.rule.expression.Expression;
import ga.rule.expression.JoiningExpression;
import ga.rule.expression.LogicalAndExpression;
import ga.rule.expression.LogicalOrExpression;
import ga.rule.expression.MultiplicativeExpression;
import ga.rule.expression.PostfixExpression;
import ga.rule.expression.PrimaryExpression;
import ga.rule.expression.RelationExpression;
import ga.rule.expression.TypeNameExpression;
import ga.rule.expression.TypeSpecificatorExpression;
import ga.rule.expression.UnaryExpression;
import ga.rule.expression.UnaryOperator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RuleUtility {
    public static HashMap<String, RuleStrategy> ruleStrategyMap = new HashMap<>();

    static {
        // izrazi
        ruleStrategyMap.put("<primarni_izraz>", new PrimaryExpression());
        ruleStrategyMap.put("<postfiks_izraz>", new PostfixExpression());
        ruleStrategyMap.put("<lista_argumenata>", new ArgumentListExpression());
        ruleStrategyMap.put("<unarni_izraz>", new UnaryExpression());
        ruleStrategyMap.put("<unarni_operator>", new UnaryOperator());
        ruleStrategyMap.put("<cast_izraz>", new CastExpression());
        ruleStrategyMap.put("<ime_tipa>", new TypeNameExpression());
        ruleStrategyMap.put("<specifikator_tipa>", new TypeSpecificatorExpression());
        ruleStrategyMap.put("<multiplikativni_izraz>", new MultiplicativeExpression());
        ruleStrategyMap.put("<aditivni_izraz>", new AditiveExpression());
        ruleStrategyMap.put("<odnosni_izraz>", new RelationExpression());
        ruleStrategyMap.put("<jednakosni_izraz>", new EqualitiyExpression());
        ruleStrategyMap.put("<bin_i_izraz>", new BinaryAndExpression());
        ruleStrategyMap.put("<bin_xili_izraz>", new BinaryXorExpression());
        ruleStrategyMap.put("<bin_ili_izraz>", new BinaryOrExpression());
        ruleStrategyMap.put("<log_i_izraz>", new LogicalAndExpression());
        ruleStrategyMap.put("<log_ili_izraz>", new LogicalOrExpression());
        ruleStrategyMap.put("<izraz_pridruzivanja>", new JoiningExpression());
        ruleStrategyMap.put("<izraz>", new Expression());

        // naredbe
        ruleStrategyMap.put("<slozena_naredba>", new ComplexCommand());
        ruleStrategyMap.put("<lista_naredbi>", new CommandList());
        ruleStrategyMap.put("<naredba>", new Command());
        ruleStrategyMap.put("<izraz_naredba>", new ExpressionCommand());
        ruleStrategyMap.put("<naredba_grananja>", new BranchCommand());
        ruleStrategyMap.put("<naredba_petlje>", new LoopCommand());
        ruleStrategyMap.put("<naredba_skoka>", new JumpCommand());
        ruleStrategyMap.put("<prijevodna_jedinica>", new TranslationUnitExpressionCommand());
        ruleStrategyMap.put("<vanjska_deklaracija>", new ExternalDeclarationCommand());

        // definitions and declarations
        ruleStrategyMap.put("<definicija_funkcije>", new FunctionDefinition());
        ruleStrategyMap.put("<lista_parametara>", new ParameterList());
        ruleStrategyMap.put("<deklaracija_parametra>", new ParameterDeclaration());
        ruleStrategyMap.put("<lista_deklaracija>", new DeclarationList());
        ruleStrategyMap.put("<deklaracija>", new Declaration());
        ruleStrategyMap.put("<lista_init_deklaratora>", new InitDeclaratorList());
        ruleStrategyMap.put("<init_deklarator>", new InitDeclarator());
        ruleStrategyMap.put("<izravni_deklarator>", new DirectDeclarator());
        ruleStrategyMap.put("<inicijalizator>", new Initializer());
        ruleStrategyMap.put("<lista_izraza_pridruzivanja>", new JoinmentExpressionList());
    }

    public static RuleStrategy getRule(String symbolValue) {
        return ruleStrategyMap.get(symbolValue);
    }

    public static void checkEnvironment(Environment environment) {
        if (!checkMain(Environment.getGlobalEnvironment(environment))) {
            throw new SemanticException("main");
        }
        if (!checkFunctionDeclarations(Environment.getGlobalEnvironment(environment))) {
            throw new SemanticException("funkcija");
        }
    }

    private static boolean checkFunctionDeclarations(Environment globalEnvironment) {

        List<Environment> environments = getAllEnvironments(globalEnvironment);

        Set<Environment.FunctionTableEntry> definedFunctions = new HashSet<>();
        Set<Environment.FunctionTableEntry> declaredFunctions = new HashSet<>();

        for (Environment env : environments) {
            Set<Environment.FunctionTableEntry> functions = new HashSet<>(env.getFunctionsTable()
                    .values());
            for (Environment.FunctionTableEntry entry : functions) {
                if (entry.getDefined() == true) {
                    definedFunctions.add(entry);
                } else {
                    declaredFunctions.add(entry);
                }
            }
        }

        for (Environment.FunctionTableEntry entry : declaredFunctions) {
            if (!definedFunctions.contains(entry)) {
                return false;
            }
        }

        return true;
    }

    private static List<Environment> getAllEnvironments(Environment environment) {

        List<Environment> children = environment.getChildrenEnvironments();
        List<Environment> environments = new ArrayList<>();
        environments.add(environment);

        if (children != null) {
            for (Environment child : children) {
                environments.addAll(getAllEnvironments(child));
            }
        }

        return environments;
    }

    private static boolean checkMain(Environment globalEnvironment) {
        List<Types> parameterTypes = new ArrayList<>();
        return Environment.checkFunctionDeclaration(globalEnvironment, "main", Types.INT,
                parameterTypes) && globalEnvironment.isDefinedFunction("main");
    }

    public static boolean checkNotType(NonTerminalNode node, Types type) {
        Types currType = node.getProperty(PropertyType.TYPE).getValue();
        if (type.equals(Types.VOID)) {
            return !currType.equals(Types.VOID);

        } else if (type.equals(Types.CONST_T)) {
            switch (currType) {
            case CONST_CHAR:
            case CONST_INT:
            case CONST_T:
                return false;
            default:
                return true;

            }
        }
        return true;
    }

    public static boolean checkNotType(Types currType, Types type) {
        if (type.equals(Types.VOID)) {
            return !currType.equals(Types.VOID);

        } else if (type.equals(Types.CONST_T)) {
            switch (currType) {
            case CONST_CHAR:
            case CONST_INT:
            case CONST_T:
                return false;
            default:
                return true;

            }
        }
        return true;
    }

    public static boolean checkType(NonTerminalNode node, Types type) {
        Types nodeType = node.getProperty(PropertyType.TYPE).getValue();
        return checkType(nodeType, type);
    }

    /**
     * Implicitna promjena
     *
     * @param originalType
     * @param finalType
     * @return
     */
    public static boolean checkType(Types originalType, Types finalType) {
        if (originalType.equals(finalType)) {
            return true;
        }
        if (originalType.equals(Types.CHAR) && finalType.equals(Types.CONST_CHAR)
                || originalType.equals(Types.CONST_CHAR) && finalType.equals(Types.CHAR)) {
            return true;
        }
        if (originalType.equals(Types.CONST_CHAR) && finalType.equals(Types.T)) {
            return true;
        }
        if (originalType.equals(Types.INT) && finalType.equals(Types.CONST_INT)
                || originalType.equals(Types.CONST_INT) && finalType.equals(Types.INT)) {
            return true;
        }
        if (originalType.equals(Types.T) && finalType.equals(Types.CONST_T)
                || originalType.equals(Types.CONST_T) && finalType.equals(Types.T)) {
            return true;
        }
        if (originalType.equals(Types.CHAR) && finalType.equals(Types.INT)
                || originalType.equals(Types.CHAR) && finalType.equals(Types.CONST_INT)) {
            return true;
        }
        if (originalType.equals(Types.CHAR) && finalType.equals(Types.T)
                || originalType.equals(Types.CHAR) && finalType.equals(Types.CONST_T)) {
            return true;
        }

        if (originalType.equals(Types.CONST_CHAR) && finalType.equals(Types.CONST_INT)
                || originalType.equals(Types.CONST_CHAR) && finalType.equals(Types.INT)) {
            return true;
        }
        if (originalType.equals(Types.CONST_INT) && finalType.equals(Types.CONST_T)
                || originalType.equals(Types.CONST_T) && finalType.equals(Types.CONST_INT)) {
            return true;
        }
        if (originalType.equals(Types.CONST_INT) && finalType.equals(Types.T)
                || originalType.equals(Types.INT) && finalType.equals(Types.T)) {
            return true;
        }
        if (originalType.equals(Types.ARRAY_CHAR) && finalType.equals(Types.ARRAY_CONST_CHAR)) {
            return true;
        }
        if (originalType.equals(Types.ARRAY_CHAR) && finalType.equals(Types.ARRAY_T)) {
            return true;
        }
        if (originalType.equals(Types.ARRAY_T) && finalType.equals(Types.ARRAY_CONST_T)
                || originalType.equals(Types.ARRAY) && finalType.equals(Types.ARRAY_CONST_T)) {
            return true;
        }
        if (originalType.equals(Types.ARRAY_CONST_CHAR) && finalType.equals(Types.ARRAY_CONST_T)
                || originalType.equals(Types.ARRAY_CHAR) && finalType.equals(Types.ARRAY_CONST_T)) {
            return true;
        }
        if (originalType.equals(Types.ARRAY_T) && finalType.equals(Types.ARRAY)
                || originalType.equals(Types.ARRAY_CONST_CHAR) && finalType.equals(Types.ARRAY)
                || originalType.equals(Types.ARRAY_CONST_T) && finalType.equals(Types.ARRAY)) {
            return true;
        }
        return false;
    }

    public static boolean checkProperty(NonTerminalNode node, PropertyType type, Object value) {
        Property property = node.getProperty(type);
        if (type.equals(PropertyType.TYPE)) {
            return RuleUtility.checkType((Types) property.getValue(), (Types) value);
        }
        return property.getValue().equals(value);
    }

    public static boolean isLExpression(Types type) {
        if (type == Types.INT || type == Types.CHAR || type == Types.T) {
            return true;
        }
        return false;
    }

}
