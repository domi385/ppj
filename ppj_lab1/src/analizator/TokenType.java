package analizator;

/**
 * Enumeracija se u generatoru puni imenima leksičkih jedinki koja su zadana u ulaznoj datoteci. 
 * Jedino je REJECT unaprijed zadan i koristi se za slučajeve kad je zadane znakove potrebno
 * odbaciti, tj. kad je u ulaznoj datoteci u akciji umjesto imena leksičke jedinke napisano -.
 * 
 * @author dunja
 *
 */
public enum TokenType {
	
	REJECT,
	
	KEYWORD,
	
	DIGIT

}
