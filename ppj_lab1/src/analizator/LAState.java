package analizator;

/**
 * Razred služi za prosljeđivanje informacija o stanju leksera, poziciji unutar izvornog koda
 * i tenutnoj linij izvornog koda koja se analizira iz razreda Analyzer u razred LA.
 * 
 * @author dunja
 *
 */

public class LAState {
	
	private int lineNum;
	private LexerState state;
	private int currIndex;
	
	public LAState(int lineNum, LexerState state, int currIndex) {
		super();
		this.lineNum = lineNum;
		this.state = state;
		this.currIndex = currIndex;
	}

	public int getLineNum() {
		return lineNum;
	}

	public LexerState getState() {
		return state;
	}

	public int getCurrIndex() {
		return currIndex;
	}
	
}
