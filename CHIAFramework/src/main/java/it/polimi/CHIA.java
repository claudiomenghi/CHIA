package it.polimi;

import it.polimi.console.CHIAAutomataConsole;
import it.polimi.console.CHIAReplacementConsole;
import it.polimi.statemachine.events.AutomataEvent;
import it.polimi.statemachine.events.ReplacementEvent;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
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

	private static final String QUIT="quit";
	private static final String EXIT="exit";
	private static final String AUTOMATA_MODE="aut";
	private static final String REPLACEMENT_MODE="rep";
	private static final String CLS="cls";
	
	/**
	 * getting the output stream
	 */
	private final PrintStream out;

	/**
	 * print the CHIA usage information
	 */
	protected void usage() {

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
		out.println(stringBuilder);

	}

	public CHIA(PrintStream out) throws IOException {
		Preconditions.checkNotNull(out);
		this.out=out;

		this.console = new ConsoleReader();
		this.console.setExpandEvents(false);
		console.setPrompt("CHIA> ");
		usage();

		if (ClassLoader.getSystemResource("History.txt") != null) {
			console.setHistory(new FileHistory(new File(ClassLoader
					.getSystemResource("History.txt").getPath())));
		} else {
			out.println("The History file cannot be loaded");
		}
		out.println("CHIA Started");
		if (ClassLoader.getSystemResource("log4j.properties") != null) {

			PropertyConfigurator.configure(ClassLoader
					.getSystemResource("log4j.properties"));
		} else {
			out.println("The logging file cannot be loaded");
		}
		

	}

	public void run() throws IOException {
		CHIAState chiaState = CHIAState.AUTOMATAMODE;

		CandidateListCompletionHandler ch = new CandidateListCompletionHandler();
		console.setCompletionHandler(ch);
		ch.setPrintSpaceAfterFullCompletion(false);

		CHIAAutomataConsole chiaAutomataConsole = new CHIAAutomataConsole(out);
		CHIAReplacementConsole chiaReplacementConsole = new CHIAReplacementConsole();

		List<Completer> replacementCompleter=ReplacementEvent.computeCompleters();
		replacementCompleter.add(new StringsCompleter(AUTOMATA_MODE));
		Completer replacementCom=new AggregateCompleter(replacementCompleter);
		
		List<Completer> automataCompletor = AutomataEvent.computeCompleters();
		automataCompletor.add(new StringsCompleter(REPLACEMENT_MODE));
		Completer automataCom=new AggregateCompleter(automataCompletor);
		
		console.addCompleter(new AggregateCompleter(automataCompletor));

		String line;

		while ((line = console.readLine()) != null) {
			try {
				if (line.equals(REPLACEMENT_MODE)) {

					chiaState = CHIAState.REPLACEMENTMODE;
					this.removeCompleter();
					console.removeCompleter(automataCom);
					console.addCompleter(replacementCom);
					out.println("replacement mode enabled");

				} else {
					if (line.equals(AUTOMATA_MODE)) {
						chiaState = CHIAState.AUTOMATAMODE;
						this.removeCompleter();
						
						console.removeCompleter(replacementCom);
						console.addCompleter(automataCom);

						out.println("automata mode enabled");

					} else {
						if (QUIT.equalsIgnoreCase(line)
								|| EXIT.equalsIgnoreCase(line)) {
							System.exit(0);
						} else {
							if (line.equalsIgnoreCase(CLS)) {
								console.clearScreen();
							} else {
								if (chiaState.equals(CHIAState.REPLACEMENTMODE)) {
									try {
										ReplacementEvent.parse(line,
												chiaReplacementConsole);
									} catch (ParseException e) {
										out.println(e.getMessage());
									}

								} else {
									if (chiaState
											.equals(CHIAState.AUTOMATAMODE)) {
										try {
											AutomataEvent.parse(line,
													chiaAutomataConsole);
										} catch (ParseException e) {
											out.println(e.getMessage());
										}

									}
								}
							}
						}
					}
				}
			} catch (Exception e) {
				out.print(e.getMessage());
			}

		}
	}

	private void removeCompleter() {
		for (Completer completer : console.getCompleters()) {
			console.removeCompleter(completer);

		}
	}

	public static void main(String[] args) throws IOException {
		CHIA chia = new CHIA(System.out);
		chia.run();
	}

}