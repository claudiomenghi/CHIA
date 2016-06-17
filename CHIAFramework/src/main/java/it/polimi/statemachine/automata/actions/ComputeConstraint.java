package it.polimi.statemachine.automata.actions;

import it.polimi.action.CHIAException;
import it.polimi.console.CHIAAutomataConsole;
import it.polimi.constraintcomputation.ConstraintGenerator;
import it.polimi.statemachine.automata.AutomataAction;
import it.polimi.statemachine.automata.AutomataState;
import it.polimi.statemachine.automata.states.Checked;
import it.polimi.statemachine.automata.states.ConstraintComputed;
import it.polimi.statemachine.automata.states.Init;
import it.polimi.statemachine.automata.states.ModelLoaded;
import it.polimi.statemachine.automata.states.PropertyLoaded;
import it.polimi.statemachine.automata.states.Ready;

import java.io.Writer;

public class ComputeConstraint implements AutomataAction {


	private final Writer out;

	public ComputeConstraint(Writer out) {
		this.out = out;
	}

	@Override
	public void perform(CHIAAutomataConsole console) throws Exception{
		ConstraintGenerator cg = new ConstraintGenerator(console.getChecker());
		console.setConstraint(cg.perform());
		out.write("Constraint computed");
	}

	@Override
	public AutomataState visit(Init state) throws CHIAException {
		throw new CHIAException("Load property and model before computing the constraint");
	}

	@Override
	public boolean isPerformable(Init state) {
		return false;
	}

	@Override
	public AutomataState visit(ModelLoaded modelLoaded) throws CHIAException {
		throw new CHIAException("Load property and model before computing the constraint");
	}

	@Override
	public boolean isPerformable(ModelLoaded modelLoaded) {
		return false;
	}

	@Override
	public AutomataState visit(Ready ready) throws CHIAException {
		throw new CHIAException("Load property and model before computing the constraint");
	}

	@Override
	public boolean isPerformable(Ready ready) {
		return false;
	}

	@Override
	public AutomataState visit(PropertyLoaded propertyLoaded) throws CHIAException {
		throw new CHIAException("Load property and model before computing the constraint");
	}

	@Override
	public boolean isPerformable(PropertyLoaded propertyLoaded) {
		return false;
	}

	@Override
	public AutomataState visit(Checked checked) throws CHIAException {
		return new ConstraintComputed();
	}

	@Override
	public boolean isPerformable(Checked checked) {
		return true;
	}

	@Override
	public AutomataState visit(ConstraintComputed constraintComputed)
			throws CHIAException {
		throw new CHIAException("Load a new property and model before re-computing the constraint");
	}

	@Override
	public boolean isPerformable(ConstraintComputed constraintComputed) {
		return false;
	}
}
