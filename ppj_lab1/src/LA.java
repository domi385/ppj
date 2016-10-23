import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

/**
 * Razred LA se ne mijenja u generatoru. Iterira po izvornom kodu i poziva
 * metodu analyze u razredu Analyzer.
 *
 * @author dunja
 *
 */

public class LA {

    public static void main(String[] args) {
BufferedReader buffReader = new BufferedReader(
                new InputStreamReader(System.in));
        List<String> inputLines = new LinkedList<String>();
        String inputLine = null;
        try {
            inputLine = buffReader.readLine();

            while (inputLine != null) {
                inputLines.add(inputLine);
                inputLine = buffReader.readLine();
            }
        } catch (IOException ignorable) {
            // TODO Auto-generated catch block
            ignorable.printStackTrace();
        }

        String input = String.join("\n", inputLines);
		
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
