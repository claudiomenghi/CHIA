/**
 * 
 */
package it.polimi.console;

import static org.junit.Assert.*;
import it.polimi.action.CHIAException;
import it.polimi.statemachine.automata.AutomataAction;
import it.polimi.statemachine.automata.actions.Check;
import it.polimi.statemachine.automata.actions.ComputeConstraint;
import it.polimi.statemachine.automata.actions.DisplayModel;
import it.polimi.statemachine.automata.actions.DisplayProperty;
import it.polimi.statemachine.automata.actions.ReadFileProperty;
import it.polimi.statemachine.automata.actions.ReadLTLProperty;
import it.polimi.statemachine.automata.actions.ReadModel;
import it.polimi.statemachine.automata.states.Checked;
import it.polimi.statemachine.automata.states.ConstraintComputed;
import it.polimi.statemachine.automata.states.ModelLoaded;
import it.polimi.statemachine.automata.states.PropertyLoaded;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Ignore;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * @author Claudio Menghi
 *
 */
public class CHIAAutomataConsoleTest {

	private final AutomataAction loadModel = new ReadModel("",
			new StringWriter());
	private final AutomataAction loadFileProperty = new ReadFileProperty(
			new StringWriter(), "");
	private final AutomataAction loadLTLProperty = new ReadLTLProperty(
			new StringWriter(), "");

	private final AutomataAction displayProperty = new DisplayProperty(
			new StringWriter());

	private final AutomataAction check = new Check(new StringWriter());

	private final AutomataAction computeConstraint = new ComputeConstraint(
			new StringWriter());

	/**
	 * Test method for
	 * {@link it.polimi.console.CHIAAutomataConsole#CHIAAutomataConsole(java.io.PrintStream)}
	 * .
	 */
	@Test
	public void testCHIAAutomataConsole() {

		assertNotNull(new CHIAAutomataConsole());
	}

	/**
	 * Test method for
	 * {@link it.polimi.console.CHIAAutomataConsole#loadModel(java.lang.String)}
	 * .
	 * 
	 * @throws CHIAException
	 */
	@Test
	public void testLoadModel() throws CHIAException {
		CHIAAutomataConsole chiaAutomataConsole = new CHIAAutomataConsole();

		chiaAutomataConsole.changeState(loadModel);
		assertTrue(new ModelLoaded().equals(chiaAutomataConsole.getChiaState()));
		assertFalse(new Checked().equals(chiaAutomataConsole.getChiaState()));
	}

	/**
	 * Test method for
	 * {@link it.polimi.console.CHIAAutomataConsole#loadProperty(java.lang.String)}
	 * .
	 * 
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws FileNotFoundException
	 * @throws CHIAException
	 */
	@Test
	public void testLoadProperty() throws FileNotFoundException,
			ParserConfigurationException, SAXException, IOException,
			CHIAException {
		CHIAAutomataConsole chiaAutomataConsole = new CHIAAutomataConsole();

		chiaAutomataConsole.setChiaState(chiaAutomataConsole.getChiaState()
				.next(loadFileProperty));

		assertNotNull(new PropertyLoaded() == chiaAutomataConsole
				.getChiaState());
	}

	/**
	 * Test method for
	 * {@link it.polimi.console.CHIAAutomataConsole#loadLTLProperty(java.lang.String)}
	 * .
	 * 
	 * @throws CHIAException
	 */
	@Test
	public void testLoadLTLProperty() throws CHIAException {
		CHIAAutomataConsole chiaAutomataConsole = new CHIAAutomataConsole();

		chiaAutomataConsole.changeState(loadFileProperty);

		assertTrue(new PropertyLoaded().equals(chiaAutomataConsole
				.getChiaState()));
		chiaAutomataConsole.setChiaState(chiaAutomataConsole.getChiaState()
				.next(loadLTLProperty));

		assertTrue(new PropertyLoaded().equals(chiaAutomataConsole
				.getChiaState()));

	}

