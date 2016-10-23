import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Generator leksickog analizatora.
 *
 *
 */

public class GLA {

    public static void main(String[] args) {

        try {

            // System.setIn(new FileInputStream(
            // "integration/lab1_ppjLang.lan.txt"));

            Scanner sc = new Scanner(System.in);
            GeneratorParser.parse(sc);

            String LexerStateFile = new String(
                    "public enum LexerState {");
            String LAStates = String.join(",",
                    GeneratorParser.states);
            LexerStateFile += LAStates + "}";

            File LSFile = new File("analizator/LexerState.java");
            FileWriter LSWriter = new FileWriter(LSFile, false);
            LSWriter.write(LexerStateFile);
            LSWriter.close();

            String TokenTypeFile = new String(
                    " public enum TokenType {" + "REJECT,");
            String TokenTypes = String.join(",",
                    GeneratorParser.tokens);
            TokenTypeFile += TokenTypes + "}";

            File TTFile = new File("analizator/TokenType.java");
            FileWriter TTWriter = new FileWriter(TTFile, false);
            TTWriter.write(TokenTypeFile);
            TTWriter.close();

            List<Rule> rules = GeneratorParser.rules;
            String cases = "";
            for (Rule rule : rules) {
                String actionState = rule.getState();
                String actionRegex = rule.getRegex();
                String tokenType = rule.getToken();
                String actions = "";

                Map<Action, String> actionMap = rule.getActions();
                Set<Action> actionSet = actionMap.keySet();
                for (Action act : actionSet) {
                    actions += act
                            .generateAction(actionMap.get(act));
                }

                cases += generateCase(tokenType, actionState,
                        actionRegex, actions);
            }

            String analyzerFile = new String(
                    Files.readAllBytes(Paths
                            .get("analizator/AnalyzerTemplate.txt")),
                    StandardCharsets.UTF_8);
            analyzerFile = analyzerFile.replace("#ACTIONS", cases);
            File AFile = new File("analizator/Analyzer.java");
            FileWriter AWriter = new FileWriter(AFile, false);
            AWriter.write(analyzerFile);
            AWriter.close();

            String beginState = GeneratorParser.states.get(0);

            String LAString = new String(Files.readAllBytes(Paths
                    .get("analizator/LATemplate.txt")),
                    StandardCharsets.UTF_8);
            LAString = LAString.replace("BEGIN", beginState);
            File LAFile = new File("analizator/LA.java");
            FileWriter LAWriter = new FileWriter(LAFile, false);
            LAWriter.write(LAString);
            LAWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String generateCase(String tokenType,
            String state, String regex, String actions) {
        return "if(state == LexerState." + state
                + " && input.matches(\"^(" + regex
                + ")(.|\\\\s)*$\")){ \n" + "int length = 0; \n"
                + "String tokenName = \"\"; \n"
                + "tokenName = findLongest(input, \"(" + regex
                + ")\"); \n" + "length = tokenName.length(); \n"
                + "if(length > maxLen){ \n" + "maxLen = length; \n"
                + "nextTokenType = TokenType." + tokenType + "; \n"
                + "nextTokenName = tokenName; \n"
                + "nextState = state;" + "nextLineNum = lineNum;"
                + "nextCurrIndex = currIndex + length;" + actions
                + "\n } \n }";
    }
}
