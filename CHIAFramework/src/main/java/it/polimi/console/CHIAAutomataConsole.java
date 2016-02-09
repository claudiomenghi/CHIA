package it.polimi.console;

import it.polimi.action.CHIAException;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.io.in.ClaimReader;
import it.polimi.automata.io.in.ModelReader;
import it.polimi.automata.io.out.ClaimToStringTrasformer;
import it.polimi.automata.io.out.ElementToStringTransformer;
import it.polimi.automata.io.out.IBAToElementTrasformer;
import it.polimi.checker.Checker;
import it.polimi.checker.SatisfactionValue;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy.AcceptingType;
import it.polimi.constraintcomputation.ConstraintGenerator;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.io.out.constraint.ConstraintToStringTrasformer;
import it.polimi.constraints.io.out.constraint.ConstraintWriter;
import it.polimi.model.ltltoba.LTLtoBATransformer;
import it.polimi.statemachine.states.CHIAAutomataState;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import com.google.common.base.Preconditions;

/**
 * Is the Console associated with the automata mode. It specifies the set of
 * action that can be performed when the automata mode is selected
 * 
 * @author Claudio Menghi
 *
 */
public class CHIAAutomataConsole {

	private static final Logger LOGGER = Logger
			.getLogger(CHIAAutomataConsole.class);

	/**
	 * contains the model of the system to be considered. Null if no model is
	 * loaded
	 */
	protected IBA model;

	/**
	 * is the stream used to write messages on the console
	 */
	private final PrintStream out;

	/**
	 * contains the claim to be considered. Null if no claim is loaded
	 */
	protected BA claim;

	/**
	 * contains the model checker to be used in the model checking activity
	 */
	protected Checker checker;

	/**
	 * is the state of the application. The state changes in response to user
	 * inputs
	 */
	private CHIAAutomataState chiaState;

	/**
	 * contains the constraint associated with the specified model and claim if
	 * the model possibly satisfy the claim
	 */
	protected Constraint constraint;

	/**
	 * contains the accepting policy which specifies how the accepting states of
	 * the intersection automata are computed
	 */
	private AcceptingType policy;

	/**
	 * contains the engine used to compute the constraint
	 */
	private ConstraintGenerator cg;

	/**
	 * creates the {@link CHIAAutomataConsole}
	 * 
	 * @param out
	 *            is the stream used to write messages
	 * @throws NullPointerException
	 *             if the out is null
	 */
	public CHIAAutomataConsole(PrintStream out) {
		Preconditions.checkNotNull(out, "The output stream cannot be null");
		policy = AcceptingType.BA;
		chiaState = CHIAAutomataState.INIT;
		this.out = out;
	}

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
	public void loadModel(String modelFilePath) {

		com.google.common.base.Preconditions.checkNotNull(modelFilePath,
				"The path of the model cannot be null");
		com.google.common.base.Preconditions.checkArgument(Files.exists(
				Paths.get(modelFilePath), LinkOption.NOFOLLOW_LINKS),
				"The path of the model cannot be null");

		try {
			this.chiaState = chiaState.perform(ModelReader.class);

			ModelReader action = new ModelReader(modelFilePath);
			this.model = action.perform();
			out.println("Model readed");
			out.println("N° states: " + this.model.getStates().size());
			out.println("N° transitions:" + this.model.getTransitions().size());

		} catch (FileNotFoundException fileNotFound) {
			LOGGER.info(fileNotFound.toString());
			out.println(fileNotFound.getMessage());
		} catch (CHIAException e) {
			LOGGER.info(ExceptionUtils.getStackTrace(e));
			out.println(e.getMessage());
		} catch (SAXException e) {
			LOGGER.info(ExceptionUtils.getStackTrace(e));
			out.println(e.getMessage());
		} catch (IOException e) {
			LOGGER.info(ExceptionUtils.getStackTrace(e));
			out.println(e.getMessage());
		} catch (ParserConfigurationException e) {
			LOGGER.info(ExceptionUtils.getStackTrace(e));
			out.println(e.getMessage());
		}
	}

	/**
	 * loads the property in form of automaton from the specified file path
	 * 
	 * @param claimFilePath
	 *            the path of the file from which the property must be loaded
	 * @throws FileNotFoundException
	 *             generated if the file is not found
	 * @throws ParserConfigurationException
	 *             generated if a parsing error occurs
	 * @throws SAXException
	 *             whenever a SAX exception occurs
	 * @throws IOException
	 *             in the case of IO exceptions
	 */
	public void loadProperty(String claimFilePath)
			throws FileNotFoundException, ParserConfigurationException,
			SAXException, IOException {
		try {
			this.chiaState = chiaState.perform(ClaimReader.class);
			ClaimReader action = new ClaimReader(claimFilePath);
			this.claim = action.perform();
			out.println("Property loaded");
			out.println("N° states: " + this.claim.getStates().size());
			out.println("N° transitions:" + this.claim.getTransitions().size());

		} catch (CHIAException e) {
			LOGGER.info(e.toString());
		}
	}

