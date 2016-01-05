package it.polimi.constraintcomputation.reachability.statepresence;

import it.polimi.automata.BA;
import it.polimi.automata.state.State;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Preconditions;

/**
 * Computes the states that are backward and forward reachable from the set of
 * interesting states <br>
 * 
 * Checks if the destination is reachable from the source and at least an
 * interesting state is traversed along the search
 * 
 * @author Claudio Menghi
 *
 */
public class StatePresencePathChecker {

    /**
     * the Buchi automaton to be considered
     */
    private final BA ba;

    /**
     * the set of the states that can be traversed during the reachability
     * search
     */
    private final Set<State> states;

    /**
     * the set of the states that are of interest
     */
    private final Set<State> intersetingStates;


    /**
     * specifies that from the first state of the entry it is possible to reach
     * the second state of the entry by crossing at least an interesting state
     */
    private final Map<State, Set<State>> reachable;

    /**
     * specifies if the search has been performed
     */
    private boolean performed;

    /**
     * creates a new checker that aims to detect whether there exists a path
     * from the source to the destination that contains an accepting state.<br/>
     * 
     * THE SET OF THE INTERESTING STATES MUST BE INCLUDED INTO THE SET OF THE
     * STATES THAT CAN BE TRAVERSED
     * 
     * @param ba
     *            the BA under analysis
     * @param states
     *            the set of the states that can be traversed in the
     *            reachability search
     * @param includedStates
     *            is the set of the states whose presence must be checked along
     *            the run
     * @throws NullPointerException
     *             if the BA under analysis or one of the two sets is null
     * @throws IllegalArgumentException
     *             if the one of the two sets has a state that is not contained
     *             into the set of the states of the BA
     * @throws IllegalArgumentException
     *             if the set of interesting states is not included into the set
     *             of the states that can be traversed
     * 
     */
    public StatePresencePathChecker(BA ba, Set<State> states,
            Set<State> intersetingStates) {
        Preconditions.checkNotNull(ba, "The BA under analysis cannot be null");
        Preconditions.checkNotNull(states,
                "The set of the states under analysis cannot be null");
        Preconditions.checkNotNull(intersetingStates,
                "The set of the interesting states cannot be null");

        Preconditions
                .checkArgument(
                        ba.getStates().containsAll(states),
                        "The set of the states to be traversed must be contained into the set of the states of the automaton");

        Preconditions
                .checkArgument(
                        ba.getStates().containsAll(intersetingStates),
                        "the set of interesting states must be contained into the set of the states of the automaton");

        Preconditions
                .checkArgument(
                        states.containsAll(intersetingStates),
                        "The set of interesting states is not included into the set of the states that can be traversed");
        this.ba = ba;
        this.states = new HashSet<State>(states);
        this.intersetingStates = new HashSet<State>(intersetingStates);

        this.reachable = new HashMap<State, Set<State>>();
        this.performed = false;
        // creates for each of the intersting state an entry in the backward and
        // in the forward map
     
    }

    /**
     * checks if the destination is reachable from the source and at least an
     * interesting state is traversed along the search
     * 
     * @param source
     *            is the source state of interest
     * @param destination
     *            is the destination state of interest
     * @return true if the destination is reachable from the source and at least
     *         an interesting state is traversed along the search
     * @throws IllegalStateException
     *             if the search has not been performed before invoking this
     *             method
     * @throws NullPointerException
     *             if the source or the destination is null
     * @throws IllegalArgumentException
     *             if the source or the destination is not in the states of the
     *             BA
     * @throws IllegalArgumentException
     *             if the source or the destination is not included into the set
     *             of the states of interest
     */
    public boolean isReachableByPassingAnInterestingState(State source,
            State destination) {
        Preconditions.checkState(performed,
                "Checks if the search has been performed");
        Preconditions.checkNotNull(source, "The source state cannot be null");
        Preconditions.checkNotNull(destination,
                "The destination state cannot be null");
        Preconditions.checkArgument(this.ba.getStates().contains(source),
                "The source state must be a state of the BA");
        Preconditions.checkArgument(this.ba.getStates().contains(destination),
                "The destination state must be a state of the BA");
        Preconditions
                .checkArgument(
                        this.states.contains(source),
                        "The source state must be contained into the set of the states that can be traversed");
        Preconditions
                .checkArgument(
                        this.states.contains(destination),
                        "The destination state must be contained into the set of the states that can be traversed");

        if(!this.reachable.containsKey(source)){
        	return false;
        	
        }
        return this.reachable.get(source).contains(destination);
    }

