package it.polimi.constraintcomputation.reachability;

import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.checker.abstractor.Abstractor;
import it.polimi.checker.intersection.IntersectionBuilder;
import it.polimi.constraintcomputation.reachability.statepresence.acceptingclaim.AcceptingClaimStatePathChecker;
import it.polimi.constraintcomputation.reachability.statepresence.acceptingmodel.AcceptingModelStatePathChecker;
import it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier;
import it.polimi.constraints.transitions.LabeledPluggingTransition;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.google.common.base.Preconditions;

/**
 * <p>
 * The ReachabilityIdentifier class allows to compute the reachability between
 * the incoming and outgoing transitions of the sub-properties and the
 * corresponding labels, through the internal private methods
 * computeLowerReachability and computeUpperReachability. 
 * </p>
 * 
 * <p>
 * The computeLowerReachability computes whether from an outgoing transition it
 * is possible to reach an incoming transition through a path that only involves
 * purely regular states of the intersection automaton. 
 * </p>
 * 
 * <p>
 * The computeUpperReachability computes for each sub-property whether from an
 * outgoing transition it is possible to reach an incoming transition through a
 * path that involves purely regular and mixed states which do not involve mixed
 * states of the sub-property. 
 * </p>
 * 
 * <p>
 * The ReachabilityIdentifier class modifies the SubProperty identifier in
 * relation with the lower and the upper reachability relations computed
 * </p>
 * 
 * @author Claudio Menghi
 *
 */
public class ReachabilityIdentifier {

	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	/**
	 * contains the CHIA logger
	 */
	private static final Logger LOGGER = Logger
			.getLogger(ReachabilityIdentifier.class);
	/**
	 * is the builder which is used to compute the intersection automaton
	 */
	private final IntersectionBuilder intersectionBuilder;

	/**
	 * is the identifier which has been used to generate the sub-properties
	 */
	private final SubPropertyIdentifier subPropertiesIdentifier;

	/**
	 * creates a new ReachabilityIdentifier component which is used to compute
	 * and update the reachability relation between the incoming and outgoing
	 * transitions of each sub-property computed by the SubPropertyIdentifier
	 * 
	 * @param subPropertiesIdentifier
	 *            is the component which has been used to compute the
	 *            sub-properties
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public ReachabilityIdentifier(SubPropertyIdentifier subPropertiesIdentifier) {
		Preconditions.checkNotNull(subPropertiesIdentifier,
				"The subproperties cannot be null");

		this.intersectionBuilder = subPropertiesIdentifier.getChecker()
				.getUpperIntersectionBuilder();
		this.subPropertiesIdentifier = subPropertiesIdentifier;
	}

	/**
	 * updates the reachability relation insider the
	 * {@link SubPropertyIdentifier}
	 */
	public void perform() {

		// gets the intersection transitions associated with an incoming
		// transition of the sub-property
		Set<Transition> intersectionTransitionsAssociatedWithAnIncomingPort = subPropertiesIdentifier
				.getMapIntersectionTransitionIncomingTransitions().keySet();
		LOGGER.debug("Number of intersection transitions associated with and incoming transition"
				+ intersectionTransitionsAssociatedWithAnIncomingPort.size());

		// gets the intersection transitions associated with an outgoing
		// transition of the sub-property
		Set<Transition> intersectionTransitionsAssociatedWithAnOutgoingPort = subPropertiesIdentifier
				.getMapIntersectionTransitionOutgoingTransitions().keySet();
		LOGGER.debug("Number of intersection transitions associated with an outgoing transition"
				+ intersectionTransitionsAssociatedWithAnOutgoingPort.size());

		// computes a map that associates for each state of the intersection
		// automaton the set of transitions of the intersection automaton
		// associated with an incoming transition of the sub-property
		Map<State, Set<Transition>> mapStateIncomingTransitionsAssociatedIncomingTransition = computeStateIncomingTransitions(
				this.intersectionBuilder.getIntersectionAutomaton(),
				intersectionTransitionsAssociatedWithAnIncomingPort);
		LOGGER.debug("Number of entry in the incoming transition map: "
				+ mapStateIncomingTransitionsAssociatedIncomingTransition
						.size());

		// computes a map that associates for each state of the intersection
		// automaton the set of transitions of the intersection automaton
		// associated with an outgoing transition of the sub-property
		Map<State, Set<Transition>> mapStateOutGoingTransitionsAssociatedOutgoingTransition = computeStateOutgoingTransitions(
				this.intersectionBuilder.getIntersectionAutomaton(),
				intersectionTransitionsAssociatedWithAnOutgoingPort);
		LOGGER.debug("Number of entry in the outgoing transition map: "
				+ mapStateOutGoingTransitionsAssociatedOutgoingTransition
						.size());

		LOGGER.debug("Computing the lower reachability map");
		// first it updates the lower reachability relation
		this.computeLowerRechabilityRelation(
				mapStateIncomingTransitionsAssociatedIncomingTransition,
				mapStateOutGoingTransitionsAssociatedOutgoingTransition);
		LOGGER.debug("Lower reachability map computed");
		LOGGER.debug("compute upper reachability");
		// second it updates the upper reachability relation
		this.computeUpperReachabilityRelation(
				mapStateIncomingTransitionsAssociatedIncomingTransition,
				mapStateOutGoingTransitionsAssociatedOutgoingTransition);
		LOGGER.debug("upper reachability computed");
	}

