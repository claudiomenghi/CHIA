package it.polimi.statemachine.replacement.action.helper;

import it.polimi.console.CHIAReplacementConsole;
import it.polimi.statemachine.replacement.ReplacementAction;
import it.polimi.statemachine.replacement.action.Help;

import java.io.Writer;

public class HelpHelper implements ReplacementActionHelper {

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
          return true;
      }


	@Override
	public ReplacementAction getAction(String command, CHIAReplacementConsole console,
			Writer out) {
		return new Help(command,
				console.getCompleter().getAutomataCompleters(), out);
	}
}