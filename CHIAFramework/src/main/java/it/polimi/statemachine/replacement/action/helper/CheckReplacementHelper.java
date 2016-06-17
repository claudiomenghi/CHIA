package it.polimi.statemachine.replacement.action.helper;

import it.polimi.console.CHIAReplacementConsole;
import it.polimi.statemachine.replacement.ReplacementAction;
import it.polimi.statemachine.replacement.action.Check;

import java.io.Writer;

public class CheckReplacementHelper implements ReplacementActionHelper {

	@Override
	public String getCommandMeaning() {
		return "check";
	}

	@Override
	public String getCommand() {
		return "ck";
	}

	@Override
	public String getDescription() {
		return "It used to check the replacement against the constraint previously generated.";
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
			CHIAReplacementConsole console, Writer out){
		return new Check(out);		
	}

	/**
	 * returns the String representation of the command
	 * 
	 * @return the String representation of the command
	 */
	@Override
	public String toString() {
		return "SYNOPSIS \n \t" + this.getCommand() + "\n" + "NAME \n \t"
				+ getCommandMeaning() + "\n" + "PARAMS \n \t" + "\n"
				+ "DESCRIPTION " + "\n \t" + this.getDescription() + " \n ";
	}

}
