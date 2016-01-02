package it.polimi.chia.scalability.tasks;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.ModelTransitionFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.checker.Checker;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy.AcceptingType;
import it.polimi.chia.scalability.configuration.Configuration;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;

public class Task1 {

	private final Configuration configuration;
	private final BA modelBA;
	private final AcceptingType acceptingPolicy;
	
	private long taskTime;
	private int taskSpace;
	
	public Task1(Configuration configuration, BA modelBA, AcceptingType acceptingPolicy) {
		this.configuration=configuration;
		this.modelBA=modelBA;
		this.acceptingPolicy=acceptingPolicy;
	}
	
	public Checker perform() {
		IBA modelIBA = getIBA(modelBA);
		Stopwatch timer = Stopwatch.createUnstarted();
		Checker checker = new Checker(modelIBA,
				configuration.getCurrentClaim(),
				AcceptingPolicy.getAcceptingPolicy(acceptingPolicy, modelBA,
						configuration.getCurrentClaim()));

		timer.start();
		checker.perform();
		timer.stop();
		this.taskTime=timer.elapsed(TimeUnit.MILLISECONDS);
		this.taskSpace=checker.getIntersectionAutomataSize();
		
		return checker;
	}
	
	private static IBA getIBA(BA model) {
		IBA iba = new IBA(new ModelTransitionFactory());
		iba.addPropositions(model.getPropositions());

		for (State s : model.getStates()) {
			iba.addState(s);
			if (model.getInitialStates().contains(s)) {
				iba.addInitialState(s);
			}
			if (model.getAcceptStates().contains(s)) {
				iba.addAcceptState(s);
			}
		}
		ModelTransitionFactory factory = new ModelTransitionFactory();
		for (Transition t : model.getTransitions()) {
			iba.addTransition(model.getTransitionSource(t),
					model.getTransitionDestination(t),
					factory.create(t.getId(), t.getPropositions()));
		}
		return iba;
	}

	/**
	 * @return the taskTime
	 */
	public long getTaskTime() {
		return taskTime;
	}

	/**
	 * @return the taskSpace
	 */
	public int getTaskSpace() {
		return taskSpace;
	}
}