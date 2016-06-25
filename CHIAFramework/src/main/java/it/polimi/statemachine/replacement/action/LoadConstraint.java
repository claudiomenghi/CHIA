package it.polimi.statemachine.replacement.action;

import java.io.File;
import java.io.Writer;

import com.google.common.base.Preconditions;

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

/**
 * loads the constraint from the specified path
 * 
 * @author Claudio Menghi
 *
 */
public class LoadConstraint implements ReplacementAction {

	/**
	 * loads the constraint from the specified path
	 */
	private final String constraintFilePath;

	/**
	 * the writer used to print messages on the console
	 */
	private final Writer out;

	/**
	 * loads the constraint from the specified file
	 * 
	 * @param out
	 * @param constraintFilePath
	 *            the path of the file that contains the constraint
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public LoadConstraint(Writer out, String constraintFilePath) {
		Preconditions.checkNotNull(out, "The writer cannot be null");
		Preconditions
				.checkNotNull(constraintFilePath,
						"The path of the file from which the constraint must be loaded");
		this.constraintFilePath = constraintFilePath;
		this.out = out;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ReplacementState visit(Init state) throws CHIAException {
		Preconditions.checkNotNull(state, "The state cannot be null");
		return new ConstraintLoaded();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPerformable(Init state) {
		Preconditions.checkNotNull(state, "The state cannot be null");
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ReplacementState visit(ConstraintLoaded state) {
		Preconditions.checkNotNull(state, "The state cannot be null");
		return state;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPerformable(ConstraintLoaded state) {
		Preconditions.checkNotNull(state, "The state cannot be null");
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ReplacementState visit(Ready state) {
		Preconditions.checkNotNull(state, "The state cannot be null");
		return state;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPerformable(Ready state) {
		Preconditions.checkNotNull(state, "The state cannot be null");
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ReplacementState visit(Checked state) {
		Preconditions.checkNotNull(state, "The state cannot be null");
		return new Ready();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPerformable(Checked state) {
		Preconditions.checkNotNull(state, "The state cannot be null");
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ReplacementState visit(ReplacementLoaded state) {
		Preconditions.checkNotNull(state, "The state cannot be null");
		return new Ready();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPerformable(ReplacementLoaded state) {
		Preconditions.checkNotNull(state, "The state cannot be null");
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void perform(CHIAReplacementConsole console) throws Exception {
		Preconditions.checkNotNull(console, "The console cannot be null");

		console.setConstraint(new ConstraintReader(new File(constraintFilePath))
				.perform());

		this.out.write("Constraint Loaded"+"\n");
		this.out.flush();
	}

}
