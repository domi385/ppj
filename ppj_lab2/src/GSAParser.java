import java.util.List;

/**
 * Created by Dominik on 8.11.2016..
 */
public class GSAParser {
    private Grammar grammar;

    public GSAParser(List<String> lines) {
        grammar = parse(lines);
    }

    private Grammar parse(List<String> lines) {
        //TODO
        return null;
    }

    public Grammar getGrammar() {
        return grammar;
    }
}
