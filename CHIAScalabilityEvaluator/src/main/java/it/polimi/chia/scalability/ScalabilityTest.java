package it.polimi.chia.scalability;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.io.in.ClaimReader;
import it.polimi.automata.io.out.BAToElementTrasformer;
import it.polimi.automata.io.out.IBAToElementTrasformer;
import it.polimi.automata.io.out.XMLWriter;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.checker.Checker;
import it.polimi.checker.SatisfactionValue;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy.AcceptingType;
import it.polimi.chia.scalability.configuration.Configuration;
import it.polimi.chia.scalability.configuration.RandomConfigurationGenerator;
import it.polimi.chia.scalability.parsers.ConfParser;
import it.polimi.chia.scalability.parsers.ConfWriter;
import it.polimi.chia.scalability.randomGenerators.BARandomGenerator;
import it.polimi.chia.scalability.randomGenerators.IBARandomGenerator;
import it.polimi.chia.scalability.results.Record;
import it.polimi.chia.scalability.results.ResultWriter;
import it.polimi.chia.scalability.results.Statistics;
import it.polimi.chia.scalability.tasks.Task1;
import it.polimi.chia.scalability.tasks.Task2;
import it.polimi.chia.scalability.tasks.Task3;
import it.polimi.chia.scalability.tasks.Task4;
import it.polimi.constraintcomputation.ConstraintGenerator;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.components.RefinementGenerator;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.components.SubProperty;
import it.polimi.constraints.io.out.constraint.ConstraintToElementTransformer;
import it.polimi.constraints.io.out.replacement.ReplacementToElementTransformer;
import it.polimi.replacementchecker.ReplacementChecker;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;

/**
 * 
 * The ScalabilityTest reads the scalability configuration from an appropriate
 * XML file
 * 
 * @author Claudio Menghi
 *
 */
public class ScalabilityTest {

	private static final Logger LOGGER = Logger
			.getLogger(ScalabilityTest.class);

	/**
	 * The accepting policy to be used
	 */
	private final static AcceptingType acceptingPolicy = AcceptingType.BA;

	private final ConfParser confParser;

	/**
	 * creates a new scalability test
	 * 
	 * @param confParser
	 *            the parser to be used to get the configuration
	 */
	public ScalabilityTest(ConfParser confParser) {
		Preconditions.checkNotNull(confParser, "The configuration parser");
		this.confParser = confParser;

	}

	/**
	 * runs the tests
	 * 
	 * @throws Exception
	 */
	public void performTests() throws Exception {
		System.out
				.println("--------------------------- STARTING THE TEST: ------------------------");

		Stopwatch timer = Stopwatch.createUnstarted();

		// BA claim = generateRandomClaim(new RandomConfiguration(3, 2, 0.5, 0,
		// 0));
		List<BA> claims = this.getClaimToBeConsidered();

		for (int testNumber = 1; testNumber <= confParser.getNumberOfTests(); testNumber++) {

			File dir = new File(confParser.getTestDirectory() + "/Test"
					+ testNumber);
			dir.mkdir();
			for (int claimNum = 0; claimNum < claims.size(); claimNum++) {
				File claimdir = new File(confParser.getTestDirectory()
						+ "/Test" + testNumber + "/Claim" + claimNum);
				claimdir.mkdir();
				File filePossibly = new File(confParser.getTestDirectory()
						+ "/Test" + testNumber + "/Claim" + claimNum + "/"
						+ confParser.getResultsFilePossibly());
				filePossibly.createNewFile();
				File file = new File(confParser.getTestDirectory() + "/Test"
						+ testNumber + "/Claim" + claimNum + "/"
						+ confParser.getResultsFile());
				file.createNewFile();

			}
		}

		test(timer);
	}

	public static void main(String[] args) throws Exception {
		ConfParser parser = new ConfParser(args[0]);

		ScalabilityTest scalabilityTest = new ScalabilityTest(parser);
		scalabilityTest.performTests();
	}

