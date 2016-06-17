package it.polimi.statemachine.automata;

import it.polimi.action.CHIAException;
import it.polimi.console.CHIAAutomataConsole;
import it.polimi.statemachine.automata.states.Checked;
import it.polimi.statemachine.automata.states.ConstraintComputed;
import it.polimi.statemachine.automata.states.Init;
import it.polimi.statemachine.automata.states.ModelLoaded;
import it.polimi.statemachine.automata.states.PropertyLoaded;
import it.polimi.statemachine.automata.states.Ready;

public interface AutomataAction {

	public AutomataState visit(Init state) throws CHIAException;
	
	public boolean isPerformable(Init state);

	public AutomataState visit(ModelLoaded modelLoaded) throws CHIAException;

	public boolean isPerformable(ModelLoaded modelLoaded);

	public AutomataState visit(Ready ready) throws CHIAException;

	public boolean isPerformable(Ready ready);

	public AutomataState visit(PropertyLoaded propertyLoaded) throws CHIAException;

	public boolean isPerformable(PropertyLoaded propertyLoaded);

	public AutomataState visit(Checked checked)  throws CHIAException;

	public boolean isPerformable(Checked checked);

	public AutomataState visit(ConstraintComputed constraintComputed)  throws CHIAException;

	public boolean isPerformable(ConstraintComputed constraintComputed);
	
	public default void perform(CHIAAutomataConsole console) throws Exception{
		
	}
	
}
