package analizator;

/**
 * Enumeracija se u generatoru puni zadanim stanjima leksičkog analizatora.
 * Jedino je početno stanje BEGIN unaprijed zadano i koristi se umjesto naziva 
 * početnog stanja koje je zadano u ulaznoj datoteci (zato da se razred LA.java ne mora
 * mijenjati). 
 * 
 * @author dunja
 *
 */

public enum LexerState {
	
	BEGIN,
	
	NUM

}
