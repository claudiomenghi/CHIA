/**
 * 
 */
package it.polimi.statemachine.states;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import it.polimi.action.CHIAException;
import it.polimi.statemachine.automata.AutomataAction;
import it.polimi.statemachine.automata.AutomataState;
import it.polimi.statemachine.automata.actions.Check;
import it.polimi.statemachine.automata.actions.ComputeConstraint;
import it.polimi.statemachine.automata.actions.DisplayModel;
import it.polimi.statemachine.automata.actions.DisplayProperty;
import it.polimi.statemachine.automata.actions.ReadFileProperty;
import it.polimi.statemachine.automata.actions.ReadLTLProperty;
import it.polimi.statemachine.automata.actions.ReadModel;
import it.polimi.statemachine.automata.actions.WriteConstraint;
import it.polimi.statemachine.automata.states.Checked;
import it.polimi.statemachine.automata.states.ConstraintComputed;
import it.polimi.statemachine.automata.states.Init;
import it.polimi.statemachine.automata.states.ModelLoaded;
import it.polimi.statemachine.automata.states.PropertyLoaded;
import it.polimi.statemachine.automata.states.Ready;

import java.io.StringWriter;

import org.junit.Test;

/**
 * @author Claudio1
 *
 */
public class CHIAAutomataStateTest {

	private final ReadModel readModel = new ReadModel(ClassLoader
			.getSystemResource("it/polimi/console/Model.xml").getPath(),
			new StringWriter());
	private final ReadLTLProperty readLTLProperty = new ReadLTLProperty(
			new StringWriter(), "[]a");

	private final ReadFileProperty readFileProperty = new ReadFileProperty(
			new StringWriter(), ClassLoader.getSystemResource(
					"it/polimi/console/Liveness.xml").getPath());

	private final AutomataAction computeConstraint = new ComputeConstraint(
			new StringWriter());

	private final AutomataAction displayProperty = new DisplayProperty(
			new StringWriter());

	private final AutomataAction check = new Check(new StringWriter());

	private final AutomataAction displayConstraint = new WriteConstraint(
			new StringWriter(), ClassLoader.getSystemResource(
					"it/polimi/console/LivenessConstraint.xml").getPath());

	private final AutomataAction displayModel = new DisplayModel(
			new StringWriter());

	private final AutomataAction writeConstraint = new WriteConstraint(
			new StringWriter(), ClassLoader.getSystemResource(
					"it/polimi/console/LivenessConstraint.xml").getPath());

	/**
	 * Test method for
	 * {@link it.polimi.statemachine.states.AutomataState#isPerformable(java.lang.Class)}
	 * .
	 */
	@Test
	public void testIsPerformable_INIT() {
		AutomataState init = new Init();

		assertTrue(init.isPerformable(this.readModel));
		assertTrue(init.isPerformable(this.readLTLProperty));
		assertTrue(init.isPerformable(this.readFileProperty));
		assertTrue(init.isPerformable(this.readFileProperty));
		assertFalse(init.isPerformable(this.check));
	}

	/**
	 * Test method for
	 * {@link it.polimi.statemachine.states.AutomataState#next(java.lang.Class)}
	 * .
	 * 
	 * @throws CHIAException
	 */
	@Test
	public void testPerform_INIT() throws CHIAException {
		AutomataState init = new Init();
		AutomataState modelLoaded = new ModelLoaded();
		assertEquals(modelLoaded, init.next(this.readModel));
		assertEquals(new PropertyLoaded(), init.next(this.readFileProperty));
		assertEquals(new PropertyLoaded(), init.next(this.readLTLProperty));
		assertEquals(new PropertyLoaded(), init.next(this.readLTLProperty));
		assertEquals(new PropertyLoaded(), init.next(this.readFileProperty));
		assertEquals(new PropertyLoaded(), init.next(this.readFileProperty));

	}

