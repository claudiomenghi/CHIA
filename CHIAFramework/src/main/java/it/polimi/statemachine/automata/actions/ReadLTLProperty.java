package it.polimi.statemachine.automata.actions;

import java.io.Writer;

import com.google.common.base.Preconditions;

import it.polimi.console.CHIAAutomataConsole;
import it.polimi.model.ltltoba.LTLtoBATransformer;

/**
 * reads the LTL property from a string
 * @author Claudio Menghi
 *
 */
public class ReadLTLProperty extends ReadProperty {

	/**
	 * the writer used to print messages
	 */
	private final Writer out;

	/**
	 * the LTL property to be considered
	 */
	private final String ltlProperty;

	/**
	 * loads the automaton that corresponds to the property of interest from the
	 * specified LTL formula
	 * 
	 * @param out
	 *            is used to write messages on the console
	 * @param ltlProperty
	 *            is the LTL property to be converted into the corresponding
	 *            automaton
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public ReadLTLProperty(Writer out, String ltlProperty) {

		Preconditions.checkNotNull(out, "The out cannot be null");
		Preconditions.checkNotNull(ltlProperty,
				"The ltlProperty cannot be null");
		this.out = out;
		this.ltlProperty = ltlProperty;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void perform(CHIAAutomataConsole console) throws Exception {
		Preconditions.checkNotNull(console, "The console cannot be null");

		if (ltlProperty != null) {
			try {
				LTLtoBATransformer action = new LTLtoBATransformer("!("
						+ ltlProperty + ")");
				console.setClaim(action.perform());
				out.write("Property loaded");
				out.write("N° states: " + console.getClaim().getStates().size());
				out.write("N° transitions:"
						+ console.getClaim().getTransitions().size());

			} catch (UnsatisfiedLinkError e) {
				out.write(e.getMessage());
				out.write(e.toString());
				out.write("The convertion of an LTL formula into the corresponding automaton is based on the LTL2BA4J library.");
				out.write("The LTL2BA4J library uses the ltl2ba tool. ltl2ba is written in ANSI C");
				out.write("The library must be available at the specified path");
				out.write("For additional informations see http://www.sable.mcgill.ca/~ebodde/rv/ltl2ba4j/");
				out.write("If you do not want to install the library you can load the claim directly from a BA using the command lp");

			} catch (Exception e) {
				out.write(e.getMessage());
				out.write(e.toString());
				out.write("The convertion of an LTL formula into the corresponding automaton is based on the LTL2BA4J library.");
				out.write("The LTL2BA4J library uses the ltl2ba tool. ltl2ba is written in ANSI C");
				out.write("If the compiled version of the library is not compatible with your operating system "
						+ "you must download the source of ltl2ba from http://www.sable.mcgill.ca/~ebodde/rv/ltl2ba4j/ltl2ba4j.tgz"
						+ " and recompile the source of ltl2ba");
				out.write("For additional informations see http://www.sable.mcgill.ca/~ebodde/rv/ltl2ba4j/");
				out.write("If you do not want to install the library you can load the claim directly from a BA using the command lp");
			} catch (NoClassDefFoundError e) {
				out.write(e.toString());
				out.write("The convertion of an LTL formula into the corresponding automaton is based on the LTL2BA4J library.");
				out.write("The LTL2BA4J library uses the ltl2ba tool. ltl2ba is written in ANSI C");
				out.write("If the compiled version of the library is not compatible with your operating system "
						+ "you must download the source of ltl2ba from http://www.sable.mcgill.ca/~ebodde/rv/ltl2ba4j/ltl2ba4j.tgz"
						+ " and recompile the source of ltl2ba");
				out.write("For additional informations see http://www.sable.mcgill.ca/~ebodde/rv/ltl2ba4j/");
				out.write("If you do not want to install the library you can load the claim directly from a BA using the command lp");
			}
		}
	}
}
