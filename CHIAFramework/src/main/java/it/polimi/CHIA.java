package it.polimi;

import it.polimi.console.CHIAAutomataConsole;
import it.polimi.console.CHIAReplacementConsole;
import it.polimi.statemachine.automata.AutomataAction;
import it.polimi.statemachine.replacement.ReplacementAction;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

import jline.console.ConsoleReader;
import jline.console.completer.AggregateCompleter;
import jline.console.completer.CandidateListCompletionHandler;
import jline.console.completer.Completer;
import jline.console.completer.StringsCompleter;
import jline.console.history.FileHistory;
import jline.internal.Preconditions;

import org.apache.log4j.PropertyConfigurator;

/**
 * is the main file of CHIA
 * 
 * @author Claudio Menghi
 *
 */
public class CHIA {

	/**
	 * The console reader
	 */
	private ConsoleReader console;

	private static final String QUIT = "quit";
	private static final String EXIT = "exit";
	private static final String AUTOMATA_MODE = "aut";
	private static final String REPLACEMENT_MODE = "rep";
	private static final String CLS = "cls";

	private final CHIAAutomataConsole automataConsole;
	/**
	 * getting the output stream
	 */
	private final Writer out;

	public CHIA(Writer out) throws IOException {
		Preconditions.checkNotNull(out);
		this.out = out;

		this.automataConsole = new CHIAAutomataConsole();
		this.console = new ConsoleReader();
		this.console.setExpandEvents(false);
		console.setPrompt("CHIA> ");
		usage();

		if (ClassLoader.getSystemResource("History.txt") != null) {
			console.setHistory(new FileHistory(new File(ClassLoader
					.getSystemResource("History.txt").getPath())));
		} else {
			out.write("The History file cannot be loaded");
		}
		out.write("CHIA Started");
		if (ClassLoader.getSystemResource("log4j.properties") != null) {

			PropertyConfigurator.configure(ClassLoader
					.getSystemResource("log4j.properties"));
		} else {
			out.write("The logging file cannot be loaded");
		}

	}

	/**
	 * print the CHIA usage information
	 * 
	 * @throws IOException
	 */
	protected void usage() throws IOException {

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append("********************************************************************\n");
		stringBuilder
				.append("WELCOME in CHIA (Checker for Incomplete Automata)\n");
		stringBuilder
				.append("You can change between the replacement and the automata mode at any time\n");
		stringBuilder.append("rep: enables replacement mode\n");
		stringBuilder.append("aut: enables repautomata mode\n");
		stringBuilder
				.append("********************************************************************\n");
		stringBuilder.append("aut: automata mode enabled\n");
		out.write(stringBuilder.toString());

	}

	public void run() throws IOException {
		CHIAState chiaState = CHIAState.AUTOMATAMODE;

		CandidateListCompletionHandler ch = new CandidateListCompletionHandler();
		console.setCompletionHandler(ch);
		ch.setPrintSpaceAfterFullCompletion(false);

		CHIAAutomataConsole chiaAutomataConsole = new CHIAAutomataConsole();
		CHIAReplacementConsole chiaReplacementConsole = new CHIAReplacementConsole();

		List<Completer> replacementCompleter = chiaReplacementConsole
				.getCompleter().computeCompleters();
		replacementCompleter.add(new StringsCompleter(AUTOMATA_MODE));
		Completer replacementCom = new AggregateCompleter(replacementCompleter);

		List<Completer> automataCompletor = this.automataConsole.getCompleter()
				.computeCompleters();
		automataCompletor.add(new StringsCompleter(REPLACEMENT_MODE));
		Completer automataCom = new AggregateCompleter(automataCompletor);

		console.addCompleter(new AggregateCompleter(automataCompletor));

		String line;

		while ((line = console.readLine()) != null) {
			try {
				if (line.equals(REPLACEMENT_MODE)) {

					chiaState = CHIAState.REPLACEMENTMODE;
					this.removeCompleter();
					console.removeCompleter(automataCom);
					console.addCompleter(replacementCom);
					out.write("replacement mode enabled");

				} else {
					if (line.equals(AUTOMATA_MODE)) {
						chiaState = CHIAState.AUTOMATAMODE;
						this.removeCompleter();

						console.removeCompleter(replacementCom);
						console.addCompleter(automataCom);

						out.write("automata mode enabled");

					} else {
						if (QUIT.equalsIgnoreCase(line)
								|| EXIT.equalsIgnoreCase(line)) {
							System.exit(0);
						} else {
							if (line.equalsIgnoreCase(CLS)) {
								console.clearScreen();
							} else {
								if (chiaState.equals(CHIAState.REPLACEMENTMODE)) {
									ReplacementAction action = chiaReplacementConsole
											.getCompleter()
											.parse(line,
													chiaReplacementConsole, out);
									if (chiaReplacementConsole.getChiaState()
											.isPerformable(action)) {
										action.perform(chiaReplacementConsole);
										chiaReplacementConsole
												.setChiaState(chiaReplacementConsole
														.getChiaState().next(
																action));
									} else {
										out.write("Action: "
												+ line
												+ " not performable into the state "
												+ chiaReplacementConsole
														.getChiaState()
														.getClass());
									}

								} else {
									if (chiaState
											.equals(CHIAState.AUTOMATAMODE)) {
										AutomataAction action = this.automataConsole
												.getCompleter().parse(line,
														chiaAutomataConsole,
														out);
										if (this.automataConsole.getChiaState()
												.isPerformable(action)) {
											action.perform(chiaAutomataConsole);
											this.automataConsole
													.setChiaState(this.automataConsole
															.getChiaState()
															.next(action));
										} else {
											out.write("Action: "
													+ line
													+ " not performable into the state "
													+ this.automataConsole
															.getChiaState()
															.getClass());
										}

									}
								}
							}
						}
					}
				}
			} catch (Exception e) {
				out.write(e.getMessage());
			}

		}
	}

	private void removeCompleter() {
		for (Completer completer : console.getCompleters()) {
			console.removeCompleter(completer);

		}
	}

	public static void main(String[] args) throws IOException {
		CHIA chia = new CHIA(new PrintWriter(System.out));
		chia.run();
	}

}