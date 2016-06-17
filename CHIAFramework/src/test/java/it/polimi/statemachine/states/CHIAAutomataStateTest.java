/**
 * 
 */
package it.polimi.statemachine.states;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import it.polimi.action.CHIAException;
import it.polimi.statemachine.automata.AutomataState;
import it.polimi.statemachine.automata.actions.Check;
import it.polimi.statemachine.automata.actions.ComputeConstraint;
import it.polimi.statemachine.automata.actions.DisplayConstraint;
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

import org.junit.Test;

/**
 * @author Claudio1
 *
 */
public class CHIAAutomataStateTest {

    /**
     * Test method for
     * {@link it.polimi.statemachine.states.AutomataState#isPerformable(java.lang.Class)}
     * .
     */
    @Test
    public void testIsPerformable_INIT() {
    	AutomataState init=new Init();
    	
        assertTrue(init.isPerformable(new ReadModel(
        		ClassLoader.getSystemResource(
        		"it/polimi/console/Model.xml").getPath(), null)));
        assertTrue(init.isPerformable(new ReadLTLProperty(null, null)));
        assertTrue(init.isPerformable(new ReadLTLProperty(null, null)));
        assertTrue(init
                .isPerformable(new ReadLTLProperty(null, null)));
        assertTrue(init.isPerformable(new ReadFileProperty(null, null)));
        assertTrue(init.isPerformable(new ReadFileProperty(null, null)));
        assertFalse(init.isPerformable(new Check(null)));
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
    	AutomataState init=new Init();
    	AutomataState modelLoaded=new ModelLoaded();
        assertEquals(modelLoaded,
                init.next(new ReadModel(ClassLoader.getSystemResource(
                		"it/polimi/console/Model.xml").getPath(), null)));
        assertEquals(new PropertyLoaded(),
                init.next(new ReadFileProperty(null, null)));
        assertEquals(new PropertyLoaded(),
                init.next(new ReadLTLProperty(null, null)));
        assertEquals(new PropertyLoaded(),
                init.next(new ReadLTLProperty(null, null)));
        assertEquals(new PropertyLoaded(),
                init.next(new ReadFileProperty(null, null)));
        assertEquals(new PropertyLoaded(),
                init.next(new ReadFileProperty(null, null)));

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
        new Init().next(new ComputeConstraint(null));
    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.states.AutomataState#isPerformable(java.lang.Class)}
     * .
     */
    @Test
    public void testIsPerformable_MODELLOADED() {
        assertTrue(new ModelLoaded()
                .isPerformable(new ReadModel(ClassLoader.getSystemResource(
                		"it/polimi/console/Model.xml").getPath(), null)));
        assertTrue(new ModelLoaded()
                .isPerformable(new ReadFileProperty(null, null)));
        assertTrue(new ModelLoaded()
                .isPerformable(new ReadLTLProperty(null, null)));
        assertTrue(new ModelLoaded()
                .isPerformable(new ReadLTLProperty(null, null)));
        assertTrue(new ModelLoaded()
                .isPerformable(new ReadFileProperty(null, null)));
        assertTrue(new ModelLoaded()
                .isPerformable(new ReadFileProperty(null, null)));
        assertTrue(new ModelLoaded()
                .isPerformable(new DisplayModel(null)));
        assertFalse(new ModelLoaded()
        		.isPerformable(new Check(null)));
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

        assertEquals(new ModelLoaded(),
        		new ModelLoaded().next(new ReadModel(ClassLoader.getSystemResource(
                		"it/polimi/console/Model.xml").getPath(), null)));
        assertEquals(new Ready(),
        		new ModelLoaded().next(new ReadFileProperty(null, null)));
        assertEquals(new Ready(),
        		new ModelLoaded().next(new ReadLTLProperty(null, null)));
        assertEquals(new Ready(),
        		new ModelLoaded().next(new ReadLTLProperty(null, null)));
        assertEquals(new Ready(),
        		new ModelLoaded().next(new ReadFileProperty(null, null)));
        assertEquals(new Ready(),
        		new ModelLoaded().next(new ReadFileProperty(null, null)));
        assertEquals(new ModelLoaded(),
        		new ModelLoaded()
                        .next(new DisplayModel(null)));
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
    	new ModelLoaded().next(new WriteConstraint(null, null));
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

        assertEquals(new Ready(),
                new PropertyLoaded().next(new ReadModel(ClassLoader.getSystemResource(
                		"it/polimi/console/Model.xml").getPath(), null)));
        assertEquals(new PropertyLoaded(),
                new PropertyLoaded().next(new ReadFileProperty(null, null)));
        assertEquals(new PropertyLoaded(),
                new PropertyLoaded().next(new ReadLTLProperty(null, null)));
        assertEquals(new PropertyLoaded(),
                new PropertyLoaded()
                        .next(new ReadLTLProperty(null, null)));
        assertEquals(new PropertyLoaded(),
                new PropertyLoaded().next(new ReadFileProperty(null, null)));
        assertEquals(new PropertyLoaded(),
                new PropertyLoaded()
                        .next(new DisplayProperty(null)));

    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.states.AutomataState#isPerformable(java.lang.Class)}
     * .
     */
    @Test
    public void testIsPerformable_PROPERTYLOADED() {
        assertTrue(new PropertyLoaded()
                .isPerformable(new ReadModel(ClassLoader.getSystemResource(
                		"it/polimi/console/Model.xml").getPath(), null)));
        assertTrue(new PropertyLoaded()
                .isPerformable(new ReadFileProperty(null, null)));
        assertTrue(new PropertyLoaded()
                .isPerformable(new ReadLTLProperty(null, null)));
        assertTrue(new PropertyLoaded()
                .isPerformable(new ReadLTLProperty(null, null)));
        assertTrue(new PropertyLoaded()
                .isPerformable(new ReadFileProperty(null, null)));
        assertTrue(new PropertyLoaded()
                .isPerformable(new DisplayProperty(null)));
        assertFalse(new PropertyLoaded()
                .isPerformable(new Check(null)));
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
        new PropertyLoaded().next(new WriteConstraint(null, null));
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

    	assertEquals(new Ready(),
                new PropertyLoaded().next(new ReadModel(ClassLoader.getSystemResource(
                		"it/polimi/console/Model.xml").getPath(), null)));
        assertEquals(new Ready(),
                new Ready().next(new ReadFileProperty(null, null)));
        assertEquals(new Ready(),
                new Ready().next(new ReadLTLProperty(null, null)));
        assertEquals(new Ready(),
                new Ready().next(new ReadLTLProperty(null, null)));
        assertEquals(new Ready(),
                new Ready().next(new ReadFileProperty(null, null)));
        assertEquals(new Ready(),
                new Ready().next(new ReadFileProperty(null, null)));
        assertEquals(new Ready(),
                new Ready().next(new DisplayModel(null)));
        assertEquals(new Ready(),
                new Ready().next(new DisplayProperty(null)));
        assertEquals(new Ready(),
                new Ready().next(new ReadModel(ClassLoader.getSystemResource(
                		"it/polimi/console/Model.xml").getPath(), null)));
        assertEquals(new Checked(),
                new Ready().next(new Check(null)));
        assertEquals(new ConstraintComputed(),
                new Checked().next(new ComputeConstraint(null)));
       
     }

    /**
     * Test method for
     * {@link it.polimi.statemachine.states.AutomataState#isPerformable(java.lang.Class)}
     * .
     */
    @Test
    public void testIsPerformable_READY() {
        assertTrue(new Ready().isPerformable(new ReadModel(ClassLoader.getSystemResource(
                		"it/polimi/console/Model.xml").getPath(), null)));
        assertTrue(new Ready().isPerformable(new ReadFileProperty(null, null)));
        assertTrue(new Ready().isPerformable(new ReadLTLProperty(null, null)));
        assertTrue(new Ready()
                .isPerformable(new ReadLTLProperty(null, null)));
        assertTrue(new Ready().isPerformable(new ReadFileProperty(null, null)));
        assertTrue(new Ready().isPerformable(new Check(null)));
        assertTrue(new Ready()
                .isPerformable(new DisplayProperty(null)));
        assertTrue(new Ready()
                .isPerformable(new DisplayModel(null)));
        assertFalse(new Ready()
                .isPerformable(new ComputeConstraint(null)));

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

        new Ready().next(new ComputeConstraint(null));
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

        assertEquals(new Ready(),
                new Checked().next(new ReadModel(ClassLoader.getSystemResource(
                		"it/polimi/console/Model.xml").getPath(), null)));
        assertEquals(new Ready(),
                new Checked().next(new ReadFileProperty(null, null)));
        assertEquals(new Ready(),
                new Checked().next(new ReadLTLProperty(null, null)));
        assertEquals(new Ready(),
                new Checked().next(new ReadLTLProperty(null, null)));
        assertEquals(new Ready(),
                new Checked().next(new ReadFileProperty(null, null)));
        assertEquals(new Ready(),
                new Checked().next(new ReadFileProperty(null, null)));
        assertEquals(new Ready(),
                new Checked().next(new ReadModel(ClassLoader.getSystemResource(
                		"it/polimi/console/Model.xml").getPath(), null)));
        assertEquals(new Checked(),
                new Checked()
                        .next(new DisplayModel(null)));
        assertEquals(new Checked(),
                new Checked()
                        .next(new DisplayProperty(null)));
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
        new Checked().next(new DisplayConstraint(null));
    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.states.AutomataState#isPerformable(java.lang.Class)}
     * .
     */
    @Test
    public void testIsPerformable_CHECKED() {
        assertTrue(new Checked().isPerformable(new ReadModel(ClassLoader.getSystemResource(
                		"it/polimi/console/Model.xml").getPath(), null)));
        assertTrue(new Checked().isPerformable(new ReadFileProperty(null, null)));
        assertTrue(new Checked()
                .isPerformable(new ReadLTLProperty(null, null)));
        assertTrue(new Checked()
                .isPerformable(new ReadLTLProperty(null, null)));
        assertTrue(new Checked().isPerformable(new ReadFileProperty(null, null)));
        assertTrue(new Checked()
                .isPerformable(new DisplayProperty(null)));
        assertTrue(new Checked()
                .isPerformable(new DisplayModel(null)));
        assertFalse(new Checked().isPerformable(new Check(null)));

        assertTrue(new Checked().isPerformable(new ComputeConstraint(null)));

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
                new ConstraintComputed().next(new ReadModel(ClassLoader.getSystemResource(
                		"it/polimi/console/Model.xml").getPath(), null)));
        assertEquals(new Ready(),
                new ConstraintComputed().next(new ReadFileProperty(null, null)));
        assertEquals(new Ready(),
                new ConstraintComputed()
                        .next(new ReadLTLProperty(null, null)));
        assertEquals(new Ready(),
                new ConstraintComputed()
                        .next(new ReadLTLProperty(null, null)));
        assertEquals(new Ready(),
                new ConstraintComputed().next(new ReadFileProperty(null, null)));
        assertEquals(new Ready(),
                new ConstraintComputed().next(new ReadFileProperty(null, null)));
        assertEquals(new Ready(),
                new ConstraintComputed().next(new ReadModel(ClassLoader.getSystemResource(
                		"it/polimi/console/Model.xml").getPath(), null)));
        assertEquals(new ConstraintComputed(),
                new ConstraintComputed()
                        .next(new DisplayModel(null)));
        assertEquals(new ConstraintComputed(),
                new ConstraintComputed()
                        .next(new DisplayProperty(null)));
        assertEquals(new ConstraintComputed(),
                new ConstraintComputed()
                        .next(new DisplayConstraint(null)));
        assertEquals(new ConstraintComputed(),
                new ConstraintComputed()
                        .next(new WriteConstraint(null, null)));
        assertEquals(new ConstraintComputed(),
                new ConstraintComputed().next(new WriteConstraint(null, null)));
    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.states.AutomataState#next(java.lang.Class)}
     * .
     * 
     * @throws CHIAException
     */
    @Test(expected=CHIAException.class)
    public void testPerform_CONSTRAINTCOMPUTED_CHIAException() throws CHIAException{
        new ConstraintComputed().next(new ComputeConstraint(null));
    }
    /**
     * Test method for
     * {@link it.polimi.statemachine.states.AutomataState#isPerformable(java.lang.Class)}
     * .
     */
    @Test
    public void testIsPerformable_CONSTRAINTCOMPUTED() {
        assertTrue(new ConstraintComputed()
                .isPerformable(new ReadModel(ClassLoader.getSystemResource(
                		"it/polimi/console/Model.xml").getPath(), null)));
        assertTrue(new ConstraintComputed()
                .isPerformable(new ReadFileProperty(null, null)));
        assertTrue(new ConstraintComputed()
                .isPerformable(new ReadLTLProperty(null, null)));
        assertTrue(new ConstraintComputed()
                .isPerformable(new ReadLTLProperty(null, null)));
        assertTrue(new ConstraintComputed()
                .isPerformable(new ReadFileProperty(null, null)));
        assertTrue(new ConstraintComputed()
                .isPerformable(new DisplayProperty(null)));
        assertTrue(new ConstraintComputed()
                .isPerformable(new DisplayModel(null)));
        assertTrue(new ConstraintComputed()
                .isPerformable(new DisplayConstraint(null)));
        assertTrue(new ConstraintComputed()
                .isPerformable(new WriteConstraint(null, null)));
        

        assertFalse(new ConstraintComputed()
                .isPerformable(new ComputeConstraint(null)));

    }

}
