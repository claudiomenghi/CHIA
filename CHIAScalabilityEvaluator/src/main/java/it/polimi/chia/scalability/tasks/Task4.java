package it.polimi.chia.scalability.tasks;

import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy.AcceptingType;
import it.polimi.chia.scalability.configuration.Configuration;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.components.SubProperty;
import it.polimi.replacementchecker.ReplacementChecker;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;

public class Task4 extends Task {

	private final Replacement replacement;
	private final Constraint constraint;
	private final AcceptingType acceptingPolicy;

	public Task4(Configuration configuration, Replacement replacement,
			Constraint constraint, AcceptingType acceptingPolicy) {
		super(configuration);
		this.replacement = replacement;
		this.constraint = constraint;
		this.acceptingPolicy = acceptingPolicy;
	}

	public ReplacementChecker perform() {
		SubProperty subProperty1 = constraint.getSubProperty(replacement
				.getModelState());

		ReplacementChecker replacementChecker = new ReplacementChecker(
				replacement, subProperty1, AcceptingPolicy.getAcceptingPolicy(
						acceptingPolicy, replacement.getAutomaton(),
						subProperty1.getAutomaton()));

		Stopwatch replacementCheckerTimer = Stopwatch.createUnstarted();
		replacementCheckerTimer.start();
		replacementChecker.perform();
		replacementCheckerTimer.stop();
		this.setTaskTime(replacementCheckerTimer.elapsed(TimeUnit.MILLISECONDS));
		this.setTaskSpace(replacementChecker.getIntersectionAutomataSize());

		return replacementChecker;
	}

}
