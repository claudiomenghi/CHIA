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

public class MutexCaseStudyScenario2Test {

	@Test
	public void testScenario1Safety() throws SAXException, IOException,
			ParserConfigurationException {
		ModelReader modelReader = new ModelReader(getClass().getClassLoader()
				.getResource("mutex/scenario2/aut/Refinement.xml").getFile());
		IBA model = modelReader.perform();

		ClaimReader claimReader = new ClaimReader(getClass().getClassLoader()
				.getResource("mutex/scenario2/aut/Safety.xml").getFile());
		BA claim = claimReader.perform();

		Checker checker = new Checker(model, claim,
				AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
						claim));
		assertTrue(SatisfactionValue.POSSIBLYSATISFIED==checker.perform());
		assertTrue(120==checker.getIntersectionAutomataSize());
		
	}

	@Test
	public void testScenario1Liveness() throws SAXException, IOException,
			ParserConfigurationException {
		ModelReader modelReader = new ModelReader(getClass().getClassLoader()
				.getResource("mutex/scenario2/aut/Refinement.xml").getFile());
		IBA model = modelReader.perform();

		ClaimReader claimReader = new ClaimReader(getClass().getClassLoader()
				.getResource("mutex/scenario2/aut/Liveness.xml").getFile());
		BA claim = claimReader.perform();

		Checker checker = new Checker(model, claim,
				AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
						claim));
		assertTrue(SatisfactionValue.POSSIBLYSATISFIED==checker.perform());
		assertTrue(260==checker.getIntersectionAutomataSize());
		
	}
	
	@Test
	public void testScenario1StarvationFreedom() throws SAXException, IOException,
			ParserConfigurationException {
		ModelReader modelReader = new ModelReader(getClass().getClassLoader()
				.getResource("mutex/scenario2/aut/Refinement.xml").getFile());
		IBA model = modelReader.perform();

		ClaimReader claimReader = new ClaimReader(getClass().getClassLoader()
				.getResource("mutex/scenario2/aut/StarvationFreedom.xml").getFile());
		BA claim = claimReader.perform();

		Checker checker = new Checker(model, claim,
				AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
						claim));
		assertTrue(SatisfactionValue.POSSIBLYSATISFIED==checker.perform());
		assertTrue(527==checker.getIntersectionAutomataSize());
		
	}
	
	@Test
	public void testScenario1UnconditionalFair() throws SAXException, IOException,
			ParserConfigurationException {
		ModelReader modelReader = new ModelReader(getClass().getClassLoader()
				.getResource("mutex/scenario2/aut/Refinement.xml").getFile());
		IBA model = modelReader.perform();

		ClaimReader claimReader = new ClaimReader(getClass().getClassLoader()
				.getResource("mutex/scenario2/aut/UnconditionalFair.xml").getFile());
		BA claim = claimReader.perform();

		Checker checker = new Checker(model, claim,
				AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
						claim));
		assertTrue(SatisfactionValue.POSSIBLYSATISFIED==checker.perform());
		assertTrue(130==checker.getIntersectionAutomataSize());
		
	}
}
