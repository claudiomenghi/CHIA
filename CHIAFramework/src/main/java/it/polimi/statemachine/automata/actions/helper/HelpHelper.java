package it.polimi.statemachine.automata.actions.helper;

import it.polimi.console.CHIAAutomataConsole;
import it.polimi.statemachine.automata.AutomataAction;
import it.polimi.statemachine.automata.actions.Help;

import java.io.Writer;

public class HelpHelper implements AutomataActionHelper {

	@Override
	public String getCommandMeaning() {
		return "help";
	}

	@Override
	public String getCommand() {
		return "help";
	}

	@Override
	public String getDescription() {
		return "shows the params of the command.";
	}

	@Override
	public String getParams() {
		return "command: is the command to be performed";
	}

	@Override
	public boolean requiresParams() {
		return false;
	}

	@Override
	public AutomataAction getAction(String command, CHIAAutomataConsole console,
			Writer out) {
		return new Help(command,
				console.getCompleter().getAutomataCompleters(), out);
	}
}
