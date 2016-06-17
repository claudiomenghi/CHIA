package it.polimi.statemachine.replacement;

import it.polimi.console.CHIAReplacementConsole;
import it.polimi.statemachine.ActionHelper;
import it.polimi.statemachine.replacement.action.helper.CheckReplacementHelper;
import it.polimi.statemachine.replacement.action.helper.DisplayConstraintHelper;
import it.polimi.statemachine.replacement.action.helper.DisplayReplacementHelper;
import it.polimi.statemachine.replacement.action.helper.HelpHelper;
import it.polimi.statemachine.replacement.action.helper.LoadConstraintHelper;
import it.polimi.statemachine.replacement.action.helper.LoadReplacementHelper;
import it.polimi.statemachine.replacement.action.helper.ReplacementActionHelper;
import it.polimi.statemachine.replacement.action.helper.SaveRefinementHelper;

import java.io.Writer;
import java.text.ParseException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import jline.console.completer.ArgumentCompleter;
import jline.console.completer.Completer;
import jline.console.completer.FileNameCompleter;
import jline.console.completer.StringsCompleter;

public class ReplacementCompleter {
	private Set<ReplacementActionHelper> commands;

	public ReplacementCompleter() {
		this.commands = new HashSet<>();
		this.commands.add(new CheckReplacementHelper());
		this.commands.add(new DisplayConstraintHelper());
		this.commands.add(new DisplayReplacementHelper());
		this.commands.add(new HelpHelper());
		this.commands.add(new LoadConstraintHelper());
		this.commands.add(new LoadReplacementHelper());
		this.commands.add(new SaveRefinementHelper());
	}

	public Set<ReplacementActionHelper> getAutomataCompleters(){
		return this.commands;
	}
	/**
	 * returns the set of the commands that can be performed
	 * 
	 * @return the set of commands that can be performed
	 */
	public Set<String> getShortCuts() {
		Set<String> shortcuts = new HashSet<>();
		for (ActionHelper<CHIAReplacementConsole> command : this.commands) {
			shortcuts.add(command.getCommand());
		}
		return shortcuts;
	}

	/**
	 * returns the set of the commands that can be performed
	 * 
	 * @return the set of commands that can be performed
	 */
	public Set<String> getCommands() {
		Set<String> retcommands = new HashSet<>();
		for (ActionHelper<CHIAReplacementConsole> command : this.commands) {
			retcommands.add(command.getCommand());
		}
		return retcommands;
	}

	public ReplacementAction parse(String toBeParsed, CHIAReplacementConsole console, Writer out)
			throws ParseException {

		for (ReplacementActionHelper command : this.commands) {
			if (toBeParsed.split(" ")[0].equals(command.getCommand())
					|| toBeParsed.equals(command.getCommand())) {
				return command.getAction(toBeParsed, console, out);
			}
		}
		throw new ParseException("The command " + toBeParsed
				+ " is not a valid command", 0);

	}

	/**
	 * returns the set of the commands that can be performed
	 * 
	 * @return the set of commands that can be performed
	 */
	public List<Completer> computeCompleters() {
		List<Completer> chiaCompletors = new LinkedList<>();

		for (ActionHelper<CHIAReplacementConsole> command : this.commands) {
			if (command.getParams() != null) {
				chiaCompletors.add(new ArgumentCompleter(new StringsCompleter(
						command.getCommand()), new FileNameCompleter()));

			} else {
				chiaCompletors.add(new StringsCompleter(command.getCommand()));
			}
		}
		return chiaCompletors;
	}
}