	private List<BA> getClaimToBeConsidered() throws Exception {

		List<BA> claims = new ArrayList<BA>();

		claims.add(new ClaimReader(new File(ClassLoader.getSystemResource(
				"Claim1.xml").getPath())).perform());

		// claims.add(new ClaimReader(new File(ClassLoader.getSystemResource(
		// "Claim2.xml").getPath())).perform());

		// claims.add(new ClaimReader(new File(ClassLoader.getSystemResource(
		// "Claim3.xml").getPath())).perform());

		return claims;

	}

	private void test(Stopwatch timer) throws Exception {

		RandomConfigurationGenerator randomConfigurationGenerator = new RandomConfigurationGenerator(
				this.getClaimToBeConsidered(),
				confParser.getInitialNumberOfStates(),
				confParser.getFinalNumberOfStates(),
				confParser.getIncrementNumberOfStates(),
				confParser.getInitialTransitionDensity(),
				confParser.getFinalTransitionDensity(),
				confParser.getIncrementTransitionDensity(),
				confParser.getInitAcceptingDensity(),
				confParser.getFinalAcceptingDensity(),
				confParser.getIncrementAcceptingDensity(),
				confParser.getInitialTransparentDensity(),
				confParser.getFinalTransparentDensity(),
				confParser.getIncrementTransparentDensity(),
				confParser.getInitialReplacementDensity(),
				confParser.getFinalReplacementDensity(),
				confParser.getIncrementReplacementDensity(),
				confParser.getNumberOfTests(),
				confParser.getCurrentConfiguration());

		Statistics stat = new Statistics();

		System.out.println("Number of configurations to be considered:"
				+ randomConfigurationGenerator
						.getNumberOfPossibleConfigurations());
		Stopwatch testTimer = Stopwatch.createUnstarted();

		testTimer.start();
		while (randomConfigurationGenerator.hasNext()) {

			Stopwatch totalTimer = Stopwatch.createUnstarted();
			totalTimer.start();

			Record record;

			Configuration configuration = randomConfigurationGenerator.next();

			record = new Record(configuration);
			ResultWriter resultWriter = new ResultWriter(
					confParser.getTestDirectory() + "/Test"
							+ configuration.getTestNumber() + "/Claim"
							+ configuration.getClaimNumber() + "/"
							+ confParser.getResultsFile());
			ResultWriter resultPossiblyWriter = new ResultWriter(
					confParser.getTestDirectory() + "/Test"
							+ configuration.getTestNumber() + "/Claim"
							+ configuration.getClaimNumber() + "/"
							+ confParser.getResultsFilePossibly());

			if (configuration.getConfigurationId() % 100 == 0) {
				LOGGER.info("--------------------------- CONFIGURATION: "
						+ configuration.getConfigurationId()
						+ "------------------------");
				LOGGER.info(randomConfigurationGenerator.toString());
				LOGGER.info(stat.toString());

			}
			System.out.println(configuration.toString());
			BARandomGenerator modelBAgenerator = new BARandomGenerator(
					configuration.getPropositions(), new StateFactory(),
					configuration.getTransitionDensity(),
					configuration.getAcceptingDensity(),
					configuration.getnStates(), new Random());

			BA modelBA = modelBAgenerator.perform();

			/**
			 * checks the refined model of the system
			 */
			new Task1(configuration, modelBA, acceptingPolicy).perform();

			IBARandomGenerator ibaGenerator = new IBARandomGenerator(modelBA,
					new StateFactory(), configuration.getTransparentDensity(),
					configuration.getReplacementDensity());

			IBA model = ibaGenerator.perform();

			/**
			 * checks the incomplete model
			 */
			Checker checker = new Task2(configuration, model, record,
					acceptingPolicy).perform();
			SatisfactionValue value = checker.perform();

			if (value == SatisfactionValue.POSSIBLYSATISFIED) {
				stat.incNumPossibly();
				// compute the constraint

				// choose a random replacement
				List<Replacement> replacements = ibaGenerator
						.getNonEmptyReplacements();
				if (replacements.isEmpty()) {
					throw new InternalError(
							"There are no non empty replacements");
				}
				Collections.shuffle(replacements);

				Replacement replacement = getReplacement(record, replacements);

				Constraint constraint = computeConstraint(configuration, model,
						checker, replacement, record);

				// VERIFICATION OF THE REFINEMENT
				IBA refinedModel = new RefinementGenerator(model, replacement)
						.perform();
				Checker refinementChecker = new Task3(configuration,
						refinedModel, replacement, record, acceptingPolicy)
						.perform();

				// VERIFICATION OF THE REPLACEMENT
				ReplacementChecker replacementChecker = new Task4(replacement,
						constraint,  acceptingPolicy).perform();

				// result comparison
				if (refinementChecker.getIntersectionAutomataSize() >= replacementChecker
						.getIntersectionAutomataSize()) {
					stat.incRepIsMoreEfficientSpace();
				}
				if (record.getRefinementVerificationTime() >= record
						.getReplacementVerificationTime()) {
					stat.incRepIsMoreEfficientTime();
				}

				resultPossiblyWriter.append(record);
				if (record.getRefinementSatisfactionValue() != record
						.getReplacementSatisfactionValue()) {
					printError(confParser.getTestDirectory(), configuration,
							replacement, refinedModel, constraint, model,
							configuration.getCurrentClaim(),
							record.getRefinementSatisfactionValue(),
							record.getReplacementSatisfactionValue());
				}
				resultWriter.append(record);
				System.gc();
				System.runFinalization();
				ConfWriter cw = new ConfWriter(confParser,
						randomConfigurationGenerator, confParser.getTestDirectory()
								+ "/confFile.txt");
				cw.write();
				totalTimer.stop();
				System.out.println("Configuration evaluated in: "
						+ totalTimer.elapsed(TimeUnit.MINUTES) + " m ");
			} 
			
		}
		testTimer.stop();
		System.out.println("Test performed in: "
				+ testTimer.elapsed(TimeUnit.MINUTES) + " m ");

	}

