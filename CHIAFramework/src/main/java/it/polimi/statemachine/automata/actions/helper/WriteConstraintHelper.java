package it.polimi.statemachine.automata.actions.helper;

import it.polimi.console.CHIAAutomataConsole;
import it.polimi.statemachine.automata.AutomataAction;
import it.polimi.statemachine.automata.actions.WriteConstraint;

import java.io.Writer;

public class WriteConstraintHelper  implements AutomataActionHelper{
	  @Override
      public String getCommandMeaning() {
          return "saveConstraint";
      }

      @Override
      public String getCommand() {
          return "sc";
      }

      @Override
      public String getDescription() {
          return "It is used to save the constraint in an XML file.";
      }

      @Override
      public String getParams() {
          return "constraintFilePath: is the path of the file where the constraint must be saved";
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
                  + getCommandMeaning() + "\n" + "PARAMS \n \t"
                  + this.getParams() + "\n" + "DESCRIPTION " + "\n \t"
                  + this.getDescription() + " \n ";
      }

	@Override
	public AutomataAction getAction(String command, CHIAAutomataConsole console, Writer out){
		String file=command.split(" ")[1];
		return new WriteConstraint(out, file);
	}
}
