package it.polimi.statemachine.automata.actions.helper;

import it.polimi.console.CHIAAutomataConsole;
import it.polimi.statemachine.automata.AutomataAction;
import it.polimi.statemachine.automata.actions.DisplayModel;

import java.io.Writer;

public class DisplayModelHelper implements AutomataActionHelper{

	 @Override
     public String getCommand() {
         return "dispm";
     }

     @Override
     public String getDescription() {
         return "It is used to display the model into the console.";
     }

     @Override
     public String getCommandMeaning() {
         return "displayModel";
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
                 + getCommandMeaning() + "\n" + "DESCRIPTION " + "\n \t"
                 + this.getDescription() + " \n ";
     }

	@Override
	public AutomataAction getAction(String command, CHIAAutomataConsole console, Writer out){
		return new DisplayModel(out);
	}
}
