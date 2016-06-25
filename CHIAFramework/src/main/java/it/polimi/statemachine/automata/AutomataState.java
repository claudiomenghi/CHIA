/**
 * 
 */
package it.polimi.statemachine.automata;

import it.polimi.action.CHIAException;

/**
 * Contains a state of CHIA
 * 
 * @author Claudio Menghi
 *
 */
public interface AutomataState {

	/**
	 * accept a visitor, i.e., an event. The event returns the next state given
	 * the current state of the system
	 * 
	 * @param event
	 *            the event to be considered
	 * @return the next state of the state machine
	 * @throws CHIAException
	 *             whenever the action is not performable over the current state
	 * @throws NullPointerException
	 *             if the event is null;
	 */
	public AutomataState next(AutomataAction event) throws CHIAException;

	/**
	 * returns true if and only if the event is performable in the current state
	 * 
	 * @param event
	 *            the visitor
	 * @return true if and only if the action is performable in the current
	 *         state
	 * @throws NullPointerException
	 *             if the event is null
	 */
	public boolean isPerformable(AutomataAction event);

}
