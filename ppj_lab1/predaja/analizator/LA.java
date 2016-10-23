import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Razred LA se ne mijenja u generatoru. Iterira po izvornom kodu i poziva
 * metodu analyze u razredu Analyzer.
 *
 * @author dunja
 *
 */

public class LA {

    public static void main(String[] args) {

        // String input = args[0];
        // String input = null;
        // try {
        // input = new String(Files.readAllBytes(Paths
        // .get("integration/lab1_program1.in.c")),
        // StandardCharsets.UTF_8);
        // } catch (IOException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
                BufferedReader buffReader = new BufferedReader(
                new InputStreamReader(System.in));
        StringBuilder sbInput = new StringBuilder();
        String inputLine = null;
        try {
            inputLine = buffReader.readLine();
            while (inputLine != null) {
                sbInput.append(inputLine);
                inputLine = buffReader.readLine() + "\n";
            }
        } catch (IOException ignorable) {
            // TODO Auto-generated catch block
            ignorable.printStackTrace();
        }

        String input = sbInput.toString();
        input = input.substring(0, input.length() - 1);
		int currIndex = 0;
        int lineNum = 1;
        LexerState state = LexerState.S_pocetno;

        while (input.length() > currIndex) {

            LAState laState = Analyzer.analyze(
                    input.substring(currIndex, input.length()),
                    lineNum, state, currIndex);

            state = laState.getState();
            lineNum = laState.getLineNum();
            currIndex = laState.getCurrIndex();
        }

    }

}
