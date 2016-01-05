package it.polimi.constraintcomputation.reachability.statepresence.acceptingclaim;

import it.polimi.automata.BA;
import it.polimi.automata.state.State;
import it.polimi.checker.intersection.IntersectionBuilder;
import it.polimi.constraintcomputation.reachability.statepresence.StatePresencePathChecker;

import java.util.HashSet;
import java.util.Set;

import com.google.common.base.Preconditions;

/**
 * Given the intersection builder and a set of states that can be traversed
 * along the search, checks the destination state is reachable
 * from a source state by crossing the set of allowed state and traversing at
 * least a state which is accepting for the claim. <br/>
 * 
 * If the destination state, is reachable from the
 * source state source, through a path that involves only allowed states and
 * crosses at least a state which is obtained from an accepting state of the
 * claim, the perform method returns true.
 * 
 * @author Claudio Menghi
 *
 */
public class AcceptingClaimStatePathChecker {

    /**
     * The checker to be used to verify whether the destination state is
     * reachable from the source state
     */
    private final StatePresencePathChecker statePresencePathChecker;

    /**
     * Creates the accepting claim state path checker
     * 
     * @param allowedStates
     *            the states that can be traversed along the search
     * @param intersectionBuilder
     *            the intersection builder
     * @throws NullPointerException
     *             if the set of the states that can be crossed is null or the
     *             intersection builder is null
     * @throws IllegalArgumentException
     *             if the set of the states that can be crossed is not contained
     *             into the set of the states of the intersection automaton
     */
    public AcceptingClaimStatePathChecker(Set<State> allowedStates,
            IntersectionBuilder intersectionBuilder, Set<State> interestingSourceStates, Set<State> interestingDestinationState) {

        Preconditions.checkNotNull(allowedStates,
                "The set of the states that can be crossed cannot be null");
        Preconditions.checkNotNull(intersectionBuilder,
                "If the intersection builder is null");
        Set<State> interestingStates = new HashSet<State>();
        BA claim = intersectionBuilder.getClaim();
        for (State state : allowedStates) {
            State claimState = intersectionBuilder.getClaimState(state);
            if (claim.getAcceptStates().contains(claimState)) {
                interestingStates.add(state);
            }
        }
        statePresencePathChecker = new StatePresencePathChecker(
                intersectionBuilder.getIntersectionAutomaton(), allowedStates,
                interestingStates);
        statePresencePathChecker.perform(interestingSourceStates, interestingDestinationState);
    }

    /**
     * returns true if the destination state, is reachable from the
     * source state, through a path that involves only allowed states and
     * crosses at least a state which is obtained from an accepting state of the
     * claim.
     * 
     * @param source
     *            the source state of interest
     * @param destination
     *            the destination state of interest
     * @return true if the destination state is reachable from the
     *         source state, through a path that involves only allowed
     *         states and crosses at least a state which is obtained from an
     *         accepting state of the claim.
     * @throws NullPointerException
     *             if one of the states is null
     * @throws IllegalArgumentException
     *             if one of the state is not into the set of the states that
     *             can be crossed in the search
     */
    public Boolean perform(State source, State destination) {
        return statePresencePathChecker.isReachableByPassingAnInterestingState(
                source, destination);
    }
}
