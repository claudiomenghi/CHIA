package it.polimi.statemachine.automata;

import it.polimi.action.CHIAException;
import it.polimi.console.CHIAAutomataConsole;
import it.polimi.statemachine.automata.states.Checked;
import it.polimi.statemachine.automata.states.ConstraintComputed;
import it.polimi.statemachine.automata.states.Init;
import it.polimi.statemachine.automata.states.ModelLoaded;
import it.polimi.statemachine.automata.states.PropertyLoaded;
import it.polimi.statemachine.automata.states.Ready;

/**
 * specifies the behavior of an action. It describes 1) whether the action is
 * performable in the state (isPerformable) 2) the next state reached by the
 * automaton when the state it is visited (visit)
 * 
 * @author Claudio Menghi
 *
 */
public interface AutomataAction {

	/**
	 * visits the state
	 * 
	 * @param state
	 *            the current state
	 * @return returns the next state of the automaton
	 * @throws CHIAException
	 *             if the action is not executable in the state
	 * @throws NullPointerException
	 *             if the state is null
	 */
	public AutomataState visit(Init state) throws CHIAException;

	/**
	 * returns true if the action can be performed in the state
	 * 
	 * @param state
	 *            the current state
	 * @return returns true if the action can be performed in the state
	 * @throws NullPointerException
	 *             if the state is null
	 */
	public boolean isPerformable(Init state);

	/**
	 * visits the state
	 * 
	 * @param state
	 *            the current state
	 * @return returns the next state of the automaton
	 * @throws CHIAException
	 *             if the action is not executable in the state
	 * @throws NullPointerException
	 *             if the state is null
	 */
	public AutomataState visit(ModelLoaded state) throws CHIAException;

	/**
	 * returns true if the action can be performed in the state
	 * 
	 * @param state
	 *            the current state
	 * @return returns true if the action can be performed in the state
	 * @throws NullPointerException
	 *             if the state is null
	 */
	public boolean isPerformable(ModelLoaded state);

	/**
	 * visits the state
	 * 
	 * @param state
	 *            the current state
	 * @return returns the next state of the automaton
	 * @throws CHIAException
	 *             if the action is not executable in the state
	 * @throws NullPointerException
	 *             if the state is null
	 */
	public AutomataState visit(Ready state) throws CHIAException;

	/**
	 * returns true if the action can be performed in the state
	 * 
	 * @param state
	 *            the current state
	 * @return returns true if the action can be performed in the state
	 * @throws NullPointerException
	 *             if the state is null
	 */
	public boolean isPerformable(Ready state);

	/**
	 * visits the state
	 * 
	 * @param state
	 *            the current state
	 * @return returns the next state of the automaton
	 * @throws CHIAException
	 *             if the action is not executable in the state
	 * @throws NullPointerException
	 *             if the state is null
	 */
	public AutomataState visit(PropertyLoaded state)
			throws CHIAException;

	/**
	 * returns true if the action can be performed in the state
	 * 
	 * @param state
	 *            the current state
	 * @return returns true if the action can be performed in the state
	 * @throws NullPointerException
	 *             if the state is null
	 */
	public boolean isPerformable(PropertyLoaded state);

	/**
	 * visits the state
	 * 
	 * @param state
	 *            the current state
	 * @return returns the next state of the automaton
	 * @throws CHIAException
	 *             if the action is not executable in the state
	 * @throws NullPointerException
	 *             if the state is null
	 */
	public AutomataState visit(Checked state) throws CHIAException;

	/**
	 * returns true if the action can be performed in the state
	 * 
	 * @param state
	 *            the current state
	 * @return returns true if the action can be performed in the state
	 * @throws NullPointerException
	 *             if the state is null
	 */
	public boolean isPerformable(Checked state);

	/**
	 * visits the state
	 * 
	 * @param state
	 *            the current state
	 * @return returns the next state of the automaton
	 * @throws CHIAException
	 *             if the action is not executable in the state
	 * @throws NullPointerException
	 *             if the state is null
	 */
	public AutomataState visit(ConstraintComputed state)
			throws CHIAException;

	/**
	 * returns true if the action can be performed in the state
	 * 
	 * @param state
	 *            the current state
	 * @return returns true if the action can be performed in the state
	 * @throws NullPointerException
	 *             if the state is null
	 */
	public boolean isPerformable(ConstraintComputed state);

	/**
	 * by default the action has no behavior
	 * 
	 * @param console
	 *            the console to be updated by the action
	 * @throws Exception
	 *             if an exception occurs
	 * @throws NullPointerException
	 *             if the state is null
	 */
	public default void perform(CHIAAutomataConsole state) throws Exception {

	}

}
