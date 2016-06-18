package it.polimi.statemachine.replacement.action.helper;

import it.polimi.console.CHIAReplacementConsole;
import it.polimi.statemachine.replacement.ReplacementAction;
import it.polimi.statemachine.replacement.action.DisplayConstraint;

import java.io.Writer;

public class DisplayConstraintHelper implements ReplacementActionHelper {
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

	@Override
	public ReplacementAction getAction(String command,
			CHIAReplacementConsole console, Writer out) {
		return new DisplayConstraint(out);
	}

	/**
	 * returns the String representation of the command
	 * 
	 * @return the String representation of the command
	 */
	@Override
	public String toString() {
		return "SYNOPSIS \n \t" + this.getCommand() + "\n" + "NAME \n \t"
				+ getCommandMeaning() + "\n" + "PARAMS \n \t"
				+ this.getParams() + "\n" + "DESCRIPTION " + "\n \t"
				+ this.getDescription() + " \n ";
	}

}
