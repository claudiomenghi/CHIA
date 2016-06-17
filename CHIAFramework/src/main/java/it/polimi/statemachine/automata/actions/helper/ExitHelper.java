package it.polimi.statemachine.automata.actions.helper;

import it.polimi.console.CHIAAutomataConsole;
import it.polimi.statemachine.automata.AutomataAction;
import it.polimi.statemachine.automata.actions.Exit;

import java.io.Writer;

public class ExitHelper implements AutomataActionHelper{

	@Override
    public String getCommand() {
        return "exit";
    }

    @Override
    public String getDescription() {
        return "Returns to the CHIA main console.";
    }

    @Override
    public String getCommandMeaning() {
        return "exit";
    }

    @Override
    public String getParams() {
        return null;
    }

    /**
     * returns the String representation of the command
     * 
     * @return the String representation of the command
     */
    @Override
    public String toString() {
        return "SYNOPSIS \n \t" + this.getCommand() + "\n" + "NAME \n \t"
                + getCommandMeaning() + "\n" + "DESCRIPTION " + "\n \t"
                + this.getDescription() + " \n ";
    }

    @Override
    public boolean requiresParams() {
        return false;
    }

	@Override
	public AutomataAction getAction(String command, CHIAAutomataConsole console, Writer out){
		return new Exit();
	}
}
