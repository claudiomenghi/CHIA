package it.polimi.statemachine.replacement.action;

import java.io.Writer;

import it.polimi.action.CHIAException;
import it.polimi.console.CHIAReplacementConsole;
import it.polimi.constraints.io.out.replacement.ReplacementToStringTransformer;
import it.polimi.statemachine.replacement.ReplacementAction;
import it.polimi.statemachine.replacement.ReplacementState;
import it.polimi.statemachine.replacement.states.Checked;
import it.polimi.statemachine.replacement.states.ConstraintLoaded;
import it.polimi.statemachine.replacement.states.Init;
import it.polimi.statemachine.replacement.states.Ready;
import it.polimi.statemachine.replacement.states.ReplacementLoaded;

public class DisplayReplacement implements ReplacementAction {

	private final Writer out;

	public DisplayReplacement(Writer out) {
		this.out = out;
	}
	
	@Override
	public ReplacementState visit(Init init) throws CHIAException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPerformable(Init init) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ReplacementState visit(ConstraintLoaded constraintLoaded) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPerformable(ConstraintLoaded constraintLoaded) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ReplacementState visit(Ready ready) {
		return ready;
	}

	@Override
	public boolean isPerformable(Ready ready) {
		return true;
	}

	@Override
	public ReplacementState visit(Checked checked) {
		return checked;
	}

	@Override
	public boolean isPerformable(Checked checked) {
		return true;
	}

	@Override
	public ReplacementState visit(ReplacementLoaded replacementLoaded) {
		return replacementLoaded;
	}

	@Override
	public boolean isPerformable(ReplacementLoaded replacementLoaded) {
		return true;
	}

	@Override
	public void perform(CHIAReplacementConsole console) throws Exception {
		ReplacementToStringTransformer action = new ReplacementToStringTransformer(
				console.getReplacement());
		out.write(action.perform());
	}
}
