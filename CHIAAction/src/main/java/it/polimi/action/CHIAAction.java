package it.polimi.action;

import javax.validation.constraints.NotNull;

import com.google.common.base.Preconditions;

/**
 * contains the abstract definition of a CHIAAction
 * 
 * @author Claudio Menghi
 *
 * @param <O>
 *            is the type of the output of the class
 */
public abstract class CHIAAction<O> {

	/**
	 * is a flag which specifies if the action has been performed or not
	 */
	private boolean performed;
	/**
	 * is the name of the CHIAAction
	 */
	private @NotNull final String name;

	/**
	 * creates a new CHIAAction with the specified name
	 * 
	 * @param name
	 *            the name of the CHIAAction
	 * @throws NullPointerException
	 *             if the name of the action is null
	 */
	//@ requires  name!=null
	public CHIAAction(@NotNull  String  name) {
		Preconditions.checkNotNull(name,
				"The name of the action cannot be null");
		performed = false;
		this.name = name;
	}

	/**
	 * returns a boolean which specifies whether the action has been performed
	 * or not
	 * 
	 * @return a boolean which specifies whether the action has been performed
	 *         or not
	 */
	/*@ pure */
	public boolean isPerformed() {
		return performed;
	}

	/**
	 * performs the action
	 * 
	 * @return the output of the action
	 * @throws Exception
	 *             if an exception in the execution of the action is thrown
	 */
	@NotNull public abstract O perform() throws Exception;

	/**
	 * specifies that the action has been performed
	 */
	/*@ ensures isPerformed()==true*/
	public void performed() {
		this.performed = true;
	}

	/**
	 * returns the name of the action
	 * 
	 * @return the name of the action
	 */
	@NotNull public String getName() {
		return this.name;
	}
}
