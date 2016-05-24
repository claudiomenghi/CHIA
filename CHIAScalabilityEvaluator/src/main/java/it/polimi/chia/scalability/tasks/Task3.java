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

public class Task3 extends Task{

	private final Configuration configuration;
	private final IBA refinedModel;
	private final Record record;
	private final AcceptingType acceptingPolicy;

	public Task3(Configuration configuration, IBA model,
			Record record,
			AcceptingType acceptingPolicy) {
		this.configuration = configuration;
		this.refinedModel = model;
		this.record = record;
		this.acceptingPolicy = acceptingPolicy;
	}

	public Checker perform() {
		Checker refinementChecker = new Checker(refinedModel,
				configuration.getCurrentClaim(),
				AcceptingPolicy.getAcceptingPolicy(acceptingPolicy,
						refinedModel, configuration.getCurrentClaim()));

		Stopwatch refinementCheckerTimer = Stopwatch.createUnstarted();
		refinementCheckerTimer.start();
		SatisfactionValue refinementSatisfactionvalue = refinementChecker
				.perform();
		refinementCheckerTimer.stop();
		this.setTaskTime(refinementCheckerTimer.elapsed(TimeUnit.MILLISECONDS));
		this.setTaskSpace(refinementChecker.getIntersectionAutomataSize());
	
		record.setRefinementVerificationTime(refinementCheckerTimer
				.elapsed(TimeUnit.MILLISECONDS));
		record.setSizeOfTheRefinementVerification(refinementChecker
				.getIntersectionAutomataSize());
		record.setRefinementSatisfactionValue(refinementSatisfactionvalue);
		return refinementChecker;
	}
}
