

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Vecina koda razreda generira se ovisno o ulaznoj datoteci. 
 * Prolazi kroz sve kombinacije stanja i njima odgovarajucih regularnih izraza i provjerava
 * odgovara li pocetak niza zadanom regularnom izrazu, a trenutno stanje trazenom stanju. 
 * Ukoliko odgovara, provjerava se je li to najdulji do sad pronacen niz. Podaci iz akcije
 * najduljeg niza se spremaju i na kraju ispisuju i vracaju pozivatelju metode.
 *
 * @author dunja
 *
 */
public class Analyzer {
	

	public static LAState analyze(String input, int lineNum, LexerState state, int currIndex) {
		
		if(input.length() == 0){
			return new LAState(lineNum, state, currIndex);
		}
		
		LexerState nextState = state;
		int nextLineNum = lineNum;
		int maxLen = 0;
		int nextCurrIndex = currIndex;
		String nextTokenName= null;
		TokenType nextTokenType = null;
		
		if(state == LexerState.S_pocetno && input.matches("^(\\t| )(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(\\t| )"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.REJECT; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(\\n)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(\\n)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.REJECT; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;nextLineNum = lineNum + 1;;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(//)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(//)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.REJECT; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;nextState = LexerState.S_jednolinijskiKomentar;
 } 
 }if(state == LexerState.S_jednolinijskiKomentar && input.matches("^(\\n)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(\\n)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.REJECT; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;nextLineNum = lineNum + 1;;nextState = LexerState.S_pocetno;
 } 
 }if(state == LexerState.S_jednolinijskiKomentar && input.matches("^((\\(|\\)|\\{|\\}|\\||\\*|\\\\|\\|\\t|\\n| |!|\"|#|%|&|'|\\+|,|\\-|\\.|/|0|1|2|3|4|5|6|7|8|9|:|;|<|=|>|\\?|@|A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z|\\[|\\]|\\^|_|`|a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z|~))(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "((\\(|\\)|\\{|\\}|\\||\\*|\\\\|\\|\\t|\\n| |!|\"|#|%|&|'|\\+|,|\\-|\\.|/|0|1|2|3|4|5|6|7|8|9|:|;|<|=|>|\\?|@|A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z|\\[|\\]|\\^|_|`|a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z|~))"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.REJECT; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(/\\*)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(/\\*)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.REJECT; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;nextState = LexerState.S_komentar;
 } 
 }if(state == LexerState.S_komentar && input.matches("^(\\*/)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(\\*/)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.REJECT; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;nextState = LexerState.S_pocetno;
 } 
 }if(state == LexerState.S_komentar && input.matches("^(\\n)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(\\n)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.REJECT; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;nextLineNum = lineNum + 1;;
 } 
 }if(state == LexerState.S_komentar && input.matches("^((\\(|\\)|\\{|\\}|\\||\\*|\\\\|\\|\\t|\\n| |!|\"|#|%|&|'|\\+|,|\\-|\\.|/|0|1|2|3|4|5|6|7|8|9|:|;|<|=|>|\\?|@|A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z|\\[|\\]|\\^|_|`|a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z|~))(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "((\\(|\\)|\\{|\\}|\\||\\*|\\\\|\\|\\t|\\n| |!|\"|#|%|&|'|\\+|,|\\-|\\.|/|0|1|2|3|4|5|6|7|8|9|:|;|<|=|>|\\?|@|A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z|\\[|\\]|\\^|_|`|a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z|~))"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.REJECT; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(\")(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(\")"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.REJECT; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;nextCurrIndex = currIndex + 0; nextTokenName = nextTokenName.substring(0,0);nextState = LexerState.S_string;
 } 
 }if(state == LexerState.S_string && input.matches("^(\"((\\(|\\)|\\{|\\}|\\||\\*|\\\\|\\|\\t| |!|#|%|&|'|\\+|,|\\-|\\.|/|0|1|2|3|4|5|6|7|8|9|:|;|<|=|>|\\?|@|A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z|\\[|\\]|\\^|_|`|a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z|~)|\\\\\")*\")(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(\"((\\(|\\)|\\{|\\}|\\||\\*|\\\\|\\|\\t| |!|#|%|&|'|\\+|,|\\-|\\.|/|0|1|2|3|4|5|6|7|8|9|:|;|<|=|>|\\?|@|A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z|\\[|\\]|\\^|_|`|a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z|~)|\\\\\")*\")"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.NIZ_ZNAKOVA; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;nextState = LexerState.S_pocetno;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(break)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(break)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.KR_BREAK; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(char)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(char)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.KR_CHAR; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(const)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(const)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.KR_CONST; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(continue)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(continue)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.KR_CONTINUE; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(else)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(else)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.KR_ELSE; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(float)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(float)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.KR_FLOAT; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(for)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(for)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.KR_FOR; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(if)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(if)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.KR_IF; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(int)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(int)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.KR_INT; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(return)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(return)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.KR_RETURN; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(struct)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(struct)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.KR_STRUCT; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(void)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(void)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.KR_VOID; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(while)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(while)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.KR_WHILE; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^((_|(a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z|A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z))(_|(a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z|A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z)|(0|1|2|3|4|5|6|7|8|9))*)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "((_|(a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z|A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z))(_|(a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z|A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z)|(0|1|2|3|4|5|6|7|8|9))*)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.IDN; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^((0|1|2|3|4|5|6|7|8|9)(0|1|2|3|4|5|6|7|8|9)*)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "((0|1|2|3|4|5|6|7|8|9)(0|1|2|3|4|5|6|7|8|9)*)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.BROJ; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(0(X|x)((0|1|2|3|4|5|6|7|8|9)|a|b|c|d|e|f|A|B|C|D|E|F)((0|1|2|3|4|5|6|7|8|9)|a|b|c|d|e|f|A|B|C|D|E|F)*)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(0(X|x)((0|1|2|3|4|5|6|7|8|9)|a|b|c|d|e|f|A|B|C|D|E|F)((0|1|2|3|4|5|6|7|8|9)|a|b|c|d|e|f|A|B|C|D|E|F)*)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.BROJ; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^((0|1|2|3|4|5|6|7|8|9)(0|1|2|3|4|5|6|7|8|9)*\\.(0|1|2|3|4|5|6|7|8|9)*(|((e|E)(|\\+|\\-)(0|1|2|3|4|5|6|7|8|9)(0|1|2|3|4|5|6|7|8|9)*)))(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "((0|1|2|3|4|5|6|7|8|9)(0|1|2|3|4|5|6|7|8|9)*\\.(0|1|2|3|4|5|6|7|8|9)*(|((e|E)(|\\+|\\-)(0|1|2|3|4|5|6|7|8|9)(0|1|2|3|4|5|6|7|8|9)*)))"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.BROJ; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^((0|1|2|3|4|5|6|7|8|9)*\\.(0|1|2|3|4|5|6|7|8|9)(0|1|2|3|4|5|6|7|8|9)*(|((e|E)(|\\+|\\-)(0|1|2|3|4|5|6|7|8|9)(0|1|2|3|4|5|6|7|8|9)*)))(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "((0|1|2|3|4|5|6|7|8|9)*\\.(0|1|2|3|4|5|6|7|8|9)(0|1|2|3|4|5|6|7|8|9)*(|((e|E)(|\\+|\\-)(0|1|2|3|4|5|6|7|8|9)(0|1|2|3|4|5|6|7|8|9)*)))"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.BROJ; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^('(\\(|\\)|\\{|\\}|\\||\\*|\\\\|\\| |!|\"|#|%|&|\\+|,|\\-|\\.|/|0|1|2|3|4|5|6|7|8|9|:|;|<|=|>|\\?|@|A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z|\\[|\\]|\\^|_|`|a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z|~)')(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "('(\\(|\\)|\\{|\\}|\\||\\*|\\\\|\\| |!|\"|#|%|&|\\+|,|\\-|\\.|/|0|1|2|3|4|5|6|7|8|9|:|;|<|=|>|\\?|@|A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z|\\[|\\]|\\^|_|`|a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z|~)')"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.ZNAK; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^('\\\\(\\(|\\)|\\{|\\}|\\||\\*|\\\\|\\| |!|\"|#|%|&|'|\\+|,|\\-|\\.|/|0|1|2|3|4|5|6|7|8|9|:|;|<|=|>|\\?|@|A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z|\\[|\\]|\\^|_|`|a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z|~)')(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "('\\\\(\\(|\\)|\\{|\\}|\\||\\*|\\\\|\\| |!|\"|#|%|&|'|\\+|,|\\-|\\.|/|0|1|2|3|4|5|6|7|8|9|:|;|<|=|>|\\?|@|A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z|\\[|\\]|\\^|_|`|a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z|~)')"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.ZNAK; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(\\+\\+)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(\\+\\+)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.OP_INC; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(\\-\\-)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(\\-\\-)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.OP_DEC; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(\\+)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(\\+)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.PLUS; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(\\-)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(\\-)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.MINUS; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(\\*)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(\\*)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.ASTERISK; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(/)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(/)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.OP_DIJELI; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(%)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(%)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.OP_MOD; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(=)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(=)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.OP_PRIDRUZI; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(<)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(<)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.OP_LT; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(<=)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(<=)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.OP_LTE; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(>)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(>)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.OP_GT; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(>=)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(>=)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.OP_GTE; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(==)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(==)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.OP_EQ; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(!=)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(!=)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.OP_NEQ; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(!)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(!)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.OP_NEG; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(~)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(~)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.OP_TILDA; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(&&)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(&&)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.OP_I; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(\\|\\|)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(\\|\\|)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.OP_ILI; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(&)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(&)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.AMPERSAND; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(\\|)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(\\|)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.OP_BIN_ILI; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(\\^)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(\\^)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.OP_BIN_XILI; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(,)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(,)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.ZAREZ; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(;)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(;)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.TOCKAZAREZ; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(\\.)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(\\.)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.TOCKA; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(\\()(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(\\()"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.L_ZAGRADA; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(\\))(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(\\))"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.D_ZAGRADA; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(\\{)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(\\{)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.L_VIT_ZAGRADA; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(\\})(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(\\})"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.D_VIT_ZAGRADA; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(\\[)(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(\\[)"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.L_UGL_ZAGRADA; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(\\])(.|\\s)*$")){ 
int length = 0; 
String tokenName = ""; 
tokenName = findLongest(input, "(\\])"); 
length = tokenName.length(); 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.D_UGL_ZAGRADA; 
nextTokenName = tokenName; 
nextState = state;nextLineNum = lineNum;nextCurrIndex = currIndex + length;
 } 
 }
		
		//Ostatak koda ostaje uvijek isti
		
		//Ispis
		if(nextTokenType != null){
			
			if(nextTokenType != TokenType.REJECT){
				System.out.println(nextTokenType + " " + lineNum + " " + nextTokenName);
			}
			
			lineNum = nextLineNum;
			state = nextState;
			currIndex = nextCurrIndex;
			
			return new LAState(lineNum, state, currIndex);
			
		//Oporavak od pogrecke
		}else{
			
			//System.err.print(input.substring(0, 1));
			
			return Analyzer.analyze(input.substring(1, input.length()), lineNum, state, currIndex+1);
		}
		
		
	}

 public static String findLongest(String input, String regex){
        String s = input;
        while(!s.matches("^("+regex+")$")){
            s=s.substring(0, s.length()-1);
        }
        return s;
    }

}
