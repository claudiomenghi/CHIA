package it.polimi.console;

import it.polimi.action.CHIAException;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.checker.Checker;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy.AcceptingType;
import it.polimi.constraintcomputation.ConstraintGenerator;
import it.polimi.constraints.Constraint;
import it.polimi.statemachine.automata.AutomataAction;
import it.polimi.statemachine.automata.AutomataCompleter;
import it.polimi.statemachine.automata.AutomataState;
import it.polimi.statemachine.automata.states.Init;

/**
 * Is the Console associated with the automata mode. It specifies the set of
 * action that can be performed when the automata mode is selected
 * 
 * @author Claudio Menghi
 *
 */
public class CHIAAutomataConsole {


	private final AutomataCompleter completer;
	
	/**
	 * contains the model of the system to be considered. Null if no model is
	 * loaded
	 */
	protected IBA model;


	/**
	 * contains the claim to be considered. Null if no claim is loaded
	 */
	protected BA claim;

	/**
	 * contains the model checker to be used in the model checking activity
	 */
	protected Checker checker;

	/**
	 * is the state of the application. The state changes in response to user
	 * inputs
	 */
	private AutomataState chiaState;

	/**
	 * contains the constraint associated with the specified model and claim if
	 * the model possibly satisfy the claim
	 */
	protected Constraint constraint;

	/**
	 * contains the accepting policy which specifies how the accepting states of
	 * the intersection automata are computed
	 */
	private AcceptingType policy;

	/**
	 * contains the engine used to compute the constraint
	 */
	private ConstraintGenerator cg;
	
	/**
	 * creates the {@link CHIAAutomataConsole}
	 * 
	 * @throws NullPointerException
	 *             if the out is null
	 */
	public CHIAAutomataConsole() {
		policy = AcceptingType.BA;
		chiaState = new Init();
		this.completer=new AutomataCompleter();
	}
	
	public Checker getChecker() {
		return checker;
	}

	public void setChecker(Checker checker) {
		this.checker = checker;
	}

	public Constraint getConstraint() {
		return constraint;
	}

	public void setConstraint(Constraint constraint) {
		this.constraint = constraint;
	}

	public ConstraintGenerator getCg() {
		return cg;
	}

	public void setCg(ConstraintGenerator cg) {
		this.cg = cg;
	}

	public BA getClaim() {
		return claim;
	}

	public void setClaim(BA claim) {
		this.claim = claim;
	}

	public IBA getModel() {
		return model;
	}

	public void setModel(IBA model) {
		this.model = model;
	}

	public AutomataState getChiaState() {
		return chiaState;
	}

	public void setChiaState(AutomataState chiaState) {
		this.chiaState = chiaState;
	}

	public AcceptingType getPolicy() {
		return policy;
	}

	public void setPolicy(AcceptingType policy) {
		this.policy = policy;
	}

	public void exit() {
	}

	public AutomataCompleter getCompleter() {
		return completer;
	}
	
	public void changeState(AutomataAction action) throws CHIAException{
		this.setChiaState(this.getChiaState()
				.next(action));
	}
	
	
}
