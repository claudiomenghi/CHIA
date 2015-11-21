package it.polimi.replacementchecker.buchiaccepting;

import static org.junit.Assert.assertTrue;
import it.polimi.checker.SatisfactionValue;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy.AcceptingType;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.components.SubProperty;
import it.polimi.constraints.io.in.constraint.ConstraintReader;
import it.polimi.constraints.io.in.replacement.ReplacementReader;
import it.polimi.replacementchecker.ReplacementChecker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

public class Test01ReplacementChecker {

	private static final String path = "it.polimi.replacementchecker/";

	private Constraint constraint;
	private Replacement replacement;

	private AcceptingType acceptingPolicy;

	@Before
	public void setUp() throws FileNotFoundException, SAXException,
			IOException, ParserConfigurationException {
		this.replacement = new ReplacementReader(new File(getClass()
				.getClassLoader()
				.getResource(path + "buchiaccepting/test01/replacement.xml")
				.getFile())).perform();

		this.constraint = new ConstraintReader(new File(getClass()
				.getClassLoader()
				.getResource(path + "buchiaccepting/test01/constraint.xml")
				.getFile())).perform();
		this.acceptingPolicy = AcceptingType.BA;
	}

	@Test
	public void test() {
		SubProperty subproperty = this.constraint
				.getSubProperty(this.replacement.getModelState());
		ReplacementChecker replacementChecker = new ReplacementChecker(
				subproperty, replacement, AcceptingPolicy.getAcceptingPolicy(
						acceptingPolicy, replacement.getAutomaton(),
						subproperty.getAutomaton()));

		SatisfactionValue retValue = replacementChecker.perform();
		System.out.println(replacementChecker.getLowerIntersectionBA());
		assertTrue(retValue == SatisfactionValue.NOTSATISFIED);
	}

}
