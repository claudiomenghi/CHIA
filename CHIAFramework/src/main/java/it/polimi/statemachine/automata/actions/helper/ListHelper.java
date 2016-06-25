package it.polimi.statemachine.automata.actions.helper;

import it.polimi.console.CHIAAutomataConsole;
import it.polimi.statemachine.automata.AutomataAction;
import it.polimi.statemachine.automata.actions.ListAction;

import java.io.Writer;

public class ListHelper implements AutomataActionHelper {

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
			return "shows all the possible commands.";
		}

		@Override
		public String getParams() {
			return "";
		}

		@Override
		public boolean requiresParams() {
			return false;
		}

		@Override
		public AutomataAction getAction(String command, CHIAAutomataConsole console,
				Writer out) {
			return new ListAction(
					console.getCompleter().getAutomataCompleters(), out);
		}
}
