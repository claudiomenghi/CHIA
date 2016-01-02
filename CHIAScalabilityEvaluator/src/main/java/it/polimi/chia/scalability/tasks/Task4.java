package it.polimi.chia.scalability.tasks;

import it.polimi.checker.SatisfactionValue;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy.AcceptingType;
import it.polimi.chia.scalability.results.Record;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.components.SubProperty;
import it.polimi.replacementchecker.ReplacementChecker;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;

public class Task4 {

	private final Replacement replacement;
	private final Constraint constraint;
	private final Record record;
	private final AcceptingType acceptingPolicy;

	public Task4(Replacement replacement, Constraint constraint, Record record,
			AcceptingType acceptingPolicy) {
		this.replacement = replacement;
		this.constraint = constraint;
		this.record = record;
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
		SatisfactionValue replacementSatisfactionvalue = replacementChecker
				.perform();
		replacementCheckerTimer.stop();

		System.out
				.println("The replacement checking activity has been performed in: "
						+ replacementCheckerTimer
								.elapsed(TimeUnit.MILLISECONDS)
								+ "ms \t size of the automata: "
								+ replacementChecker.getIntersectionAutomataSize()
								+ "(|Q|+|T|) \t value of the property:" + replacementSatisfactionvalue);

		record.setReplacementVerificationTime(replacementCheckerTimer
				.elapsed(TimeUnit.MILLISECONDS));
		record.setSizeOfTheReplacementVerification(replacementChecker
				.getIntersectionAutomataSize());
		record.setReplacementSatisfactionValue(replacementSatisfactionvalue);
		record.setTriviallySatisfied(replacementChecker
				.isTriviallyPossiblySatisfied());
		return replacementChecker;
	}

}
