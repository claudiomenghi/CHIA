package it.polimi.statemachine.automata.actions;

import it.polimi.action.CHIAException;
import it.polimi.statemachine.automata.AutomataAction;
import it.polimi.statemachine.automata.AutomataState;
import it.polimi.statemachine.automata.states.Checked;
import it.polimi.statemachine.automata.states.ConstraintComputed;
import it.polimi.statemachine.automata.states.Init;
import it.polimi.statemachine.automata.states.ModelLoaded;
import it.polimi.statemachine.automata.states.PropertyLoaded;
import it.polimi.statemachine.automata.states.Ready;

public abstract class ReadProperty implements AutomataAction{

	@Override
	public AutomataState visit(Init state) {
		return new PropertyLoaded();
	}

	@Override
	public boolean isPerformable(Init state) {
		return true;
	}

	@Override
	public AutomataState visit(ModelLoaded modelLoaded) {
		return new Ready();
	}

	@Override
	public boolean isPerformable(ModelLoaded modelLoaded) {
		return true;
	}

	@Override
	public AutomataState visit(Ready ready) {
		return ready;
	}

	@Override
	public boolean isPerformable(Ready ready) {
		return true;
	}

	@Override
	public AutomataState visit(PropertyLoaded propertyLoaded) {
		return propertyLoaded;
	}

	@Override
	public boolean isPerformable(PropertyLoaded propertyLoaded) {
		return true;
	}

	@Override
	public AutomataState visit(Checked checked) throws CHIAException {
		return new Ready();
	}

	@Override
	public boolean isPerformable(Checked checked) {
		return true;
	}

	@Override
	public AutomataState visit(ConstraintComputed constraintComputed)
			throws CHIAException {
		return new Ready();
	}

	@Override
	public boolean isPerformable(ConstraintComputed constraintComputed) {
		return true;
	}

	
}
