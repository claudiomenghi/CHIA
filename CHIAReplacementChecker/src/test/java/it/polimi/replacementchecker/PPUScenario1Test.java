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

public class PPUScenario1Test {

	@Test
	public void testScenario1Req2() throws Exception {
		ReplacementReader modelReader = new ReplacementReader(new File(getClass().getClassLoader()
				.getResource("ppu/scenario1/rep/ReplacementCrane.xml").getFile()));
		Replacement model = modelReader.perform();

		ConstraintReader claimReader = new ConstraintReader(new File(getClass().getClassLoader()
				.getResource("ppu/scenario1/rep/ConstraintReq2.xml").getFile()));
		Constraint claim = claimReader.perform();

		ReplacementChecker checker = new ReplacementChecker(model, 
				claim.getSubProperty(model.getModelState()),
				AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model.getAutomaton(),
						claim.getSubProperty(model.getModelState()).getAutomaton()));
		assertTrue(SatisfactionValue.SATISFIED==checker.perform());
		assertTrue(265==checker.getIntersectionAutomataSize());
		
	}

	@Test
	public void testScenario1Req4() throws Exception {
		ReplacementReader modelReader = new ReplacementReader(new File(getClass().getClassLoader()
				.getResource("ppu/scenario1/rep/ReplacementCrane.xml").getFile()));
		Replacement model = modelReader.perform();

		ConstraintReader claimReader = new ConstraintReader(new File(getClass().getClassLoader()
				.getResource("ppu/scenario1/rep/ConstraintReq4.xml").getFile()));
		Constraint claim = claimReader.perform();

		ReplacementChecker checker = new ReplacementChecker(model, 
				claim.getSubProperty(model.getModelState()),
				AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model.getAutomaton(),
						claim.getSubProperty(model.getModelState()).getAutomaton()));
		assertTrue(SatisfactionValue.SATISFIED==checker.perform());
		
		assertTrue(239==checker.getIntersectionAutomataSize());

	}
}
