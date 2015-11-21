package it.polimi.model.ltltoba;

import it.polimi.automata.BA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.Transition;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import rwth.i2.ltl2ba4j.internal.jnibridge.BAJni;
import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.IState;
import rwth.i2.ltl2ba4j.model.ITransition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;
import action.CHIAAction;

import com.google.common.base.Preconditions;

/**
 * contains the transformer which transforms an LTL formula into the
 * corresponding Buchi automaton
 * 
 * @author Claudio Menghi
 *
 */
public class LTLtoBATransformer extends CHIAAction<BA> {

	/**
	 * The name of the LTLtoBaTransformer action
	 */
	private static final String NAME = "CONVERTING LTL TO AUTOMATON";

	/**
	 * contains the formula to be converted
	 */
	protected final String ltlFormula;

	protected final String syntax = "Propositonal Symbols:\r\n"
			+ "        true, false\r\n" + "        any lowercase string\r\n"
			+ "\r\n" + "Boolean operators:\r\n" + "        !   (negation)\r\n"
			+ "        ->  (implication)\r\n" + "        <-> (equivalence)\r\n"
			+ "        ^  (and)\r\n" + "        V  (or)\r\n" + "\r\n"
			+ "Temporal operators:\r\n" + "        []  (always)\r\n"
			+ "        <>  (eventually)\r\n" + "        U   (until)\r\n"
			+ "        V   (release)\r\n" + "        X   (next)";

	/**
	 * creates the LTL to Buchi automaton transformer
	 * 
	 * @param ltlFormula
	 *            is the formula to be converted
	 * @throws NullPointerException
	 *             if the ltlFormula is null
	 */
	public LTLtoBATransformer(String ltlFormula) {
		super(NAME);
		Preconditions.checkNotNull(ltlFormula,
				"The LTL formula to be converted cannot be null");
		this.ltlFormula = ltlFormula.replace("^", "&&");

	}

	/**
	 * transforms the LTL formula into the corresponding Buchi Automaton
	 * 
	 * @return the BA corresponding to the LTL formula specified as parameter
	 * @throws SecurityException
	 * @throws Exception
	 */
	public BA perform() throws Exception {

		/*
		 * creates a new Buchi automaton
		 */
		BA ba = new BA(new ClaimTransitionFactory());

		try {
			/*
			 * calls the LTL2BA4J that transforms the LTL formula into the
			 * corresponding automaton. The tool returns the transitions of the
			 * Buchi automaton
			 */
			// call ltl2ba over JNI
			BAJni b = new BAJni(ltlFormula);

			Collection<ITransition> transitions = b.getTransitions();
			// Collection<ITransition> transitions = LTL2BA4J
			// .formulaToBA(ltlFormula);
			/*
			 * populates the BA to be returned with the specified set of
			 * transitions
			 */
			this.addTransitionsToTheBA(ba, transitions);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(
					"Your string must be consistent with the following syntax\\"
							+ syntax + "\\" + e.getMessage());
		}

		// returns the Buchi automaton
		return ba;
	}

	/**
	 * populates the Buchi Automaton with the set of transitions specified as
	 * parameter
	 * 
	 * @param ba
	 *            is the Buchi Automaton to be populated
	 * @param transitions
	 *            contains the transitions to be added to the Buchi Automaton
	 * 
	 * @throws NullPointerException
	 *             if the Buchi automaton or the set of transitions is null
	 */
	private void addTransitionsToTheBA(BA ba,
			Collection<ITransition> transitions) {
		Preconditions.checkNotNull(ba,
				"The Buchi automaton to be converted cannot be null");

		Preconditions.checkNotNull(transitions,
				"The set of transitions cannot be null");

		/*
		 * maps each end point (state) of an ITransition to the corresponding
		 * state of the Buchi Automaton
		 */
		Map<IState, State> map = new HashMap<IState, State>();

		/*
		 * analyzes each transition and populates the Buchi Automaton with the
		 * corresponding states. i.e., the end points of the transitions
		 */
		for (ITransition t : transitions) {
			this.analyzeState(t.getSourceState(), map, ba);
			this.analyzeState(t.getTargetState(), map, ba);
		}

		/*
		 * analyzes each transition and populates the corresponding Buchi
		 * Automaton
		 */
		for (ITransition t : transitions) {
			this.analyzeTransition(t, map, ba);
		}
	}

	/**
	 * analyzes the end point state of a transition if the end Point has been
	 * already visited no action is performed, otherwise a new state of the BA
	 * is created and added to the map
	 * 
	 * @param endPoint
	 *            is the endPoint state of a transition
	 * @param map
	 *            is the map which maps each end point state to the
	 *            corresponding state of the Buchi automaton
	 * @param ba
	 *            is the Buchi automaton to be populated
	 * @throws NullPointerException
	 *             if the endPoint, the map or the buchi automaton is null
	 */
	private void analyzeState(IState endPoint, Map<IState, State> map, BA ba) {
		Preconditions.checkNotNull(endPoint,
				"The end point state cannot be null");

		Preconditions.checkNotNull(map, "The map cannot be null");
		Preconditions.checkNotNull(ba, "The Buchi Automaton cannot be null");

		StateFactory stateFactory = new StateFactory();
		if (!map.containsKey(endPoint)) {
			// a new state which correspond to the source state is created
			State s = stateFactory.create();
			// the source state and the state created are added to the map
			map.put(endPoint, s);
			ba.addState(s);
			// if the endPoint is initial
			if (endPoint.isInitial()) {
				/*
				 * the state created is also added to the set of initial states
				 */
				ba.addInitialState(s);
			}
			// if the endPoing is final
			if (endPoint.isFinal()) {
				/*
				 * the state which has been created is also added to the set of
				 * accepting states
				 */
				ba.addAcceptState(s);
			}
		}
	}

	/**
	 * analyzes a transition of the returned automaton and encodes it into a
	 * transition of the CHIA automaton and add the transition to the Buchi
	 * Automaton
	 * 
	 * @param transition
	 *            is the transition to be converted and added to the Buchi
	 *            Automaton
	 * @param map
	 *            is the map which maps each end point state to the
	 *            corresponding state of the Buchi automaton
	 * @param ba
	 *            is the Buchi Automaton to be populated
	 * @throws NullPointerException
	 *             if the transition, the map or the Buchi Automaton is null
	 */
	private void analyzeTransition(ITransition transition,
			Map<IState, State> map, BA ba) {
		Preconditions.checkNotNull(transition,
				"The transition to be added cannot be null");
		Preconditions.checkNotNull(map, "The map cannot be null");
		Preconditions.checkNotNull(ba, "The Buchi automaton cannot be null");

		ClaimTransitionFactory transitionFactory = new ClaimTransitionFactory();
		// returns the source state of the transition
		State source = map.get(transition.getSourceState());

		// returns the destination state of the transition
		State destination = map.get(transition.getTargetState());

		// returns the label of the transition
		Set<IGraphProposition> label = transition.getLabels();

		// creates a new transition
		Transition t = transitionFactory.create(label);

		// adds the label to the current buchi automaton

		for (IGraphProposition p : t.getPropositions()) {
			if (!p.isNegated()) {
				ba.addProposition(p);
			} else {
				ba.addProposition(new GraphProposition(p.getLabel(), false));
			}
		}

		// add the transition from the source state to the destination state
		ba.addTransition(source, destination, t);

	}
}
