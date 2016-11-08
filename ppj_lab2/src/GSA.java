import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Dominik on 8.11.2016..
 */
public class GSA {
    public static void main(String[] args) {
        Grammar grammar = new GSAParser(readLines()).getGrammar();


    }

    private static List<String> readLines() {
        Scanner sc = new Scanner(System.in);
        List<String> lines = new ArrayList<>();

        while (sc.hasNextLine()) {
            lines.add(sc.nextLine());
        }

        return lines;
    }
}
