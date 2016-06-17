package it.polimi.statemachine.replacement;

import it.polimi.action.CHIAException;
import it.polimi.console.CHIAReplacementConsole;
import it.polimi.statemachine.replacement.states.Checked;
import it.polimi.statemachine.replacement.states.ConstraintLoaded;
import it.polimi.statemachine.replacement.states.Init;
import it.polimi.statemachine.replacement.states.Ready;
import it.polimi.statemachine.replacement.states.ReplacementLoaded;

public interface ReplacementAction {

	public ReplacementState visit(Init init) throws CHIAException;

	public boolean isPerformable(Init init);

	public ReplacementState visit(ConstraintLoaded constraintLoaded) throws CHIAException;

	public boolean isPerformable(ConstraintLoaded constraintLoaded);

	public ReplacementState visit(Ready ready);

	public boolean isPerformable(Ready ready);

	public ReplacementState visit(Checked checked);

	public boolean isPerformable(Checked checked);

	public ReplacementState visit(ReplacementLoaded replacementLoaded) throws CHIAException;

	public boolean isPerformable(ReplacementLoaded replacementLoaded);

	void perform(CHIAReplacementConsole console) throws Exception;

}
