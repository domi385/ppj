package analizator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Većina koda razreda generira se ovisno o ulaznoj datoteci. 
 * Prolazi kroz sve kombinacije stanja i njima odgovarajućih regularnih izraza i provjerava
 * odgovara li početak niza zadanom regularnom izrazu, a trenutno stanje traženom stanju. 
 * Ukoliko odgovara, provjerava se je li to najdulji do sad pronađen niz. Podaci iz akcije
 * najduljeg niza se spremaju i na kraju ispisuju i vraćaju pozivatelju metode.
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
		
		if(state == LexerState.S_pocetno && input.matches("^(#\\|).*$")){ 
int length = 0; 
String tokenName = ""; 
Pattern pattern = Pattern.compile("^(#\\|)"); 
Matcher matcher = pattern.matcher(input); 
if (matcher.find()){ 
tokenName = matcher.group(1); 
length = tokenName.length(); 
} 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.REJECT; 
nextTokenName = tokenName; 
nextCurrIndex = currIndex + length;nextState = LexerState.S_komentar;
 } 
 }if(state == LexerState.S_komentar && input.matches("^(\\|#).*$")){ 
int length = 0; 
String tokenName = ""; 
Pattern pattern = Pattern.compile("^(\\|#)"); 
Matcher matcher = pattern.matcher(input); 
if (matcher.find()){ 
tokenName = matcher.group(1); 
length = tokenName.length(); 
} 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.REJECT; 
nextTokenName = tokenName; 
nextCurrIndex = currIndex + length;nextState = LexerState.S_pocetno;
 } 
 }if(state == LexerState.S_komentar && input.matches("^(\\n).*$")){ 
int length = 0; 
String tokenName = ""; 
Pattern pattern = Pattern.compile("^(\\n)"); 
Matcher matcher = pattern.matcher(input); 
if (matcher.find()){ 
tokenName = matcher.group(1); 
length = tokenName.length(); 
} 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.REJECT; 
nextTokenName = tokenName; 
nextCurrIndex = currIndex + length;nextLineNum = lineNum + 1;
 } 
 }if(state == LexerState.S_komentar && input.matches("^((\\(|\\)|\\{|\\}|\\||\\\\|\\*|\\$)).*$")){ 
int length = 0; 
String tokenName = ""; 
Pattern pattern = Pattern.compile("^((\\(|\\)|\\{|\\}|\\||\\\\|\\*|\\$))"); 
Matcher matcher = pattern.matcher(input); 
if (matcher.find()){ 
tokenName = matcher.group(1); 
length = tokenName.length(); 
} 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.REJECT; 
nextTokenName = tokenName; 
nextCurrIndex = currIndex + length;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(-(\\t|\\n)*-).*$")){ 
int length = 0; 
String tokenName = ""; 
Pattern pattern = Pattern.compile("^(-(\\t|\\n)*-)"); 
Matcher matcher = pattern.matcher(input); 
if (matcher.find()){ 
tokenName = matcher.group(1); 
length = tokenName.length(); 
} 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.OP_MINUS; 
nextTokenName = tokenName; 
nextCurrIndex = currIndex + length;nextState = LexerState.S_unarni;nextCurrIndex -=1;
 } 
 }if(state == LexerState.S_pocetno && input.matches("^(\\((\\t|\\n)*-).*$")){ 
int length = 0; 
String tokenName = ""; 
Pattern pattern = Pattern.compile("^(\\((\\t|\\n)*-)"); 
Matcher matcher = pattern.matcher(input); 
if (matcher.find()){ 
tokenName = matcher.group(1); 
length = tokenName.length(); 
} 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.LIJEVA_ZAGRADA; 
nextTokenName = tokenName; 
nextCurrIndex = currIndex + length;nextState = LexerState.S_unarni;nextCurrIndex -=1;
 } 
 }if(state == LexerState.S_unarni && input.matches("^(-).*$")){ 
int length = 0; 
String tokenName = ""; 
Pattern pattern = Pattern.compile("^(-)"); 
Matcher matcher = pattern.matcher(input); 
if (matcher.find()){ 
tokenName = matcher.group(1); 
length = tokenName.length(); 
} 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.UMINUS; 
nextTokenName = tokenName; 
nextCurrIndex = currIndex + length;nextState = LexerState.S_pocetno;
 } 
 }if(state == LexerState.S_unarni && input.matches("^(-(\\t|\\n)*-).*$")){ 
int length = 0; 
String tokenName = ""; 
Pattern pattern = Pattern.compile("^(-(\\t|\\n)*-)"); 
Matcher matcher = pattern.matcher(input); 
if (matcher.find()){ 
tokenName = matcher.group(1); 
length = tokenName.length(); 
} 
if(length > maxLen){ 
maxLen = length; 
nextTokenType = TokenType.UMINUS; 
nextTokenName = tokenName; 
nextCurrIndex = currIndex + length;nextCurrIndex -=1;
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
			
		//Oporavak od pogreške
		}else{
			
			System.err.print(input.substring(0, 1));
			
			return Analyzer.analyze(input.substring(1, input.length()), lineNum, state, currIndex+1);
		}
		
		
	}

}
