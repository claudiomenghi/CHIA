package it.polimi.statemachine.replacement.action.helper;

import it.polimi.console.CHIAReplacementConsole;
import it.polimi.statemachine.ActionHelper;
import it.polimi.statemachine.replacement.ReplacementAction;

import java.io.Writer;


public interface ReplacementActionHelper extends ActionHelper<CHIAReplacementConsole>{

	public ReplacementAction getAction(String command, CHIAReplacementConsole console, Writer out);
}
