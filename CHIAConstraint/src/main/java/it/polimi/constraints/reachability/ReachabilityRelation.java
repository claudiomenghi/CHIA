package it.polimi.constraints.reachability;

import it.polimi.automata.state.State;
import it.polimi.constraints.transitions.LabeledPluggingTransition;

import java.util.Collection;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * contains the reachability relation. The reachability relation specified
 * whether an incoming port of a sub-property is reachable from the outgoing
 * port of the same transition
 * 
 * @author Claudio Menghi
 *
 */
public class ReachabilityRelation {

	/**
	 * If a state is the source of a reachability relation, contains the state
	 * and the corresponding reabhability entry
	 */
	private final Multimap<State, ReachabilityEntry> acceptingMap;

	/**
	 * creates a new empty reachability relation. The reachability relation is
	 * used to map the incoming transitions which are reachable from the
	 * outgoing transition of the same component
	 */
	public ReachabilityRelation() {
		this.acceptingMap = HashMultimap.create();
	}

	/**
	 * adds the transition to the reachability relation. The sourceTransition is
	 * the outgoingPort of the sub-property from which the destinationTransition
	 * can be reached. The destinationTransition is the incoming port of the
	 * subproperty which is reachable from the sourceTransition
	 * 
	 * @param sourceTransition
	 *            is the source of the transition
	 * @param destinationTransition
	 *            is the destination of the transition
	 * @throws NullPointerException
	 *             if one of the two transitions is null
	 * @throws IllegalArgumentException
	 *             if the incoming or the outgoing transition is not
	 *             incoming/outgoing
	 */
	public void addTransition(LabeledPluggingTransition outgoingTransition,
			LabeledPluggingTransition incomingTransition,
			Boolean modelAccepting, Boolean claimAccepting) {
		Preconditions.checkNotNull(outgoingTransition,
				"The outgoing transition cannot be null");
		Preconditions.checkNotNull(incomingTransition,
				"The destination transition cannot be null");
		Preconditions
				.checkArgument(!outgoingTransition.isIncoming(),
						"The source of the reachability relation must be an outgoingTransition");
		Preconditions
				.checkArgument(incomingTransition.isIncoming(),
						"The destination of the reachability relation must be an incomingTransition");

		ReachabilityEntry reachabilityEntry = new ReachabilityEntry(
				incomingTransition, outgoingTransition, modelAccepting,
				claimAccepting);
		this.acceptingMap
				.put(outgoingTransition.getSource(), reachabilityEntry);
	}

	/**
	 * returns the map that associates for each state the corresponding
	 * reachability entries
	 * 
	 * @return the map that associates for each state the corresponding
	 *         reachability entries
	 */
	public Multimap<State, ReachabilityEntry> getReachabilityAcceptingMap() {
		return this.acceptingMap;
	}

	/**
	 * returns the collection of reachability entries associated to one state
	 * 
	 * @param subPropertyState
	 *            the state of the sub-property to be considered
	 * @return the set of the reachability entries whose source is the specified
	 *         property state
	 */
	public Collection<ReachabilityEntry> get(State subPropertyState) {
		return this.acceptingMap.get(subPropertyState);
	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public String toString() {
		return "ReachabilityRelation [acceptingMap=" + acceptingMap + "]";
	}
}
