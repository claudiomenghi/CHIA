package it.polimi.statemachine.replacement.action;

import java.io.Writer;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Preconditions;

import it.polimi.action.CHIAException;
import it.polimi.checker.SatisfactionValue;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.console.CHIAReplacementConsole;
import it.polimi.constraints.components.SubProperty;
import it.polimi.replacementchecker.ReplacementChecker;
import it.polimi.statemachine.replacement.ReplacementAction;
import it.polimi.statemachine.replacement.ReplacementState;
import it.polimi.statemachine.replacement.states.Checked;
import it.polimi.statemachine.replacement.states.ConstraintLoaded;
import it.polimi.statemachine.replacement.states.Init;
import it.polimi.statemachine.replacement.states.Ready;
import it.polimi.statemachine.replacement.states.ReplacementLoaded;

/**
 * contains the replacement checking action
 * 
 * @author Claudio Menghi
 *
 */
public class Check implements ReplacementAction {

	/**
	 * the writer used to print messages
	 */
	private final Writer out;

	/**
	 * crates a new checking action
	 * 
	 * @param out
	 *            the writer used to write messages
	 * @throws NullPointerException
	 *             if the writer is null
	 */
	public Check(Writer out) {
		this.out = out;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ReplacementState visit(Init state) throws CHIAException {
		Preconditions.checkNotNull(state, "The state cannot be null");
		throw new CHIAException(
				"Load a constriant and a replacement before running the checker");
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
	public ReplacementState visit(ReplacementLoaded state) throws CHIAException {
		Preconditions.checkNotNull(state, "The state cannot be null");
		throw new CHIAException("Load a constriant before running the checker");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPerformable(ReplacementLoaded state) {
		Preconditions.checkNotNull(state, "The state cannot be null");
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ReplacementState visit(ConstraintLoaded state) throws CHIAException {
		Preconditions.checkNotNull(state, "The state cannot be null");
		throw new CHIAException("Load a replacement before running the checker");

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPerformable(ConstraintLoaded state) {
		Preconditions.checkNotNull(state, "The state cannot be null");
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ReplacementState visit(Ready state) {
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
	 */
	@Override
	public void perform(CHIAReplacementConsole console) throws Exception {
		Preconditions.checkNotNull(console, "The console cannot be null");
		SubProperty subProperty = console.getConstraint().getSubProperty(
				console.getReplacement().getModelState());
		console.setReplacementChecker(new ReplacementChecker(console
				.getReplacement(), subProperty, AcceptingPolicy
				.getAcceptingPolicy(console.getPolicy(), console
						.getReplacement().getAutomaton(), subProperty
						.getAutomaton())));

		ThreadMXBean thradBean = ManagementFactory.getThreadMXBean();
		long startTime = thradBean.getCurrentThreadCpuTime();
		SatisfactionValue result = console.getReplacementChecker().perform();
		long endTime = thradBean.getCurrentThreadCpuTime();
		out.write("Verification result: " + result.toString()+"\n");
		out.write("Verification time: "
				+ Long.toString(TimeUnit.MILLISECONDS.convert(endTime
						- startTime, TimeUnit.NANOSECONDS)) + " ms"+"\n");
		if (result.equals(SatisfactionValue.NOTSATISFIED)) {
			out.write("COUNTEREXAMPLE: "
					+ console.getReplacementChecker().getCouterexample()+"\n");

		}
		if (!result.equals(SatisfactionValue.NOTSATISFIED)) {
			out.write("Dimension of the intersection automaton (states+transitions): "
					+ (console.getReplacementChecker().getUpperIntersectionBA()
							.size() + console.getReplacementChecker()
							.getLowerIntersectionBA().size())+"\n");
		}
		this.out.flush();
	}
}
