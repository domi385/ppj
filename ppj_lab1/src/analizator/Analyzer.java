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
		
		//generira se kombinacija stanja i regularni izraz
		if(state == LexerState.BEGIN && input.matches("^(broj).*$")){
			int length = 0;
			String tokenName = "";
			
			//generira se regularni izraz
			Pattern pattern = Pattern.compile("^(broj)");
			Matcher matcher = pattern.matcher(input);
			if (matcher.find())
			{
				tokenName = matcher.group(1);
				length = tokenName.length();
			}

			if(length > maxLen){				
				maxLen = length;
				nextTokenName = tokenName;
				
				//generiraju se akcije 
				nextState = LexerState.NUM; //promjena stanja
				nextLineNum = lineNum; //prelazi li se u novi red ili ne
				nextTokenType = TokenType.KEYWORD; //ime leksičke jedinke
				nextCurrIndex = currIndex + length; //promjena trenutnog indeksa(za opciju VRATI_SE)
				
			}
			
			
		}
		
		if(state == LexerState.NUM && input.matches("^[0-9]+.*$")){
			int length = 0;
			String tokenName = "";
			
			Pattern pattern = Pattern.compile("^[0-9]+");
			Matcher matcher = pattern.matcher(input);
			if (matcher.find())
			{
				tokenName = matcher.group();
				length = tokenName.length();
			}

			if(length > maxLen){				
				maxLen = length;
				nextTokenName = tokenName;
				
				nextState = LexerState.BEGIN;
				nextLineNum = lineNum;
				nextTokenType = TokenType.DIGIT;
				nextCurrIndex = currIndex + length;
				
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
