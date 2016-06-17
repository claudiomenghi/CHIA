package it.polimi.statemachine.automata.actions;

import it.polimi.action.CHIAException;
import it.polimi.console.CHIAAutomataConsole;
import it.polimi.constraints.io.out.constraint.ConstraintWriter;
import it.polimi.statemachine.automata.AutomataAction;
import it.polimi.statemachine.automata.AutomataState;
import it.polimi.statemachine.automata.states.Checked;
import it.polimi.statemachine.automata.states.ConstraintComputed;
import it.polimi.statemachine.automata.states.Init;
import it.polimi.statemachine.automata.states.ModelLoaded;
import it.polimi.statemachine.automata.states.PropertyLoaded;
import it.polimi.statemachine.automata.states.Ready;

import java.io.Writer;

public class WriteConstraint implements AutomataAction {

	private final Writer out;

	private final String constraintFilePath;

	public WriteConstraint(Writer out, String constraintFilePath) {
		this.out = out;
		this.constraintFilePath = constraintFilePath;
	}
	
	
	@Override
	public void perform(CHIAAutomataConsole console) throws Exception{

		ConstraintWriter constraintWriter = new ConstraintWriter(
				console.getConstraint(), constraintFilePath);
			constraintWriter.perform();
			out.write("Constraint saved");
		
	}

	@Override
	public AutomataState visit(Init state) throws CHIAException {
		throw new CHIAException(
				"You must compute the constraint before writing");
	}

	@Override
	public boolean isPerformable(Init state) {
		return false;
	}

	@Override
	public AutomataState visit(ModelLoaded modelLoaded) throws CHIAException {
		throw new CHIAException("You must compute the constraint before writing");
	}

	@Override
	public boolean isPerformable(ModelLoaded modelLoaded) {
		return false;
	}

	@Override
	public AutomataState visit(Ready ready) throws CHIAException {
		throw new CHIAException("You must compute the constraint before writing");
	}

	@Override
	public boolean isPerformable(Ready ready) {
		return false;
	}

	@Override
	public AutomataState visit(PropertyLoaded propertyLoaded) throws CHIAException {
		throw new CHIAException("You must compute the constraint before writing");
	}

	@Override
	public boolean isPerformable(PropertyLoaded propertyLoaded) {
		return false;
	}

	@Override
	public AutomataState visit(Checked checked) throws CHIAException {
		throw new CHIAException("You must compute the constraint before writing");
	}

	@Override
	public boolean isPerformable(Checked checked) {
		return false;
	}

	@Override
	public AutomataState visit(ConstraintComputed constraintComputed)
			throws CHIAException {
		return constraintComputed;
	}

	@Override
	public boolean isPerformable(ConstraintComputed constraintComputed) {
		return true;
	}
	
	
      /* @Override
      public void executeCommand(String command, CHIAAutomataConsole console)
              throws Exception {
          console.saveConstraint(command.substring(command.indexOf(" ") + 1)
                  .replaceAll(" +$", ""));

      }
*/
     

}
