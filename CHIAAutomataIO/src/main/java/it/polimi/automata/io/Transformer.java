package it.polimi.automata.io;

/**
 * transforms an object of type I into an object of type O
 * 
 * @author Claudio Menghi
 *
 * @param <I>
 *            is the type of the input object to be transformed
 * @param <O>
 *            is the type of the output object
 */
public interface Transformer<I, O> {

	/**
	 * transforms an object of type I to an object of type O
	 * 
	 * @param input
	 *            is the input object to be transformed
	 * @return the object of type O which corresponds to the input
	 * @throws NullPointerException
	 *             if the input is null
	 * @throws Exception
	 *             if an exception occurs in the transformation
	 */
	public O transform(I input) throws Exception;
}
