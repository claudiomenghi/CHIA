package it.polimi.statemachine.automata.actions.helper;

import it.polimi.console.CHIAAutomataConsole;
import it.polimi.statemachine.automata.AutomataAction;
import it.polimi.statemachine.automata.actions.ReadLTLProperty;

import java.io.Writer;

public class ReadLTLPropertyHelper implements AutomataActionHelper{
	  @Override
      public String getCommandMeaning() {
          return "loadLTLProperty";
      }

      @Override
      public String getCommand() {
          return "lpLTL";
      }

      @Override
      public String getDescription() {
          return "It is used to load the property from an LTL formula";
      }

      @Override
      public String getParams() {
          return "LTLFormula: is the LTL formula that represents the property."
                  + "The LTL formula can be created starting from a set of propositional symbol \n"
                  + "true, false any lowercase string"
                  + "A set of boolean operators,\n "
                  + "\t ! (negation)\n"
                  + "\t -> (implication) \n"
                  + "\t <-> (equivalence) \n"
                  + "\t ^ (and)\n"
                  + "\t V (or),"
                  + "A set of temporal operators,\n"
                  + "\t [] (always)\n"
                  + "\t <> (eventually)" + "\t U (until)" + "\t X (next).";
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
		return new ReadLTLProperty(out, ltlProperty);
	}
}
