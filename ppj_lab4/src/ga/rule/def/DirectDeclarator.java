package ga.rule.def;

import ga.Environment;
import ga.Property;
import ga.PropertyType;
import ga.SemanticException;
import ga.SemantickiAnalizator;
import ga.Types;
import ga.node.Node;
import ga.node.NonTerminalNode;
import ga.node.TerminalNode;
import ga.rule.RuleStrategy;
import javafx.scene.control.Tab;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DirectDeclarator extends RuleStrategy {

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {
        if (node.getChildNodeNumber() == 1) {
            TerminalNode identificator = (TerminalNode) node.getChidlAt(0);
            if (identificator.getSymbol().getSymbol().equals(Types.VOID.toString())
                    || environment.isDeclaredLocaly(identificator.getValue())) {
                throw new SemanticException(node.toString());
            }
            Types type = node.getProperty(PropertyType.N_TYPE).getValue();
            //environment.declareLocalVariable(identificator.getValue(), type);
            node.setProperty(PropertyType.TYPE, new Property(type));

        } else if (node.getChildNodeNumber() == 4) {

            Node thirdChildNode = node.getChidlAt(2);
            if (thirdChildNode.getSymbol().getSymbol().equals("BROJ")) {

                TerminalNode identificator = (TerminalNode) node.getChidlAt(0);
                if (identificator.getSymbol().getSymbol().equals(Types.VOID.toString())
                        || environment.isDeclaredLocaly(identificator.getValue())) {

                    throw new SemanticException(node.toString());
                }

                Integer numberValue = 0;
                try {
                    numberValue = Integer.parseInt(((TerminalNode) node.getChidlAt(2)).getValue());
                } catch (NumberFormatException ex) {

                    throw new SemanticException(node.toString());

                }
                if (numberValue < 1 || numberValue > 1024) {

                    throw new SemanticException(node.toString());
                }

                Types type = Types.getArrayType(node.getProperty(PropertyType.N_TYPE).getValue());
                // TODO check
                //environment.declareLocalVariable(identificator.getValue(), type);
                node.setProperty(PropertyType.TYPE, new Property(type));
                node.setProperty(PropertyType.NUM_ELEM, new Property(numberValue));

            } else if (thirdChildNode.getSymbol().getSymbol().equals("KR_VOID")) {

                String functionName = ((TerminalNode) node.getChidlAt(0)).getValue();
                Types returnType = node.getProperty(PropertyType.N_TYPE).getValue();
                if (environment.isDeclaredLocaly(functionName)) {

                    if (!checkFunctionDeclaration(functionName, returnType, Arrays.asList(),
                            environment)) {

                        throw new SemanticException(node.toString());
                    }
                } else {
                    environment.declareFunction(functionName, returnType, Arrays.asList());
                }
                node.setProperty(PropertyType.TYPE, new Property(Types.FUNCTION));
            } else if (thirdChildNode.getSymbol().getSymbol().equals("<lista_parametara>")) {

                node.getChidlAt(2).visitNode(environment);
                String functionName = ((TerminalNode) node.getChidlAt(0)).getValue();

                Types returnType = node.getProperty(PropertyType.N_TYPE).getValue();
                List<Types> parameterTypes = ((NonTerminalNode) node.getChidlAt(2)).getProperty(
                        PropertyType.TYPES).getValue();

                if (environment.isDeclaredLocaly(functionName)) {

                    if (!checkFunctionDeclaration(functionName, returnType, parameterTypes,
                            environment)) {

                        throw new SemanticException(node.toString());
                    }

                } else {
                    environment.declareFunction(functionName, returnType, parameterTypes);
                }
                node.setProperty(PropertyType.TYPE, new Property(Types.FUNCTION));
            } else {
                // losa produkcija
            }
        } else {
            // losa produkcija
        }
    }


    @Override
    public void emit(NonTerminalNode node, Environment environment) {
        if(node.getChildNodeNumber() == 1) {
            TerminalNode identificator = (TerminalNode) node.getChidlAt(0);
            Environment.TableEntry entry = environment.declareLocalVariable(identificator.getValue(), 1);
            SemantickiAnalizator.currentEntry = entry;

            //if (type)
        }

        //TODO: Ostale
    }

    private boolean checkFunctionDeclaration(String functionName, Types returnType,
            List<Types> parameterTypes, Environment environment) {

        if (!environment.isDeclaredLocaly(functionName)) {
            return true;
        }

        return Environment.checkFunctionDeclaration(environment, functionName, returnType,
                parameterTypes);
    }
}
