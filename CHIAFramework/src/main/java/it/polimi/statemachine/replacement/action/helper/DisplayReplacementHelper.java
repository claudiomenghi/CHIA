package it.polimi.statemachine.replacement.action.helper;

import it.polimi.console.CHIAReplacementConsole;
import it.polimi.statemachine.replacement.ReplacementAction;
import it.polimi.statemachine.replacement.action.DisplayReplacement;

import java.io.Writer;

public class DisplayReplacementHelper implements ReplacementActionHelper {

	@Override
	public String getCommandMeaning() {
		return "displayReplacement";
	}

	@Override
	public String getCommand() {
		return "dispr";
	}

	@Override
	public String getDescription() {
		return "It is used to display the replacement into the console.";
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
		return new DisplayReplacement(out);
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