    /**
     * computes whether there exists a path from every possible source to
     * destination state that contains at least a state of interest
     */
    public void perform(Set<State> interestingSourceStates, Set<State> interestingDestinationState) {

    	System.out.println("Heap size: "+Runtime.getRuntime().totalMemory());
    	System.out.println("Heap max size: "+Runtime.getRuntime().maxMemory());
    	System.out.println("Free Heap size: "+Runtime.getRuntime().freeMemory());
    	Set<State> forwardReachableStates=new HashSet<State>(this.intersetingStates.size());
    	Set<State> backwardStates=new HashSet<State>(this.intersetingStates.size());
        // for each state of interest computes the backward and the forward
        // reachable states
        for (State interestingState : this.intersetingStates) {
            backwardCheck(interestingState, backwardStates);
            forwardCheck(interestingState, forwardReachableStates);
            this.findConnectedStates(backwardStates, forwardReachableStates, interestingSourceStates, interestingDestinationState);
            forwardReachableStates.clear();
            backwardStates.clear();
           
        }

        this.intersetingStates.clear();
        this.performed = true;
        System.out.println("End");
    }

    /**
     * Finds the source and the destination state that are connected with a path
     * that crosses the interesting state
     * 
     * @param interstingState
     *            the state that is currently analyzed
     */
    private void findConnectedStates(Set<State> backwardStates, Set<State> forwardStates, Set<State> interestingSourceStates, Set<State> interestingDestinationState) {
    	backwardStates.retainAll(interestingSourceStates);
    	forwardStates.retainAll(interestingDestinationState);
    	for (State sourceState : backwardStates) {
        	if(this.reachable.containsKey(sourceState)){
        		this.reachable.get(sourceState).addAll(forwardStates);
        	}
        	else{
        		this.reachable.put(sourceState, forwardStates);
        	}
        }
    }

    /**
     * returns the states that are backward reachable from the specified state
     * (including the state itself)
     * 
     * @return the states that are backward reachable from the specified state
     * @throws NullPointerException
     *             if the specified state is null
     */
    private void backwardCheck(State state, Set<State> backwardStates) {

    	backwardStates.add(state);
        // the set of the states to be analyzed next
        Set<State> nextStates = new HashSet<State>();
        nextStates.add(state);
        Set<State> visitedStates = new HashSet<State>();
        while (!nextStates.isEmpty()) {
            State next = nextStates.iterator().next();
            nextStates.remove(next);
            visitedStates.add(next);
            for (State predecessor : this.ba.getPredecessors(next)) {
                if (this.states.contains(predecessor)
                        && !visitedStates.contains(predecessor)) {
                	backwardStates.add(predecessor);
                    nextStates.add(predecessor);
                }
            }
        }
    }

    /**
     * returns the states that are forward reachable from the specified state
     * (including the state itself)
     * 
     * @return the states that are forward reachable from the specified state
     * @throws NullPointerException
     *             if the specified state is null
     */
    private void forwardCheck(State state, Set<State> forwardStates) {
 
    	forwardStates.add(state);
        Set<State> nextStates = new HashSet<State>();
        nextStates.add(state);
        Set<State> visitedStates = new HashSet<State>();
        while (!nextStates.isEmpty()) {
            State next = nextStates.iterator().next();
            nextStates.remove(next);
            visitedStates.add(next);
            for (State successorState : this.ba.getSuccessors(next)) {
                if (this.states.contains(successorState)
                        && !visitedStates.contains(successorState)) {
                	forwardStates.add(successorState);
                    nextStates.add(successorState);
                }
            }
        }
    }
    
}
