package it.polimi.contraintcomputation.subpropertyidentifier;

import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.checker.emptiness.EmptinessChecker;
import it.polimi.checker.intersection.IntersectionBuilder;
import it.polimi.contraintcomputation.CHIAOperation;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.StrongConnectivityInspector;

import com.google.common.base.Preconditions;

/**
 * The intersection cleaner removes from the intersection automaton the states
 * from which it it not possible to reach an accepting state of the intersection
 * automaton which can be entered infinitely often. Indeed, the states from
 * which it is not possible to reach an accepting state are not useful in the
 * constraint computation.<br>
 * 
 * Note that the intersectionCleaner also updates the IntersectionBuilder which
 * keeps track of the relation between the intersection state and the original
 * states of the model
 * 
 * @author claudiomenghi
 * 
 */
public class IntersectionCleaner extends CHIAOperation {

	
	/**
	 * contains the automaton to be considered by the {@link EmptinessChecker}
	 */
	private final IntersectionBA intersectionAutomaton;

	/**
	 * contains the IntersectionBuilder to be updated
	 */
	private final IntersectionBuilder intersectionBuilder;

	/**
	 * creates a new IntersectionCleaner
	 * 
	 * @param intersectionBuilder
	 *            contains the intersection automaton, the relation between the
	 *            states of the intersection automaton and the states of the
	 *            model etc
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public IntersectionCleaner(IntersectionBuilder intersectionBuilder) {
		super();
		Preconditions.checkNotNull(intersectionBuilder,
				"The intersection Builder cannot be null");

		this.intersectionAutomaton = intersectionBuilder.perform();
		this.intersectionBuilder = intersectionBuilder;

	}

	/**
	 * Removes the states from which it is not possible to reach an accepting
	 * state from the intersection automaton
	 */
	public void clean() {

		
		this.removeNoSuccessorStates();

		/*
		 * contains the set of the visited states
		 */
		StrongConnectivityInspector<State, Transition> connectivityInspector = new StrongConnectivityInspector<State, Transition>(
				this.intersectionAutomaton.getGraph());
		List<Set<State>> connectedSets = connectivityInspector
				.stronglyConnectedSets();

		/*
		 * contains the set of the states that has been encountered by
		 * <i>some<i> invocation of the first DFS
		 */
		Set<State> next = new HashSet<State>();
		for (Set<State> scc : connectedSets) {
			if (!Collections.disjoint(scc,
					this.intersectionAutomaton.getAcceptStates())) {
				if(scc.size()>1){
					next.addAll(scc);
				}
				else{
					State scState=scc.iterator().next();
					if(this.intersectionAutomaton.getSuccessors(scState).contains(scState)){
						next.add(scState);
					}
				}
			}
		}
		
		Set<State> visited = new HashSet<State>();
		while (!next.isEmpty()) {

			State currentState = next.iterator().next();
			visited.add(currentState);
			next.remove(currentState);
			
			Set<State> potentialNext=new HashSet<State>(this.intersectionAutomaton
					.getPredecessors(currentState));
			potentialNext.removeAll(visited);
			next.addAll(potentialNext);
		}

		Set<State> toBeRemoved = new HashSet<State>(
				this.intersectionAutomaton.getStates());
		toBeRemoved.removeAll(visited);

		// removing the non reachable states
		for (State s : toBeRemoved) {
			this.intersectionBuilder.removeIntersectionState(s);
		}

		this.setPerformed(true);
	}

	/**
	 * removes the nodes with no successors. The node with no successors cannot
	 * not involved from definition into infinite paths
	 * 
	 * @param automata
	 *            the automata from which the state with no successors must be
	 *            removed
	 */
	private void removeNoSuccessorStates() {

		DirectedGraph<State, Transition> g = this.intersectionAutomaton
				.getGraph();
		Set<State> toBeRemoved = new HashSet<State>();
		for (State s : g.vertexSet()) {
			if (g.outDegreeOf(s) == 0) {
				toBeRemoved.add(s);
			}
		}
		for (State s : toBeRemoved) {
			this.intersectionBuilder.removeIntersectionState(s);
		}
	}
}
