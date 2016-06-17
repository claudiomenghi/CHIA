package it.polimi.statemachine.automata.actions;

import it.polimi.action.CHIAException;
import it.polimi.automata.io.out.ElementToStringTransformer;
import it.polimi.automata.io.out.IBAToElementTrasformer;
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

public class DisplayModel implements AutomataAction {

	private final Writer out;

	public DisplayModel(Writer out) {
		this.out = out;
	}

	@Override
	public void perform(CHIAAutomataConsole console) throws Exception{
		out.write(new ElementToStringTransformer()
				.transform(new IBAToElementTrasformer().transform(console.getModel())));
	}

	@Override
	public AutomataState visit(Init state) throws CHIAException {
		throw new CHIAException(
				"It is not possible to display the model in the initial state");
	}

	@Override
	public boolean isPerformable(Init state) {
		return false;
	}

	@Override
	public AutomataState visit(ModelLoaded modelLoaded) {
		return modelLoaded;
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
	public AutomataState visit(PropertyLoaded propertyLoaded) throws CHIAException {
		throw new CHIAException(
				"It is not possible to display the model into the state property loaded");
	}

	@Override
	public boolean isPerformable(PropertyLoaded propertyLoaded) {
		return false;
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