	/**
	 * Test method for
	 * {@link it.polimi.statemachine.states.AutomataState#isPerformable(java.lang.Class)}
	 * .
	 * 
	 * @throws CHIAException
	 */
	@Test(expected = CHIAException.class)
	public void testIsPerformable_INIT_CHIAException() throws CHIAException {
		new Init().next(this.computeConstraint);
	}

	/**
	 * Test method for
	 * {@link it.polimi.statemachine.states.AutomataState#isPerformable(java.lang.Class)}
	 * .
	 */
	@Test
	public void testIsPerformable_MODELLOADED() {
		assertTrue(new ModelLoaded().isPerformable(this.readModel));
		assertTrue(new ModelLoaded().isPerformable(this.readFileProperty));
		assertTrue(new ModelLoaded().isPerformable(this.readLTLProperty));
		assertTrue(new ModelLoaded().isPerformable(this.readLTLProperty));
		assertTrue(new ModelLoaded().isPerformable(this.readFileProperty));
		assertTrue(new ModelLoaded().isPerformable(this.readFileProperty));
		assertTrue(new ModelLoaded().isPerformable(this.displayModel));
		assertFalse(new ModelLoaded().isPerformable(this.check));
	}

	/**
	 * Test method for
	 * {@link it.polimi.statemachine.states.AutomataState#next(java.lang.Class)}
	 * .
	 * 
	 * @throws CHIAException
	 */
	@Test
	public void testPerform_MODELLOADED() throws CHIAException {

		assertEquals(new ModelLoaded(), new ModelLoaded().next(this.readModel));
		assertEquals(new Ready(), new ModelLoaded().next(this.readFileProperty));
		assertEquals(new Ready(), new ModelLoaded().next(this.readLTLProperty));
		assertEquals(new Ready(), new ModelLoaded().next(this.readLTLProperty));
		assertEquals(new Ready(), new ModelLoaded().next(this.readFileProperty));
		assertEquals(new Ready(), new ModelLoaded().next(this.readFileProperty));
		assertEquals(new ModelLoaded(),
				new ModelLoaded().next(this.displayModel));
	}

	/**
	 * Test method for
	 * {@link it.polimi.statemachine.states.AutomataState#next(java.lang.Class)}
	 * .
	 * 
	 * @throws CHIAException
	 */
	@Test(expected = CHIAException.class)
	public void testIsPerformable_MODELLOADED_CHIAException()
			throws CHIAException {
		new ModelLoaded().next(this.writeConstraint);
	}

	/**
	 * Test method for
	 * {@link it.polimi.statemachine.states.AutomataState#next(java.lang.Class)}
	 * .
	 * 
	 * @throws CHIAException
	 */
	@Test
	public void testPerform_PROPERTYLOADED() throws CHIAException {

		assertEquals(new Ready(), new PropertyLoaded().next(this.readModel));
		assertEquals(new PropertyLoaded(),
				new PropertyLoaded().next(this.readFileProperty));
		assertEquals(new PropertyLoaded(),
				new PropertyLoaded().next(this.readLTLProperty));
		assertEquals(new PropertyLoaded(),
				new PropertyLoaded().next(this.readLTLProperty));
		assertEquals(new PropertyLoaded(),
				new PropertyLoaded().next(this.readFileProperty));
		assertEquals(new PropertyLoaded(),
				new PropertyLoaded().next(this.displayProperty));

	}

	/**
	 * Test method for
	 * {@link it.polimi.statemachine.states.AutomataState#isPerformable(java.lang.Class)}
	 * .
	 */
	@Test
	public void testIsPerformable_PROPERTYLOADED() {
		assertTrue(new PropertyLoaded().isPerformable(this.readModel));
		assertTrue(new PropertyLoaded().isPerformable(this.readFileProperty));
		assertTrue(new PropertyLoaded().isPerformable(this.readLTLProperty));
		assertTrue(new PropertyLoaded().isPerformable(this.readLTLProperty));
		assertTrue(new PropertyLoaded().isPerformable(this.readFileProperty));
		assertTrue(new PropertyLoaded().isPerformable(this.displayProperty));
		assertFalse(new PropertyLoaded().isPerformable(this.check));
	}

