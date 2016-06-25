package it.polimi.statemachine.automata.actions;

import it.polimi.action.CHIAException;
import it.polimi.console.CHIAAutomataConsole;
import it.polimi.constraints.io.out.constraint.ConstraintWriter;
import it.polimi.statemachine.automata.AutomataAction;
import it.polimi.statemachine.automata.AutomataState;
import it.polimi.statemachine.automata.states.Checked;
import it.polimi.statemachine.automata.states.ConstraintComputed;
import it.polimi.statemachine.automata.states.Init;
import it.polimi.statemachine.automata.states.ModelLoaded;
import it.polimi.statemachine.automata.states.PropertyLoaded;
import it.polimi.statemachine.automata.states.Ready;

import java.io.Writer;

import com.google.common.base.Preconditions;

/**
 * Writes the constraint into a file
 * 
 * @author Claudio Menghi
 *
 */
public class WriteConstraint implements AutomataAction {

	/**
	 * the writer used to print messages
	 */
	private final Writer out;

	/**
	 * the path where the constraint must be saved
	 */
	private final String constraintFilePath;

	/**
	 * creates a new writer for a constraint
	 * 
	 * @param out
	 *            the writer to be used to save the constraint
	 * @param constraintFilePath
	 *            the path of the file where the constraint must be saved
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public WriteConstraint(Writer out, String constraintFilePath) {
		Preconditions.checkNotNull(out, "The out cannot be null");
		Preconditions.checkNotNull(constraintFilePath,
				"The path of the file where the constraint must be saved");
		
		this.out = out;
		this.constraintFilePath = constraintFilePath;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AutomataState visit(Init state) throws CHIAException {
		Preconditions.checkNotNull(state, "The state cannot be null");
		throw new CHIAException(
				"You must compute the constraint before writing");
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
	public AutomataState visit(ModelLoaded state) throws CHIAException {
		Preconditions.checkNotNull(state, "The state cannot be null");
		throw new CHIAException(
				"You must compute the constraint before writing");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPerformable(ModelLoaded state) {
		Preconditions.checkNotNull(state, "The state cannot be null");
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AutomataState visit(Ready state) throws CHIAException {
		Preconditions.checkNotNull(state, "The state cannot be null");
		throw new CHIAException(
				"You must compute the constraint before writing");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPerformable(Ready state) {
		Preconditions.checkNotNull(state, "The state cannot be null");
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AutomataState visit(PropertyLoaded state) throws CHIAException {
		Preconditions.checkNotNull(state, "The state cannot be null");
		throw new CHIAException(
				"You must compute the constraint before writing");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPerformable(PropertyLoaded state) {
		Preconditions.checkNotNull(state, "The state cannot be null");
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AutomataState visit(Checked state) throws CHIAException {
		Preconditions.checkNotNull(state, "The state cannot be null");
		throw new CHIAException(
				"You must compute the constraint before writing");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPerformable(Checked state) {
		Preconditions.checkNotNull(state, "The state cannot be null");
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AutomataState visit(ConstraintComputed state) throws CHIAException {
		Preconditions.checkNotNull(state, "The state cannot be null");
		return state;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPerformable(ConstraintComputed state) {
		Preconditions.checkNotNull(state, "The state cannot be null");
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void perform(CHIAAutomataConsole console) throws Exception {
		Preconditions.checkNotNull(console, "The console cannot be null");

		ConstraintWriter constraintWriter = new ConstraintWriter(
				console.getConstraint(), constraintFilePath);
		constraintWriter.perform();
		this.out.write("Constraint saved"+"\n");
		this.out.flush();
	}

}