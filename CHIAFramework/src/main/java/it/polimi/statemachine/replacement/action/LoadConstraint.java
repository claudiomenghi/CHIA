package it.polimi.statemachine.replacement.action;

import java.io.File;
import java.io.Writer;

import it.polimi.action.CHIAException;
import it.polimi.console.CHIAReplacementConsole;
import it.polimi.constraints.io.in.constraint.ConstraintReader;
import it.polimi.statemachine.replacement.ReplacementAction;
import it.polimi.statemachine.replacement.ReplacementState;
import it.polimi.statemachine.replacement.states.Checked;
import it.polimi.statemachine.replacement.states.ConstraintLoaded;
import it.polimi.statemachine.replacement.states.Init;
import it.polimi.statemachine.replacement.states.Ready;
import it.polimi.statemachine.replacement.states.ReplacementLoaded;

public class LoadConstraint implements ReplacementAction {

	private final String constraintFilePath;

	private final Writer out;

	public LoadConstraint(Writer out, String constraintFilePath) {
		this.constraintFilePath = constraintFilePath;
		this.out = out;
	}

	@Override
	public ReplacementState visit(Init init) throws CHIAException {
		return new ConstraintLoaded();
	}

	@Override
	public boolean isPerformable(Init init) {
		return true;
	}

	@Override
	public ReplacementState visit(ConstraintLoaded constraintLoaded) {
		return constraintLoaded;
	}

	@Override
	public boolean isPerformable(ConstraintLoaded constraintLoaded) {
		return true;
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
		return new Ready();
	}

	@Override
	public boolean isPerformable(Checked checked) {
		return true;
	}

	@Override
	public ReplacementState visit(ReplacementLoaded replacementLoaded) {
		return new Ready();
	}

	@Override
	public boolean isPerformable(ReplacementLoaded replacementLoaded) {
		return true;
	}

	@Override
	public void perform(CHIAReplacementConsole console) throws Exception {

		console.setConstraint(new ConstraintReader(new File(constraintFilePath))
				.perform());

		out.write("Constraint Loaded");
	}

}
