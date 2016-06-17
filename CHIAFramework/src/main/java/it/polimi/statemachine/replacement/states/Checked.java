package it.polimi.statemachine.replacement.states;

import it.polimi.action.CHIAException;
import it.polimi.statemachine.replacement.ReplacementAction;
import it.polimi.statemachine.replacement.ReplacementState;

public class Checked implements ReplacementState {

	@Override
	public ReplacementState next(ReplacementAction event) throws CHIAException {
		return event.visit(this);
	}

	@Override
	public boolean isPerformable(ReplacementAction event) {
		return event.isPerformable(this);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Checked) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return 50909865;
	}
}
