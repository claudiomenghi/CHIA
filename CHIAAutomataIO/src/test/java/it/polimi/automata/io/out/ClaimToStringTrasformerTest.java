/**
 * 
 */
package it.polimi.automata.io.out;

import static org.junit.Assert.*;
import it.polimi.automata.BA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ClaimTransitionFactory;

import org.junit.Test;

import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

/**
 * @author Claudio1
 *
 */
public class ClaimToStringTrasformerTest {

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.ClaimToStringTrasformer#ClaimToStringTrasformer(it.polimi.automata.BA)}
	 * .
	 */
	@Test
	public void testClaimToStringTrasformer() {
		assertNotNull("The constructor returns a not null object",
				new ClaimToStringTrasformer(
						new BA(new ClaimTransitionFactory())));
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.ClaimToStringTrasformer#ClaimToStringTrasformer(it.polimi.automata.BA)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testClaimToStringTrasformerNull() {
		new ClaimToStringTrasformer(null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.ClaimToStringTrasformer#perform()}.
	 * @throws Exception 
	 */
	@Test
	public void testPerform() throws Exception {
		
		BA ba=new BA(new ClaimTransitionFactory());
		ba.addProposition(new GraphProposition("label", false));
		State state1=new StateFactory().create("state1", 1);
		State state2=new StateFactory().create("state2", 2);
		State state3=new StateFactory().create("state3", 3);
		
		
		ba.addInitialState(state1);
		ba.addState(state2);
		ba.addAcceptState(state3);
		ba.addTransition(state1, state2, new ClaimTransitionFactory().create());
		assertNotNull(new ClaimToStringTrasformer(ba).perform());
	}

}
