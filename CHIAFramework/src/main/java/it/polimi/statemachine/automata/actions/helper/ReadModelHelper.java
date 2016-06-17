package it.polimi.statemachine.automata.actions.helper;

import it.polimi.console.CHIAAutomataConsole;
import it.polimi.statemachine.automata.AutomataAction;
import it.polimi.statemachine.automata.actions.ReadModel;

import java.io.Writer;

public class ReadModelHelper  implements AutomataActionHelper{

	 @Override
     public String getCommand() {
         return "lm";
     }

     @Override
     public String getDescription() {
         return "Is used to load the model from an XML file. The XML file must mach the IBA.xsd.";
     }

     @Override
     public String getCommandMeaning() {
         return "loadModel";
     }

     @Override
     public String getParams() {
         return "modelFilePath:  the path of the file that contains the model to be checked";
     }

     @Override
     public boolean requiresParams() {

         return true;
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
	public AutomataAction getAction(String command, CHIAAutomataConsole console, Writer out){
		String ltlProperty=command.split(" ")[1];
		return new ReadModel(ltlProperty, out);
	}
}
