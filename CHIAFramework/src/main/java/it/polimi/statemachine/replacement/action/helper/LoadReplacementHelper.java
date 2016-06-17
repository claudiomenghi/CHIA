package it.polimi.statemachine.replacement.action.helper;

import it.polimi.console.CHIAReplacementConsole;
import it.polimi.statemachine.replacement.ReplacementAction;
import it.polimi.statemachine.replacement.action.LoadRepacement;

import java.io.Writer;

public class LoadReplacementHelper implements ReplacementActionHelper{

	 @Override
     public String getCommandMeaning() {
         return "loadReplacement";
     }

     @Override
     public String getCommand() {
         return "lr";
     }

     @Override
     public String getDescription() {
         return "IT is used to load the replacement from an XML file. The XML file must mach the Replacement.xsd";
     }

     @Override
     public String getParams() {
         return "replacementFilePath: is the path of the file that contains the replacement to be considered";
     }

     @Override
     public boolean requiresParams() {
         return true;
     }

     @Override
 	public ReplacementAction getAction(String command,
 			CHIAReplacementConsole console, Writer out) {
 	
    	 return new LoadRepacement(out, command.substring(command.indexOf(" ") + 1)
		         .replaceAll(" +$", ""));
     }

     /**
      * returns the String representation of the command
      * 
      * @return the String representation of the command
      */
     @Override
     public String toString() {
         return "SYNOPSIS \n \t" + this.getCommand() + "\n" + "NAME \n \t"
                 + getCommandMeaning() + "\n" + "PARAMS \n \t"
                 + this.getParams() + "\n" + "DESCRIPTION " + "\n \t"
                 + this.getDescription() + " \n ";
     }

}