	private Replacement getReplacement(Record record,
			List<Replacement> replacements) {
		Replacement replacement = replacements.iterator().next();

		record.setNumReplacementIncomingTransitions(replacement
				.getIncomingTransitions().size());
		record.setNumReplacementOutgoingTransitions(replacement
				.getOutgoingTransitions().size());

		record.setNumReplacementStates(replacement.getAutomaton().size());
		return replacement;
	}

	private Constraint computeConstraint(Configuration configuration,
			IBA model, Checker checker, Replacement replacement, Record record) {
		Stopwatch constraintComputationTimer = Stopwatch.createUnstarted();

		constraintComputationTimer.start();
		Constraint constraint = computeConstraint(
				configuration.getCurrentClaim(), model, checker,
				replacement.getModelState());
		constraintComputationTimer.stop();

		record.setConstraintComputationTime(constraintComputationTimer
				.elapsed(TimeUnit.NANOSECONDS));
		SubProperty subProperty = constraint.getSubProperty(replacement
				.getModelState());

		record.setSubPropertyYellowIncomingTransitions(subProperty
				.getNumYellowIncomingTransitions());
		record.setSubPropertyNumIncomingTransitions(subProperty
				.getNumIncomingTransitions());
		record.setSubPropertyGreenIncomingTransitions(subProperty
				.getGreenIncomingTransitions().size());

		record.setSubPropertyRedOutgoingTransitions(subProperty
				.getNumRedOutgoingTransitions());
		record.setSubPropertyYellowOutgingTransition(subProperty
				.getNumYellowOutgoingTransitions());
		record.setSubPropertyNumOutgoingTransition(subProperty
				.getNumOutgoingTransitions());

		record.setSubPropertyStates(subProperty.getAutomaton().size());
		record.setConstraintComputationTime(constraintComputationTimer
				.elapsed(TimeUnit.MILLISECONDS));
		System.out.println("Constraint computed in: "
				+ constraintComputationTimer.elapsed(TimeUnit.MINUTES));
		return constraint;
	}

