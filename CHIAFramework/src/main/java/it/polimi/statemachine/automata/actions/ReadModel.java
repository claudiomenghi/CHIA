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

import com.google.common.base.Preconditions;

/**
 * reads the model from a file
 * 
 * @author Claudio Menghi
 *
 */
public class ReadModel implements AutomataAction {

	/**
	 * the path of the file from which the model must be loaded
	 */
	private final String modelFilePath;

	/**
	 * the writer used to print messages
	 */
	private final Writer out;

	/**
	 * loads the model from the specified path
	 * 
	 * @param modelFilePath
	 *            is the path of the file that contains the model
	 * 
	 * @throws NullPointerException
	 *             if one of the arguments is null
	 * 
	 * @throws IllegalArgumentException
	 *             if the file does not exist
	 */
	public ReadModel(String modelFilePath, Writer out) {
		Preconditions.checkNotNull(out, "The out cannot be null");
		Preconditions.checkNotNull(modelFilePath,
				"The path of the model cannot be null");
		Preconditions.checkArgument(Files.exists(
				Paths.get(modelFilePath), LinkOption.NOFOLLOW_LINKS),
				"The file " + modelFilePath + " does not exists");

		this.modelFilePath = modelFilePath;
		this.out = out;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AutomataState visit(Init state) {
		Preconditions.checkNotNull(state, "The state cannot be null");
		return new ModelLoaded();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPerformable(Init state) {
		Preconditions.checkNotNull(state, "The state cannot be null");
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AutomataState visit(ModelLoaded state) {
		Preconditions.checkNotNull(state, "The state cannot be null");
		return state;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPerformable(ModelLoaded state) {
		Preconditions.checkNotNull(state, "The state cannot be null");
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AutomataState visit(Ready state) {
		Preconditions.checkNotNull(state, "The state cannot be null");
		return state;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPerformable(Ready state) {
		Preconditions.checkNotNull(state, "The state cannot be null");
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AutomataState visit(PropertyLoaded state) {
		Preconditions.checkNotNull(state, "The state cannot be null");
		return new Ready();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPerformable(PropertyLoaded state) {
		Preconditions.checkNotNull(state, "The state cannot be null");
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AutomataState visit(Checked state) throws CHIAException {
		Preconditions.checkNotNull(state, "The state cannot be null");
		return new Ready();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPerformable(Checked state) {
		Preconditions.checkNotNull(state, "The state cannot be null");
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AutomataState visit(ConstraintComputed state)
			throws CHIAException {
		Preconditions.checkNotNull(state, "The state cannot be null");
		return new Ready();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPerformable(ConstraintComputed state) {
		Preconditions.checkNotNull(state, "The state cannot be null");
		return true;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void perform(CHIAAutomataConsole console) throws Exception {
		Preconditions.checkNotNull(console, "The console cannot be null");

		ModelReader action = new ModelReader(modelFilePath);
		console.setModel(action.perform());
		this.out.write("Model readed"+"\n");
		this.out.write("N° states: " + console.getModel().getStates().size()+"\n");
		this.out.write("N° transitions:"
				+ console.getModel().getTransitions().size()+"\n");
		this.out.flush();
	}
}
