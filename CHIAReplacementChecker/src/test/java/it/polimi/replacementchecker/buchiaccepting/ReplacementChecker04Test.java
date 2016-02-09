package it.polimi.replacementchecker.buchiaccepting;

import static org.junit.Assert.assertTrue;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.io.in.ClaimReader;
import it.polimi.automata.io.in.ModelReader;
import it.polimi.checker.Checker;
import it.polimi.checker.SatisfactionValue;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy.AcceptingType;
import it.polimi.constraintcomputation.ConstraintGenerator;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.components.RefinementGenerator;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.components.SubProperty;
import it.polimi.constraints.io.in.replacement.ReplacementReader;
import it.polimi.replacementchecker.ReplacementChecker;

import java.io.File;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;

public class ReplacementChecker04Test {

	private static final String path = "it.polimi.replacementchecker/";

	private IBA model;
	private BA claim;

	private Replacement replacement;
	private AcceptingType acceptingPolicy;

	@Before
	public void setUp() throws Exception {
		this.model = new ModelReader(new File(getClass().getClassLoader()
				.getResource(path + "buchiaccepting/test04/model.xml")
				.getFile())).perform();

		this.claim = new ClaimReader(new File(getClass().getClassLoader()
				.getResource(path + "buchiaccepting/test04/claim.xml")
				.getFile())).perform();

		this.replacement = new ReplacementReader(new File(getClass()
				.getClassLoader()
				.getResource(path + "buchiaccepting/test04/replacement.xml")
				.getFile())).perform();
		this.acceptingPolicy = AcceptingType.BA;
	}

	@Test
	public void test() throws ParserConfigurationException, Exception {

		Checker checker = new Checker(model, claim,
				AcceptingPolicy.getAcceptingPolicy(this.acceptingPolicy, model,
						claim));
		SatisfactionValue returnValue = checker.perform();
		assertTrue("The property must be possibly satisfied",
				returnValue == SatisfactionValue.POSSIBLYSATISFIED);

		ConstraintGenerator cg = new ConstraintGenerator(checker);
		Constraint constraint = cg.perform();

		IBA refinement = new RefinementGenerator(model, replacement).perform();

		Checker refinementChecker = new Checker(refinement, claim,
				AcceptingPolicy.getAcceptingPolicy(this.acceptingPolicy,
						refinement, claim));
		assertTrue("The property must be not satisfied",
				refinementChecker.perform() == SatisfactionValue.NOTSATISFIED);

		SubProperty subproperty = constraint.getSubProperty(replacement
				.getModelState());
		ReplacementChecker rc = new ReplacementChecker(replacement,
				subproperty, AcceptingPolicy.getAcceptingPolicy(
						this.acceptingPolicy, replacement.getAutomaton(),
						subproperty.getAutomaton()));

		SatisfactionValue satisfactionValue = rc.perform();

		assertTrue(satisfactionValue == SatisfactionValue.NOTSATISFIED);

	}

}
