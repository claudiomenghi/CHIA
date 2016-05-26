package it.polimi.chia.scalability.experiments;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.io.out.ElementToStringTransformer;
import it.polimi.automata.io.out.ModelToStringTrasformer;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.checker.Checker;
import it.polimi.checker.SatisfactionValue;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy.AcceptingType;
import it.polimi.chia.scalability.claimLoader.ClaimLoader;
import it.polimi.chia.scalability.configuration.Configuration;
import it.polimi.chia.scalability.configuration.Ex3Configuration;
import it.polimi.chia.scalability.configuration.Ex3bRandomConfigurationGenerator;
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

import java.io.File;
import java.io.FileWriter;
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
public class Experiment3b {

	private static final Logger LOGGER = Logger.getLogger(Experiment3b.class);

	/**
	 * The accepting policy to be used
	 */
	private static final  AcceptingType acceptingPolicy = AcceptingType.BA;

	private final Ex3bConfParser confParser;
	
	
	/**
	 * creates a new scalability test
	 * 
	 * @param confParser
	 *            the parser to be used to get the configuration
	 */
	public Experiment3b(Ex3bConfParser confParser) {
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
				.println("--------------------------- STARTING THE EXPERIMENT 3b: ------------------------");

		System.out.println("creating the folder structure");
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
		
		System.out.println("folder structure created");

		test(timer);
	}

	public static void main(String[] args) throws Exception {
		
		Ex3bConfParser parser = new Ex3bConfParser("configEx3b.txt");

		Experiment3b scalabilityTest = new Experiment3b(parser);
		scalabilityTest.performTests();
	}

	private void test(Stopwatch timer) throws Exception {

		try {


			Ex3bRandomConfigurationGenerator randomConfigurationGenerator = new Ex3bRandomConfigurationGenerator(
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
					confParser.getInitialReplacementDensityPlugTransition(),
					confParser.getFinalreplacementDensityPlugTransition(),
					confParser.getIncrementReplacementDensityPlugTransition(),
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
						configuration.getReplacementDensity(), configuration.getReplacementPlugDensity());

				IBA ibaModel = ibaGenerator.perform();
				

				if(modelBA.getOutTransitions(modelBA.getInitialStates().iterator().next()).size()>0){
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
						Replacement replacement = ibaGenerator
								.getNonEmptyReplacements();
						
						IBA refinementmodelBA=new RefinementGenerator(ibaModel, replacement).perform();
						
						Task1 task3 = new Task1(configuration, refinementmodelBA,  acceptingPolicy);
						Checker checkerTask3=task3.perform();

						SatisfactionValue task3value=checkerTask3.perform();
						int task3Space=task3.getTaskSpace();
						long task3Time=task3.getTaskTime();
						
						Constraint constraint = computeConstraint(configuration, ibaModel,
								checker, replacement, record);
						Task4 task4=new Task4(configuration, replacement, constraint, acceptingPolicy);
						ReplacementChecker replacementChecker=task4.perform();
						SatisfactionValue task4value=replacementChecker.perform();
						int task4Space=task4.getTaskSpace();
						long task4Time=task4.getTaskTime();
						
						if(task3value!=task4value){
							resultWriter.close();
							System.out.println("TASK 3 returns: "+task3value+"\t TASK 4 returns: "+task4value);
							
							System.out.println("The counterexample is: ");
							
							if(task3value==SatisfactionValue.NOTSATISFIED){
								System.out.println(checkerTask3.getCounterexample());
							}
							if(task4value==SatisfactionValue.NOTSATISFIED){
								System.out.println(replacementChecker.getFilteredCounterexample());
							}
							
							
							System.out.println(new ElementToStringTransformer().transform(new ReplacementToElementTransformer().transform(replacement)));
							System.out.println(new ModelToStringTrasformer(refinementmodelBA).perform());
							System.out.println(new ModelToStringTrasformer(ibaModel).perform());
							
							throw new InternalError("The return value of the two model checking procedures are different");
						}
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

						
						totalTimer.stop();
						
					}
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
