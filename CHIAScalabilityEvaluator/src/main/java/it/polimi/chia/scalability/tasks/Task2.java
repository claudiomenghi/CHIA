package it.polimi.chia.scalability.tasks;

import it.polimi.automata.IBA;
import it.polimi.checker.Checker;
import it.polimi.checker.SatisfactionValue;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy.AcceptingType;
import it.polimi.chia.scalability.configuration.Configuration;
import it.polimi.chia.scalability.results.Record;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;

public class Task2 extends Task{

	private final IBA model;
	private final Record record;
	private final AcceptingType acceptingPolicy;
	private long taskTime;
	private int taskSpace;
	

	public Task2(Configuration configuration, IBA model, Record record,
			AcceptingType acceptingPolicy) {
		super(configuration);
		this.model = model;
		this.record = record;
		this.acceptingPolicy = acceptingPolicy;
	}

	public Checker perform() {
		// check whether the model possibly satisfies the claim
		Checker checker = new Checker(model, this.getConfiguration().getCurrentClaim(),
				AcceptingPolicy.getAcceptingPolicy(acceptingPolicy, model,
						 this.getConfiguration().getCurrentClaim()));
		Stopwatch incompleteCheckerTimer = Stopwatch.createUnstarted();
		incompleteCheckerTimer.start();
		SatisfactionValue ibavalue = checker.perform();
		incompleteCheckerTimer.stop();

		this.taskTime=incompleteCheckerTimer.elapsed(TimeUnit.MILLISECONDS);
		this.taskSpace=checker.getIntersectionAutomataSize();
		record.setSizeModel(model.size());
		record.setInitialSatisfactioValue(ibavalue);
		record.setNumTransparentStatesModel(model.getBlackBoxStates().size());
		return checker;
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
