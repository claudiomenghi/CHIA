package it.polimi.statemachine.replacement.action;

import java.io.Writer;

import com.google.common.base.Preconditions;

import it.polimi.action.CHIAException;
import it.polimi.console.CHIAReplacementConsole;
import it.polimi.constraints.io.out.constraint.ConstraintToStringTrasformer;
import it.polimi.statemachine.replacement.ReplacementAction;
import it.polimi.statemachine.replacement.ReplacementState;
import it.polimi.statemachine.replacement.states.Checked;
import it.polimi.statemachine.replacement.states.ConstraintLoaded;
import it.polimi.statemachine.replacement.states.Init;
import it.polimi.statemachine.replacement.states.Ready;
import it.polimi.statemachine.replacement.states.ReplacementLoaded;

/**
 * contains the display constraint action
 * 
 * @author Claudio Menghi
 *
 */
public class DisplayConstraint implements ReplacementAction {

	/**
	 * the writer used to print messages
	 */
	private final Writer out;

	/**
	 * crates a new display constraint action
	 * 
	 * @param out
	 *            the writer used to write messages
	 * @throws NullPointerException
	 *             if the writer is null
	 */
	public DisplayConstraint(Writer out) {
		this.out = out;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ReplacementState visit(Init state) throws CHIAException {
		Preconditions.checkNotNull(state, "The state cannot be null");
		throw new CHIAException("It is not possible to display the constraint into the init state");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPerformable(Init state) {
		Preconditions.checkNotNull(state, "The state cannot be null");
		return false;
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
	public ReplacementState visit(Ready state) throws CHIAException {
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
		return state;
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
	 * @throws CHIAException 
	 */
	@Override
	public ReplacementState visit(ReplacementLoaded state) throws CHIAException {
		Preconditions.checkNotNull(state, "The state cannot be null");
		throw new CHIAException("It is not possible to display the constraint into the init state");

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
		out.write(new ConstraintToStringTrasformer(console.getConstraint()).perform());	
	}
}
