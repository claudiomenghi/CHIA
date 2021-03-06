/**
 * 
 */
package it.polimi.statemachine.automata.states;

import it.polimi.action.CHIAException;
import it.polimi.statemachine.automata.AutomataAction;
import it.polimi.statemachine.automata.AutomataState;

/**
 * @author Claudio Menghi
 *
 */
public class Ready implements AutomataState {

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
		if(o instanceof Ready){
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return 124123509;
	}
}
