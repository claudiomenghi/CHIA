package it.polimi.statemachine.automata.actions;

import java.io.Writer;
import java.util.Set;

import it.polimi.action.CHIAException;
import it.polimi.console.CHIAAutomataConsole;
import it.polimi.statemachine.ActionHelper;
import it.polimi.statemachine.automata.AutomataAction;
import it.polimi.statemachine.automata.AutomataState;
import it.polimi.statemachine.automata.actions.helper.AutomataActionHelper;
import it.polimi.statemachine.automata.states.Checked;
import it.polimi.statemachine.automata.states.ConstraintComputed;
import it.polimi.statemachine.automata.states.Init;
import it.polimi.statemachine.automata.states.ModelLoaded;
import it.polimi.statemachine.automata.states.PropertyLoaded;
import it.polimi.statemachine.automata.states.Ready;

public class Help implements AutomataAction{

	private final String command;
	private final Writer out;
	private final Set<AutomataActionHelper> commands;
	
	public Help(String command, Set<AutomataActionHelper> commands, Writer out){
		this.command=command;
		this.out=out;
		this.commands=commands;
	}
	
	@Override
	public AutomataState visit(Init state) throws CHIAException {
		return state;
	}

	@Override
	public boolean isPerformable(Init state) {
		return true;
	}

	@Override
	public AutomataState visit(ModelLoaded modelLoaded) throws CHIAException {
		return modelLoaded;
	}

	@Override
	public boolean isPerformable(ModelLoaded modelLoaded) {
		return true;
	}

	@Override
	public AutomataState visit(Ready ready) throws CHIAException {
		return ready;
	}

	@Override
	public boolean isPerformable(Ready ready) {
		return true;
	}

	@Override
	public AutomataState visit(PropertyLoaded propertyLoaded) throws CHIAException {
		return propertyLoaded;
	}

	@Override
	public boolean isPerformable(PropertyLoaded propertyLoaded) {
		return true;
	}

	@Override
	public AutomataState visit(Checked checked) throws CHIAException {
		return checked;
	}

	@Override
	public boolean isPerformable(Checked checked) {
		return true;
	}

	@Override
	public AutomataState visit(ConstraintComputed constraintComputed)
			throws CHIAException {
		return constraintComputed;
	}

	@Override
	public boolean isPerformable(ConstraintComputed constraintComputed) {
		return true;
	}
	
	 @Override
     public void perform(CHIAAutomataConsole console)
             throws Exception {
         boolean founded = false;
         String commandOfInterest = command
                 .substring(command.indexOf(" ") + 1);
         
         for (ActionHelper<CHIAAutomataConsole> analyzedCommand : this.commands) {
             if (commandOfInterest.equals(analyzedCommand.getCommand())) {
                 out.write(analyzedCommand.toString());
                 founded = true;
             }
         }
         if (!founded) {
             out.write("The command: " + commandOfInterest
                     + " is not a valid command");
         }
     }
}
