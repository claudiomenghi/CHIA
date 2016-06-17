package it.polimi.statemachine.automata.actions;

import it.polimi.action.CHIAException;
import it.polimi.automata.io.out.ClaimToStringTrasformer;
import it.polimi.console.CHIAAutomataConsole;
import it.polimi.statemachine.automata.AutomataAction;
import it.polimi.statemachine.automata.AutomataState;
import it.polimi.statemachine.automata.states.Checked;
import it.polimi.statemachine.automata.states.ConstraintComputed;
import it.polimi.statemachine.automata.states.Init;
import it.polimi.statemachine.automata.states.ModelLoaded;
import it.polimi.statemachine.automata.states.PropertyLoaded;
import it.polimi.statemachine.automata.states.Ready;

import java.io.Writer;

public class DisplayProperty implements AutomataAction {

	private final Writer out;

	public DisplayProperty(Writer out) {
		this.out = out;
	}

	@Override
	public void perform(CHIAAutomataConsole console) throws Exception{
		
		ClaimToStringTrasformer action = new ClaimToStringTrasformer(
				console.getClaim());
		out.write(action.perform());
	}

	
	@Override
	public AutomataState visit(Init state) throws CHIAException {
		throw new CHIAException(
				"It is not possible to display the property. No property loaded");
	}

	@Override
	public boolean isPerformable(Init state) {
		return false;
	}

	@Override
	public AutomataState visit(ModelLoaded modelLoaded) throws CHIAException {
		throw new CHIAException(
				"It is not possible to display the property. No property loaded");
	}

	@Override
	public boolean isPerformable(ModelLoaded modelLoaded) {
		return false;
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
	public AutomataState visit(PropertyLoaded propertyLoaded) throws CHIAException {
		return propertyLoaded;
	}

	@Override
	public boolean isPerformable(PropertyLoaded propertyLoaded) {
		return true;
	}

	@Override
	public AutomataState visit(Checked checked) throws CHIAException {
		return checked;
	}

	@Override
	public boolean isPerformable(Checked checked) {
		return true;
	}

	@Override
	public AutomataState visit(ConstraintComputed constraintComputed)
			throws CHIAException {
		return constraintComputed;
	}

	@Override
	public boolean isPerformable(ConstraintComputed constraintComputed) {
		return true;
	}
}
