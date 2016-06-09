/**
 * 
 */
package it.polimi.checker;

import static org.junit.Assert.*;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.io.in.ClaimReader;
import it.polimi.automata.io.in.ModelReader;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy.AcceptingType;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * @author Claudio Menghi
 *
 */
public class PPUCaseStudyScenario2Test {

	@Test
	public void testScenario2Safety() throws SAXException, IOException,
			ParserConfigurationException {
		ModelReader modelReader = new ModelReader(getClass().getClassLoader()
				.getResource("ppu/scenario2/aut/Model.xml").getFile());
		IBA model = modelReader.perform();

		ClaimReader claimReader = new ClaimReader(getClass().getClassLoader()
				.getResource("ppu/scenario2/aut/Req1Automaton.xml").getFile());
		BA claim = claimReader.perform();

		Checker checker = new Checker(model, claim,
				AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
						claim));
		assertTrue(SatisfactionValue.SATISFIED==checker.perform());
		
	}

	@Test
	public void testScenario2Liveness() throws SAXException, IOException,
			ParserConfigurationException {
		ModelReader modelReader = new ModelReader(getClass().getClassLoader()
				.getResource("ppu/scenario2/aut/Refinement.xml").getFile());
		IBA model = modelReader.perform();

		ClaimReader claimReader = new ClaimReader(getClass().getClassLoader()
				.getResource("ppu/scenario2/aut/Req2Automaton.xml").getFile());
		BA claim = claimReader.perform();

		Checker checker = new Checker(model, claim,
				AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
						claim));
		assertTrue(SatisfactionValue.SATISFIED==checker.perform());
		
		assertTrue(506==checker.getIntersectionAutomataSize());
		
	}
	
	@Test
	public void testScenario2StarvationFreedom() throws SAXException, IOException,
			ParserConfigurationException {
		ModelReader modelReader = new ModelReader(getClass().getClassLoader()
				.getResource("ppu/scenario2/aut/Model.xml").getFile());
		IBA model = modelReader.perform();

		ClaimReader claimReader = new ClaimReader(getClass().getClassLoader()
				.getResource("ppu/scenario2/aut/Req3Automaton.xml").getFile());
		BA claim = claimReader.perform();

		Checker checker = new Checker(model, claim,
				AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
						claim));
		assertTrue(SatisfactionValue.SATISFIED==checker.perform());
		
	}
	
	@Test
	public void testScenario2UnconditionalFair() throws SAXException, IOException,
			ParserConfigurationException {
		ModelReader modelReader = new ModelReader(getClass().getClassLoader()
				.getResource("ppu/scenario2/aut/Refinement.xml").getFile());
		IBA model = modelReader.perform();

		ClaimReader claimReader = new ClaimReader(getClass().getClassLoader()
				.getResource("ppu/scenario2/aut/Req4Automaton.xml").getFile());
		BA claim = claimReader.perform();

		Checker checker = new Checker(model, claim,
				AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
						claim));
		assertTrue(SatisfactionValue.SATISFIED==checker.perform());
		assertTrue(460==checker.getIntersectionAutomataSize());
			
	}

}
