package it.polimi.statemachine.automata.actions;

import it.polimi.automata.io.in.ClaimReader;
import it.polimi.console.CHIAAutomataConsole;

import java.io.Writer;

import com.google.common.base.Preconditions;

/**
 * creates a class which reads a property from the file
 * 
 * @author Claudio Menghi
 *
 */
public class ReadFileProperty extends ReadProperty {

	/**
	 * the writer used to print messages
	 */
	private final Writer out;

	/**
	 * the path of the file from which the claim must be loaded
	 */
	private final String claimFilePath;

	/**
	 * creates a new reader for the property
	 * 
	 * @param out
	 *            is used to write messages on the console
	 * @param claimFilePath
	 *            the path of the file to be considered
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public ReadFileProperty(Writer out, String claimFilePath) {
		Preconditions.checkNotNull(out, "The out cannot be null");
		Preconditions.checkNotNull(claimFilePath, "The claimFilePath cannot be null");
		
		this.out = out;
		this.claimFilePath = claimFilePath;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void perform(CHIAAutomataConsole console) throws Exception {
		Preconditions.checkNotNull(console, "The console cannot be null");

		ClaimReader action = new ClaimReader(claimFilePath);
		console.setClaim(action.perform());
		out.write("Property loaded"+"\n");
		out.write("N° states: " + console.getClaim().getStates().size()+"\n");
		out.write("N° transitions:"
				+ console.getClaim().getTransitions().size()+"\n");
		out.flush();
	}
}
