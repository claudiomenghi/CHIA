/**
 * 
 */
package it.polimi.statemachine.states;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import it.polimi.action.CHIAException;
import it.polimi.statemachine.replacement.action.Check;
import it.polimi.statemachine.replacement.action.DisplayConstraint;
import it.polimi.statemachine.replacement.action.DisplayReplacement;
import it.polimi.statemachine.replacement.action.LoadConstraint;
import it.polimi.statemachine.replacement.action.LoadRepacement;
import it.polimi.statemachine.replacement.states.Checked;
import it.polimi.statemachine.replacement.states.ConstraintLoaded;
import it.polimi.statemachine.replacement.states.Init;
import it.polimi.statemachine.replacement.states.Ready;
import it.polimi.statemachine.replacement.states.ReplacementLoaded;

import java.io.StringWriter;

import org.junit.Test;

/**
 * @author Claudio Menghi
 *
 */
public class CHIAReplacementStateTest {

	private final LoadRepacement loadReplacement = new LoadRepacement(
			new StringWriter(), "");

	/**
	 * Test method for
	 * {@link it.polimi.statemachine.replacement.states.CHIAReplacementState#next(java.lang.Class)}
	 * .
	 * 
	 * @throws CHIAException
	 */
	@Test
	public void testPerform_INIT() throws CHIAException {

		assertEquals(new Init().next(loadReplacement), new ReplacementLoaded());
		assertEquals(new ConstraintLoaded(),
				new Init().next(new LoadConstraint(new StringWriter(), "")));
	}

	/**
	 * Test method for
	 * {@link it.polimi.statemachine.replacement.states.CHIAReplacementState#isPerformable(java.lang.Class)}
	 * .
	 */
	@Test
	public void isPerformable_INIT() {

		assertTrue(new Init().isPerformable(loadReplacement));
		assertTrue(new Init().isPerformable(new LoadConstraint(
				new StringWriter(), "")));
		assertFalse(new Init().isPerformable(new Check(new StringWriter())));
	}

	/**
	 * Test method for
	 * {@link it.polimi.statemachine.replacement.states.CHIAReplacementState#next(java.lang.Class)}
	 * .
	 * 
	 * @throws CHIAException
	 */
	@Test(expected = CHIAException.class)
	public void testPerform_INIT_Exception() throws CHIAException {
		new Init().next(new Check(new StringWriter()));
	}

	/**
	 * Test method for
	 * {@link it.polimi.statemachine.replacement.states.CHIAReplacementState#next(java.lang.Class)}
	 * .
	 * 
	 * @throws CHIAException
	 */
	@Test
	public void testPerform_REPLACEMENTLOADED() throws CHIAException {

		assertEquals(new ReplacementLoaded(),
				new ReplacementLoaded().next(loadReplacement));
		assertEquals(new Ready(),
				new ReplacementLoaded().next(new LoadConstraint(
						new StringWriter(), "")));
		assertEquals(new ReplacementLoaded(),
				new ReplacementLoaded().next(new DisplayReplacement(
						new StringWriter())));
	}

	/**
	 * Test method for
	 * {@link it.polimi.statemachine.replacement.states.CHIAReplacementState#isPerformable(java.lang.Class)}
	 * .
	 */
	@Test
	public void isPerformable_REPLACEMENTLOADED() {

		assertTrue(new ReplacementLoaded().isPerformable(loadReplacement));
		assertTrue(new ReplacementLoaded().isPerformable(new LoadConstraint(
				new StringWriter(), "")));
		assertTrue(new ReplacementLoaded()
				.isPerformable(new DisplayReplacement(new StringWriter())));
		assertFalse(new ReplacementLoaded().isPerformable(new Check(
				new StringWriter())));
	}

	/**
	 * Test method for
	 * {@link it.polimi.statemachine.replacement.states.CHIAReplacementState#next(java.lang.Class)}
	 * .
	 * 
	 * @throws CHIAException
	 */
	@Test(expected = CHIAException.class)
	public void testPerform_REPLACEMENTLOADED_Exception() throws CHIAException {
		new ReplacementLoaded().next(new Check(new StringWriter()));
	}

