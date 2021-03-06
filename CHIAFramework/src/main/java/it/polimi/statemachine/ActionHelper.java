package it.polimi.statemachine;

/**
 * Is the command Event provided by the user
 * 
 * @author Claudio Menghi
 *
 * @param <C>
 *            the console
 */
public interface ActionHelper<C> {
	/**
	 * returns the command meaning
	 * 
	 * @return the command meaning
	 */
	public String getCommandMeaning();

	/**
	 * returns the shortcut of the command
	 * 
	 * @return the shortcut of the command
	 */
	public String getCommand();

	/**
	 * returns the description of the command
	 * 
	 * @return the description of the command
	 */
	public String getDescription();

	/**
	 * returns the parameters of the command (if any)
	 * 
	 * @return the parameters of the command (if any)
	 */
	public String getParams();

	/**
	 * the String representation of the command
	 * 
	 * @return String representation of the command
	 */
	@Override
	public String toString();

	/**
	 * true if at least a parameter is associated with the command
	 * 
	 * @return if at least a parameter is associated with the command
	 */
	public boolean requiresParams();

	/**
	 * performs the command changes the state of the console
	 * 
	 * @param command
	 *            the command to be executed
	 * @param console
	 *            the console to be modified
	 * @throws Exception
	 *             if the command execution generates an exception
	 */
	//public void executeCommand(String command, C console) throws Exception;
}
