package it.polimi.statemachine.automata.actions.helper;

import it.polimi.console.CHIAAutomataConsole;
import it.polimi.statemachine.automata.AutomataAction;
import it.polimi.statemachine.automata.actions.ComputeConstraint;

import java.io.Writer;

public class ComputeConstraintHelper implements AutomataActionHelper{

	 @Override
	    public String getCommandMeaning() {
	        return "computeConstraint";
	    }

	    @Override
	    public String getCommand() {
	        return "cc";
	    }

	    @Override
	    public String getDescription() {
	        return "Is used to compute the constraint corresponding to the model and the specified claim.";
	    }

	    @Override
	    public String getParams() {
	        return null;
	    }

	    @Override
	    public boolean requiresParams() {
	        return false;
	    }


	    /**
	     * returns the String representation of the command
	     * 
	     * @return the String representation of the command
	     */
	    @Override
	    public String toString() {
	        return "SYNOPSIS \n \t" + this.getCommand() + "\n" + "NAME \n \t"
	                + getCommandMeaning() + "\n PARAMS \n \t"
	                + this.getParams() + "\n" + "DESCRIPTION " + "\n \t"
	                + this.getDescription() + " \n ";
	    }

		@Override
		public AutomataAction getAction(String command, CHIAAutomataConsole console, Writer out){
			return new ComputeConstraint(out);
		}
}
