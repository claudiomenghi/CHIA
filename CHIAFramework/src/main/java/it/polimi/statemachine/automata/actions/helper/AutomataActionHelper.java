package it.polimi.statemachine.automata.actions.helper;

import java.io.Writer;

import it.polimi.console.CHIAAutomataConsole;
import it.polimi.statemachine.ActionHelper;
import it.polimi.statemachine.automata.AutomataAction;

public interface AutomataActionHelper extends ActionHelper<CHIAAutomataConsole>{

	public AutomataAction getAction(String command, CHIAAutomataConsole console, Writer out);
}
