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

public class Check implements AutomataAction {

	private final Writer out;

	public Check(Writer out) {
		this.out = out;
	}

	@Override
	public void perform(CHIAAutomataConsole console) throws Exception{
	
		ThreadMXBean thradBean = ManagementFactory.getThreadMXBean();
		long startTime = thradBean.getCurrentThreadCpuTime();
		console.setChecker(new Checker(
				console.getModel(),
				console.getClaim(),
				AcceptingPolicy.getAcceptingPolicy(console.getPolicy(), 
						console.getModel(),
						console.getClaim())));
		SatisfactionValue result = console.getChecker().perform();
		long endTime = thradBean.getCurrentThreadCpuTime();
		out.write("Verification result: " + result.toString());
		out.write("Verification time: "
				+ Long.toString(TimeUnit.MILLISECONDS.convert(
						endTime - startTime, TimeUnit.NANOSECONDS))
				+ " ms");
		out.write("Dimension of the intersection automaton (states+transitions): "
				+ console.getChecker().getIntersectionAutomataSize());
		if (result.equals(SatisfactionValue.NOTSATISFIED)) {
			out.write("Counterexample:"
					+ console.getChecker().getFilteredCounterexample());
		}
	}

	
	
	
	@Override
	public AutomataState visit(Init state) throws CHIAException {
		throw new CHIAException(
				"You must load the property and the model before the checking");
	}

	@Override
	public boolean isPerformable(Init state) {
		return false;
	}

	@Override
	public AutomataState visit(ModelLoaded modelLoaded) throws CHIAException {
		throw new CHIAException(
				"You must load the property before the checking");
	}

	@Override
	public boolean isPerformable(ModelLoaded modelLoaded) {
		return false;
	}

	@Override
	public AutomataState visit(Ready ready) {
		return new Checked();
	}

	@Override
	public boolean isPerformable(Ready ready) {
		return true;
	}

	@Override
	public AutomataState visit(PropertyLoaded propertyLoaded) throws CHIAException {
		throw new CHIAException(
				"You must load the model and the model before the checking");
	}

	@Override
	public boolean isPerformable(PropertyLoaded propertyLoaded) {
		return false;
	}

	@Override
	public AutomataState visit(Checked checked) throws CHIAException {
		throw new CHIAException(
				"You already check the model against the current property. Change model or property");
	}

	@Override
	public boolean isPerformable(Checked checked) {
		return false;
	}

	@Override
	public AutomataState visit(ConstraintComputed constraintComputed)
			throws CHIAException {
		throw new CHIAException(
				"You already check the model against the current property. Change model or property");
	}

	@Override
	public boolean isPerformable(ConstraintComputed constraintComputed) {
		return false;
	}
}
