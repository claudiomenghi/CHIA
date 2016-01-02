package it.polimi.chia.scalability.randomGenerators;

import it.polimi.action.CHIAAction;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ModelTransitionFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.transitions.PluggingTransition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;

/**
 * In the experiments, both the IBAs and the replacements of the transparent
 * states are extracted from the randomly generated BAs. The idea is to randomly
 * encapsulate parts of the BAs into transparent states. The encapsulated
 * automata and the corresponding incoming and outgoing transitions are the
 * replacements associated with the transparent states, while the IBA is the
 * automaton obtained by replacing the encapsulated parts with the corresponding
 * transparent states. The transparent state density ($t=|T|/|Q|$) specifies the
 * number of transparent states which must be inserted into the BA. The
 * replacement density ($r=(\sum_{t \in T}|Q_t|)/|Q|$) specifies the number of
 * states of the automaton to be injected inside the replacement of each
 * transparent state.
 * 
 * @author Claudio Menghi
 *
 */
public class IBARandomGenerator extends CHIAAction<IBA> {

	/**
	 * contains the number of transparent states to be used in the IBA
	 * computation
	 */
	private int numBlackBoxStates;

	/**
	 * the number of states of the BA to be encapsulated into a replacement
	 */
	private int numEncapsulatedStates;

	/**
	 * the BA which must be used in the computation of the IBA
	 */
	private BA ba;

	/**
	 * the map specifies for each transparent state the corresponding
	 * replacement
	 */
	private BiMap<State, Replacement> blackBoxStateReplacementMap;
	
	/**
	 * the map specifies for each transparent state the corresponding
	 * replacement
	 */
	private final BiMap<State, Replacement> blackBoxStateNonEmptyReplacementMap;

	/**
	 * if a state of the BA is encapsulated into the replacement it maps the
	 * state to the corresponding replacement
	 */
	private Map<State, Replacement> stateReplacementMap;

	/**
	 * the IBA that is computed starting from the BA
	 */
	private IBA iba;

	/**
	 * the state factory used to create the transparent states
	 */
	private StateFactory stateFactory;

	private static final String ACTION_NAME = "IBA Random Generation";

	/**
	 * 
	 * @param ba
	 *            is the BA from which the IBA must be extracted
	 * @param transparentStateDensity
	 *            is the density of the transparent states to be considered in
	 *            the IBA computation
	 * @param replacementDensity
	 *            specifies the number of states of the automaton to be injected
	 *            inside the replacement of each transparent state.
	 * @param stateFactory
	 *            is the factory to be used in the creation of the transparent
	 *            states
	 * @throws NullPointerException
	 *             if the BA, the state factory or the randomGenerator is null
	 * @throws IllegalArgumentException
	 *             if the transparent state density is not grater than or equal
	 *             to zero
	 * @throws IllegalArgumentException
	 *             if the replacement density is not grater than or equal to
	 *             zero
	 * @throws IllegalArgumentException
	 *             if the transparentStateDensity multiplied per the number of
	 *             states of the automaton multiplied for the replacement
	 *             density multiplied for the number of the state of the
	 *             automaton is not less than the number of the states of the
	 *             automaton
	 */
	public IBARandomGenerator(BA ba, StateFactory stateFactory,
			double transparentStateDensity, double replacementDensity) {
		super(ACTION_NAME);
		Preconditions.checkNotNull(ba, "The BA cannot be null");
		Preconditions.checkNotNull(stateFactory,
				"The state factory cannot be null");
		Preconditions
				.checkArgument(transparentStateDensity >= 0 && replacementDensity<=1,
						"The transparent state density must be grater than or equal to zero and less than or equal to 1");
		Preconditions.checkArgument(replacementDensity >= 0 && replacementDensity<=1,
				"The replacement density must be grater than or equal to zero and less than or equal to 1");

		
		Preconditions
				.checkArgument(
						Math.abs(Math.abs(transparentStateDensity)
								* ba.getStates().size()
								+ Math.abs(replacementDensity)
								* ba.getStates().size()) <= ba.getStates()
								.size(),
						"The transparentStateDensity * ba.getStates().size() * replacementDensity* ba.getStates().size() <= ba.getStates().size(): "
								+ Math.abs(Math.abs(transparentStateDensity)
										* ba.getStates().size()
										+ Math.abs(replacementDensity)
										* ba.getStates().size())
								+ "<"
								+ ba.getStates().size());

		this.numBlackBoxStates = (int) Math.abs(ba.getStates().size()
				* transparentStateDensity);
		this.ba = ba;
		this.blackBoxStateReplacementMap = HashBiMap.create();
		this.blackBoxStateNonEmptyReplacementMap=HashBiMap.create();
		this.stateReplacementMap = new HashMap<State, Replacement>();
		this.numEncapsulatedStates = (int) Math.abs((ba.getStates().size() - 1)
				* replacementDensity);
		Preconditions.checkArgument(numEncapsulatedStates<=ba.getStates().size(), "The number of the encapsultated states must be less than or equal to the states of the BA");
		this.stateFactory = stateFactory;
	}

