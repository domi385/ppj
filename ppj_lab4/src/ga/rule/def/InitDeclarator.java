package ga.rule.def;

import ga.Environment;
import ga.Property;
import ga.PropertyType;
import ga.SemanticException;
import ga.Ulaz;
import ga.Types;
import ga.node.NonTerminalNode;
import ga.rule.RuleStrategy;
import ga.rule.RuleUtility;

import java.util.List;

public class InitDeclarator extends RuleStrategy {

    @SuppressWarnings("unchecked")
    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {

        if (node.getChildNodeNumber() == 1) {

            NonTerminalNode directDeclarator = (NonTerminalNode) node.getChidlAt(0);

            directDeclarator.setProperty(PropertyType.N_TYPE, node.getProperty(PropertyType.N_TYPE));
            directDeclarator.visitNode(environment);

            if (!RuleUtility.checkNotType((Types) directDeclarator.getProperty(PropertyType.N_TYPE).getValue(),
                    Types.CONST_T) || !RuleUtility.checkNotType(directDeclarator, Types.ARRAY_CONST_T)) {

                throw new SemanticException(node.toString());
            }

        } else if (node.getChildNodeNumber() == 3) {

            NonTerminalNode directDeclarator = (NonTerminalNode) node.getChidlAt(0);
            directDeclarator.setProperty(PropertyType.N_TYPE, node.getProperty(PropertyType.N_TYPE));
            directDeclarator.visitNode(environment);

            node.getChidlAt(2).visitNode(environment);

            Types directDeclaratorType = (Types) directDeclarator.getProperty(PropertyType.TYPE).getValue();

            if (RuleUtility.checkType(directDeclaratorType, Types.CONST_T) ||
                RuleUtility.checkType(directDeclaratorType, Types.T)) {

                if (!RuleUtility.checkType((NonTerminalNode) node.getChidlAt(2),
                        ((NonTerminalNode) node.getChidlAt(0)).getProperty(PropertyType.TYPE).getValue()) ||
                    !RuleUtility.checkType((NonTerminalNode) node.getChidlAt(2), Types.T)) {

                    throw new SemanticException(node.toString());

                }

            } else if (RuleUtility.checkType(directDeclaratorType, Types.ARRAY_CONST_T) ||
                       RuleUtility.checkType(directDeclaratorType, Types.ARRAY_T)) {

                NonTerminalNode initializer = ((NonTerminalNode) node.getChidlAt(2));

                Property num_elem_prop = initializer.getProperty(PropertyType.NUM_ELEM);
                if (num_elem_prop == null) {

                    throw new SemanticException(node.toString());
                }

                Integer initializerElementNumber = num_elem_prop.getValue();

                Integer directDeclaratorElementNumber = directDeclarator.getProperty(PropertyType.NUM_ELEM).getValue();

                if (initializerElementNumber > directDeclaratorElementNumber) {

                    throw new SemanticException(node.toString());
                }

                List<Types> initializerElementTypes =
                        (List<Types>) initializer.getProperty(PropertyType.TYPES).getValue();
                for (Types type : initializerElementTypes) {

                    if (!RuleUtility.checkType(type, Types.T)) {
                        throw new SemanticException(node.toString());
                    }
                }

            } else {

                throw new SemanticException(node.toString());
            }

        } else {
            // losa produkcija
        }
    }

    @Override
    public void emit(NonTerminalNode node, Environment environment) {
        Ulaz.init = true;

        node.getChidlAt(0).visitNode(environment);
        if (node.getChildNodeNumber() == 3) {
            node.getChidlAt(2).visitNode(environment);

            if(environment.getParentEnvironment() == null) {
                String name = Ulaz.currentEntry.getName().toUpperCase();
                int size = Ulaz.currentEntry.getSize();
                int[] konst = Ulaz.konst;

                System.out.println("G_" + name + "\t" + Ulaz.defineWord(size, konst));
            } else {
               if(Ulaz.konst != null) {
                   int size = Ulaz.currentEntry.getSize();
                   int[] konst = Ulaz.konst;
                   DeclarationList.params.put(size, konst);
               }
            }
        }

        Ulaz.init = false;
    }
}
