package it.polimi.statemachine.automata.actions.helper;

import it.polimi.console.CHIAAutomataConsole;
import it.polimi.statemachine.automata.AutomataAction;
import it.polimi.statemachine.automata.actions.Check;

import java.io.Writer;

public class CheckHelper implements AutomataActionHelper{

	@Override
    public String getCommand() {
        return "ck";
    }

    @Override
    public String getDescription() {
        return "Is used to check the model against the specified claim. Before running the model checking procedure it is necessary to load the model and the claim to be considered.";
    }

    @Override
    public String getCommandMeaning() {
        return "check";
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
		return new Check(out);
	}

}
