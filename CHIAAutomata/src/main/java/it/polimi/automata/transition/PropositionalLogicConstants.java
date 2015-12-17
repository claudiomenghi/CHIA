package it.polimi.automata.transition;

/**
 * contains the constants used to print the propositional logic formula
 * 
 * @author Claudio Menghi
 */
public final class PropositionalLogicConstants {

	public static final String AND = "^";
	public static final String OR = "|";
	public static final String NOT = "!";

	public static final String LPAR = "(";
	public static final String RPAR = ")";

	public static final String STUTTERING_CHARACTER = "stuttering";

	private PropositionalLogicConstants() throws InstantiationException {
		throw new InstantiationException("Instances of this type are forbidden.");
	}
}
