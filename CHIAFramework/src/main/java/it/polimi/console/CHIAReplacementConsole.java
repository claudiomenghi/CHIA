package it.polimi.console;

import it.polimi.automata.IBA;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy.AcceptingType;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.components.Replacement;
import it.polimi.replacementchecker.ReplacementChecker;
import it.polimi.statemachine.replacement.ReplacementCompleter;
import it.polimi.statemachine.replacement.ReplacementState;
import it.polimi.statemachine.replacement.states.Init;

/**
 * contains the console which is used for the replacement checking
 * 
 * @author Claudio Menghi
 *
 */
public class CHIAReplacementConsole {


	private final ReplacementCompleter completer;

	private ReplacementState chiaState;
	/**
	 * contains the constraint to be considered
	 */
	private Constraint constraint;
	/**
	 * contains the replacement to be considered
	 */
	private Replacement replacement;

	/**
	 * is the checker used in the replacement checking activity
	 */
	private ReplacementChecker replacementChecker;

	/**
	 * is the model from which the replacement is obtained
	 */
	private IBA model;

	/**
	 * contains the refinement obtained by the current model and the replacement
	 */
	private IBA refinement;

	private AcceptingType policy;

	/**
	 * creates a new replacement console
	 */
	public CHIAReplacementConsole() {
		this.policy = AcceptingType.BA;
		this.chiaState = new Init();
		this.completer=new ReplacementCompleter();
	}

	public IBA getModel() {
		return model;
	}

	public void setModel(IBA model) {
		this.model = model;
	}

	public Replacement getReplacement() {
		return replacement;
	}

	public void setReplacement(Replacement replacement) {
		this.replacement = replacement;
	}

	public Constraint getConstraint() {
		return constraint;
	}

	public AcceptingType getPolicy() {
		return policy;
	}

	public void setPolicy(AcceptingType policy) {
		this.policy = policy;
	}

	public void setConstraint(Constraint constraint) {
		this.constraint = constraint;
	}

	public ReplacementChecker getReplacementChecker() {
		return replacementChecker;
	}

	public void setReplacementChecker(ReplacementChecker replacementChecker) {
		this.replacementChecker = replacementChecker;
	}
	
	public IBA getRefinement() {
		return refinement;
	}

	public void setRefinement(IBA refinement) {
		this.refinement = refinement;
	}

	public ReplacementState getChiaState() {
		return chiaState;
	}

	public void setChiaState(ReplacementState chiaState) {
		this.chiaState = chiaState;
	}

	public ReplacementCompleter getCompleter() {
		return completer;
	}

	
}
