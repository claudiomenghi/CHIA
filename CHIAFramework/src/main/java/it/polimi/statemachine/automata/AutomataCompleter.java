package it.polimi.statemachine.automata;

import it.polimi.console.CHIAAutomataConsole;
import it.polimi.statemachine.ActionHelper;
import it.polimi.statemachine.automata.actions.helper.AutomataActionHelper;
import it.polimi.statemachine.automata.actions.helper.CheckHelper;
import it.polimi.statemachine.automata.actions.helper.ComputeConstraintHelper;
import it.polimi.statemachine.automata.actions.helper.DisplayConstraintHelper;
import it.polimi.statemachine.automata.actions.helper.DisplayModelHelper;
import it.polimi.statemachine.automata.actions.helper.DisplayPropertyHelper;
import it.polimi.statemachine.automata.actions.helper.ExitHelper;
import it.polimi.statemachine.automata.actions.helper.HelpHelper;
import it.polimi.statemachine.automata.actions.helper.ReadFilePropertyHelper;
import it.polimi.statemachine.automata.actions.helper.ReadLTLPropertyHelper;
import it.polimi.statemachine.automata.actions.helper.ReadModelHelper;
import it.polimi.statemachine.automata.actions.helper.WriteConstraintHelper;

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

public class AutomataCompleter {

	private Set<AutomataActionHelper> commands;

	public AutomataCompleter() {
		this.commands = new HashSet<>();
		this.commands.add(new CheckHelper());
		this.commands.add(new ComputeConstraintHelper());
		this.commands.add(new DisplayConstraintHelper());
		this.commands.add(new DisplayModelHelper());
		this.commands.add(new DisplayPropertyHelper());
		this.commands.add(new ExitHelper());
		this.commands.add(new HelpHelper());
		this.commands.add(new ReadFilePropertyHelper());
		this.commands.add(new ReadLTLPropertyHelper());
		this.commands.add(new ReadModelHelper());
		this.commands.add(new WriteConstraintHelper());
	}

	public Set<AutomataActionHelper> getAutomataCompleters(){
		return this.commands;
	}
	/**
	 * returns the set of the commands that can be performed
	 * 
	 * @return the set of commands that can be performed
	 */
	public Set<String> getShortCuts() {
		Set<String> shortcuts = new HashSet<>();
		for (ActionHelper<CHIAAutomataConsole> command : this.commands) {
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
		for (ActionHelper<CHIAAutomataConsole> command : this.commands) {
			retcommands.add(command.getCommand());
		}
		return retcommands;
	}

	public AutomataAction parse(String toBeParsed, CHIAAutomataConsole console, Writer out)
			throws ParseException {

		for (AutomataActionHelper command : this.commands) {
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

		for (ActionHelper<CHIAAutomataConsole> command : this.commands) {
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
