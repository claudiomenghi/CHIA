package it.polimi.statemachine.automata.actions.helper;

import it.polimi.console.CHIAAutomataConsole;
import it.polimi.statemachine.automata.AutomataAction;
import it.polimi.statemachine.automata.actions.DisplayProperty;

import java.io.Writer;

public class DisplayPropertyHelper implements AutomataActionHelper{
	 @Override
     public String getCommand() {
         return "dispp";
     }

     @Override
     public String getDescription() {
         return "It is used to display the property into the console.";
     }

     @Override
     public String getCommandMeaning() {
         return "displayProperty";
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
		return new DisplayProperty(out);
	}
}
