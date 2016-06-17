package it.polimi.statemachine.replacement.action;

import java.io.Writer;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.TimeUnit;

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

public class Check implements ReplacementAction {

	private final Writer out;

	public Check(Writer out) {
		this.out = out;
	}

	@Override
	public ReplacementState visit(Init init) throws CHIAException {
		throw new CHIAException("Load a constriant and a replacement before running the checker");
	}

	@Override
	public boolean isPerformable(Init init) {
		return false;
	}

	@Override
	public ReplacementState visit(ReplacementLoaded replacementLoaded)throws CHIAException {
		throw new CHIAException("Load a constriant before running the checker");
	}

	@Override
	public boolean isPerformable(ReplacementLoaded replacementLoaded) {
		return false;
	}

	@Override
	public ReplacementState visit(ConstraintLoaded constraintLoaded) throws CHIAException {
		throw new CHIAException("Load a replacement before running the checker");
		
	}

	@Override
	public boolean isPerformable(ConstraintLoaded constraintLoaded) {
		return false;
	}

	@Override
	public ReplacementState visit(Ready ready) {
		return new Checked();
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
	public void perform(CHIAReplacementConsole console) throws Exception {
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
		out.write("Verification result: " + result.toString());
		out.write("Verification time: "
				+ Long.toString(TimeUnit.MILLISECONDS.convert(
						endTime - startTime, TimeUnit.NANOSECONDS)) + " ms");
		if (result.equals(SatisfactionValue.NOTSATISFIED)) {
			out.write("COUNTEREXAMPLE: "
					+ console.getReplacementChecker().getCouterexample());

		}
		if (!result.equals(SatisfactionValue.NOTSATISFIED)) {
			out.write("Dimension of the intersection automaton (states+transitions): "
					+ (console.getReplacementChecker().getUpperIntersectionBA()
							.size() + console.getReplacementChecker()
							.getLowerIntersectionBA().size()));
		}

	}
}
