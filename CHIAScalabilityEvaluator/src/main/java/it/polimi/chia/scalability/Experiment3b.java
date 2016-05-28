package it.polimi.chia.scalability;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.io.out.ElementToStringTransformer;
import it.polimi.automata.io.out.ModelToStringTrasformer;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.checker.Checker;
import it.polimi.checker.SatisfactionValue;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy.AcceptingType;
import it.polimi.chia.scalability.configuration.Ex3Configuration;
import it.polimi.chia.scalability.configuration.Ex3bRandomConfigurationGenerator;
import it.polimi.chia.scalability.experiments.Experiment;
import it.polimi.chia.scalability.parsers.Ex3bConfParser;
import it.polimi.chia.scalability.randomGenerators.BARandomGenerator;
import it.polimi.chia.scalability.randomGenerators.IBARandomGeneratorOneBlackBoxState;
import it.polimi.chia.scalability.results.Record;
import it.polimi.chia.scalability.results.Statistics;
import it.polimi.chia.scalability.tasks.Task1;
import it.polimi.chia.scalability.tasks.Task2;
import it.polimi.chia.scalability.tasks.Task4;
import it.polimi.constraintcomputation.ConstraintGenerator;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.components.RefinementGenerator;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.components.SubProperty;
import it.polimi.constraints.io.out.replacement.ReplacementToElementTransformer;
import it.polimi.replacementchecker.ReplacementChecker;

import java.io.FileWriter;
import java.io.PrintStream;
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
public class Experiment3b extends Experiment {

	private static final Logger LOGGER = Logger.getLogger(Experiment3b.class);

	/**
	 * creates a new scalability test
	 * 
	 * @param confParser
	 *            the parser to be used to get the configuration
	 * @throws Exception 
	 */
	public Experiment3b(Ex3bConfParser confParser, PrintStream out,  String claimFolder) throws Exception {
		super(confParser, out, claimFolder);
	}

	public static void main(String[] args) throws Exception {

		Preconditions.checkNotNull(args[0], "You must specify the configuration file, which specifies the parameters of the random generation procedure as first parameter");
		Preconditions.checkNotNull(args[1], "You must specify the folder which contains the claims of interest");

		Ex3bConfParser parser = new Ex3bConfParser(args[0]);

		Experiment3b scalabilityTest = new Experiment3b(parser,
				new PrintStream(System.out), args[1]);
		scalabilityTest.performTests();
	}

	@Override
	protected void test(Stopwatch timer) throws Exception {

		Ex3bConfParser confParser=(Ex3bConfParser) this.confParser;
		Ex3bRandomConfigurationGenerator randomConfigurationGenerator = new Ex3bRandomConfigurationGenerator(
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
				confParser.getInitialReplacementDensityPlugTransition(),
				confParser.getFinalreplacementDensityPlugTransition(),
				confParser.getIncrementReplacementDensityPlugTransition(),
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

			Ex3Configuration configuration = randomConfigurationGenerator
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

			IBARandomGeneratorOneBlackBoxState ibaGenerator = new IBARandomGeneratorOneBlackBoxState(
					modelBA, new StateFactory(),
					configuration.getTransparentDensity(),
					configuration.getReplacementDensity(),
					configuration.getReplacementPlugDensity());

			IBA ibaModel = ibaGenerator.perform();

			if (modelBA.getOutTransitions(
					modelBA.getInitialStates().iterator().next()).size() > 0) {
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
					Replacement replacement = ibaGenerator
							.getNonEmptyReplacements();

					IBA refinementmodelBA = new RefinementGenerator(ibaModel,
							replacement).perform();

					Task1 task3 = new Task1(configuration, refinementmodelBA,
							AcceptingType.BA);
					Checker checkerTask3 = task3.perform();

					SatisfactionValue task3value = checkerTask3.perform();
					int task3Space = task3.getTaskSpace();
					long task3Time = task3.getTaskTime();

					Constraint constraint = computeConstraint(confParser,
							ibaModel, checker, replacement, record);
					Task4 task4 = new Task4(configuration, replacement,
							constraint, AcceptingType.BA);
					ReplacementChecker replacementChecker = task4.perform();
					SatisfactionValue task4value = replacementChecker.perform();
					int task4Space = task4.getTaskSpace();
					long task4Time = task4.getTaskTime();

					if (task3value != task4value) {
						resultWriter.close();
						out.println("TASK 3 returns: " + task3value
								+ "\t TASK 4 returns: " + task4value);

						out.println("The counterexample is: ");

						if (task3value == SatisfactionValue.NOTSATISFIED) {
							out.println(checkerTask3.getCounterexample());
						}
						if (task4value == SatisfactionValue.NOTSATISFIED) {
							out.println(replacementChecker
									.getFilteredCounterexample());
						}

						out.println(new ElementToStringTransformer()
								.transform(new ReplacementToElementTransformer()
										.transform(replacement)));
						out.println(new ModelToStringTrasformer(
								refinementmodelBA).perform());
						out.println(new ModelToStringTrasformer(ibaModel)
								.perform());

						throw new InternalError(
								"The return value of the two model checking procedures are different");
					}
					out.println(configuration.toString() + task3value
							+ "\t" + task4value + "\t" + task3Space + "\t"
							+ task4Space + "\t"
							+ ((task3Time == 0) ? 1 : task3Time) + "\t"
							+ ((task4Time == 0) ? 1 : task4Time));
					resultWriter.write(configuration.toString()
							+ adapter.adapt(task3value) + "\t"
							+ adapter.adapt(task4value) + "\t" + task3Time
							+ "\t" + task4Time + "\t"
							+ ((task3Time == 0) ? 1 : task3Time) + "\t"
							+ ((task4Time == 0) ? 1 : task4Time) + "\t"
							+ record.getConstraintComputationTime() + "\n");
					resultWriter.close();

					System.gc();
					System.runFinalization();

					totalTimer.stop();

				}
			}

		}
		testTimer.stop();
		out.println("Test performed in: " + testTimer.elapsed(TimeUnit.MINUTES)
				+ " m ");

	}

	private Constraint computeConstraint(Ex3bConfParser configuration,
			IBA model, Checker checker, Replacement replacement, Record record) {
		Stopwatch constraintComputationTimer = Stopwatch.createUnstarted();

		constraintComputationTimer.start();
		Constraint constraint = computeConstraint(
				 model, checker,
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

	private static Constraint computeConstraint(IBA model,
			Checker checker, State blackBoxState) {
		ConstraintGenerator cg = new ConstraintGenerator(checker);

		return cg.perform(blackBoxState);
	}
}
