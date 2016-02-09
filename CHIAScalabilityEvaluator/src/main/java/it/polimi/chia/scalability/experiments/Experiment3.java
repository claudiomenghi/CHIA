package it.polimi.chia.scalability.experiments;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.checker.Checker;
import it.polimi.checker.SatisfactionValue;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy.AcceptingType;
import it.polimi.chia.scalability.claimLoader.ClaimLoader;
import it.polimi.chia.scalability.configuration.Configuration;
import it.polimi.chia.scalability.configuration.RandomConfigurationGenerator;
import it.polimi.chia.scalability.parsers.ConfParser;
import it.polimi.chia.scalability.parsers.ConfWriter;
import it.polimi.chia.scalability.randomGenerators.BARandomGenerator;
import it.polimi.chia.scalability.randomGenerators.IBARandomGenerator;
import it.polimi.chia.scalability.results.Record;
import it.polimi.chia.scalability.results.Statistics;
import it.polimi.chia.scalability.tasks.Task2;
import it.polimi.chia.scalability.tasks.Task3;
import it.polimi.chia.scalability.tasks.Task4;
import it.polimi.constraintcomputation.ConstraintGenerator;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.components.SubProperty;
import it.polimi.replacementchecker.ReplacementChecker;

import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class Experiment3 {

	private static final Logger LOGGER = Logger.getLogger(Experiment3.class);

	/**
	 * The accepting policy to be used
	 */
	private final static AcceptingType acceptingPolicy = AcceptingType.BA;

	private final ConfParser confParser;
	
	private final  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	/**
	 * creates a new scalability test
	 * 
	 * @param confParser
	 *            the parser to be used to get the configuration
	 */
	public Experiment3(ConfParser confParser) {
		Preconditions.checkNotNull(confParser, "The configuration parser");
		this.confParser = confParser;
		System.out.println("Test directory: " + confParser.getTestDirectory());

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
		List<BA> claims = new ClaimLoader().getClaimToBeConsidered();

		for (int testNumber = 1; testNumber <= confParser.getNumberOfTests(); testNumber++) {

			File dir = new File(confParser.getTestDirectory() + "/Test"
					+ testNumber);
			dir.mkdir();
			for (int claimNum = 0; claimNum < claims.size(); claimNum++) {
				File claimdir = new File(confParser.getTestDirectory()
						+ "/Test" + testNumber + "/Claim" + claimNum);
				claimdir.mkdir();
				File file = new File(confParser.getTestDirectory() + "/Test"
						+ testNumber + "/Claim" + claimNum + "/"
						+ confParser.getResultsFile());
				file.createNewFile();

			}
		}

		test(timer);
	}

	public static void main(String[] args) throws Exception {
		
		ConfParser parser = new ConfParser("configEx3.txt");

		Experiment3 scalabilityTest = new Experiment3(parser);
		scalabilityTest.performTests();
	}

	private void test(Stopwatch timer) throws Exception {

		try {

			RandomConfigurationGenerator randomConfigurationGenerator = new RandomConfigurationGenerator(
					new ClaimLoader().getClaimToBeConsidered(),
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

				Configuration configuration = randomConfigurationGenerator
						.next();

				record = new Record(configuration);
				FileWriter resultWriter = new FileWriter(
						confParser.getTestDirectory() + "/Test"
								+ configuration.getTestNumber() + "/Claim"
								+ configuration.getClaimNumber() + "/"
								+ confParser.getResultsFile(), true);

				if (configuration.getConfigurationId() % 100 == 0) {
					LOGGER.info("--------------------------- CONFIGURATION: "
							+ configuration.getConfigurationId()
							+ "------------------------");
					LOGGER.info(randomConfigurationGenerator.toString());
					LOGGER.info(stat.toString());

				}
				BARandomGenerator modelBAgenerator = new BARandomGenerator(
						configuration.getPropositions(), new StateFactory(),
						configuration.getTransitionDensity(),
						configuration.getAcceptingDensity(),
						configuration.getnStates(), new Random());

				BA modelBA = modelBAgenerator.perform();

				/**
				 * checks the refined model of the system
				 */
				//Task1 task1 = new Task1(configuration, modelBA, acceptingPolicy);
				//Checker task1Checker = task1.perform();
				//SatisfactionValue task1value = task1Checker.perform();
				//int task1Space = task1.getTaskSpace();
				//long task1Time = task1.getTaskTime();
				//task1 = null;

				IBARandomGenerator ibaGenerator = new IBARandomGenerator(
						modelBA, new StateFactory(),
						configuration.getTransparentDensity(),
						configuration.getReplacementDensity());

				IBA ibaModel = ibaGenerator.perform();

				/**
				 * executing the task 2
				 */
				Task2 task2 = new Task2(configuration, ibaModel, record,
						acceptingPolicy);
				Checker checker = task2.perform();
				SatisfactionValue task2value = checker.perform();

				if (task2value == SatisfactionValue.POSSIBLYSATISFIED) {
					/**
					 * executing the task 3
					 */
					// choose a random replacement
					List<Replacement> replacements = ibaGenerator
							.getNonEmptyReplacements();
					if (replacements.isEmpty()) {
						resultWriter.close();
						throw new InternalError(
								"There are no non empty replacements");
					}
					
					Replacement replacement = getRandomReplacement(record,
							replacements);

					Task3 task3 = new Task3(configuration, ibaModel, replacement,
							record, acceptingPolicy);
					Checker checkerTask3=task3.perform();

					SatisfactionValue task3value=checkerTask3.perform();
					int task3Space=task3.getTaskSpace();
					long task3Time=task3.getTaskTime();
					
					System.out.println("Computing the constraints "+dateFormat.format(new Date()));
					Constraint constraint = computeConstraint(configuration, ibaModel,
							checker, replacement, record);
					Task4 task4=new Task4(replacement, constraint, acceptingPolicy);
					ReplacementChecker replacementChecker=task4.perform();
					SatisfactionValue task4value=replacementChecker.perform();
					int task4Space=task4.getTaskSpace();
					long task4Time=task4.getTaskTime();
					
					System.out.println(configuration.toString() + task3value
							+ "\t" + task4value + "\t" + task3Space + "\t"
							+ task4Space + "\t" + ((task3Time==0) ? 1 :task3Time) + "\t"
							+ ((task4Time==0) ? 1 :task4Time));
					resultWriter.write(configuration.toString()
							+ resultStringAdaper(task3value) + "\t"
							+ resultStringAdaper(task4value) + "\t"
							+ task3Time + "\t" + task4Time + "\t"
							+ ((task3Time==0) ? 1 :task3Time) + "\t" + ((task4Time==0) ? 1 :task4Time) + "\t"+record.getConstraintComputationTime()+"\n");
					resultWriter.close();
					
					System.gc();
					System.runFinalization();

					ConfWriter cw = new ConfWriter(confParser,
							randomConfigurationGenerator,
							confParser.getTestDirectory() + "/confFile.txt");
					cw.write();
					totalTimer.stop();
					System.out.println("Test performed in: "
							+ totalTimer.elapsed(TimeUnit.MINUTES) + " m ");
				}
			}
			testTimer.stop();
			System.out.println("Test performed in: "
					+ testTimer.elapsed(TimeUnit.MINUTES) + " m ");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());

		}
	}

	private String resultStringAdaper(SatisfactionValue satisfactionValue) {
		if (satisfactionValue.equals(SatisfactionValue.SATISFIED)) {
			return "Y";
		}
		if (satisfactionValue.equals(SatisfactionValue.NOTSATISFIED)) {
			return "N";
		}
		return "M";
	}

	private Replacement getRandomReplacement(Record record,
			List<Replacement> replacements) {

		Random random=new Random();
		int nextInt=random.nextInt(replacements.size());
		
		
		Replacement replacement = replacements.get(nextInt);

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
				.elapsed(TimeUnit.MINUTES));
		return constraint;
	}
	
	private static Constraint computeConstraint(BA claim, IBA model,
			Checker checker, State blackBoxState) {
		ConstraintGenerator cg = new ConstraintGenerator(checker);
		Constraint constraint = cg.perform(blackBoxState);
		return constraint;
	}
}