	/**
	 * updates the lower reachability relation of the
	 * {@link SubPropertyIdentifier}
	 */
	private void computeLowerRechabilityRelation(
			Map<State, Set<Transition>> mapStateIncomingTransitionsAssociatedIncomingTransition,
			Map<State, Set<Transition>> mapStateOutGoingTransitionsAssociatedOutgoingTransition) {

		// gets the intersection automaton
		IntersectionBA intersectionAutomaton = this.intersectionBuilder
				.getIntersectionAutomaton();

		// considers only the purely regular states of the intersection
		// automaton
		IntersectionBA abstractedIntersection = new Abstractor<IntersectionBA>()
				.perform(intersectionAutomaton,
						intersectionAutomaton.getPurelyRegularStates());

		Set<State> interestingSourceStates=this.getInterestingDestinationsStates(mapStateOutGoingTransitionsAssociatedOutgoingTransition, intersectionAutomaton);
		
		// contains the source states of the incoming transitions
		Set<State> interestingDestinationStates=this.getInterestingSourceStates(mapStateIncomingTransitionsAssociatedIncomingTransition, intersectionAutomaton);
		
		// computes the presence of accepting states of the claim in the
		// corresponding abstracted graph
		LOGGER.debug("lower: accepting claim states: ");
		AcceptingClaimStatePathChecker acceptingClaimStatePathChecker = new AcceptingClaimStatePathChecker(
				abstractedIntersection.getPurelyRegularStates(),
				intersectionBuilder, interestingSourceStates, interestingDestinationStates);

		LOGGER.debug("lower: accepting model states: ");
		// computes the presence of accepting states of the model in the
		// corresponding abstracted graph
		AcceptingModelStatePathChecker acceptingModelStatePathChecker = new AcceptingModelStatePathChecker(
				abstractedIntersection.getPurelyRegularStates(),
				intersectionBuilder, interestingSourceStates, interestingDestinationStates);

		
		
		// analyzes each reachability entry. <s1, s2> is a reachability entry if
		// from s1 it is possible to reach s2
		Map<State, Set<State>> reachabilityMap = this.getReachabilityRelation(
				abstractedIntersection, abstractedIntersection.getPurelyRegularStates(),
				mapStateOutGoingTransitionsAssociatedOutgoingTransition
						.keySet(),
				mapStateIncomingTransitionsAssociatedIncomingTransition
						.keySet());

		for (State sourceState : reachabilityMap.keySet()) {
			for (State destinationState : reachabilityMap.get(sourceState)) {
				if (mapStateOutGoingTransitionsAssociatedOutgoingTransition
						.containsKey(sourceState)) {
					if (mapStateIncomingTransitionsAssociatedIncomingTransition
							.containsKey(destinationState)) {

						for (Transition outTransition : mapStateOutGoingTransitionsAssociatedOutgoingTransition
								.get(sourceState)) {

							for (Transition inTransition : mapStateIncomingTransitionsAssociatedIncomingTransition
									.get(destinationState)) {

								LabeledPluggingTransition subPropertyOuttransition = this.subPropertiesIdentifier
										.getOutgoingTransition(outTransition);
								LabeledPluggingTransition subPropertyInTransition = this.subPropertiesIdentifier
										.getIncomingTransition(inTransition);

								State source = intersectionAutomaton
										.getTransitionDestination(outTransition);
								State destination = intersectionAutomaton
										.getTransitionSource(inTransition);
								if(abstractedIntersection.getStates().contains(source) && abstractedIntersection.getStates().contains(destination)){
									this.subPropertiesIdentifier.perform()
									.addReachabilityRelation(
											subPropertyOuttransition,
											subPropertyInTransition,
											acceptingModelStatePathChecker
													.perform(source,
															destination),
											acceptingClaimStatePathChecker
													.perform(source,
															destination));
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * updates the upper reachability relation of the
	 * {@link SubPropertyIdentifier}
	 */
	private void computeUpperReachabilityRelation(
			Map<State, Set<Transition>> mapStateIncomingTransitionsAssociatedIncomingTransition,
			Map<State, Set<Transition>> mapStateOutGoingTransitionsAssociatedOutgoingTransition) {

		// gets the intersection automaton
		IntersectionBA intersectionAutomaton = this.intersectionBuilder
				.getIntersectionAutomaton();
		// gets the states of the intersection automaton
		Set<State> analyzedStates = new HashSet<State>(
				intersectionAutomaton.getStates());
		// removes from the states of the intersection automaton the states
		// involved in a sub-property
		analyzedStates.removeAll(this.subPropertiesIdentifier.perform()
				.getAutomaton().getStates());

		// computes the abstract intersection, i.e., it removes from the
		// intersection automaton the states of the automaton associated with
		// the sub-property
		IntersectionBA abstractedIntersection = new Abstractor<IntersectionBA>()
				.perform(intersectionAutomaton, analyzedStates);

		Set<State> interestingSourceStates=this.getInterestingDestinationsStates(mapStateOutGoingTransitionsAssociatedOutgoingTransition, intersectionAutomaton);
		Set<State> interestingDestinationStates=this.getInterestingSourceStates(mapStateIncomingTransitionsAssociatedIncomingTransition, intersectionAutomaton);
		// computes the presence states of accepting states of the claim
		// considering the set of analyzed states
		LOGGER.debug("upper: Accepting states");
		AcceptingClaimStatePathChecker acceptingClaimStatePathChecker = new AcceptingClaimStatePathChecker(
				analyzedStates, intersectionBuilder, interestingSourceStates, interestingDestinationStates);

		// computes the presence states of accepting states of the model
		// considering the set of analyzed states
		AcceptingModelStatePathChecker acceptingModelStatePathChecker = new AcceptingModelStatePathChecker(
				analyzedStates, intersectionBuilder, interestingSourceStates, interestingDestinationStates);

		LOGGER.debug("upper: Accepting statesComputed");
		LOGGER.debug("upper: Quering");




		// analyzes each reachability entry. <s1, s2> is a reachability entry if
		// from s1 it is possible to reach s2
		Map<State, Set<State>> reachabilityMap = this.getReachabilityRelation(
				abstractedIntersection, analyzedStates,
				mapStateOutGoingTransitionsAssociatedOutgoingTransition
						.keySet(),
				mapStateIncomingTransitionsAssociatedIncomingTransition
						.keySet());
		for (State sourceState : reachabilityMap.keySet()) {

			for (State destinationState : reachabilityMap.get(sourceState)) {
				if (mapStateOutGoingTransitionsAssociatedOutgoingTransition
						.containsKey(sourceState)) {
					if (mapStateIncomingTransitionsAssociatedIncomingTransition
							.containsKey(destinationState)) {

						for (Transition outTransition : mapStateOutGoingTransitionsAssociatedOutgoingTransition
								.get(sourceState)) {

							for (Transition inTransition : mapStateIncomingTransitionsAssociatedIncomingTransition
									.get(destinationState)) {

								LabeledPluggingTransition subPropertyOuttransition = this.subPropertiesIdentifier
										.getOutgoingTransition(outTransition);
								LabeledPluggingTransition subPropertyInTransition = this.subPropertiesIdentifier
										.getIncomingTransition(inTransition);

								State source = intersectionAutomaton
										.getTransitionDestination(outTransition);
								State destination = intersectionAutomaton
										.getTransitionSource(inTransition);
								this.subPropertiesIdentifier.perform()
										.addPossibleReachabilityRelation(
												subPropertyOuttransition,
												subPropertyInTransition,
												acceptingModelStatePathChecker
														.perform(source,
																destination),
												acceptingClaimStatePathChecker
														.perform(source,
																destination));

							}
						}

					}
				}
			}
		}
		LOGGER.debug("Sub-property upper reachability relation size "
				+ this.subPropertiesIdentifier.perform()
						.getUpperReachabilityRelation()
						.getReachabilityAcceptingMap().size());

		LOGGER.debug("end: reachability");
	}

	private Set<State> getInterestingDestinationsStates(
			Map<State, Set<Transition>> mapStateOutGoingTransitionsAssociatedOutgoingTransition,
			IntersectionBA intersectionAutomaton) {
		Set<State> interestingSourceStates = new HashSet<State>();
		for (Set<Transition> outTransitions : mapStateOutGoingTransitionsAssociatedOutgoingTransition
				.values()) {

			for (Transition outTransition : outTransitions) {
				State source = intersectionAutomaton
						.getTransitionDestination(outTransition);
				interestingSourceStates.add(source);

			}
		}
		return interestingSourceStates;
	}
	
	private Set<State> getInterestingSourceStates(
			Map<State, Set<Transition>> mapStateOutGoingTransitionsAssociatedIncomingTransition,
			IntersectionBA intersectionAutomaton) {
		Set<State> interestingDestinationStates = new HashSet<State>();
		for (Set<Transition> inTransitions : mapStateOutGoingTransitionsAssociatedIncomingTransition
				.values()) {

			for (Transition inTransition : inTransitions) {
				State source = intersectionAutomaton.getTransitionSource(inTransition);
				interestingDestinationStates.add(source);

			}
		}
		return interestingDestinationStates;
	}


	/**
	 * @param intersectionAutomaton
	 * @param intersectionTransitionsAssociatedWithAnOutgoingPort
	 * @return
	 */
	private Map<State, Set<Transition>> computeStateOutgoingTransitions(
			IntersectionBA intersectionAutomaton,
			Set<Transition> intersectionTransitionsAssociatedWithAnOutgoingPort) {
		Map<State, Set<Transition>> mapStateOutGoingTransitionsAssociatedOutgoingTransition = new HashMap<State, Set<Transition>>();

		for (Transition outTransition : intersectionTransitionsAssociatedWithAnOutgoingPort) {

			State outTransitionDestination = intersectionAutomaton
					.getTransitionDestination(outTransition);
			if (!mapStateOutGoingTransitionsAssociatedOutgoingTransition
					.containsKey(outTransitionDestination)) {
				mapStateOutGoingTransitionsAssociatedOutgoingTransition.put(
						outTransitionDestination, new HashSet<Transition>());
			}
			mapStateOutGoingTransitionsAssociatedOutgoingTransition.get(
					outTransitionDestination).add(outTransition);
		}

		LOGGER.debug("mapStateOutGoingTransitionsAssociatedOutgoingTransition "
				+ mapStateOutGoingTransitionsAssociatedOutgoingTransition
						.size());
		return mapStateOutGoingTransitionsAssociatedOutgoingTransition;
	}

	/**
	 * computes a map that associates for each state of the intersection
	 * automaton the set of transitions of the intersection automaton associated
	 * with an incoming transition of the sub-property
	 * 
	 * @param intersectionAutomaton
	 *            the intersection automaton to be considered
	 * @param intersectionTransitionsAssociatedWithAnIncomingPort
	 *            the set of transitions of the intersection automaton
	 *            associated with an incoming transition of the sub-property
	 * @return a map that associates for each state of the intersection
	 *         automaton the set of transitions of the intersection automaton
	 *         associated with an incoming transition of the sub-property
	 */
	private Map<State, Set<Transition>> computeStateIncomingTransitions(
			IntersectionBA intersectionAutomaton,
			Set<Transition> intersectionTransitionsAssociatedWithAnIncomingPort) {
		Map<State, Set<Transition>> mapStateIncomingTransitionsAssociatedIncomingTransition = new HashMap<State, Set<Transition>>();

		for (Transition inTransition : intersectionTransitionsAssociatedWithAnIncomingPort) {

			State inTransitionSource = intersectionAutomaton
					.getTransitionSource(inTransition);
			if (!mapStateIncomingTransitionsAssociatedIncomingTransition
					.containsKey(inTransitionSource)) {
				mapStateIncomingTransitionsAssociatedIncomingTransition.put(
						inTransitionSource, new HashSet<Transition>());
			}
			mapStateIncomingTransitionsAssociatedIncomingTransition.get(
					inTransitionSource).add(inTransition);
		}
		LOGGER.debug("mapStateIncomingTransitionsAssociatedIncomingTransition "
				+ mapStateIncomingTransitionsAssociatedIncomingTransition
						.size());
		return mapStateIncomingTransitionsAssociatedIncomingTransition;
	}

	/**
	 * returns the set of reachability entries of the corresponding graph
	 * 
	 * @param paths
	 *            the paths to be considered
	 * @param states
	 *            the set of the states under analysis
	 * @return a set of entry <state, state> a couple <s1, s2> is in the set if
	 *         from s1 it is possible to reach s2
	 */
	private Map<State, Set<State>> getReachabilityRelation(IntersectionBA abstractedIntersection,
			 Set<State> states,
			Set<State> sourceStates, Set<State> destinationStates) {
		Preconditions.checkNotNull(abstractedIntersection, "The abstracted intersection cannot be null");
		Preconditions.checkNotNull(states, "The states cannot be null");
		

		// computes the reachability between the shortest paths between the
		// states of the graph
		LOGGER.debug("lower: Floyd warshall");
		
		ReachableStatesIdentifier identifier=new ReachableStatesIdentifier(abstractedIntersection);
		
		Map<State, Set<State>> reachabilityMap = new HashMap<State, Set<State>>();

		for (State sourceState: sourceStates) {
			reachabilityMap.put(sourceState, identifier.getReachableStates(sourceState, destinationStates));
		}
		
		LOGGER.debug("reachability entries to be analyzed:"
				+ reachabilityMap.size());
		return reachabilityMap;
	}
	/*private Map<State, Set<State>> getReachabilityRelation(IntersectionBA abstractedIntersection,
			 Set<State> states,
			Set<State> sourceStates, Set<State> destinationStates) {
		Preconditions.checkNotNull(abstractedIntersection, "The abstracted intersection cannot be null");
		Preconditions.checkNotNull(states, "The states cannot be null");
		
		/// gets the corresponding graph
		DirectedGraph<State, Transition> graph = abstractedIntersection
				.getGraph();

		// computes the reachability between the shortest paths between the
		// states of the graph
		LOGGER.debug("lower: Floyd warshall");
		
		Collection<GraphPath<State, Transition>> paths = new FloydWarshallShortestPaths<State, Transition>(
				graph).getShortestPaths();

		
		Map<State, Set<State>> reachabilityMap = new HashMap<State, Set<State>>();

		for (Iterator<GraphPath<State, Transition>> iterator = paths.iterator(); iterator
				.hasNext();) {
			GraphPath<State, Transition> path = iterator.next();
			if (sourceStates.contains(path.getStartVertex())
					&& destinationStates.contains(path.getEndVertex())) {
				if (reachabilityMap.containsKey(path.getStartVertex())) {
					reachabilityMap.get(path.getStartVertex()).add(
							path.getEndVertex());
				} else {
					reachabilityMap.put(path.getStartVertex(),
							new HashSet<State>());
					reachabilityMap.get(path.getStartVertex()).add(
							path.getEndVertex());
				}
			}

			iterator.remove();
		}
		for (State state : states) {
			if (sourceStates.contains(state)
					&& destinationStates.contains(state))
				if (reachabilityMap.containsKey(state)) {
					reachabilityMap.get(state).add(state);
				} else {
					reachabilityMap.put(state, new HashSet<State>());
					reachabilityMap.get(state).add(state);
				}
		}
		LOGGER.debug("reachability entries to be analyzed:"
				+ reachabilityMap.size());
		return reachabilityMap;
	}*/
}
