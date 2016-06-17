package it.polimi.statemachine.replacement.action.helper;

import it.polimi.console.CHIAReplacementConsole;
import it.polimi.statemachine.replacement.ReplacementAction;
import it.polimi.statemachine.replacement.action.LoadConstraint;

import java.io.Writer;

public class LoadConstraintHelper implements ReplacementActionHelper {

	@Override
	public String getCommandMeaning() {
		return "loadConstraint";
	}

	@Override
	public String getCommand() {
		return "lc";
	}

	@Override
	public String getDescription() {
		return "It is used to load the constraint from an XML file. The XML file must mach the Constraint.xsd";
	}

	@Override
	public String getParams() {
		return "constraintFilePath:  the path of the file that contains the constraint to be loaded";
	}

	@Override
	public boolean requiresParams() {
		return true;
	}

	@Override
	public ReplacementAction getAction(String command,
			CHIAReplacementConsole console, Writer out) {
		return new LoadConstraint(out, command.substring(
				command.indexOf(" ") + 1).replaceAll(" +$", ""));
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
