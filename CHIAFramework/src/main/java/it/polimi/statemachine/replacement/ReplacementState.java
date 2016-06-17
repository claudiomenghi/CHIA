package it.polimi.statemachine.replacement;

import it.polimi.action.CHIAException;

public interface ReplacementState {
	/**
	 * accept a visitor, i.e., an event. The event returns the next state given the current state of the system
	 * @param event the event to be considered
	 * @return the next state of the state machine
	 * @throws CHIAException whenever the action is not performable over the current state
	 * @throws NullPointerException if the event is null;
	 */
	public ReplacementState next(ReplacementAction event) throws CHIAException;
	
	
	/**
	 * returns true if and only if the event is performable in the current state 
	 * @param event the visitor
	 * @return true if and only if the action is performable in the current state
	 */
	public boolean isPerformable(ReplacementAction event);
}
