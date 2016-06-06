package it.polimi.replacementchecker;

import static org.junit.Assert.assertTrue;
import it.polimi.checker.SatisfactionValue;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy.AcceptingType;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.io.in.constraint.ConstraintReader;
import it.polimi.constraints.io.in.replacement.ReplacementReader;

import java.io.File;

import org.junit.Test;

public class MutexScenario1Test {

	@Test
	public void testScenario1Safety() throws Exception {
		ReplacementReader modelReader = new ReplacementReader(new File(getClass().getClassLoader()
				.getResource("mutex/scenario1/rep/ReplacementT10.xml").getFile()));
		Replacement model = modelReader.perform();

		ConstraintReader claimReader = new ConstraintReader(new File(getClass().getClassLoader()
				.getResource("mutex/scenario1/rep/SafetyConstraint.xml").getFile()));
		Constraint claim = claimReader.perform();

		ReplacementChecker checker = new ReplacementChecker(model, 
				claim.getSubProperty(model.getModelState()),
				AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model.getAutomaton(),
						claim.getSubProperty(model.getModelState()).getAutomaton()));
		assertTrue(SatisfactionValue.SATISFIED==checker.perform());
		assertTrue(53==checker.getIntersectionAutomataSize());
		
	}

	@Test
	public void testScenario1Liveness() throws Exception {
		ReplacementReader modelReader = new ReplacementReader(new File(getClass().getClassLoader()
				.getResource("mutex/scenario1/rep/ReplacementT10.xml").getFile()));
		Replacement model = modelReader.perform();

		ConstraintReader claimReader = new ConstraintReader(new File(getClass().getClassLoader()
				.getResource("mutex/scenario1/rep/LivenessConstraint.xml").getFile()));
		Constraint claim = claimReader.perform();

		ReplacementChecker checker = new ReplacementChecker(model, 
				claim.getSubProperty(model.getModelState()),
				AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model.getAutomaton(),
						claim.getSubProperty(model.getModelState()).getAutomaton()));
		assertTrue(SatisfactionValue.SATISFIED==checker.perform());
		
		assertTrue(321==checker.getIntersectionAutomataSize());

	}

	@Test
	public void testScenario1StarvationFreedom() throws Exception {
		ReplacementReader modelReader = new ReplacementReader(new File(getClass().getClassLoader()
				.getResource("mutex/scenario1/rep/ReplacementT10.xml").getFile()));
		Replacement model = modelReader.perform();

		ConstraintReader claimReader = new ConstraintReader(new File(getClass().getClassLoader()
				.getResource("mutex/scenario1/rep/StarvationFreedomConstraint.xml").getFile()));
		Constraint claim = claimReader.perform();

		ReplacementChecker checker = new ReplacementChecker(model, 
				claim.getSubProperty(model.getModelState()),
				AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model.getAutomaton(),
						claim.getSubProperty(model.getModelState()).getAutomaton()));
		assertTrue(SatisfactionValue.SATISFIED==checker.perform());
		
		assertTrue(631==checker.getIntersectionAutomataSize());

	}

	@Test
	public void testScenario1UnconditionalFair() throws Exception {
		ReplacementReader modelReader = new ReplacementReader(new File(getClass().getClassLoader()
				.getResource("mutex/scenario1/rep/ReplacementT10.xml").getFile()));
		Replacement model = modelReader.perform();

		ConstraintReader claimReader = new ConstraintReader(new File(getClass().getClassLoader()
				.getResource("mutex/scenario1/rep/UnconditionalFairConstraint.xml").getFile()));
		Constraint claim = claimReader.perform();

		ReplacementChecker checker = new ReplacementChecker(model, 
				claim.getSubProperty(model.getModelState()),
				AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model.getAutomaton(),
						claim.getSubProperty(model.getModelState()).getAutomaton()));
		assertTrue(SatisfactionValue.SATISFIED==checker.perform());
		
		assertTrue(127==checker.getIntersectionAutomataSize());

	}

}