	/**
	 * loads the automaton that corresponds to the property of interest from the
	 * specified LTL formula
	 * 
	 * @param ltlProperty
	 *            is the LTL property to be converted into the corresponding
	 *            automaton
	 */
	public void loadLTLProperty(String ltlProperty) {
		if (ltlProperty != null) {
			try {
				this.chiaState = chiaState.perform(LTLtoBATransformer.class);
				LTLtoBATransformer action = new LTLtoBATransformer("!("
						+ ltlProperty + ")");
				this.claim = action.perform();
				out.println("Property loaded");
				out.println("N° states: " + this.claim.getStates().size());
				out.println("N° transitions:" + this.claim.getTransitions().size());

			} catch (UnsatisfiedLinkError e) {
				LOGGER.info(e.getMessage());
				LOGGER.info(e.toString());
				LOGGER.info("The convertion of an LTL formula into the corresponding automaton is based on the LTL2BA4J library.");
				LOGGER.info("The LTL2BA4J library uses the ltl2ba tool. ltl2ba is written in ANSI C");
				LOGGER.info("The library must be available at the specified path");

			} catch (Exception e) {
				LOGGER.info(e.toString());
				LOGGER.info("The convertion of an LTL formula into the corresponding automaton is based on the LTL2BA4J library.");
				LOGGER.info("The LTL2BA4J library uses the ltl2ba tool. ltl2ba is written in ANSI C");
				LOGGER.info("If the compiled version of the library is not compatible with your operating system "
						+ "you must download the source of ltl2ba from http://www.sable.mcgill.ca/~ebodde/rv/ltl2ba4j/ltl2ba4j.tgz"
						+ " and recompile the source of ltl2ba");
			} catch (NoClassDefFoundError e) {
				LOGGER.info(e.toString());
				LOGGER.info("The convertion of an LTL formula into the corresponding automaton is based on the LTL2BA4J library.");
				LOGGER.info("The LTL2BA4J library uses the ltl2ba tool. ltl2ba is written in ANSI C");
				LOGGER.info("If the compiled version of the library is not compatible with your operating system "
						+ "you must download the source of ltl2ba from http://www.sable.mcgill.ca/~ebodde/rv/ltl2ba4j/ltl2ba4j.tgz"
						+ " and recompile the source of ltl2ba");
			}
		}
	}

	/**
	 * displays the model of the system
	 * 
	 * @throws Exception
	 *             if an exception occurs
	 */
	public void dispModel() throws Exception {

		Preconditions
				.checkNotNull(model,
						"The model of the system must be loaded before being displayed");

		out.println(new ElementToStringTransformer()
				.transform(new IBAToElementTrasformer().transform(this.model)));
	}

	public void dispClaim() throws ParserConfigurationException, Exception {
		try {

			this.chiaState = chiaState.perform(ClaimToStringTrasformer.class);
			ClaimToStringTrasformer action = new ClaimToStringTrasformer(
					this.claim);
			out.println(action.perform());
		} catch (CHIAException e) {
			LOGGER.info(e.toString());
		}
	}

	public void check() {
		try {
			this.chiaState = chiaState.perform(Checker.class);
			ThreadMXBean thradBean = ManagementFactory.getThreadMXBean();
			long startTime = thradBean.getCurrentThreadCpuTime();
			checker = new Checker(model, claim,
					AcceptingPolicy.getAcceptingPolicy(policy, model, claim));
			SatisfactionValue result = checker.perform();
			long endTime = thradBean.getCurrentThreadCpuTime();
			out.println("Verification result: " + result.toString());
			out.println("Verification time: "
					+ Long.toString(TimeUnit.MILLISECONDS.convert(
							(endTime - startTime), TimeUnit.NANOSECONDS))
					+ " ms");
			out.println("Dimension of the intersection automaton (states+transitions): "
					+ this.checker.getIntersectionAutomataSize());
			if (result.equals(SatisfactionValue.NOTSATISFIED)) {
				out.println("Counterexample:"
						+ this.checker.getFilteredCounterexample());
			}

		} catch (CHIAException e) {
			LOGGER.info(e.toString());
		}
	}

	public void computeConstraint() throws FileNotFoundException,
			ParserConfigurationException, SAXException, IOException {
		try {
			this.chiaState = chiaState.perform(ConstraintGenerator.class);
			cg = new ConstraintGenerator(this.checker);
			this.constraint = cg.perform();

			LOGGER.info("Constraint computed");
			// this.constraint = this.chia.generateConstraint();
		} catch (CHIAException e) {
			LOGGER.info(e.toString());
		}

	}

	public void saveConstraint(String constraintFilePath) throws Exception {
		try {
			this.chiaState = chiaState.perform(ConstraintWriter.class);

			ConstraintWriter constraintWriter = new ConstraintWriter(
					this.constraint, constraintFilePath);
			constraintWriter.perform();
			LOGGER.info("Constraint saved");
		} catch (CHIAException e) {
			LOGGER.info(e.toString());
		}
	}

	public void dispConstraint() throws ParserConfigurationException, Exception {
		try {
			this.chiaState = chiaState
					.perform(ConstraintToStringTrasformer.class);
			ConstraintToStringTrasformer action = new ConstraintToStringTrasformer(
					this.constraint);
			LOGGER.info(action.perform());
		} catch (CHIAException e) {
			LOGGER.info(e.toString());
		}
	}

	public void exit() {
	}

}
