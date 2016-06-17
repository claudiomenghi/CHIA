package it.polimi.statemachine.automata.actions.helper;

import it.polimi.console.CHIAAutomataConsole;
import it.polimi.statemachine.automata.AutomataAction;
import it.polimi.statemachine.automata.actions.ReadFileProperty;

import java.io.Writer;

public class ReadFilePropertyHelper implements AutomataActionHelper{
	 @Override
     public String getCommand() {
         return "lp";
     }

     @Override
     public String getDescription() {
         return "Is used to load the property from an XML file. The XML file must mach the BA.xsd.";
     }

     @Override
     public String getCommandMeaning() {
         return "loadProperty";
     }

     @Override
     public String getParams() {
         return "file";
     }

     /**
      * returns the String representation of the command
      * 
      * @return the String representation of the command
      */
     @Override
     public String toString() {
         return "SYNOPSIS \n \t" + this.getCommand() + "\n" + "NAME \n \t"
                 + getCommandMeaning() + "\n"
                 + ("PARAMS \n \t" + this.getParams()) + "\n"
                 + "DESCRIPTION " + "\n \t" + this.getDescription() + " \n ";
     }

     @Override
     public boolean requiresParams() {
         return true;
     }

	@Override
	public AutomataAction getAction(String command, CHIAAutomataConsole console, Writer out){
		String filePath=command.split(" ")[1];
		return new ReadFileProperty(out, filePath);
	}

}
