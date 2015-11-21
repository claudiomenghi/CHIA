package it.polimi.replacementchecker.buchiaccepting;

import static org.junit.Assert.assertTrue;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.io.in.ClaimReader;
import it.polimi.automata.io.in.ModelReader;
import it.polimi.automata.io.out.ElementToStringTransformer;
import it.polimi.checker.Checker;
import it.polimi.checker.SatisfactionValue;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy.AcceptingType;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.components.SubProperty;
import it.polimi.constraints.io.in.constraint.ConstraintReader;
import it.polimi.constraints.io.in.replacement.ReplacementReader;
import it.polimi.constraints.io.out.constraint.ConstraintToElementTransformer;
import it.polimi.contraintcomputation.ConstraintGenerator;
import it.polimi.replacementchecker.ReplacementChecker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

public class Test03ReplacementChecker {
	private static final String path = "it.polimi.replacementchecker/";

	private Constraint constraint;
	private Replacement replacement;
	private AcceptingType acceptingPolicy;

	private IBA model;
	private IBA refinement;
	private BA claim;

	@Before
	public void setUp() throws FileNotFoundException, SAXException,
			IOException, ParserConfigurationException {
		this.replacement = new ReplacementReader(new File(getClass()
				.getClassLoader()
				.getResource(path + "buchiaccepting/test03/replacement.xml")
				.getFile())).perform();

		this.constraint = new ConstraintReader(new File(getClass()
				.getClassLoader()
				.getResource(path + "buchiaccepting/test03/constraint.xml")
				.getFile())).perform();
		this.acceptingPolicy = AcceptingType.BA;
		this.model = new ModelReader(new File(getClass().getClassLoader()
				.getResource(path + "buchiaccepting/test03/model.xml")
				.getFile())).perform();
		this.refinement = new ModelReader(new File(getClass().getClassLoader()
				.getResource(path + "buchiaccepting/test03/refinement.xml")
				.getFile())).perform();

		this.claim = new ClaimReader(new File(getClass().getClassLoader()
				.getResource(path + "buchiaccepting/test03/claim.xml")
				.getFile())).perform();
	}

	@Test
	public void test() throws ParserConfigurationException, Exception {

		Checker checker = new Checker(model, claim,
				AcceptingPolicy.getAcceptingPolicy(this.acceptingPolicy, model,
						claim));
		SatisfactionValue returnValue = checker.perform();
		assertTrue("The property must be possibly satisfied",
				returnValue == SatisfactionValue.POSSIBLYSATISFIED);

		System.out.println(checker.getUpperIntersectionBA().toString());

		ConstraintGenerator cg = new ConstraintGenerator(checker);
		Constraint constraint = cg.perform();
		cg.computeIndispensable();
		cg.computePortReachability();
		cg.coloring();

		System.out.println(new ElementToStringTransformer()
				.transform(new ConstraintToElementTransformer()
						.transform(constraint)));

		SubProperty subproperty = this.constraint
				.getSubProperty(this.replacement.getModelState());
		System.out.println(subproperty);

		Checker refinementChecker = new Checker(refinement, claim,
				AcceptingPolicy.getAcceptingPolicy(this.acceptingPolicy, model,
						claim));
		 returnValue = refinementChecker.perform();
		assertTrue("The property must be possibly satisfied",
				returnValue == SatisfactionValue.NOTSATISFIED);
		
		ReplacementChecker replacementChecker = new ReplacementChecker(
				subproperty, replacement, AcceptingPolicy.getAcceptingPolicy(
						this.acceptingPolicy, this.replacement.getAutomaton(),
						subproperty.getAutomaton()));

		SatisfactionValue retValue = replacementChecker.perform();
		System.out.println(replacementChecker.getLowerIntersectionBA());
		assertTrue(retValue == SatisfactionValue.NOTSATISFIED);
		

		
	}
}
