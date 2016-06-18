/**
 * 
 */
package it.polimi.console;

import static org.junit.Assert.assertNotNull;
import it.polimi.action.CHIAException;
import it.polimi.statemachine.replacement.ReplacementAction;
import it.polimi.statemachine.replacement.action.DisplayReplacement;
import it.polimi.statemachine.replacement.action.LoadConstraint;
import it.polimi.statemachine.replacement.action.LoadRepacement;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * @author Claudio Menghi
 *
 */
public class CHIAReplacementConsoleTest {

	/**
	 * Test method for
	 * {@link it.polimi.console.CHIAReplacementConsole#CHIAReplacementConsole()}
	 * .
	 */
	@Test
	public void testCHIAReplacementConsole() {
		assertNotNull(new CHIAReplacementConsole());
	}

	/**
	 * Test method for
	 * {@link it.polimi.console.CHIAReplacementConsole#loadConstraint(java.lang.String)}
	 * .
	 * 
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws FileNotFoundException
	 * @throws CHIAException 
	 */
	@Test
	public void testLoadConstraint() throws FileNotFoundException,
			ParserConfigurationException, SAXException, IOException, CHIAException {
		CHIAReplacementConsole rep = new CHIAReplacementConsole();
		
		ReplacementAction action=new LoadConstraint(new StringWriter(), getClass().getClassLoader()
				.getResource("it/polimi/console/LivenessConstraint.xml")
				.getPath());
		rep.getChiaState().next(action);
		assertNotNull(rep.getChiaState());
	}

	/**
	 * Test method for
	 * {@link it.polimi.console.CHIAReplacementConsole#dispConstraint()}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDispConstraint() throws Exception {
		CHIAReplacementConsole rep = new CHIAReplacementConsole();
		
		ReplacementAction action =new LoadConstraint(new StringWriter(), getClass().getClassLoader()
				.getResource("it/polimi/console/LivenessConstraint.xml").getPath());
		rep.getChiaState().next(action);
		assertNotNull(rep.getChiaState());
	}

	/**
	 * Test method for
	 * {@link it.polimi.console.CHIAReplacementConsole#loadReplacement(java.lang.String)}
	 * .
	 * 
	 * @throws Exception
	 */
	@Test
	public void testLoadReplacement() throws Exception {
		CHIAReplacementConsole rep = new CHIAReplacementConsole();
		
		ReplacementAction action=new LoadRepacement(new StringWriter(), getClass().getClassLoader()
				.getResource("it/polimi/console/ReplacementT10.xml").getPath());
		rep.getChiaState().next(action);
		assertNotNull(rep.getChiaState());
	}

	/**
	 * Test method for
	 * {@link it.polimi.console.CHIAReplacementConsole#dispReplacement()}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDispReplacement() throws Exception {
		CHIAReplacementConsole rep = new CHIAReplacementConsole();

		ReplacementAction action = new LoadRepacement(new StringWriter(),
				getClass().getClassLoader()
						.getResource("it/polimi/console/ReplacementT10.xml")
						.getPath());
		
		rep.changeState(action);
		
		action=new DisplayReplacement(new StringWriter());
		rep.changeState(action);
		assertNotNull(rep.getChiaState());
	}

	

	/**
	 * Test method for
	 * {@link it.polimi.console.CHIAReplacementConsole#replacementChecking()}.
	 * 
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws FileNotFoundException
	 * @throws CHIAException 
	 */
	@Test
	public void testReplacementChecking() throws FileNotFoundException,
			ParserConfigurationException, SAXException, IOException, CHIAException {
		CHIAReplacementConsole rep = new CHIAReplacementConsole();
		
		ReplacementAction action = new LoadRepacement(new StringWriter(), getClass().getClassLoader()
				.getResource("it/polimi/console/ReplacementT10.xml").getPath());
		
		
		rep.getChiaState().next(action);
		
		action=new LoadConstraint(new StringWriter(), getClass().getClassLoader()
				.getResource("it/polimi/console/LivenessConstraint.xml")
				.getPath());

		rep.getChiaState().next(action);
		assertNotNull(rep.getChiaState());
	}
}
