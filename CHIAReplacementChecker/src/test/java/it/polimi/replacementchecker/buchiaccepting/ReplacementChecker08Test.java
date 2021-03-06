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
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.components.SubProperty;
import it.polimi.constraints.io.in.constraint.ConstraintReader;
import it.polimi.constraints.io.in.replacement.ReplacementReader;
import it.polimi.replacementchecker.ReplacementChecker;

import java.io.File;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;

public class ReplacementChecker08Test {

	private static final String path = "it.polimi.replacementchecker/";

	private Constraint constraint;
	private Replacement replacement;
	
	private IBA refinement;
	private BA claim;
	private IBA model;
	private AcceptingType acceptingPolicy; 
	
	
	@Before
	public void setUp() throws Exception{
		this.replacement = new ReplacementReader(new File(getClass().getClassLoader()
				.getResource(path + "buchiaccepting/test08/replacement.xml").getFile())).perform();
		
		this.constraint=new ConstraintReader(new File(getClass().getClassLoader()
				.getResource(path + "buchiaccepting/test08/constraint.xml").getFile())).perform();
		this.refinement=new ModelReader(new File(getClass().getClassLoader()
				.getResource(path + "buchiaccepting/test08/refinement.xml").getFile())).perform();
		
		this.claim=new ClaimReader(new File(getClass().getClassLoader()
				.getResource(path + "buchiaccepting/test08/claim.xml").getFile())).perform();
		this.model=new ModelReader(new File(getClass().getClassLoader()
				.getResource(path + "buchiaccepting/test08/model.xml").getFile())).perform();
		this.acceptingPolicy=AcceptingType.BA;
	}
	@Test
	public void test() throws ParserConfigurationException, Exception {
		
		
		Checker checker=new Checker(model, claim, 
				AcceptingPolicy.getAcceptingPolicy(this.acceptingPolicy, model, claim));
		
		checker.perform();
		
		ConstraintGenerator cg = new ConstraintGenerator(checker);
		cg.perform();
	
		
		checker=new Checker(refinement, claim, 
				AcceptingPolicy.getAcceptingPolicy(this.acceptingPolicy, refinement,claim));
		SatisfactionValue ret=checker.perform();
		assertTrue(ret==SatisfactionValue.POSSIBLYSATISFIED);
		
		SubProperty subproperty=this.constraint.getSubProperty(this.replacement.getModelState());
		ReplacementChecker replacementChecker=new ReplacementChecker( replacement,subproperty, 
				AcceptingPolicy.getAcceptingPolicy(this.acceptingPolicy, replacement.getAutomaton(), subproperty.getAutomaton()));
		
		SatisfactionValue retValue=replacementChecker.perform();
		
		assertTrue(retValue==SatisfactionValue.POSSIBLYSATISFIED);
		
		
	}

}
