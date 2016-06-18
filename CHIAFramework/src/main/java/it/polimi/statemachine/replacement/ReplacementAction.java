package it.polimi.statemachine.replacement;

import it.polimi.action.CHIAException;
import it.polimi.console.CHIAReplacementConsole;
import it.polimi.statemachine.replacement.states.Checked;
import it.polimi.statemachine.replacement.states.ConstraintLoaded;
import it.polimi.statemachine.replacement.states.Init;
import it.polimi.statemachine.replacement.states.Ready;
import it.polimi.statemachine.replacement.states.ReplacementLoaded;

/**
 * specifies the behavior of an action. It describes 1) whether the action is
 * performable in the state (isPerformable) 2) the next state reached by the
 * automaton when the state it is visited (visit)
 * 
 * @author Claudio Menghi
 *
 */
public interface ReplacementAction {

	/**
	 * visits the state
	 * 
	 * @param state
	 *            the current state
	 * @return returns the next state of the automaton
	 * @throws CHIAException
	 *             if the action is not executable in the state
	 * @throws NullPointerException
	 *             if the state is null
	 */
	public ReplacementState visit(Init init) throws CHIAException;

	/**
	 * returns true if the action can be performed in the state
	 * 
	 * @param state
	 *            the current state
	 * @return returns true if the action can be performed in the state
	 * @throws NullPointerException
	 *             if the state is null
	 */
	public boolean isPerformable(Init init);

	/**
	 * visits the state
	 * 
	 * @param state
	 *            the current state
	 * @return returns the next state of the automaton
	 * @throws CHIAException
	 *             if the action is not executable in the state
	 * @throws NullPointerException
	 *             if the state is null
	 */
	public ReplacementState visit(ConstraintLoaded constraintLoaded) throws CHIAException;

	/**
	 * returns true if the action can be performed in the state
	 * 
	 * @param state
	 *            the current state
	 * @return returns true if the action can be performed in the state
	 * @throws NullPointerException
	 *             if the state is null
	 */
	public boolean isPerformable(ConstraintLoaded constraintLoaded);

	/**
	 * visits the state
	 * 
	 * @param state
	 *            the current state
	 * @return returns the next state of the automaton
	 * @throws CHIAException
	 *             if the action is not executable in the state
	 * @throws NullPointerException
	 *             if the state is null
	 */
	public ReplacementState visit(Ready ready) throws CHIAException;

	/**
	 * returns true if the action can be performed in the state
	 * 
	 * @param state
	 *            the current state
	 * @return returns true if the action can be performed in the state
	 * @throws NullPointerException
	 *             if the state is null
	 */
	public boolean isPerformable(Ready ready);

	/**
	 * visits the state
	 * 
	 * @param state
	 *            the current state
	 * @return returns the next state of the automaton
	 * @throws CHIAException
	 *             if the action is not executable in the state
	 * @throws NullPointerException
	 *             if the state is null
	 */
	public ReplacementState visit(Checked checked);

	/**
	 * returns true if the action can be performed in the state
	 * 
	 * @param state
	 *            the current state
	 * @return returns true if the action can be performed in the state
	 * @throws NullPointerException
	 *             if the state is null
	 */
	public boolean isPerformable(Checked checked);

	/**
	 * visits the state
	 * 
	 * @param state
	 *            the current state
	 * @return returns the next state of the automaton
	 * @throws CHIAException
	 *             if the action is not executable in the state
	 * @throws NullPointerException
	 *             if the state is null
	 */
	public ReplacementState visit(ReplacementLoaded replacementLoaded) throws CHIAException;

	/**
	 * returns true if the action can be performed in the state
	 * 
	 * @param state
	 *            the current state
	 * @return returns true if the action can be performed in the state
	 * @throws NullPointerException
	 *             if the state is null
	 */
	public boolean isPerformable(ReplacementLoaded replacementLoaded);

	/**
	 * visits the state
	 * 
	 * @param state
	 *            the current state
	 * @return returns the next state of the automaton
	 * @throws CHIAException
	 *             if the action is not executable in the state
	 * @throws NullPointerException
	 *             if the state is null
	 */
	public void perform(CHIAReplacementConsole console) throws Exception;

}
