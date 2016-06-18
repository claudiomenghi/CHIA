package it.polimi.statemachine.automata.actions;

import it.polimi.action.CHIAException;
import it.polimi.checker.Checker;
import it.polimi.checker.SatisfactionValue;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
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
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Preconditions;

/**
 * The checking action
 * 
 * @author Claudio Menghi
 */
public class Check implements AutomataAction {

	/**
	 * the writer used to print messages
	 */
	private final Writer out;

	/**
	 * crates a new checking action
	 * @param out the writer used to write messages
	 * @throws NullPointerException if the writer is null
	 */
	public Check(Writer out) {
		Preconditions.checkNotNull(out, "The writer cannot be null");
		this.out = out;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void perform(CHIAAutomataConsole console) throws Exception {
		Preconditions.checkNotNull(console, "The console cannot be null");
		
		ThreadMXBean thradBean = ManagementFactory.getThreadMXBean();
		long startTime = thradBean.getCurrentThreadCpuTime();
		console.setChecker(new Checker(console.getModel(), console.getClaim(),
				AcceptingPolicy.getAcceptingPolicy(console.getPolicy(),
						console.getModel(), console.getClaim())));
		SatisfactionValue result = console.getChecker().perform();
		long endTime = thradBean.getCurrentThreadCpuTime();
		out.write("Verification result: " + result.toString());
		out.write("Verification time: "
				+ Long.toString(TimeUnit.MILLISECONDS.convert(endTime
						- startTime, TimeUnit.NANOSECONDS)) + " ms");
		out.write("Dimension of the intersection automaton (states+transitions): "
				+ console.getChecker().getIntersectionAutomataSize());
		if (result.equals(SatisfactionValue.NOTSATISFIED)) {
			out.write("Counterexample:"
					+ console.getChecker().getFilteredCounterexample());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AutomataState visit(Init state) throws CHIAException {
		Preconditions.checkNotNull(state, "The state cannot be null");
		throw new CHIAException(
				"You must load the property and the model before the checking");
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
				"You must load the property before the checking");
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
		return new Checked();
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
	public AutomataState visit(PropertyLoaded state)
			throws CHIAException {
		Preconditions.checkNotNull(state, "The state cannot be null");
		throw new CHIAException(
				"You must load the model and the model before the checking");
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
				"You already check the model against the current property. Change model or property");
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
	public AutomataState visit(ConstraintComputed state)
			throws CHIAException {
		Preconditions.checkNotNull(state, "The state cannot be null");
		throw new CHIAException(
				"You already check the model against the current property. Change model or property");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPerformable(ConstraintComputed state) {
		Preconditions.checkNotNull(state, "The state cannot be null");
		return false;
	}
}
