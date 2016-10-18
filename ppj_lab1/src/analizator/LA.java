package analizator;

/**
 * Razred LA se ne mijenja u generatoru.
 * Iterira po izvornom kodu i poziva metodu analyze u razredu Analyzer.
 * 
 * @author dunja
 *
 */

public class LA {
	
	public static void main(String[] args){
		
		String input = args[0];
		int currIndex = 0;
		int lineNum = 1;
		LexerState state = LexerState.BEGIN;
		
		while(input.length() > currIndex){
			
			LAState laState = Analyzer.analyze(input.substring(currIndex, input.length()), lineNum, state, currIndex);
			
			state = laState.getState();
			lineNum = laState.getLineNum();
			currIndex = laState.getCurrIndex();
		}
		
	}

}
