package it.polimi.chia.scalability;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.checker.Checker;
import it.polimi.checker.SatisfactionValue;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy.AcceptingType;
import it.polimi.chia.scalability.configuration.Configuration;
import it.polimi.chia.scalability.configuration.RandomConfigurationGenerator;
import it.polimi.chia.scalability.experiments.Experiment;
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
import it.polimi.constraints.components.RefinementGenerator;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.components.SubProperty;
import it.polimi.replacementchecker.ReplacementChecker;

import java.io.FileWriter;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
public class Experiment3 extends Experiment {


	private final  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	/**
	 * creates a new scalability test
	 * 
	 * @param confParser
	 *            the parser to be used to get the configuration
	 * @throws Exception 
	 */
	public Experiment3(ConfParser confParser, PrintStream out, String claimFolder) throws Exception {
		super(confParser, out, claimFolder);
	}

	
	public static void main(String[] args) throws Exception {

		System.out.println("----------------------------------  EXPERIMENT 3 ---------------------------------- ");
		Preconditions.checkNotNull(args[0], "You must specify the configuration file, which specifies the parameters of the random generation procedure as first parameter");
		Preconditions.checkNotNull(args[1], "You must specify the folder which contains the claims of interest");
		
		ConfParser parser = new ConfParser(args[0]);

		Experiment3 scalabilityTest = new Experiment3(parser, new PrintStream(
				System.out), args[1]);
		scalabilityTest.performTests();
	}

	@Override
	protected void test(Stopwatch timer) throws Exception {

		RandomConfigurationGenerator randomConfigurationGenerator = new RandomConfigurationGenerator(
				this.getClaims(),
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

		out.println("Number of configurations to be considered:"
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
			FileWriter resultWriter = new FileWriter(
					confParser.getTestDirectory() + "/Test"
							+ configuration.getTestNumber() + "/Claim"
							+ configuration.getClaimNumber() + "/"
							+ confParser.getResultsFile(), true);

			if (configuration.getConfigurationId() % 100 == 0) {
				out.println("--------------------------- CONFIGURATION: "
						+ configuration.getConfigurationId()
						+ "------------------------");
				out.println(randomConfigurationGenerator.toString());
				out.println(stat.toString());

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
			IBARandomGenerator ibaGenerator = new IBARandomGenerator(modelBA,
					new StateFactory(), configuration.getTransparentDensity(),
					configuration.getReplacementDensity());

			IBA ibaModel = ibaGenerator.perform();

			/**
			 * executing the task 2
			 */
			Task2 task2 = new Task2(configuration, ibaModel, record,
					AcceptingType.BA);
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

				IBA refinedModel = new RefinementGenerator(ibaModel,
						replacement).perform();
				Task3 task3 = new Task3(configuration, refinedModel, record,
						AcceptingType.BA);
				Checker checkerTask3 = task3.perform();

				SatisfactionValue task3value = checkerTask3.perform();
				int task3Space = task3.getTaskSpace();
				long task3Time = task3.getTaskTime();

				out.println("Computing the constraints "
						+ dateFormat.format(new Date()));
				Constraint constraint = computeConstraint(
						 checker, replacement, record);
				Task4 task4 = new Task4(configuration, replacement, constraint,
						AcceptingType.BA);
				ReplacementChecker replacementChecker = task4.perform();
				SatisfactionValue task4value = replacementChecker.perform();
				int task4Space = task4.getTaskSpace();
				long task4Time = task4.getTaskTime();

				out.println(configuration.toString() + task3value + "\t"
						+ task4value + "\t" + task3Space + "\t" + task4Space
						+ "\t" + ((task3Time == 0) ? 1 : task3Time) + "\t"
						+ ((task4Time == 0) ? 1 : task4Time));
				resultWriter.write(configuration.toString()
						+ adapter.adapt(task3value) + "\t"
						+ adapter.adapt(task4value) + "\t" + task3Time + "\t"
						+ task4Time + "\t" + ((task3Time == 0) ? 1 : task3Time)
						+ "\t" + ((task4Time == 0) ? 1 : task4Time) + "\t"
						+ record.getConstraintComputationTime() + "\n");
				resultWriter.close();

				System.gc();
				System.runFinalization();

				ConfWriter cw = new ConfWriter(confParser,
						randomConfigurationGenerator,
						confParser.getTestDirectory() + "/confFile.txt");
				cw.write();
				totalTimer.stop();
				out.println("Test performed in: "
						+ totalTimer.elapsed(TimeUnit.MINUTES) + " m ");
			}
		}
		testTimer.stop();
		out.println("Test performed in: " + testTimer.elapsed(TimeUnit.MINUTES)
				+ " m ");

	}

	private Replacement getRandomReplacement(Record record,
			List<Replacement> replacements) {

		Random random = new Random();
		int nextInt = random.nextInt(replacements.size());

		Replacement replacement = replacements.get(nextInt);

		record.setNumReplacementIncomingTransitions(replacement
				.getIncomingTransitions().size());
		record.setNumReplacementOutgoingTransitions(replacement
				.getOutgoingTransitions().size());

		record.setNumReplacementStates(replacement.getAutomaton().size());
		return replacement;
	}

	private Constraint computeConstraint( Checker checker, Replacement replacement, Record record) {
		Stopwatch constraintComputationTimer = Stopwatch.createUnstarted();

		constraintComputationTimer.start();
		Constraint constraint = computeConstraint(
				  checker,
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
	
	

	private static Constraint computeConstraint(
			Checker checker, State blackBoxState) {
		ConstraintGenerator cg = new ConstraintGenerator(checker);
		return cg.perform(blackBoxState);
	}
}
