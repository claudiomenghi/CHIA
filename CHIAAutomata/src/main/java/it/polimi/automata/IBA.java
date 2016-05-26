package it.polimi.automata;

import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

import com.google.common.base.Preconditions;

/**
 * <p>
 * The \texttt{IBA} class contains the class which describes an Incomplete Buchi
 * Automaton. The \texttt{IBA} class extends \texttt{BA} by storing the set of
 * the \emph{black box} states. <br>
 * 
 * @author claudiomenghi
 */
public class IBA extends BA {

	/**
	 * contains the set of the black box states of the automaton
	 */
	private Set<State> blackBoxesStates;

	/**
	 * creates a new incomplete Buchi automaton
	 * 
	 * @param transitionFactory
	 *            the factory to be used in the creation of the transitions
	 */
	public IBA(@NotNull TransitionFactory<State, Transition> transitionFactory) {
		super(transitionFactory);
		this.blackBoxesStates = new HashSet<>();
	}

	/**
	 * check if the state is a black box of the automaton
	 * 
	 * @param s
	 *            is the state to be checked if it is a black box of the
	 *            automaton
	 * @return true if the state s is a black box of the automaton, false
	 *         otherwise
	 * @throws NullPointerException
	 *             if the state s is null
	 * @throws IllegalArgumentException
	 *             if the state is not contained into the set of the states of
	 *             the automaton
	 */
	public boolean isBlackBox(@NotNull State s) {
		Preconditions.checkNotNull(s, "The state to be added cannot be null");
		Preconditions
				.checkArgument(this.getStates().contains(s),
						"The state is not contained into the set of the states of the IBA");

		return this.blackBoxesStates.contains(s);
	}

	/**
	 * returns the set of the black box states of the Incomplete Buchi Automaton
	 * 
	 * @return the set of the black box states of the Incomplete Buchi Automaton
	 *         (if no black box states are present an empty set is returned)
	 */
	public Set<State> getBlackBoxStates() {
		return Collections.unmodifiableSet(this.blackBoxesStates);
	}

	/**
	 * returns the set of the regular states of the Incomplete Buchi Automaton
	 * 
	 * @return the set of the regular states of the Incomplete Buchi Automaton
	 */
	public Set<State> getRegularStates() {
		Set<State> states = new HashSet<>();
		states.addAll(this.getStates());
		states.removeAll(this.getBlackBoxStates());
		return states;
	}

	/**
	 * adds the black box state s to the states of the {@link IBA} and to the
	 * set of the black box state<br>
	 * if the state is already a black box no action is performed <br>
	 * if the state is a state of the BA but is not a black box, it is also
	 * added to the set of the black box states
	 * 
	 * @param s
	 *            the state to be added in the black box states of the
	 *            {@link IBA}
	 * @throws NullPointerException
	 *             if the state s is null
	 */
	public void addBlackBoxState(State s) {
		Preconditions.checkNotNull(s, "The state to be added cannot be null");

		this.blackBoxesStates.add(s);
		if (!this.getStates().contains(s)) {
			this.addState(s);
		}
	}

	/**
	 * creates a copy of the Incomplete Buchi Automaton
	 * 
	 * @return a copy of the Incomplete Buchi Automaton
	 */
	@Override
	public IBA clone() {
		IBA clone = new IBA(
				(TransitionFactory<State, Transition>) this.automataGraph
						.getEdgeFactory());
		for (IGraphProposition l : this.getPropositions()) {
			clone.addProposition(l);
		}
		for (State s : this.getStates()) {
			clone.addState(s);
		}
		for (State s : this.getAcceptStates()) {
			clone.addAcceptState(s);
		}
		for (State s : this.getInitialStates()) {
			clone.addInitialState(s);
		}
		for (State s : this.getBlackBoxStates()) {
			clone.addBlackBoxState(s);
		}
		for (Transition t : this.getTransitions()) {
			clone.addTransition(this.getTransitionSource(t),
					this.getTransitionDestination(t), t);
		}

		return clone;
	}

	/**
	 * {@inheritDoc} removes the state from the IBA. If the state is also
	 * contained in the set of black box state it is removed from this set
	 * 
	 * @param state
	 *            the state to be removed from the IBA
	 * @throws NullPointerException
	 *             if the state to be removed is null
	 */
	@Override
	public void removeState(State state) {
		super.removeState(state);
		if (this.blackBoxesStates.contains(state)) {
			this.blackBoxesStates.remove(state);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		
		StringBuilder stringBuilder=new StringBuilder();
		String ret = "";

		stringBuilder.append("ALPHABET: " + this.getPropositions() + "\n");
		stringBuilder.append("STATES: " + this.automataGraph.vertexSet() + "\n");
		stringBuilder.append("INITIAL STATES: " + this.getInitialStates() + "\n");
		stringBuilder.append("ACCEPTING STATES: " + this.getAcceptStates() + "\n");
		stringBuilder.append("BLACK BOX STATES: " + this.getBlackBoxStates() + "\n");
		stringBuilder.append("TRANSITIONS\n");
		for (State s : this.automataGraph.vertexSet()) {
			stringBuilder.append("state " + s + " ->\n");
			for (Transition outEdge : this.automataGraph.outgoingEdgesOf(s)) {
				stringBuilder.append("\t \t" + outEdge + "\t"
						+ this.getTransitionDestination(outEdge));
			}
			stringBuilder.append("\n");
		}
		return ret;
	}
}
