package sa.rule;

import java.util.HashMap;

import sa.Environment;
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

public abstract class RuleStrategy {

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
        ruleStrategyMap.put("<lista_paramterata>", new ParameterList());
        ruleStrategyMap.put("<deklaracija_parametara>", new ParameterDeclaration());
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

    public abstract void evaluate(NonTerminalNode node, Environment environment);

}
