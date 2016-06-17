package it.polimi.statemachine.replacement.action.helper;

import it.polimi.console.CHIAReplacementConsole;
import it.polimi.statemachine.replacement.ReplacementAction;
import it.polimi.statemachine.replacement.action.SaveRefinement;

import java.io.Writer;

public class SaveRefinementHelper implements ReplacementActionHelper{

	@Override
    public String getCommandMeaning() {
        return "save refinement";
    }

    @Override
    public String getCommand() {
        return "sref";
    }

    @Override
    public String getDescription() {
        return "starting from the intial model and the replacement associated to one of its black box states generate the corresponding"
                + "refinement.";
    }

    @Override
    public String getParams() {
        return "The path of the file where the refinement must be saved";
    }

    @Override
    public boolean requiresParams() {
        return true;
    }

	@Override
	public ReplacementAction getAction(String command,
			CHIAReplacementConsole console, Writer out) {
		return new SaveRefinement(out, 
				command.substring(command.indexOf(" ") + 1)
                .replaceAll(" +$", ""));
	}

}
