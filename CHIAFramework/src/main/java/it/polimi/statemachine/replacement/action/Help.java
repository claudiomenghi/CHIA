package it.polimi.statemachine.replacement.action;

import it.polimi.action.CHIAException;
import it.polimi.console.CHIAReplacementConsole;
import it.polimi.statemachine.ActionHelper;
import it.polimi.statemachine.replacement.ReplacementAction;
import it.polimi.statemachine.replacement.ReplacementState;
import it.polimi.statemachine.replacement.action.helper.ReplacementActionHelper;
import it.polimi.statemachine.replacement.states.Checked;
import it.polimi.statemachine.replacement.states.ConstraintLoaded;
import it.polimi.statemachine.replacement.states.Init;
import it.polimi.statemachine.replacement.states.Ready;
import it.polimi.statemachine.replacement.states.ReplacementLoaded;

import java.io.Writer;
import java.util.Set;

import com.google.common.base.Preconditions;

/**
 * contains the help action
 * 
 * @author Claudio Menghi
 *
 */
public class Help implements ReplacementAction {

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
	private final Set<ReplacementActionHelper> commands;


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
	public Help(String command, Set<ReplacementActionHelper> commands,
			Writer out) {
		Preconditions.checkNotNull(command, "The command cannot be null");
		Preconditions.checkNotNull(commands, "The set of helper cannot be null");
		Preconditions.checkNotNull(out, "The writer cannot be null");
		
		this.command = command;
		this.out = out;
		this.commands = commands;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ReplacementState visit(Init state) throws CHIAException {
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
	public ReplacementState visit(ConstraintLoaded state) {
		Preconditions.checkNotNull(state, "The state cannot be null");
		return state;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPerformable(ConstraintLoaded state) {
		Preconditions.checkNotNull(state, "The state cannot be null");
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ReplacementState visit(Ready state) {
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
	public ReplacementState visit(Checked state) {
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
	public ReplacementState visit(ReplacementLoaded state) {
		Preconditions.checkNotNull(state, "The state cannot be null");
		return state;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPerformable(ReplacementLoaded state) {
		Preconditions.checkNotNull(state, "The state cannot be null");
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void perform(CHIAReplacementConsole console) throws Exception {
		Preconditions.checkNotNull(console, "The console cannot be null");
		
		boolean founded = false;
		String commandOfInterest = command.substring(command.indexOf(" ") + 1);

		for (ActionHelper<CHIAReplacementConsole> analyzedCommand : this.commands) {
			if (commandOfInterest.equals(analyzedCommand.getCommand())) {
				out.write(analyzedCommand.toString());
				founded = true;
			}
		}
		if (!founded) {
			this.out.write("The command: " + commandOfInterest
					+ " is not a valid command"+"\n");
		}
		this.out.flush();
	}

}
