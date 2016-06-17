package it.polimi.statemachine.replacement.action;

import it.polimi.action.CHIAException;
import it.polimi.console.CHIAReplacementConsole;
import it.polimi.statemachine.ActionHelper;
import it.polimi.statemachine.replacement.ReplacementAction;
import it.polimi.statemachine.replacement.ReplacementState;
import it.polimi.statemachine.replacement.action.helper.ReplacementActionHelper;
import it.polimi.statemachine.replacement.states.Checked;
import it.polimi.statemachine.replacement.states.ConstraintLoaded;
import it.polimi.statemachine.replacement.states.Init;
import it.polimi.statemachine.replacement.states.Ready;
import it.polimi.statemachine.replacement.states.ReplacementLoaded;

import java.io.Writer;
import java.util.Set;

public class Help implements ReplacementAction {

	private final String command;
	private final Writer out;
	private final Set<ReplacementActionHelper> commands;
	
	public Help(String command, Set<ReplacementActionHelper> commands, Writer out){
		this.command=command;
		this.out=out;
		this.commands=commands;
	}
	
	@Override
	public ReplacementState visit(Init init) throws CHIAException {
		return init;
	}

	@Override
	public boolean isPerformable(Init init) {
		return true;
	}

	@Override
	public ReplacementState visit(ConstraintLoaded constraintLoaded) {
		return constraintLoaded;
	}

	@Override
	public boolean isPerformable(ConstraintLoaded constraintLoaded) {
		return true;
	}

	@Override
	public ReplacementState visit(Ready ready) {
		return ready;
	}

	@Override
	public boolean isPerformable(Ready ready) {
		return true;
	}

	@Override
	public ReplacementState visit(Checked checked) {
		return checked;
	}

	@Override
	public boolean isPerformable(Checked checked) {
		return true;
	}

	@Override
	public ReplacementState visit(ReplacementLoaded replacementLoaded) {
		return replacementLoaded;
	}

	@Override
	public boolean isPerformable(ReplacementLoaded replacementLoaded) {
		return true;
	}
	
	 @Override
     public void perform(CHIAReplacementConsole console)
             throws Exception {
         boolean founded = false;
         String commandOfInterest = command
                 .substring(command.indexOf(" ") + 1);
         
         for (ActionHelper<CHIAReplacementConsole> analyzedCommand : this.commands) {
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