	/**
	 * Test method for {@link it.polimi.console.CHIAAutomataConsole#dispModel()}
	 * .
	 * 
	 * @throws Exception
	 * @throws ParserConfigurationException
	 */
	@Test
	public void testDispModel() throws ParserConfigurationException, Exception {
		CHIAAutomataConsole chiaAutomataConsole = new CHIAAutomataConsole();
		chiaAutomataConsole.changeState(loadModel);
		chiaAutomataConsole.changeState(new DisplayModel(new StringWriter()));

		assertNotNull(new ModelLoaded().equals(chiaAutomataConsole
				.getChiaState()));
	}

	/**
	 * Test method for {@link it.polimi.console.CHIAAutomataConsole#dispModel()}
	 * .
	 * 
	 * @throws Exception
	 * @throws ParserConfigurationException
	 */
	@Test(expected = CHIAException.class)
	public void testDispModel_NullPointerException()
			throws ParserConfigurationException, Exception {
		CHIAAutomataConsole chiaAutomataConsole = new CHIAAutomataConsole();
		chiaAutomataConsole.changeState(new DisplayModel(new StringWriter()));
	}

	/**
	 * Test method for {@link it.polimi.console.CHIAAutomataConsole#dispClaim()}
	 * .
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDispClaim() throws Exception {
		CHIAAutomataConsole chiaAutomataConsole = new CHIAAutomataConsole();
		chiaAutomataConsole.changeState(this.loadLTLProperty);

		chiaAutomataConsole.changeState(this.loadFileProperty);

		chiaAutomataConsole.changeState(this.displayProperty);

		assertNotNull(new PropertyLoaded().equals(chiaAutomataConsole
				.getChiaState()));
	}

	/**
	 * Test method for {@link it.polimi.console.CHIAAutomataConsole#check()}.
	 * 
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws FileNotFoundException
	 * @throws CHIAException
	 */
	@Test
	public void testCheck() throws FileNotFoundException,
			ParserConfigurationException, SAXException, IOException,
			CHIAException {
		CHIAAutomataConsole chiaAutomataConsole = new CHIAAutomataConsole();
		chiaAutomataConsole.changeState(this.loadFileProperty);
		chiaAutomataConsole.changeState(this.loadModel);
		chiaAutomataConsole.changeState(this.check);

		assertTrue(new Checked().equals(chiaAutomataConsole.getChiaState()));
	}

	/**
	 * Test method for
	 * {@link it.polimi.console.CHIAAutomataConsole#computeConstraint()}.
	 * 
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws FileNotFoundException
	 * @throws CHIAException
	 */
	@Test
	public void testComputeConstraint() throws FileNotFoundException,
			ParserConfigurationException, SAXException, IOException,
			CHIAException {
		CHIAAutomataConsole chiaAutomataConsole = new CHIAAutomataConsole();
		
		chiaAutomataConsole.changeState(this.loadFileProperty);

		chiaAutomataConsole.changeState(this.loadModel);

		chiaAutomataConsole.changeState(this.check);
		chiaAutomataConsole.changeState(this.computeConstraint);

		assertTrue(new ConstraintComputed().equals(chiaAutomataConsole
				.getChiaState()));
	}

	/**
	 * Test method for
	 * {@link it.polimi.console.CHIAAutomataConsole#saveConstraint(java.lang.String)}
	 * .
	 */
	@Ignore
	@Test
	public void testSaveConstraint() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link it.polimi.console.CHIAAutomataConsole#dispConstraint()}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDispConstraint() throws Exception {

		CHIAAutomataConsole chiaAutomataConsole = new CHIAAutomataConsole();

		chiaAutomataConsole.changeState(loadModel);
		
		chiaAutomataConsole.changeState(loadFileProperty);
		
		chiaAutomataConsole.changeState(check);
		
		chiaAutomataConsole.changeState(computeConstraint);
		
		chiaAutomataConsole.changeState(displayProperty);
	
		assertTrue(new ConstraintComputed().equals(chiaAutomataConsole.getChiaState()));

	}

	/**
	 * Test method for {@link it.polimi.console.CHIAAutomataConsole#exit()}.
	 */
	@Test
	public void testExit() {
		CHIAAutomataConsole chiaAutomataConsole = new CHIAAutomataConsole();
		chiaAutomataConsole.exit();
	}

}
