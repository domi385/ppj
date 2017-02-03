package sa.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import sa.Environment;
import sa.Property;
import sa.PropertyType;
import sa.SemanticException;
import sa.Types;
import sa.node.NonTerminalNode;
import sa.rule.command.BranchCommand;
import sa.rule.command.Command;
import sa.rule.command.CommandList;
import sa.rule.command.ComplexCommand;
import sa.rule.command.ExpressionCommand;
import sa.rule.command.ExternalDeclarationCommand;
import sa.rule.command.JumpCommand;
import sa.rule.command.LoopCommand;
import sa.rule.command.TranslationUnitExpressionCommand;
import sa.rule.def.Declaration;
import sa.rule.def.DeclarationList;
import sa.rule.def.DirectDeclarator;
import sa.rule.def.FunctionDefinition;
import sa.rule.def.InitDeclarator;
import sa.rule.def.InitDeclaratorList;
import sa.rule.def.Initializer;
import sa.rule.def.JoinmentExpressionList;
import sa.rule.def.ParameterDeclaration;
import sa.rule.def.ParameterList;
import sa.rule.expression.AditiveExpression;
import sa.rule.expression.ArgumentListExpression;
import sa.rule.expression.BinaryAndExpression;
import sa.rule.expression.BinaryOrExpression;
import sa.rule.expression.BinaryXorExpression;
import sa.rule.expression.CastExpression;
import sa.rule.expression.EqualitiyExpression;
import sa.rule.expression.Expression;
import sa.rule.expression.JoiningExpression;
import sa.rule.expression.LogicalAndExpression;
import sa.rule.expression.LogicalOrExpression;
import sa.rule.expression.MultiplicativeExpression;
import sa.rule.expression.PostfixExpression;
import sa.rule.expression.PrimaryExpression;
import sa.rule.expression.RelationExpression;
import sa.rule.expression.TypeNameExpression;
import sa.rule.expression.TypeSpecificatorExpression;
import sa.rule.expression.UnaryExpression;
import sa.rule.expression.UnaryOperator;

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
