package it.polimi.statemachine.automata.states;

import it.polimi.action.CHIAException;
import it.polimi.statemachine.automata.AutomataAction;
import it.polimi.statemachine.automata.AutomataState;

public class ModelLoaded implements AutomataState {

	@Override
	public AutomataState next(AutomataAction event) throws CHIAException {
		return event.visit(this);
	}

	@Override
	public boolean isPerformable(AutomataAction event) {
		return event.isPerformable(this);
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof ModelLoaded){
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return 1234510;
	}
}
