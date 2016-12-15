public class PrimaryExpression extends RuleStrategy {

    @Override
    public void evaluate(NonTerminalNode node, Environment environment) {

        if (node.getChildNodeNumber() == 1) {

            TerminalNode childNode = (TerminalNode) node.getChidlAt(0);

            if (childNode.getSymbol().getSymbol().equals("IDN")) {
                boolean declared = environment.isDeclared(childNode.getValue());
                if (!declared) {
                    throw new SemanticException(node.toString());
                }

                Types identificatorType = environment.getIdentificatorType(childNode.getValue());
                node.setProperty(PropertyType.TYPE, new Property(identificatorType));
                if (identificatorType.equals(Types.FUNCTION)) {
                    node.setProperty(PropertyType.FUNCTION_NAME, new Property(childNode.getValue()));
                }
                node.setProperty(PropertyType.L_EXPRESSION,
                        new Property(isLExpression(environment, childNode.getValue())));

            } else if (childNode.getSymbol().getSymbol().equals("BROJ")) {

                if (!checkIntValue(childNode.getValue())) {
                    throw new SemanticException(node.toString());
                }
                node.setProperty(PropertyType.TYPE, new Property(Types.INT));
                node.setProperty(PropertyType.L_EXPRESSION, new Property(0));

            } else if (childNode.getSymbol().getSymbol().equals("ZNAK")) {
                if (!checkCharValue(childNode.getValue())) {
                    throw new SemanticException(node.toString());
                }
                node.setProperty(PropertyType.TYPE, new Property(Types.CHAR));
                node.setProperty(PropertyType.L_EXPRESSION, new Property(0));

            } else if (childNode.getSymbol().getSymbol().equals("NIZ_ZNAKOVA")) {

                if (!checkConstCharArray(childNode.getValue())) {
                    throw new SemanticException(node.toString());
                }
                node.setProperty(PropertyType.TYPE, new Property(Types.ARRAY_CONST_CHAR));
                node.setProperty(PropertyType.L_EXPRESSION, new Property(0));
            } else {
                // losa produkcija
            }

        } else if (node.getChildNodeNumber() == 3) {

            node.getChidlAt(1).visitNode(environment);
            node.setProperty(PropertyType.TYPE,
                    ((NonTerminalNode) node.getChidlAt(1)).getProperty(PropertyType.TYPE));

            node.setProperty(PropertyType.L_EXPRESSION,
                    ((NonTerminalNode) node.getChidlAt(1)).getProperty(PropertyType.L_EXPRESSION));
        } else {
            // losa produkcija
        }
    }

    private Integer isLExpression(Environment environment, String identificatorName) {

        Types type = Environment.getDeclaredType(identificatorName, environment);
        if (RuleUtility.isLExpression(type)) {
            return 1;
        }
        return 0;
    }

    public static boolean checkCharValue(String value) {
        if (value.length() == 3 && value.startsWith("'") && value.endsWith("'")) {
            if (value.charAt(1) == '\\') {
                return false;
            }
            return true;
        } else if (value.length() == 4 && value.startsWith("'") && value.endsWith("'")
                && isValidEscapedChar(value)) {
            return true;
        }
        try {
            int intValue = Integer.parseInt(value);
            if (intValue < 0 || intValue > 255) {
                return false;
            }
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    public static boolean isValidEscapedChar(String value) {
        if (value.equals("\'\\\\\'") || value.equals("\'\\n\'") || value.equals("\'\\0\'")
                || value.equals("\'\\t\'") || value.equals("\'\\\'\'") || value.equals("\'\\\"\'")) {
            return true;
        }
        return false;
    }

    public static boolean checkConstCharArray(String value) {
        if (value.length() < 2) {
            return false;
        }
        value = value.substring(1, value.length() - 1);
        value = value.replace("\\t", "").replace("\\n", "").replace("\\0", "").replace("\\'", "")
                .replace("\\\"", "").replace("\\\\", "");
        return !value.contains("\\");
    }

    public static boolean checkIntValue(String value) {
        try {
            if ((value.startsWith("0x") || value.startsWith("0X")) && value.length() > 2) {
                Integer.parseInt(value.substring(2), 16);
            } else {
                Integer.parseInt(value);
            }
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;

    }
}
