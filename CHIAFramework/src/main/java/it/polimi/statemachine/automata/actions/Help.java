package it.polimi.statemachine.automata.actions;

import java.io.Writer;
import java.util.Set;

import com.google.common.base.Preconditions;

import it.polimi.action.CHIAException;
import it.polimi.console.CHIAAutomataConsole;
import it.polimi.statemachine.ActionHelper;
import it.polimi.statemachine.automata.AutomataAction;
import it.polimi.statemachine.automata.AutomataState;
import it.polimi.statemachine.automata.actions.helper.AutomataActionHelper;
import it.polimi.statemachine.automata.states.Checked;
import it.polimi.statemachine.automata.states.ConstraintComputed;
import it.polimi.statemachine.automata.states.Init;
import it.polimi.statemachine.automata.states.ModelLoaded;
import it.polimi.statemachine.automata.states.PropertyLoaded;
import it.polimi.statemachine.automata.states.Ready;

/**
 * contains the help action
 * 
 * @author Claudio Menghi
 *
 */
public class Help implements AutomataAction {

	/**
	 * The command the used requires the help
	 */
	private final String command;
	/**
	 * The writer used to write messages
	 */
	private final Writer out;
	/**
	 * The helper for the actions
	 */
	private final Set<AutomataActionHelper> commands;

	/**
	 * creates a new help action
	 * 
	 * @param command
	 *            the command to be considered
	 * @param commands
	 *            the helper of the different commands
	 * @param out
	 *            the writer used to write messages
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public Help(String command, Set<AutomataActionHelper> commands, Writer out) {
		Preconditions.checkNotNull(command, "The command cannot be null");
		Preconditions
				.checkNotNull(commands, "The set of helper cannot be null");
		Preconditions.checkNotNull(out, "The writer cannot be null");

		this.command = command;
		this.out = out;
		this.commands = commands;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AutomataState visit(Init state) throws CHIAException {
		Preconditions.checkNotNull(state, "The state cannot be null");
		return state;
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
	public AutomataState visit(ModelLoaded state) throws CHIAException {
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
	public AutomataState visit(Ready state) throws CHIAException {
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
	public AutomataState visit(PropertyLoaded state) throws CHIAException {
		Preconditions.checkNotNull(state, "The state cannot be null");
		return state;
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
		return state;
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
	public AutomataState visit(ConstraintComputed state) throws CHIAException {
		Preconditions.checkNotNull(state, "The state cannot be null");
		return state;
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
		boolean founded = false;
		String commandOfInterest = command
				.substring(command.indexOf(" ") + 1);

		for (ActionHelper<CHIAAutomataConsole> analyzedCommand : this.commands) {
			if (commandOfInterest.equals(analyzedCommand.getCommand())) {
				out.write(analyzedCommand.toString());
				founded = true;
			}
		}
		if (!founded) {
			out.write("The command: " + commandOfInterest
					+ " is not a valid command"+"\n");
		}
		out.flush();
	}
}
