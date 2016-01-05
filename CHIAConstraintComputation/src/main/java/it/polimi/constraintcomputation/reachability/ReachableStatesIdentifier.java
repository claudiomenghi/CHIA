package it.polimi.constraintcomputation.reachability;

import java.util.HashSet;
import java.util.Set;

import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.State;

public class ReachableStatesIdentifier {

	private final IntersectionBA abstractedIntersection;

	public ReachableStatesIdentifier(IntersectionBA abstractedIntersection) {
		this.abstractedIntersection = abstractedIntersection;
	}

	public Set<State> getReachableStates(State state, Set<State> destinationStates) {

		Set<State> forwardStates = new HashSet<State>();
		Set<State> nextStates = new HashSet<State>();
		forwardStates.add(state);
		
		if(this.abstractedIntersection.getStates().contains(state)){
			nextStates.add(state);
		}
		Set<State> visitedStates = new HashSet<State>();
		while (!nextStates.isEmpty()) {
			State next = nextStates.iterator().next();
			nextStates.remove(next);
			visitedStates.add(next);
			for (State successorState : this.abstractedIntersection.getSuccessors(next)) {
				if (!visitedStates.contains(successorState)) {
					if(destinationStates.contains(successorState)){
						forwardStates.add(successorState);
					}
					nextStates.add(successorState);
				}
			}
		}
		return forwardStates;
	}

}
