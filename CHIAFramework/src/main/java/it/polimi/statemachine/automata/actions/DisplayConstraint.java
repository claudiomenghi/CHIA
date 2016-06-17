package it.polimi.statemachine.automata.actions;

import it.polimi.action.CHIAException;
import it.polimi.console.CHIAAutomataConsole;
import it.polimi.constraints.io.out.constraint.ConstraintToStringTrasformer;
import it.polimi.statemachine.automata.AutomataAction;
import it.polimi.statemachine.automata.AutomataState;
import it.polimi.statemachine.automata.states.Checked;
import it.polimi.statemachine.automata.states.ConstraintComputed;
import it.polimi.statemachine.automata.states.Init;
import it.polimi.statemachine.automata.states.ModelLoaded;
import it.polimi.statemachine.automata.states.PropertyLoaded;
import it.polimi.statemachine.automata.states.Ready;

import java.io.Writer;

public class DisplayConstraint implements AutomataAction {

	private final Writer out;

	public DisplayConstraint(Writer out) {
		this.out = out;
	}

	@Override
	public AutomataState visit(Init state) throws CHIAException {
		throw new CHIAException("No constraint has been computed");
	}

	@Override
	public boolean isPerformable(Init state) {
		return false;
	}

	@Override
	public AutomataState visit(ModelLoaded modelLoaded) throws CHIAException {
		throw new CHIAException("No constraint has been computed");
	}

	@Override
	public boolean isPerformable(ModelLoaded modelLoaded) {
		return false;
	}

	@Override
	public AutomataState visit(Ready ready) throws CHIAException {
		throw new CHIAException("No constraint has been computed");
	}

	@Override
	public boolean isPerformable(Ready ready) {
		return false;
	}

	@Override
	public AutomataState visit(PropertyLoaded propertyLoaded)
			throws CHIAException {
		throw new CHIAException("No constraint has been computed");
	}

	@Override
	public boolean isPerformable(PropertyLoaded propertyLoaded) {
		return false;
	}

	@Override
	public AutomataState visit(Checked checked) throws CHIAException {
		throw new CHIAException("No constraint has been computed");
	}

	@Override
	public boolean isPerformable(Checked checked) {
		return false;
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

	@Override
	public void perform(CHIAAutomataConsole console) throws Exception {

		out.write(new ConstraintToStringTrasformer(console.getConstraint())
				.perform());
	}
}