	/**
	 * returns the randomly generated IBA
	 * 
	 * @return the randomly generated IBA
	 */
	public IBA perform() {

		// creates the IBA to be returned
		this.iba = new IBA(new ModelTransitionFactory());

		// adds the propositions to the IBA
		this.addPropositions();

		// creates the transparent states and the corresponding replacements
		this.createBlackBoxStatesAndReplacements();

		Set<State> setOfTheInjectedStates=new HashSet<State>();
		if(this.blackBoxStateReplacementMap.keySet().size()>0){
			List<State> baStates = new ArrayList<State>(ba.getStates());
			Collections.shuffle(baStates);
			 setOfTheInjectedStates = ImmutableSet.copyOf(Iterables.limit(baStates, numEncapsulatedStates));

			// adds the subset of states to the replacement
			this.addStatesToTheReplacements(setOfTheInjectedStates);

		}
		
		// adds the remaining states to the IBA as regular states
		Set<State> remainingBAStates = new HashSet<State>(ba.getStates());
		remainingBAStates.removeAll(setOfTheInjectedStates);
		this.addStatesToTheIBA(remainingBAStates);

		// generates the transition between the different states
		this.generateTransitions();

		this.performed();
		return iba;
	}

	/**
	 * adds the propositions to the BA to be returned
	 */
	private void addPropositions() {
		for (IGraphProposition proposition : this.ba.getPropositions()) {
			this.iba.addProposition(proposition);
		}
	}

	/**
	 * adds the state to the replacement
	 * 
	 * @param states
	 *            the set of states to be added to the replacements
	 * @throws NullPointerException
	 *             if states is null
	 * @throws IllegalArgumentException
	 *             if the set of state is not included in the set of states of
	 *             the BA
	 */
	private void addStatesToTheReplacements(Set<State> states) {
		Preconditions.checkNotNull(states,
				"The set of the states cannot be null");
		Preconditions
				.checkArgument(
						this.ba.getStates().containsAll(states),
						"The set of states must be contained into the set of the states of the automaton");
		Preconditions
				.checkArgument(
						this.blackBoxStateReplacementMap.keySet().size()
								+ numEncapsulatedStates <= this.ba.getStates()
								.size(),
						"The number of the transparent states ("
								+ this.blackBoxStateReplacementMap.keySet()
										.size()
								+ ")\n plus by the number of states added inside each replacement ("
								+ numEncapsulatedStates
								+ ") \n must be less than or equal the numebr of states of the Ba from which the IBA must be extracted("
								+ this.ba.getStates() + ")");

		

		List<State> blackBoxStates = new ArrayList<State>(
				this.blackBoxStateReplacementMap.keySet());
		for(State blackBoxState: blackBoxStates){
			iba.addBlackBoxState(blackBoxState);
		}
		
		Iterator<State> baStatesIterator = states.iterator();

		Random random=new Random();
		
		if(blackBoxStates.size()>0){
			for (int i = 0; i < numEncapsulatedStates; i++) {

				State baState = baStatesIterator.next();
				State blackBoxState=blackBoxStates.get(random.nextInt(blackBoxStates.size()));
				
				Replacement replacement = this.blackBoxStateReplacementMap
						.get(blackBoxState);
				replacement.getAutomaton().addState(baState);
				this.blackBoxStateNonEmptyReplacementMap.put(blackBoxState, replacement);
				this.stateReplacementMap.put(baState,
						this.blackBoxStateReplacementMap.get(blackBoxState));
				if (this.ba.getInitialStates().contains(baState)) {
					iba.addInitialState(blackBoxState);
					replacement.getAutomaton().addInitialState(baState);
				}
				if (this.ba.getAcceptStates().contains(baState)) {
					iba.addAcceptState(blackBoxState);
					replacement.getAutomaton().addAcceptState(baState);
				}
			}
		}
	}

	/**
	 * adds the states to the set of regular states of the IBA
	 * 
	 * @param states
	 *            the regular states to be added to the IBA
	 * @throws NullPointerException
	 *             if the set of the states is null
	 * @throws IllegalArgumentException
	 *             if the set of the state is not included in the states of the
	 *             BA
	 */
	private void addStatesToTheIBA(Set<State> states) {
		Preconditions.checkNotNull(states,
				"The set of the states cannot be null");
		Preconditions
				.checkArgument(
						this.ba.getStates().containsAll(states),
						"The set of the state to be added must be included into the set of the states of the IBA");
		// adding the other states to the iba
		for (State state : states) {
			iba.addState(state);
			if (this.ba.getInitialStates().contains(state)) {
				iba.addInitialState(state);
			}
			if (this.ba.getAcceptStates().contains(state)) {
				iba.addAcceptState(state);
			}
		}
	}

