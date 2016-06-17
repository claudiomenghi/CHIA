package it.polimi.statemachine.replacement.action;

import it.polimi.action.CHIAException;
import it.polimi.automata.io.out.IBAToElementTrasformer;
import it.polimi.automata.io.out.XMLWriter;
import it.polimi.console.CHIAReplacementConsole;
import it.polimi.constraints.components.RefinementGenerator;
import it.polimi.statemachine.replacement.ReplacementAction;
import it.polimi.statemachine.replacement.ReplacementState;
import it.polimi.statemachine.replacement.states.Checked;
import it.polimi.statemachine.replacement.states.ConstraintLoaded;
import it.polimi.statemachine.replacement.states.Init;
import it.polimi.statemachine.replacement.states.Ready;
import it.polimi.statemachine.replacement.states.ReplacementLoaded;

import java.io.File;
import java.io.Writer;

public class SaveRefinement implements ReplacementAction {

	private final String refinementFilePath;

	private final Writer out;

	public SaveRefinement(Writer out, String constraintFilePath) {
		this.refinementFilePath = constraintFilePath;
		this.out = out;
	}

	
	@Override
	public ReplacementState visit(Init init) throws CHIAException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPerformable(Init init) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ReplacementState visit(ConstraintLoaded constraintLoaded)
			throws CHIAException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPerformable(ConstraintLoaded constraintLoaded) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ReplacementState visit(Ready ready) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPerformable(Ready ready) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ReplacementState visit(Checked checked) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPerformable(Checked checked) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ReplacementState visit(ReplacementLoaded replacementLoaded)
			throws CHIAException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPerformable(ReplacementLoaded replacementLoaded) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void perform(CHIAReplacementConsole console) throws Exception {
		RefinementGenerator action = new RefinementGenerator(
				console.getModel(),
				console.getReplacement());
		console.setRefinement(action.perform());

		new XMLWriter(new File(this.refinementFilePath),
				new IBAToElementTrasformer().transform(console.getRefinement()))
				.perform();
		out.write("Refinement written in the file "+this.refinementFilePath);
	}

}
