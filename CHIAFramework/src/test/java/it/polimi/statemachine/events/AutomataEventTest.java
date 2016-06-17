/**
 * 
 */
package it.polimi.statemachine.events;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.polimi.console.CHIAAutomataConsole;
import it.polimi.statemachine.automata.actions.helper.CheckHelper;
import it.polimi.statemachine.automata.actions.helper.ComputeConstraintHelper;
import it.polimi.statemachine.automata.actions.helper.DisplayConstraintHelper;
import it.polimi.statemachine.automata.actions.helper.DisplayModelHelper;
import it.polimi.statemachine.automata.actions.helper.DisplayPropertyHelper;
import it.polimi.statemachine.automata.actions.helper.ExitHelper;
import it.polimi.statemachine.automata.actions.helper.HelpHelper;
import it.polimi.statemachine.automata.actions.helper.ReadFilePropertyHelper;
import it.polimi.statemachine.automata.actions.helper.ReadLTLPropertyHelper;
import it.polimi.statemachine.automata.actions.helper.ReadModelHelper;
import it.polimi.statemachine.automata.actions.helper.WriteConstraintHelper;

import org.junit.Test;

/**
 * @author Claudio Menghi
 *
 */
public class AutomataEventTest {

    /**
     * Test method for
     * {@link it.polimi.statemachine.automata.actions.new CHIAAutomataConsole().getCompleter()#getShortCuts()}.
     */
    @Test
    public void testGetShortCuts() {

    	assertEquals("ck", new CheckHelper().getCommand());
        assertEquals("cc", new ComputeConstraintHelper().getCommand());
        assertEquals("dispc", new DisplayConstraintHelper().getCommand());
        assertEquals("dispm", new DisplayModelHelper().getCommand());
        assertEquals("dispp", new DisplayPropertyHelper().getCommand());
        assertEquals("exit", new ExitHelper().getCommand());
        assertEquals("help", new HelpHelper().getCommand());
        
        assertEquals("lpLTL", new ReadLTLPropertyHelper().getCommand());
        assertEquals("lm", new ReadModelHelper().getCommand());
        assertEquals("lp", new ReadFilePropertyHelper().getCommand());
        assertEquals("sc", new WriteConstraintHelper().getCommand());
        

    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.automata.actions.new CHIAAutomataConsole().getCompleter()#getShortCuts()}.
     */
    @Test
    public void testGetCommandMeaning() {

        assertEquals("check", new CheckHelper().getCommandMeaning());
        assertEquals("computeConstraint",
                new ComputeConstraintHelper().getCommandMeaning());
        assertEquals("displayConstraint",
                new DisplayConstraintHelper().getCommandMeaning());
        assertEquals("displayModel",
                new DisplayModelHelper().getCommandMeaning());
        assertEquals("displayProperty",
                new DisplayPropertyHelper().getCommandMeaning());
        assertEquals("exit", new ExitHelper().getCommandMeaning());
        assertEquals("help", new HelpHelper().getCommandMeaning());
        assertEquals("loadLTLProperty",
                new ReadLTLPropertyHelper().getCommandMeaning());
        assertEquals("loadModel", new ReadModelHelper().getCommandMeaning());
        assertEquals("loadProperty",
                new ReadFilePropertyHelper().getCommandMeaning());
        assertEquals("saveConstraint",
                new WriteConstraintHelper().getCommandMeaning());

    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.automata.actions.new CHIAAutomataConsole().getCompleter()#getShortCuts()}.
     */
    @Test
    public void testGetParams() {

        assertTrue(null == new CheckHelper().getParams());
        assertTrue(null == new ComputeConstraintHelper().getParams());
        assertTrue(null == new DisplayConstraintHelper().getParams());
        assertTrue(null == new DisplayModelHelper().getParams());
        assertTrue(null == new DisplayPropertyHelper().getParams());
        assertTrue(null == new ExitHelper().getParams());
        assertEquals("command: is the command to be performed",
                new HelpHelper().getParams());
        assertEquals(
                "LTLFormula: is the LTL formula that represents the property."
                        + "The LTL formula can be created starting from a set of propositional symbol \n"
                        + "true, false any lowercase string"
                        + "A set of boolean operators,\n "
                        + "\t ! (negation)\n" + "\t -> (implication) \n"
                        + "\t <-> (equivalence) \n" + "\t ^ (and)\n"
                        + "\t V (or)," + "A set of temporal operators,\n"
                        + "\t [] (always)\n" + "\t <> (eventually)"
                        + "\t U (until)" + "\t X (next).",
                new ReadLTLPropertyHelper().getParams());
        assertEquals(
                "modelFilePath:  the path of the file that contains the model to be checked",
                new ReadModelHelper().getParams());
        assertEquals("file", new ReadFilePropertyHelper().getParams());
        assertEquals(
                "constraintFilePath: is the path of the file where the constraint must be saved",
                new WriteConstraintHelper().getParams());

    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.automata.actions.new CHIAAutomataConsole().getCompleter()#getShortCuts()}.
     */
    @Test
    public void testRequireParams() {

        assertFalse(new CheckHelper().requiresParams());
        assertFalse(new ComputeConstraintHelper().requiresParams());
        assertFalse(new DisplayConstraintHelper().requiresParams());
        assertFalse(new DisplayModelHelper().requiresParams());
        assertFalse(new DisplayPropertyHelper().requiresParams());
        assertFalse(new ExitHelper().requiresParams());
        assertFalse(new HelpHelper().requiresParams());
        assertTrue(new ReadLTLPropertyHelper().requiresParams());
        assertTrue(new ReadModelHelper().requiresParams());
        assertTrue(new ReadFilePropertyHelper().requiresParams());
        assertTrue(new WriteConstraintHelper().requiresParams());

    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.automata.actions.new CHIAAutomataConsole().getCompleter()#getShortCuts()}.
     */
    @Test
    public void testGetDescription() {

        assertNotNull(new CheckHelper().getDescription());
        assertNotNull(new ComputeConstraintHelper().getDescription());
        assertNotNull(new DisplayConstraintHelper().getDescription());
        assertNotNull(new DisplayModelHelper().getDescription());
        assertNotNull(new DisplayPropertyHelper().getDescription());
        assertNotNull(new ExitHelper().getDescription());
        assertNotNull(new HelpHelper().getDescription());
        assertNotNull(new ReadLTLPropertyHelper().getDescription());
        assertNotNull(new ReadModelHelper().getDescription());
        assertNotNull(new ReadFilePropertyHelper().getDescription());
        assertNotNull(new WriteConstraintHelper().getDescription());

    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.automata.actions.new CHIAAutomataConsole().getCompleter()#getCommands()}.
     */
    @Test
    public void testGetCommands() {
        assertTrue(new CHIAAutomataConsole().getCompleter().getCommands().contains(
                new CheckHelper().getCommand()));
        assertTrue(new CHIAAutomataConsole().getCompleter().getCommands().contains(
                new ComputeConstraintHelper().getCommand()));
        assertTrue(new CHIAAutomataConsole().getCompleter().getCommands().contains(
                new DisplayConstraintHelper().getCommand()));
        assertTrue(new CHIAAutomataConsole().getCompleter().getCommands().contains(
                new DisplayModelHelper().getCommand()));
        assertTrue(new CHIAAutomataConsole().getCompleter().getCommands().contains(
                new DisplayPropertyHelper().getCommand()));
        assertTrue(new CHIAAutomataConsole().getCompleter().getCommands().contains(
                new ExitHelper().getCommand()));
        assertTrue(new CHIAAutomataConsole().getCompleter().getCommands().contains(
                new HelpHelper().getCommand()));
        assertTrue(new CHIAAutomataConsole().getCompleter().getCommands().contains(
                new ReadLTLPropertyHelper().getCommand()));
        assertTrue(new CHIAAutomataConsole().getCompleter().getCommands().contains(
                new ReadModelHelper().getCommand()));
        assertTrue(new CHIAAutomataConsole().getCompleter().getCommands().contains(
                new ReadFilePropertyHelper().getCommand()));
        assertTrue(new CHIAAutomataConsole().getCompleter().getCommands().contains(
                new WriteConstraintHelper().getCommand()));
    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.automata.actions.new CHIAAutomataConsole().getCompleter()#getCommands()}.
     */
    @Test
    public void testGetShortCuts_Test() {
        assertTrue(new CHIAAutomataConsole().getCompleter().getShortCuts().contains(
                new CheckHelper().getCommand()));
        assertTrue(new CHIAAutomataConsole().getCompleter().getShortCuts().contains(
                new ComputeConstraintHelper().getCommand()));
        assertTrue(new CHIAAutomataConsole().getCompleter().getShortCuts().contains(
                new DisplayConstraintHelper().getCommand()));
        assertTrue(new CHIAAutomataConsole().getCompleter().getShortCuts().contains(
                new DisplayModelHelper().getCommand()));
        assertTrue(new CHIAAutomataConsole().getCompleter().getShortCuts().contains(
                new DisplayPropertyHelper().getCommand()));
        assertTrue(new CHIAAutomataConsole().getCompleter().getShortCuts().contains(
                new ExitHelper().getCommand()));
        assertTrue(new CHIAAutomataConsole().getCompleter().getShortCuts().contains(
                new HelpHelper().getCommand()));
        assertTrue(new CHIAAutomataConsole().getCompleter().getShortCuts().contains(
                new ReadLTLPropertyHelper().getCommand()));
        assertTrue(new CHIAAutomataConsole().getCompleter().getShortCuts().contains(
                new ReadModelHelper().getCommand()));
        assertTrue(new CHIAAutomataConsole().getCompleter().getShortCuts().contains(
                new ReadFilePropertyHelper().getCommand()));
        assertTrue(new CHIAAutomataConsole().getCompleter().getShortCuts().contains(
                new WriteConstraintHelper().getCommand()));
    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.automata.actions.new CHIAAutomataConsole().getCompleter()#getCommands()}.
     */
    @Test
    public void testToString() {
        assertNotNull(new CheckHelper().toString());
        assertNotNull(new ComputeConstraintHelper().toString());
        assertNotNull(new DisplayConstraintHelper().toString());
        assertNotNull(new DisplayModelHelper().toString());
        assertNotNull(new DisplayPropertyHelper().toString());
        assertNotNull(new ExitHelper().toString());
        assertNotNull(new HelpHelper().toString());
        assertNotNull(new ReadLTLPropertyHelper().toString());
        assertNotNull(new ReadModelHelper().toString());
        assertNotNull(new ReadFilePropertyHelper().toString());
        assertNotNull(new WriteConstraintHelper().toString());
    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.automata.actions.new CHIAAutomataConsole().getCompleter()#computeCompleters()}.
     */
    @Test
    public void testComputeCompleters() {
        assertNotNull(new CHIAAutomataConsole().getCompleter().computeCompleters());
    }

}
