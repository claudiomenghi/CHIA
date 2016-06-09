package it.polimi.checker;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.io.in.ClaimReader;
import it.polimi.automata.io.in.ModelReader;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy.AcceptingType;

import org.junit.Test;
import org.xml.sax.SAXException;

public class MutexCaseStudyScenario1Test {

	@Test
	public void testScenario1Safety() throws SAXException, IOException,
			ParserConfigurationException {
		ModelReader modelReader = new ModelReader(getClass().getClassLoader()
				.getResource("mutex/scenario1/aut/Refinement.xml").getFile());
		IBA model = modelReader.perform();

		ClaimReader claimReader = new ClaimReader(getClass().getClassLoader()
				.getResource("mutex/scenario1/aut/Safety.xml").getFile());
		BA claim = claimReader.perform();

		Checker checker = new Checker(model, claim,
				AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
						claim));
		assertTrue(13==model.getStates().size());
		assertTrue(20==model.getTransitions().size());
		assertTrue(SatisfactionValue.SATISFIED==checker.perform());
		assertTrue(checker.getLowerIntersectionBA().size()==33);
		assertTrue(checker.getUpperIntersectionBA().size()==33);
		assertTrue(66==checker.getIntersectionAutomataSize());
		
	}

	@Test
	public void testScenario1Liveness() throws SAXException, IOException,
			ParserConfigurationException {
		ModelReader modelReader = new ModelReader(getClass().getClassLoader()
				.getResource("mutex/scenario1/aut/Refinement.xml").getFile());
		IBA model = modelReader.perform();

		ClaimReader claimReader = new ClaimReader(getClass().getClassLoader()
				.getResource("mutex/scenario1/aut/Liveness.xml").getFile());
		BA claim = claimReader.perform();

		Checker checker = new Checker(model, claim,
				AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
						claim));
		assertTrue(SatisfactionValue.SATISFIED==checker.perform());
		assertTrue(378==checker.getIntersectionAutomataSize());
		
	}
	
	@Test
	public void testScenario1StarvationFreedom() throws SAXException, IOException,
			ParserConfigurationException {
		ModelReader modelReader = new ModelReader(getClass().getClassLoader()
				.getResource("mutex/scenario1/aut/Refinement.xml").getFile());
		IBA model = modelReader.perform();

		ClaimReader claimReader = new ClaimReader(getClass().getClassLoader()
				.getResource("mutex/scenario1/aut/StarvationFreedom.xml").getFile());
		BA claim = claimReader.perform();

		Checker checker = new Checker(model, claim,
				AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
						claim));
		assertTrue(SatisfactionValue.SATISFIED==checker.perform());
		assertTrue(670==checker.getIntersectionAutomataSize());
		
	}
	
	@Test
	public void testScenario1UnconditionalFair() throws SAXException, IOException,
			ParserConfigurationException {
		ModelReader modelReader = new ModelReader(getClass().getClassLoader()
				.getResource("mutex/scenario1/aut/Refinement.xml").getFile());
		IBA model = modelReader.perform();

		ClaimReader claimReader = new ClaimReader(getClass().getClassLoader()
				.getResource("mutex/scenario1/aut/UnconditionalFair.xml").getFile());
		BA claim = claimReader.perform();

		Checker checker = new Checker(model, claim,
				AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
						claim));
		assertTrue(SatisfactionValue.SATISFIED==checker.perform());
		assertTrue(162==checker.getIntersectionAutomataSize());
		
	}
}
