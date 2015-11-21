/**
 * 
 */
package it.polimi.automata.transition;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

/**
 * @author claudiomenghi
 * 
 */
public class ClaimTransitionFactoryTest {

	private ClaimTransitionFactory claim;
	private Set<IGraphProposition> labels;

	@Before
	public void setUp() {
		this.claim = new ClaimTransitionFactory();
		this.labels = new HashSet<IGraphProposition>();
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.transition.ClaimTransitionFactory#create()}
	 * .
	 */
	@Test
	public void testCreate() {
		assertNotNull(this.claim.create());
		assertTrue(this.claim.create().getId() >= 0);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.transition.ClaimTransitionFactory#create(java.util.Set)}
	 * .
	 */
	@Test
	public void testCreateSetOfLABEL() {
		Transition t = this.claim.create(labels);
		assertNotNull(t);
		assertTrue(t.getId() >= 0);
		assertTrue(t.getPropositions() != labels);
		assertTrue(t.getPropositions().equals(labels));
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.transition.ClaimTransitionFactory#createFromLabel(null)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testCreateSetOfNull() {
		this.claim.create(null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.transition.ClaimTransitionFactory#create(int, java.util.Set)}
	 * .
	 * 
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	@Test
	public void testCreateIntSetOfLABEL() throws NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = Transition.class
				.getDeclaredField("transition_counter");
		field.setAccessible(true);
		field.set(claim, 0);
		Transition t = this.claim.create(5, labels);
		assertNotNull(t);
		assertTrue(t.getId() == 5);
		assertTrue(t.getPropositions() != labels);
		assertTrue(t.getPropositions().equals(labels));
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.transition.ClaimTransitionFactory#create(int, null)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testCreateIntSetOfLABELNull() {
		this.claim.create(1, null);
	}

	/**
	 * Test method for {@link
	 * it.polimi.automata.transition.impl.ClaimTransitionFactoryImpl#create(-1,
	 * java.util.Set)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCreateIntSetOfLABELNegativeNumber() {
		this.claim.create(-1, new HashSet<IGraphProposition>());
	}

}
