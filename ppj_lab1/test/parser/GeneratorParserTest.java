package parser;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Dominik on 19.10.2016..
 */
public class GeneratorParserTest {
    @Test
    public void parse() throws Exception {
        List<String> lines = new ArrayList<>();
        lines.add("{oktalnaZnamenka} 0|1|2|3|4|5|6|7");
        lines.add("{dekadskaZnamenka} {oktalnaZnamenka}|8|9");
        lines.add("{hexZnamenka} a|b|c|d|e|f|{dekadskaZnamenka}|A|B|C|D|E|F");
        lines.add("%X S_pocetno S_komentar S_unarniMinus");
        lines.add("%L IDENTIFIKATOR brojcanaKonstanta znakovnaKonstanta OP_PLUS");

        GeneratorParser.parse(lines);

        List<GeneratorParser.Tuple<String, String>> results = new ArrayList<>();
        results.add(new GeneratorParser.Tuple<>("oktalnaZnamenka", "0|1|2|3|4|5|6|7"));
        results.add(new GeneratorParser.Tuple<>("dekadskaZnamenka", "(0|1|2|3|4|5|6|7)|8|9"));
        results.add(new GeneratorParser.Tuple<>("hexZnamenka",
                "a|b|c|d|e|f|((0|1|2|3|4|5|6|7)|8|9)|A|B|C|D|E|F"));

        for(GeneratorParser.Tuple result : results) {
            Assert.assertEquals(result.getRight(), GeneratorParser.definitions.get(result.getLeft()));
        }

        List<String> states = GeneratorParser.states;
        Assert.assertEquals("S_pocetno", states.get(0));
        Assert.assertEquals("S_komentar", states.get(1));
        Assert.assertEquals("S_unarniMinus", states.get(2));

        List<String> tokens = GeneratorParser.tokens;
        Assert.assertEquals("IDENTIFIKATOR", tokens.get(0));
        Assert.assertEquals("brojcanaKonstanta", tokens.get(1));
        Assert.assertEquals("znakovnaKonstanta", tokens.get(2));
        Assert.assertEquals("OP_PLUS", tokens.get(3));
    }

    @Test
    public void parse2() throws Exception {
        GeneratorParser.parse("test.txt");
    }

}