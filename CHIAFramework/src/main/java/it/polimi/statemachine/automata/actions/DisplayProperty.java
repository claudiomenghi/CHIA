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

import com.google.common.base.Preconditions;

/**
 * it is used to display the property
 * 
 * @author Claudio Menghi
 *
 */
public class DisplayProperty implements AutomataAction {

	/**
	 * the writer used to print messages
	 */
	private final Writer out;

	/**
	 * crates a new display property action
	 * 
	 * @param out
	 *            the writer used to write messages
	 * @throws NullPointerException
	 *             if the writer is null
	 */
	public DisplayProperty(Writer out) {
		Preconditions.checkNotNull(out, "The writer cannot be null");
		this.out = out;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AutomataState visit(Init state) throws CHIAException {
		Preconditions.checkNotNull(state, "The state cannot be null");
		throw new CHIAException(
				"It is not possible to display the property. No property loaded");
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
				"It is not possible to display the property. No property loaded");
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
	public AutomataState visit(Ready state) {
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
	public AutomataState visit(PropertyLoaded state) throws CHIAException {
		Preconditions.checkNotNull(state, "The state cannot be null");
		return state;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPerformable(PropertyLoaded state) {
		Preconditions.checkNotNull(state, "The state cannot be null");
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AutomataState visit(Checked state) throws CHIAException {
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
	 */
	@Override
	public AutomataState visit(ConstraintComputed state)
			throws CHIAException {
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
	public void perform(CHIAAutomataConsole console) throws Exception{
		Preconditions.checkNotNull(console, "The console cannot be null");
		ClaimToStringTrasformer action = new ClaimToStringTrasformer(
				console.getClaim());
		out.write(action.perform()+"\n");
		out.flush();
	}

}