	/**
	 * Test method for
	 * {@link it.polimi.statemachine.states.AutomataState#next(java.lang.Class)}
	 * .
	 * 
	 * @throws CHIAException
	 */
	@Test(expected = CHIAException.class)
	public void testPerform_PROPERTYLOADED_CHIAException() throws CHIAException {
		new PropertyLoaded().next(this.displayConstraint);
	}

	/**
	 * Test method for
	 * {@link it.polimi.statemachine.states.AutomataState#next(java.lang.Class)}
	 * .
	 * 
	 * @throws CHIAException
	 */
	@Test
	public void testPerform_READY() throws CHIAException {

		assertEquals(new Ready(), new PropertyLoaded().next(this.readModel));
		assertEquals(new Ready(), new Ready().next(this.readFileProperty));
		assertEquals(new Ready(), new Ready().next(this.readLTLProperty));
		assertEquals(new Ready(), new Ready().next(this.readLTLProperty));
		assertEquals(new Ready(), new Ready().next(this.readFileProperty));
		assertEquals(new Ready(), new Ready().next(this.readFileProperty));
		assertEquals(new Ready(), new Ready().next(this.displayModel));
		assertEquals(new Ready(), new Ready().next(this.displayProperty));
		assertEquals(new Ready(), new Ready().next(this.readModel));
		assertEquals(new Checked(), new Ready().next(this.check));
		assertEquals(new ConstraintComputed(),
				new Checked().next(this.computeConstraint));

	}

	/**
	 * Test method for
	 * {@link it.polimi.statemachine.states.AutomataState#isPerformable(java.lang.Class)}
	 * .
	 */
	@Test
	public void testIsPerformable_READY() {
		assertTrue(new Ready().isPerformable(this.readModel));
		assertTrue(new Ready().isPerformable(this.readFileProperty));
		assertTrue(new Ready().isPerformable(this.readLTLProperty));
		assertTrue(new Ready().isPerformable(this.readLTLProperty));
		assertTrue(new Ready().isPerformable(this.readFileProperty));
		assertTrue(new Ready().isPerformable(this.check));
		assertTrue(new Ready().isPerformable(this.displayProperty));
		assertTrue(new Ready().isPerformable(this.displayModel));
		assertFalse(new Ready().isPerformable(this.computeConstraint));

	}

	/**
	 * Test method for
	 * {@link it.polimi.statemachine.states.AutomataState#next(java.lang.Class)}
	 * .
	 * 
	 * @throws CHIAException
	 */
	@Test(expected = CHIAException.class)
	public void testPerform_READY_CHIAException() throws CHIAException {
		new Ready().next(this.computeConstraint);
	}

	/**
	 * Test method for
	 * {@link it.polimi.statemachine.states.AutomataState#next(java.lang.Class)}
	 * .
	 * 
	 * @throws CHIAException
	 */
	@Test
	public void testPerform_CHECKED() throws CHIAException {

		assertEquals(new Ready(), new Checked().next(this.readModel));
		assertEquals(new Ready(), new Checked().next(this.readFileProperty));
		assertEquals(new Ready(), new Checked().next(this.readLTLProperty));
		assertEquals(new Ready(), new Checked().next(this.readLTLProperty));
		assertEquals(new Ready(), new Checked().next(this.readFileProperty));
		assertEquals(new Ready(), new Checked().next(this.readFileProperty));
		assertEquals(new Ready(), new Checked().next(this.readModel));
		assertEquals(new Checked(), new Checked().next(this.displayModel));
		assertEquals(new Checked(), new Checked().next(this.displayProperty));
	}

	/**
	 * Test method for
	 * {@link it.polimi.statemachine.states.AutomataState#next(java.lang.Class)}
	 * .
	 * 
	 * @throws CHIAException
	 */
	@Test(expected = CHIAException.class)
	public void testPerform_CHECKED_CHIAException() throws CHIAException {
		new Checked().next(this.displayConstraint);
	}

