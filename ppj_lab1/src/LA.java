import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

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
        String input = null;
        try {
            input = new String(Files.readAllBytes(Paths
                    .get("integration/minusLang.in")),
                    StandardCharsets.UTF_8);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
