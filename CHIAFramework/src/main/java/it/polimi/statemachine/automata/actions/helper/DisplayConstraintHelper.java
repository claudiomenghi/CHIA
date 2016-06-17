package it.polimi.statemachine.automata.actions.helper;

import it.polimi.console.CHIAAutomataConsole;
import it.polimi.statemachine.automata.AutomataAction;
import it.polimi.statemachine.automata.actions.DisplayConstraint;

import java.io.Writer;

public class DisplayConstraintHelper implements AutomataActionHelper{

	@Override
	public String getCommandMeaning() {
		return "displayConstraint";
	}

	@Override
	public String getCommand() {
		return "dispc";
	}

	@Override
	public String getDescription() {
		return "It is used to display the constraint into the console.";
	}

	@Override
	public String getParams() {
		return null;
	}

	@Override
	public boolean requiresParams() {
		return false;
	}

	/**
	 * returns the String representation of the command
	 * 
	 * @return the String representation of the command
	 */
	@Override
	public String toString() {
		return "SYNOPSIS \n \t" + this.getCommand() + "\n" + "NAME \n \t"
				+ getCommandMeaning() + "\n" + "DESCRIPTION " + "\n \t"
				+ this.getDescription() + " \n ";
	}

	@Override
	public AutomataAction getAction(String command, CHIAAutomataConsole console, Writer out){
		return new DisplayConstraint(out);
	}
}