	/**
	 * Test method for
	 * {@link it.polimi.statemachine.states.AutomataState#isPerformable(java.lang.Class)}
	 * .
	 */
	@Test
	public void testIsPerformable_CHECKED() {
		assertTrue(new Checked().isPerformable(this.readModel));
		assertTrue(new Checked().isPerformable(this.readFileProperty));
		assertTrue(new Checked().isPerformable(this.readLTLProperty));
		assertTrue(new Checked().isPerformable(this.readLTLProperty));
		assertTrue(new Checked().isPerformable(this.readFileProperty));
		assertTrue(new Checked().isPerformable(this.displayProperty));
		assertTrue(new Checked().isPerformable(this.displayModel));
		assertFalse(new Checked().isPerformable(this.check));
		assertTrue(new Checked().isPerformable(this.computeConstraint));
	}

	/**
	 * Test method for
	 * {@link it.polimi.statemachine.states.AutomataState#next(java.lang.Class)}
	 * .
	 * 
	 * @throws CHIAException
	 */
	@Test
	public void testPerform_CONSTRAINTCOMPUTED() throws CHIAException {

		assertEquals(new Ready(),
				new ConstraintComputed().next(this.readFileProperty));
		assertEquals(new Ready(),
				new ConstraintComputed().next(this.readFileProperty));
		assertEquals(new Ready(),
				new ConstraintComputed().next(this.readLTLProperty));
		assertEquals(new Ready(),
				new ConstraintComputed().next(this.readLTLProperty));
		assertEquals(new Ready(),
				new ConstraintComputed().next(this.readFileProperty));
		assertEquals(new Ready(),
				new ConstraintComputed().next(this.readFileProperty));
		assertEquals(new Ready(), new ConstraintComputed().next(this.readModel));
		assertEquals(new ConstraintComputed(),
				new ConstraintComputed().next(this.displayModel));
		assertEquals(new ConstraintComputed(),
				new ConstraintComputed().next(this.displayProperty));
		assertEquals(new ConstraintComputed(),
				new ConstraintComputed().next(this.displayConstraint));
		assertEquals(new ConstraintComputed(),
				new ConstraintComputed().next(this.writeConstraint));
		assertEquals(new ConstraintComputed(),
				new ConstraintComputed().next(this.writeConstraint));
	}

	/**
	 * Test method for
	 * {@link it.polimi.statemachine.states.AutomataState#next(java.lang.Class)}
	 * .
	 * 
	 * @throws CHIAException
	 */
	@Test(expected = CHIAException.class)
	public void testPerform_CONSTRAINTCOMPUTED_CHIAException()
			throws CHIAException {
		new ConstraintComputed().next(this.computeConstraint);
	}

	/**
	 * Test method for
	 * {@link it.polimi.statemachine.states.AutomataState#isPerformable(java.lang.Class)}
	 * .
	 */
	@Test
	public void testIsPerformable_CONSTRAINTCOMPUTED() {
		assertTrue(new ConstraintComputed().isPerformable(this.readModel));
		assertTrue(new ConstraintComputed()
				.isPerformable(this.readFileProperty));
		assertTrue(new ConstraintComputed().isPerformable(this.readLTLProperty));
		assertTrue(new ConstraintComputed().isPerformable(this.readLTLProperty));
		assertTrue(new ConstraintComputed()
				.isPerformable(this.readFileProperty));
		assertTrue(new ConstraintComputed().isPerformable(this.displayProperty));
		assertTrue(new ConstraintComputed().isPerformable(this.displayModel));
		assertTrue(new ConstraintComputed()
				.isPerformable(this.displayConstraint));
		assertTrue(new ConstraintComputed().isPerformable(this.writeConstraint));
		assertFalse(new ConstraintComputed()
				.isPerformable(this.computeConstraint));

	}

}