	/**
	 * @param testDirectory
	 * @param configuration
	 * @param refinementSatisfactionvalue
	 * @param replacementSatisfactionvalue
	 * @throws InternalError
	 * @throws Exception
	 */
	private static void printError(String testDirectory,
			Configuration configuration, Replacement replacement,
			IBA refinedModel, Constraint constraint, IBA model, BA claim,
			SatisfactionValue refinementSatisfactionvalue,
			SatisfactionValue replacementSatisfactionvalue)
			throws InternalError, Exception {

		File dir = new File(testDirectory + "Test"
				+ configuration.getTestNumber() + "/Claim"
				+ configuration.getClaimNumber() + "/Experiment"
				+ configuration.getConfigurationId());
		dir.mkdir();

		ReplacementToElementTransformer replacementTransformer = new ReplacementToElementTransformer();
		XMLWriter replacementWriter = new XMLWriter(new File(testDirectory
				+ "Test" + configuration.getTestNumber() + "/Claim"
				+ configuration.getClaimNumber() + "/" + "/Experiment"
				+ configuration.getConfigurationId() + "/replacement.xml"),
				replacementTransformer.transform(replacement));
		replacementWriter.perform();

		IBAToElementTrasformer refinementTransformer = new IBAToElementTrasformer();
		XMLWriter refinementWriter = new XMLWriter(new File(testDirectory
				+ "Test" + configuration.getTestNumber() + "/Claim"
				+ configuration.getClaimNumber() + "/" + "/Experiment"
				+ configuration.getConfigurationId() + "/refinement.xml"),
				refinementTransformer.transform(refinedModel));
		refinementWriter.perform();

		ConstraintToElementTransformer constraintTransformer = new ConstraintToElementTransformer();
		XMLWriter constraintWriter = new XMLWriter(new File(testDirectory
				+ "Test" + configuration.getTestNumber() + "/Claim"
				+ configuration.getClaimNumber() + "/" + "/Experiment"
				+ configuration.getConfigurationId() + "/constraint.xml"),
				constraintTransformer.transform(constraint));
		constraintWriter.perform();

		IBAToElementTrasformer modelToElementTransformer = new IBAToElementTrasformer();

		XMLWriter writer = new XMLWriter(new File(testDirectory + "Test"
				+ configuration.getTestNumber() + "/Claim"
				+ configuration.getClaimNumber() + "/" + "/Experiment"
				+ configuration.getConfigurationId() + "/model.xml"),
				modelToElementTransformer.transform(model));
		writer.perform();

		BAToElementTrasformer claimToElementTransformer = new BAToElementTrasformer();

		XMLWriter claimWriter = new XMLWriter(new File(testDirectory + "Test"
				+ configuration.getTestNumber() + "/Claim"
				+ configuration.getClaimNumber() + "/" + "/Experiment"
				+ configuration.getConfigurationId() + "/claim.xml"),
				claimToElementTransformer.transform(claim));
		claimWriter.perform();

		throw new InternalError("Test Number " + configuration.getTestNumber()
				+ " \t Claim Number " + configuration.getClaimNumber()
				+ " \t Configuration " + configuration.getConfigurationId()
				+ "\t refinement " + refinementSatisfactionvalue
				+ " \t replacement: " + replacementSatisfactionvalue);
	}

	private static Constraint computeConstraint(BA claim, IBA model,
			Checker checker, State blackBoxState) {
		ConstraintGenerator cg = new ConstraintGenerator(checker);
		Constraint constraint = cg.perform(blackBoxState);

		return constraint;
	}

}
