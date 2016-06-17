package it.polimi.statemachine.automata.actions;

import it.polimi.automata.io.in.ClaimReader;
import it.polimi.console.CHIAAutomataConsole;

import java.io.Writer;

public class ReadFileProperty extends ReadProperty {

	private final Writer out;

	private final String claimFilePath;

	public ReadFileProperty(Writer out, String claimFilePath) {
		this.out = out;
		this.claimFilePath = claimFilePath;
	}

	@Override
	public void perform(CHIAAutomataConsole console) throws Exception {
		ClaimReader action = new ClaimReader(claimFilePath);
		console.setClaim( action.perform());
		out.write("Property loaded");
		out.write("N° states: " + console.getClaim().getStates().size());
		out.write("N° transitions:" + console.getClaim().getTransitions().size());

	}
	
	
   //  @Override
   /*  public void executeCommand(String command, CHIAAutomataConsole console)
             throws Exception {
         console.loadProperty(command.substring(command.indexOf(" ") + 1)
                 .replaceAll(" +$", ""));
     }
*/
}
