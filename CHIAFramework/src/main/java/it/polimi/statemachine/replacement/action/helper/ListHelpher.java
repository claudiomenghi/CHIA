package it.polimi.statemachine.replacement.action.helper;

import it.polimi.console.CHIAReplacementConsole;
import it.polimi.statemachine.replacement.ReplacementAction;
import it.polimi.statemachine.replacement.action.ListAction;

import java.io.Writer;

public class ListHelpher implements ReplacementActionHelper {

	@Override
	public String getCommandMeaning() {
		return "list";
	}

	@Override
	public String getCommand() {
		return "list";
	}

	@Override
	public String getDescription() {
		return "shows the available commands.";
	}

	@Override
	public String getParams() {
		return "";
	}

	@Override
	public boolean requiresParams() {
		return true;
	}

	@Override
	public ReplacementAction getAction(String command,
			CHIAReplacementConsole console, Writer out) {
		return new ListAction(console.getCompleter().getAutomataCompleters(),
				out);
	}
}
