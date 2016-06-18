package it.polimi.statemachine.replacement.action;

import java.io.File;
import java.io.Writer;

import com.google.common.base.Preconditions;

import it.polimi.action.CHIAException;
import it.polimi.console.CHIAReplacementConsole;
import it.polimi.constraints.io.in.replacement.ReplacementReader;
import it.polimi.statemachine.replacement.ReplacementAction;
import it.polimi.statemachine.replacement.ReplacementState;
import it.polimi.statemachine.replacement.states.Checked;
import it.polimi.statemachine.replacement.states.ConstraintLoaded;
import it.polimi.statemachine.replacement.states.Init;
import it.polimi.statemachine.replacement.states.Ready;
import it.polimi.statemachine.replacement.states.ReplacementLoaded;

/**
 * loads the replacement from the specified path
 * 
 * @author Claudio Menghi
 *
 */
public class LoadRepacement implements ReplacementAction {

	/**
	 * the path of the file from which the replacement must be loaded
	 */
	private final String replacementFilePath;

	/**
	 * the writer used to print messages on the console
	 */
	private final Writer out;

	/**
	 * loads the replacement from the specified path
	 * 
	 * @param out
	 *            the writer used to print messages on the console
	 * @param replacementFilePath
	 *            the path of the file from which the replacement must be loaded
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public LoadRepacement(Writer out, String replacementFilePath) {
		Preconditions.checkNotNull(out, "The writer cannot be null");
		Preconditions
				.checkNotNull(replacementFilePath,
						"The path of the file from which the replacement must be loaded");
	
		this.replacementFilePath = replacementFilePath;
		this.out = out;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ReplacementState visit(Init state) throws CHIAException {
		Preconditions.checkNotNull(state, "The state cannot be null");
		return new ReplacementLoaded();
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
		return new Ready();
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
		return state;
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

		ReplacementReader rr = new ReplacementReader(new File(
				replacementFilePath));
		console.setReplacement(rr.perform());
		console.setModel(rr.getModel());
		out.write("Replacement Loaded");
	}
}
