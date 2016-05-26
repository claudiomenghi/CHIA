package it.polimi.chia.scalability;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.state.StateFactory;
import it.polimi.checker.Checker;
import it.polimi.checker.SatisfactionValue;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy.AcceptingType;
import it.polimi.chia.scalability.claimLoader.ClaimLoader;
import it.polimi.chia.scalability.configuration.Configuration;
import it.polimi.chia.scalability.configuration.RandomConfigurationGenerator;
import it.polimi.chia.scalability.experiments.Experiment;
import it.polimi.chia.scalability.parsers.ConfParser;
import it.polimi.chia.scalability.parsers.ConfWriter;
import it.polimi.chia.scalability.randomGenerators.BARandomGenerator;
import it.polimi.chia.scalability.randomGenerators.IBARandomGenerator;
import it.polimi.chia.scalability.results.Record;
import it.polimi.chia.scalability.results.Statistics;
import it.polimi.chia.scalability.tasks.Task1;
import it.polimi.chia.scalability.tasks.Task2;

import java.io.FileWriter;
import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.google.common.base.Stopwatch;

/**
 * 
 * The ScalabilityTest reads the scalability configuration from an appropriate
 * XML file
 * 
 * @author Claudio Menghi
 *
 */
public class Experiment1 extends Experiment {

	private static final Logger LOGGER = Logger.getLogger(Experiment1.class);

	/**
	 * The accepting policy to be used
	 */
	private static final AcceptingType acceptingPolicy = AcceptingType.BA;

	/**
	 * creates a new scalability test
	 * 
	 * @param confParser
	 *            the parser to be used to get the configuration
	 */
	public Experiment1(ConfParser confParser, PrintStream out) {
		super(confParser, out);
	}

	public static void main(String[] args) throws Exception {
		ConfParser parser = new ConfParser("configEx1.txt");

		Experiment1 scalabilityTest = new Experiment1(parser, new PrintStream(
				System.out));
		scalabilityTest.performTests();
	}

	@Override
	protected void test(Stopwatch timer) throws Exception {

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
			Task1 task1 = new Task1(configuration, modelBA, acceptingPolicy);
			Checker task1Checker = task1.perform();
			SatisfactionValue task1value = task1Checker.perform();
			int stackSpace = task1.getTaskSpace();
			long stackTime = task1.getTaskTime();
			task1 = null;

			IBARandomGenerator ibaGenerator = new IBARandomGenerator(modelBA,
					new StateFactory(), configuration.getTransparentDensity(),
					configuration.getReplacementDensity());

			IBA model = ibaGenerator.perform();

			/**
			 * checks the incomplete model
			 */
			Task2 task2 = new Task2(configuration, model, record,
					acceptingPolicy);
			Checker checker = task2.perform();
			SatisfactionValue task2value = checker.perform();

			out.println(configuration.toString() + task1value + "\t"
					+ task2value + "\t" + stackSpace + "\t"
					+ task2.getTaskSpace() + "\t"
					+ ((stackTime == 0) ? 1 : stackTime) + "\t"
					+ ((task2.getTaskTime() == 0) ? 1 : task2.getTaskTime()));
			resultWriter.write(configuration.toString()
					+ adapter.adapt(task1value) + "\t"
					+ adapter.adapt(task2value) + "\t" + stackSpace + "\t"
					+ task2.getTaskSpace() + "\t"
					+ ((stackTime == 0) ? 1 : stackTime) + "\t"
					+ ((task2.getTaskTime() == 0) ? 1 : task2.getTaskTime())
					+ "\n");
			resultWriter.close();
			System.gc();
			System.runFinalization();

			ConfWriter cw = new ConfWriter(confParser,
					randomConfigurationGenerator, confParser.getTestDirectory()
							+ "/confFile.txt");
			cw.write();
			totalTimer.stop();

		}
		testTimer.stop();
		out.println("Test performed in: " + testTimer.elapsed(TimeUnit.MINUTES)
				+ " m ");

	}

}
