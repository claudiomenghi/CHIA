package it.polimi.statemachine.automata.states;

import com.google.common.base.Preconditions;

import it.polimi.action.CHIAException;
import it.polimi.statemachine.automata.AutomataAction;
import it.polimi.statemachine.automata.AutomataState;

/**
 * In this state the model of the system has already been checked against the
 * property of interest
 * 
 * @author Claudio Menghi
 *
 */
public class Checked implements AutomataState {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AutomataState next(AutomataAction event) throws CHIAException {
		Preconditions.checkNotNull(event,
				"The event to be considered cannot be null");
		return event.visit(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPerformable(AutomataAction event) {
		Preconditions.checkNotNull(event,
				"The event to be considered cannot be null");
		return event.isPerformable(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Checked) {
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return 123451015;
	}
}