	/**
	 * Test method for
	 * {@link it.polimi.statemachine.replacement.states.CHIAReplacementState#next(java.lang.Class)}
	 * .
	 * 
	 * @throws CHIAException
	 */
	@Test
	public void testPerform_CONSTRAINTLOADED() throws CHIAException {

		assertEquals(new Ready(), new ConstraintLoaded().next(loadReplacement));
		assertEquals(new ConstraintLoaded(),
				new ConstraintLoaded().next(new LoadConstraint(
						new StringWriter(), "")));
		assertEquals(new ConstraintLoaded(),
				new ConstraintLoaded().next(new DisplayConstraint()));

	}

	/**
	 * Test method for
	 * {@link it.polimi.statemachine.replacement.states.CHIAReplacementState#isPerformable(java.lang.Class)}
	 * .
	 */
	@Test
	public void isPerformable_CONSTRAINTLOADED() {

		assertTrue(new ConstraintLoaded().isPerformable(loadReplacement));
		assertTrue(new ConstraintLoaded().isPerformable(new LoadConstraint(
				new StringWriter(), "")));
		assertTrue(new ConstraintLoaded()
				.isPerformable(new DisplayConstraint()));
		assertFalse(new ConstraintLoaded().isPerformable(new Check(
				new StringWriter())));
	}

	/**
	 * Test method for
	 * {@link it.polimi.statemachine.replacement.states.CHIAReplacementState#next(java.lang.Class)}
	 * .
	 * 
	 * @throws CHIAException
	 */
	@Test(expected = CHIAException.class)
	public void testPerform_CONSTRAINTLOADED_Exception() throws CHIAException {
		new ConstraintLoaded().next(new Check(new StringWriter()));
	}

	/**
	 * Test method for
	 * {@link it.polimi.statemachine.replacement.states.CHIAReplacementState#next(java.lang.Class)}
	 * .
	 * 
	 * @throws CHIAException
	 */
	@Test
	public void testPerform_READY() throws CHIAException {

		assertEquals(new Ready(), new Ready().next(loadReplacement));
		assertEquals(new Ready(),
				new Ready().next(new LoadConstraint(new StringWriter(), "")));
		assertEquals(new Ready(),
				new Ready().next(new DisplayReplacement(new StringWriter())));
		assertEquals(new Checked(),
				new Ready().next(new Check(new StringWriter())));
		assertEquals(new Ready(), new Ready().next(new DisplayConstraint()));

	}

	/**
	 * Test method for
	 * {@link it.polimi.statemachine.replacement.states.CHIAReplacementState#isPerformable(java.lang.Class)}
	 * .
	 */
	@Test
	public void isPerformable_READY() {

		assertTrue(new Ready().isPerformable(loadReplacement));
		assertTrue(new Ready().isPerformable(new LoadConstraint(
				new StringWriter(), "")));
		assertTrue(new Ready().isPerformable(new DisplayConstraint()));
		assertTrue(new Ready().isPerformable(new DisplayReplacement(
				new StringWriter())));
		assertTrue(new Ready().isPerformable(new Check(new StringWriter())));
	}

	/**
	 * Test method for
	 * {@link it.polimi.statemachine.replacement.states.CHIAReplacementState#next(java.lang.Class)}
	 * .
	 * 
	 * @throws CHIAException
	 */
	@Test
	public void testPerform_CHECKED() throws CHIAException {

		assertEquals(new Ready(), new Checked().next(loadReplacement));
		assertEquals(new Ready(),
				new Checked().next(new LoadConstraint(new StringWriter(), "")));
		assertEquals(new Checked(), new Checked().next(new DisplayConstraint()));
		assertEquals(new Checked(),
				new Checked().next(new DisplayReplacement(new StringWriter())));
		assertEquals(new Checked(),
				new Checked().next(new Check(new StringWriter())));

	}

	/**
	 * Test method for
	 * {@link it.polimi.statemachine.replacement.states.CHIAReplacementState#isPerformable(java.lang.Class)}
	 * .
	 */
	@Test
	public void isPerformable_CHECKED() {

		assertTrue(new Checked().isPerformable(loadReplacement));
		assertTrue(new Checked().isPerformable(new LoadConstraint(
				new StringWriter(), "")));
		assertTrue(new Checked().isPerformable(new DisplayConstraint()));
		assertTrue(new Checked().isPerformable(new DisplayReplacement(
				new StringWriter())));
		assertTrue(new Checked().isPerformable(new Check(new StringWriter())));

	}

}