	/**
	 * generates the transitions between the states of the IBA and inside the
	 * replacement
	 */
	private void generateTransitions() {
		// adding the transitions
		for (Transition transition : this.ba.getTransitions()) {
			State source = this.ba.getTransitionSource(transition);
			State destination = this.ba.getTransitionDestination(transition);

			if (stateReplacementMap.containsKey(source)
					&& stateReplacementMap.containsKey(destination)) {

				Replacement replacementSource = stateReplacementMap.get(source);
				Replacement replacementDestination = stateReplacementMap
						.get(destination);
				State transparentSource = blackBoxStateReplacementMap
						.inverse().get(replacementSource);
				State transparentDestination = blackBoxStateReplacementMap
						.inverse().get(replacementDestination);

				if (replacementSource==replacementDestination) {
					replacementSource.getAutomaton().addTransition(source,
							destination, 
							new ModelTransitionFactory().create(transition.getId(), transition.getPropositions()));
				} else {
					this.iba.addTransition(transparentSource,
							transparentDestination, new ModelTransitionFactory().create(transition.getId(), transition.getPropositions()));
					replacementSource.addOutgoingTransition(new PluggingTransition(transition.getId(),
							source, transparentDestination, new ModelTransitionFactory().create(transition.getId(), transition.getPropositions()), false));
					replacementDestination
							.addIncomingTransition(new PluggingTransition(transition.getId(),
									transparentSource, destination, new ModelTransitionFactory().create(transition.getId(), transition.getPropositions()),
									true));
				}
			} else {
				if (stateReplacementMap.containsKey(source)) {
					Replacement replacementSource = stateReplacementMap
							.get(source);
					State blackBoxState = blackBoxStateReplacementMap
							.inverse().get(replacementSource);

					this.iba.addTransition(blackBoxState, destination,
	                         new ModelTransitionFactory().create(transition.getId(), transition.getPropositions()));
					replacementSource.addOutgoingTransition(new PluggingTransition(transition.getId(),
							source, destination, new ModelTransitionFactory().create(transition.getId(), transition.getPropositions()), false));
				} else {
					if (stateReplacementMap.containsKey(destination)) {
						Replacement replacementDestination = stateReplacementMap
								.get(destination);
						State transparentDestination = blackBoxStateReplacementMap
								.inverse().get(replacementDestination);

						this.iba.addTransition(source, transparentDestination,
	                            new ModelTransitionFactory().create(transition.getId(), transition.getPropositions()));
						replacementDestination
								.addIncomingTransition(new PluggingTransition(source,
										destination, new ModelTransitionFactory().create(transition.getId(), transition.getPropositions()), true));
					} else {
					    if(this.iba.getBlackBoxStates().contains(source)){
					        throw new InternalError("The source cannot be a black box");
					    }
					    if(this.iba.getBlackBoxStates().contains(destination)){
                            throw new InternalError("The destination cannot be a black box");
                            
                        }
						this.iba.addTransition(source, destination, new ModelTransitionFactory().create(transition.getId(), transition.getPropositions()));
					}
				}
			}
		}
	}

	/**
	 * creates the transparent state and the corresponding replacements
	 */
	private void createBlackBoxStatesAndReplacements() {

		for (int i = 0; i < numBlackBoxStates; i++) {
			State blackBoxState = stateFactory.create("t" + i);
			Replacement rep = new Replacement(blackBoxState, new IBA(
					new ModelTransitionFactory()),
					new HashSet<PluggingTransition>(),
					new HashSet<PluggingTransition>());
			rep.getAutomaton().addPropositions(this.ba.getPropositions());
			this.blackBoxStateReplacementMap.put(blackBoxState, rep);
		}
	}

	/**
	 * returns a Bidirectional map which specifies for each transparent state
	 * the corresponding replacement
	 * 
	 * @return a Bidirectional map which specifies for each transparent state
	 *         the corresponding replacement
	 * @throws IllegalStateException
	 *             if the IBA generation has not been already performed
	 */
	public BiMap<State, Replacement> getTransparentStateReplacementMap() {
		Preconditions
				.checkState(
						this.isPerformed(),
						"The action "
								+ ACTION_NAME
								+ " must be performed before getting the map between transparent states and replacements");
		return Maps.unmodifiableBiMap(this.blackBoxStateReplacementMap);
	}
	
	/**
	 * returns a Bidirectional map which specifies for each transparent state
	 * the corresponding replacement
	 * 
	 * @return a Bidirectional map which specifies for each transparent state
	 *         the corresponding replacement
	 * @throws IllegalStateException
	 *             if the IBA generation has not been already performed
	 */
	public List<Replacement> getNonEmptyReplacements() {
		Preconditions
				.checkState(
						this.isPerformed(),
						"The action "
								+ ACTION_NAME
								+ " must be performed before getting the map between transparent states and replacements");
		return new ArrayList<Replacement>(this.blackBoxStateNonEmptyReplacementMap.values());
	}
}
