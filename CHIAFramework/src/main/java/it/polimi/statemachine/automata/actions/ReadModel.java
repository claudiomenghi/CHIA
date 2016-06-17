package it.polimi.statemachine.automata.actions;

import it.polimi.action.CHIAException;
import it.polimi.automata.io.in.ModelReader;
import it.polimi.console.CHIAAutomataConsole;
import it.polimi.statemachine.automata.AutomataAction;
import it.polimi.statemachine.automata.AutomataState;
import it.polimi.statemachine.automata.states.Checked;
import it.polimi.statemachine.automata.states.ConstraintComputed;
import it.polimi.statemachine.automata.states.Init;
import it.polimi.statemachine.automata.states.ModelLoaded;
import it.polimi.statemachine.automata.states.PropertyLoaded;
import it.polimi.statemachine.automata.states.Ready;

import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;

public class ReadModel implements AutomataAction {

	private final String modelFilePath;

	private final Writer out;

	/**
	 * loads the model from the specified path
	 * 
	 * @param modelFilePath
	 *            is the path of the file that contains the model
	 * 
	 * @throws NullPointerException
	 *             if the path of the file is null
	 * @throws IllegalArgumentException
	 *             if the file does not exist
	 */
	public ReadModel(String modelFilePath, Writer out) {
		com.google.common.base.Preconditions.checkNotNull(modelFilePath,
				"The path of the model cannot be null");
		com.google.common.base.Preconditions.checkArgument(Files.exists(
				Paths.get(modelFilePath), LinkOption.NOFOLLOW_LINKS),
				"The file "+modelFilePath+" does not exists");

		this.modelFilePath = modelFilePath;
		this.out=out;
	}

	@Override
	public void perform(CHIAAutomataConsole console) throws Exception {

		ModelReader action = new ModelReader(modelFilePath);
		console.setModel(action.perform());
		this.out.write("Model readed");
		this.out.write("N° states: " + console.getModel().getStates().size());
		this.out.write("N° transitions:" + console.getModel().getTransitions().size());

	}

	@Override
	public AutomataState visit(Init state) {
		return new ModelLoaded();
	}

	@Override
	public boolean isPerformable(Init state) {
		return true;
	}

	@Override
	public AutomataState visit(ModelLoaded modelLoaded) {
		return modelLoaded;
	}

	@Override
	public boolean isPerformable(ModelLoaded modelLoaded) {
		return true;
	}

	@Override
	public AutomataState visit(Ready ready) {
		return ready;
	}

	@Override
	public boolean isPerformable(Ready ready) {
		return true;
	}

	@Override
	public AutomataState visit(PropertyLoaded propertyLoaded) {
		return new Ready();
	}

	@Override
	public boolean isPerformable(PropertyLoaded propertyLoaded) {
		return true;
	}

	@Override
	public AutomataState visit(Checked checked) throws CHIAException {
		return new Ready();
	}

	@Override
	public boolean isPerformable(Checked checked) {
		return true;
	}

	@Override
	public AutomataState visit(ConstraintComputed constraintComputed)
			throws CHIAException {
		return new Ready();
	}

	@Override
	public boolean isPerformable(ConstraintComputed constraintComputed) {
		return true;
	}
	
	 

      /**
       * @throws NullPointerException
       *             if the command to be executed or the console is null
       * @throws IllegalArgumentException
       *             if the command requires a parameter but no parameters are
       *             provided
       */
//      @Override
//      public void executeCommand(String command, CHIAAutomataConsole console)
//              throws Exception {
//          Preconditions.checkNotNull(command,
//                  "The command to be executed cannot be null");
//          Preconditions.checkNotNull(console,
//                  "The console to be updated cannot be null");
//          Preconditions.checkArgument(command.contains(" "),
//                  "No argument is provided");
//
//          console.loadModel(command.substring(command.indexOf(" ") + 1)
//                  .replaceAll(" +$", ""));
//      }

     

}
