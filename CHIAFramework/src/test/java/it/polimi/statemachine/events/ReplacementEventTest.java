/**
 * 
 */
package it.polimi.statemachine.events;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import it.polimi.console.CHIAReplacementConsole;
import it.polimi.statemachine.replacement.action.helper.CheckReplacementHelper;
import it.polimi.statemachine.replacement.action.helper.DisplayConstraintHelper;
import it.polimi.statemachine.replacement.action.helper.DisplayReplacementHelper;
import it.polimi.statemachine.replacement.action.helper.HelpHelper;
import it.polimi.statemachine.replacement.action.helper.LoadConstraintHelper;
import it.polimi.statemachine.replacement.action.helper.LoadReplacementHelper;
import it.polimi.statemachine.replacement.action.helper.SaveRefinementHelper;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Claudio Menghi
 *
 */
public class ReplacementEventTest {

    /**
     * Test method for
     * {@link it.polimi.statemachine.replacement.action.ReplacementEvent#parse(java.lang.String, it.polimi.console.CHIAReplacementConsole)}
     * .
     */
    @Ignore
    @Test
    public void testParse() {
        fail("Not yet implemented");
    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.replacement.action.ReplacementEvent#getShortCuts()}.
     */
    @Test
    public void testGetShortCuts() {
        assertEquals("ck", new CheckReplacementHelper().getCommand());
        assertEquals("dispc", new DisplayConstraintHelper().getCommand());
        assertEquals("dispr", new DisplayReplacementHelper().getCommand());
        assertEquals("help", new HelpHelper().getCommand());
        assertEquals("lc", new LoadConstraintHelper().getCommand());
        assertEquals("lr", new LoadReplacementHelper().getCommand());
        assertEquals("sref", new SaveRefinementHelper().getCommand());
    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.replacement.action.ReplacementEvent#getShortCuts()}.
     */
    @Test
    public void testGetCommandMeaning() {
        assertNotNull(new CheckReplacementHelper().getCommandMeaning());
        assertNotNull(new DisplayConstraintHelper().getCommandMeaning());
        assertNotNull(new DisplayReplacementHelper().getCommandMeaning());
        assertNotNull(new HelpHelper().getCommandMeaning());
        assertNotNull(new LoadConstraintHelper().getCommandMeaning());
        assertNotNull(new LoadReplacementHelper().getCommandMeaning());
        assertNotNull(new SaveRefinementHelper().getCommandMeaning());
    }

    

    /**
     * Test method for
     * {@link it.polimi.statemachine.replacement.action.ReplacementEvent#toString()}.
     */
    @Test
    public void testToString() {
        assertNotNull(new CheckReplacementHelper().toString());
        assertNotNull(new DisplayConstraintHelper().toString());
        assertNotNull(new DisplayReplacementHelper().toString());
        assertNotNull(new HelpHelper().toString());
        assertNotNull(new LoadConstraintHelper().toString());
        assertNotNull(new LoadReplacementHelper().toString());
        assertNotNull(new SaveRefinementHelper().toString());
    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.replacement.action.ReplacementEvent#getShortCuts()}.
     */
    @Test
    public void testRequireParams() {

        assertFalse(new CheckReplacementHelper().requiresParams());
        assertFalse(new DisplayConstraintHelper().requiresParams());
        assertFalse(new DisplayReplacementHelper().requiresParams());
        assertTrue(new HelpHelper().requiresParams());
        assertTrue(new LoadConstraintHelper().requiresParams());
        assertTrue(new LoadReplacementHelper().requiresParams());
        assertTrue(new SaveRefinementHelper().requiresParams());

    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.automata.actions.AutomataEvent#computeCompleters()}.
     */
    @Test
    public void testComputeCompleters() {
        assertNotNull(new CHIAReplacementConsole().getCompleter().computeCompleters());
    }

    /**
    * Test method for
    * {@link it.polimi.statemachine.automata.actions.AutomataEvent#getCommands()}.
    */
   @Test
   public void testGetCommands() {
       assertTrue(new CHIAReplacementConsole().getCompleter().getCommands().contains(
               new CheckReplacementHelper().getCommand()));
       assertTrue(new CHIAReplacementConsole().getCompleter().getCommands().contains(
               new DisplayConstraintHelper().getCommand()));
       assertTrue(new CHIAReplacementConsole().getCompleter().getCommands().contains(
               new DisplayReplacementHelper().getCommand()));
       assertTrue(new CHIAReplacementConsole().getCompleter().getCommands().contains(
               new HelpHelper().getCommand()));
       assertTrue(new CHIAReplacementConsole().getCompleter().getCommands().contains(
               new LoadConstraintHelper().getCommand()));
       assertTrue(new CHIAReplacementConsole().getCompleter().getCommands().contains(
               new LoadReplacementHelper().getCommand()));
       assertTrue(new CHIAReplacementConsole().getCompleter().getCommands().contains(
               new SaveRefinementHelper().getCommand()));
      
   }
   /**
    * Test method for
    * {@link it.polimi.statemachine.replacement.action.ReplacementEvent#toString()}.
    */
   @Test
   public void testGetDescription() {
       assertNotNull(new CheckReplacementHelper().getDescription());
       assertNotNull(new DisplayConstraintHelper().getDescription());
       assertNotNull(new DisplayReplacementHelper().getDescription());
       assertNotNull(new HelpHelper().getDescription());
       assertNotNull(new LoadConstraintHelper().getDescription());
       assertNotNull(new LoadReplacementHelper().getDescription());
       assertNotNull(new SaveRefinementHelper().getDescription());
   }
}
