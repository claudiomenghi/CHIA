package it.polimi.chia.scalability.parsers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

import com.google.common.base.Preconditions;

/**
 * The parser to be used to load the parsing parameters
 * 
 * @author Claudio Menghi
 *
 */
public class Ex3bConfParser {

	/**
	 * contains the file where the results of the parsing are written
	 */
	private final static String RESULT_FILE = "RESULTS:";

	/**
	 * contains the file where the possible results of the parsing are written
	 */
	private final static String RESULT_FILE_POSSIBLY = "RESULTSPOSSIBLY:";

	/**
	 * contains the directory where the results are written
	 */
	private final static String TESTD_IRECTORY = "TESTDIRECTORY";

	/**
	 * the number of tests to be performed
	 */
	private final static String N_TESTS = "N_TESTS";

	// NUMBER OF STATES
	private final static String INIT_NSTATES = "INIT_NSTATES";
	private final static String FINAL_NSTATES = "FINAL_NSTATES";
	private final static String INCREMENT_NSTATES = "INCREMENT_NSTATES";
	private final static String CURRENT_NSTATES = "CURRENT_NSTATES";

	// TRANSITIONS DENSITIES
	private static final String INIT_TRANSITION_DENSITY = "INIT_TRANSITION_DENSITY";
	private static final String FINAL_TRANSITION_DENSITY = "FINAL_TRANSITION_DENSITY";
	private static final String INCREMENT_TRANSITION_DENSITY = "INCREMENT_TRANSITION_DENSITY";

	// ACCEPTING DENSITIES
	private static final String INIT_ACCEPTING_DENSITY = "INIT_ACCEPTING_DENSITY";
	private static final String FINAL_ACCEPTING_DENSITY = "FINAL_ACCEPTING_DENSITY";
	private static final String INCREMENT_ACCEPTING_DENSITY = "INCREMENT_ACCEPTING_DENSITY";

	// TRANSPARENT DENSITIES
	private static final String INIT_TRANSPARENT_DENSITY = "INIT_TRANSPARENT_DENSITY";
	private static final String FINAL_TRANSPARENT_DENSITY = "FINAL_TRANSPARENT_DENSITY";
	private static final String INCREMENT_TRANSPARENT_DENSITY = "INCREMENT_TRANSPARENT_DENSITY";

	// REPLACEMENT DENSITIES
	private static final String INIT_REPLACEMENT_DENSITY = "INIT_REPLACEMENT_DENSITY";
	private static final String FINAL_REPLACEMENT_DENSITY = "FINAL_REPLACEMENT_DENSITY";
	private static final String INCREMENT_REPLACEMENT_DENSITY = "INCREMENT_REPLACEMENT_DENSITY";
	
	// REPLACEMENT PLUGTRANSITIONS DENSITIES
	private static final String INIT_REPLACEMENT_PLUGTRANSITIONS_DENSITY = "INIT_REPLACEMENT_PLUGTRANSITIONS";
	private static final String FINAL_REPLACEMENT_PLUGTRANSITIONS_DENSITY = "FINAL_REPLACEMENT_PLUGTRANSITIONS";
	private static final String INCREMENT_REPLACEMENT_PLUGTRANSITIONS_DENSITY = "INCREMENT_REPLACEMENT_PLUGTRANSITIONS";

	// REPLACEMENT DENSITIES
	/**
	 * the initial replacement density
	 */
	private int initialReplacementDensityPlugTransition;
	/**
	 * the final replacement density
	 */
	private int finalreplacementDensityPlugTransition;
	/**
	 * increment replacement density
	 */
	private int incrementReplacementDensityPlugTransition;


	private static final String CURRENT_CONFIGURATION = "CURRENT_CONFIGURATION";
	/**
	 * the file where the results must be written
	 */
	private String resultsFile;

	/**
	 * the file where the results of the possibly satisfied claim must be
	 * written
	 */
	private String resultsFilePossibly;

	/**
	 * the directory where the results must be written
	 */
	private String testDirectory;

	/**
	 * the number of tests to be performed
	 */
	private int numberOfTests;

	// NUMBER OF STATES
	/**
	 * contains the initial number of states
	 */
	private int initialNumberOfStates;

	/**
	 * contains the final number of states
	 */
	private int finalNumberOfStates;

	/**
	 * contains the increment over the number of the states
	 */
	private int incrementNumberOfStates;

	/**
	 * contains the currentNumberOfStates
	 */
	private int currentNumberOfStates;

	// TRANSITION DENSITIES
	/**
	 * contains the initial number of transitions
	 */
	private double initialTransitionDensity;
	/**
	 * contains the final transition density
	 */
	private double finalTransitionDensity;
	/**
	 * contains the increment over the transition density
	 */
	private double incrementTransitionDensity;
	/**
	 * contains the current transition density
	 */
	private double currentTransitionDensity;

	// ACCEPTING DENSITIES
	/**
	 * the initial density of accepting states
	 */
	private double initialAcceptingDensity;
	/**
	 * the final density of accepting states
	 */
	private double finalAcceptingDensity;
	/**
	 * the increment over the number of accepting states
	 */
	private double incrementAcceptingDensity;

	// TRANSPARENT DENSITIES
	/**
	 * the initial density of transparent states
	 */
	private double initialTransparentDensity;
	/**
	 * the final density of transparent states
	 */
	private double finalTransparentDensity;
	/**
	 * the increment over the density of transparent states
	 */
	private double incrementTransparentDensity;

	// REPLACEMENT DENSITIES
	/**
	 * the initial replacement density
	 */
	private double initialReplacementDensity;
	/**
	 * the final replacement density
	 */
	private double finalreplacementDensity;
	/**
	 * increment replacement density
	 */
	private double incrementReplacementDensity;

	/**
	 * The current configuration
	 */
	private int currentConfiguration;

	/**
	 * The parser of the conf file
	 * 
	 * @param confFile
	 *            the file to be parsed
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws NullPointerException
	 *             is thrown if the configuration file is null
	 * 
	 */
	public Ex3bConfParser(String confFile) throws IOException, URISyntaxException {
		Preconditions.checkNotNull(confFile,
				"The file to be considered cannot be null");
		if(ClassLoader.getSystemResource(confFile)==null){
			throw new IllegalArgumentException("The file "+confFile+" has not been found");
		}
		File f = new File(ClassLoader.getSystemResource(confFile).toURI());
		Preconditions.checkState(f.exists() && !f.isDirectory(), "The file "
				+ confFile + " does not exists");
		this.currentConfiguration = 1;
		this.parse(f);
	}

	private void parse(File f) throws IOException {
		FileInputStream inputStream = new FileInputStream(f);
		BufferedReader br = new BufferedReader(new InputStreamReader(
				inputStream));
		String line = null;
		while ((line = br.readLine()) != null) {
			this.parseLine(line);
		}
		br.close();
	}

	private void parseLine(String line) {
		if (line.startsWith(RESULT_FILE)) {
			this.resultsFile = line.substring(RESULT_FILE.length() + 1);
		}
		if (line.startsWith(RESULT_FILE_POSSIBLY)) {
			this.resultsFilePossibly = line.substring(RESULT_FILE_POSSIBLY
					.length() + 1);
		}
		if (line.startsWith(TESTD_IRECTORY)) {
			this.testDirectory = line.substring(TESTD_IRECTORY.length() + 2);
		}
		if (line.startsWith(N_TESTS)) {
			this.numberOfTests = Integer.parseInt(line.substring(N_TESTS
					.length() + 2));
		}
		if (line.startsWith(CURRENT_CONFIGURATION)) {
			this.currentConfiguration = Integer.parseInt(line
					.substring(CURRENT_CONFIGURATION.length() + 2));
		}
		checkNumberOfStates(line);
		checkTransitionDensities(line);
		// ACCEPTING DENSITY
		checkAcceptingStates(line);
		checkTransparentStates(line);
		checkReplacementDensity(line);
		checkReplacementDensityPlugTransitions(line);
	}

	private void checkReplacementDensity(String line) {
		if (line.startsWith(INIT_REPLACEMENT_DENSITY)) {
			this.initialReplacementDensity = Double.parseDouble(line
					.substring(INIT_REPLACEMENT_DENSITY.length() + 2));
		}
		if (line.startsWith(FINAL_REPLACEMENT_DENSITY)) {
			this.finalreplacementDensity = Double.parseDouble(line
					.substring(FINAL_REPLACEMENT_DENSITY.length() + 2));
		}
		if (line.startsWith(INCREMENT_REPLACEMENT_DENSITY)) {
			this.incrementReplacementDensity = Double.parseDouble(line
					.substring(INCREMENT_REPLACEMENT_DENSITY.length() + 2));
		}
	}

	private void checkTransparentStates(String line) {
		if (line.startsWith(INIT_TRANSPARENT_DENSITY)) {
			this.initialTransparentDensity = Double.parseDouble(line
					.substring(INIT_TRANSPARENT_DENSITY.length() + 2));
		}
		if (line.startsWith(FINAL_TRANSPARENT_DENSITY)) {
			this.finalTransparentDensity = Double.parseDouble(line
					.substring(FINAL_TRANSPARENT_DENSITY.length() + 2));
		}
		if (line.startsWith(INCREMENT_TRANSPARENT_DENSITY)) {
			this.incrementTransparentDensity = Double.parseDouble(line
					.substring(INCREMENT_TRANSPARENT_DENSITY.length() + 2));
		}
	}

	private void checkAcceptingStates(String line) {
		if (line.startsWith(INIT_ACCEPTING_DENSITY)) {
			this.initialAcceptingDensity = Double.parseDouble(line
					.substring(INIT_ACCEPTING_DENSITY.length() + 2));
		}
		if (line.startsWith(FINAL_ACCEPTING_DENSITY)) {
			this.finalAcceptingDensity = Double.parseDouble(line
					.substring(FINAL_ACCEPTING_DENSITY.length() + 2));
		}
		if (line.startsWith(INCREMENT_ACCEPTING_DENSITY)) {
			this.incrementAcceptingDensity = Double.parseDouble(line
					.substring(INCREMENT_ACCEPTING_DENSITY.length() + 2));
		}
	}

	private void checkTransitionDensities(String line) {
		// TRANSITION DENSITY
		if (line.startsWith(INIT_TRANSITION_DENSITY)) {
			this.initialTransitionDensity = Double.parseDouble(line
					.substring(INIT_TRANSITION_DENSITY.length() + 2));
		}
		if (line.startsWith(FINAL_TRANSITION_DENSITY)) {
			this.finalTransitionDensity = Double.parseDouble(line
					.substring(FINAL_TRANSITION_DENSITY.length() + 2));
		}
		if (line.startsWith(INCREMENT_TRANSITION_DENSITY)) {
			this.incrementTransitionDensity = Double.parseDouble(line
					.substring(INCREMENT_TRANSITION_DENSITY.length() + 2));
		}
	}

	private void checkNumberOfStates(String line) {
		// NUMBER OF STATES
		if (line.startsWith(INIT_NSTATES)) {
			this.initialNumberOfStates = Integer.parseInt(line
					.substring(INIT_NSTATES.length() + 2));
		}
		if (line.startsWith(FINAL_NSTATES)) {
			this.finalNumberOfStates = Integer.parseInt(line
					.substring(FINAL_NSTATES.length() + 2));
		}
		if (line.startsWith(INCREMENT_NSTATES)) {
			this.incrementNumberOfStates = Integer.parseInt(line
					.substring(INCREMENT_NSTATES.length() + 2));
		}
		if (line.startsWith(CURRENT_NSTATES)) {
			this.currentNumberOfStates = Integer.parseInt(line
					.substring(CURRENT_NSTATES.length() + 2));
		}
	}

	private void checkReplacementDensityPlugTransitions(String line) {
		if (line.startsWith(INIT_REPLACEMENT_PLUGTRANSITIONS_DENSITY)) {
			this.setInitialReplacementDensityPlugTransition(Integer.parseInt(line
					.substring(INIT_REPLACEMENT_PLUGTRANSITIONS_DENSITY.length() + 2)));
		}
		if (line.startsWith(FINAL_REPLACEMENT_PLUGTRANSITIONS_DENSITY)) {
			this.setFinalreplacementDensityPlugTransition(Integer.parseInt(line
					.substring(FINAL_REPLACEMENT_PLUGTRANSITIONS_DENSITY.length() + 2)));
		}
		if (line.startsWith(INCREMENT_REPLACEMENT_PLUGTRANSITIONS_DENSITY)) {
			this.setIncrementReplacementDensityPlugTransition(Integer.parseInt(line
					.substring(INCREMENT_REPLACEMENT_PLUGTRANSITIONS_DENSITY.length() + 2)));
		}
	}

	
	/**
	 * @return the initAcceptingDensity
	 */
	public double getInitAcceptingDensity() {
		return this.initialAcceptingDensity;
	}

	/**
	 * @return the finalAcceptingDensity
	 */
	public double getFinalAcceptingDensity() {
		return this.finalAcceptingDensity;
	}

	/**
	 * @return the incrementAcceptingDensity
	 */
	public double getIncrementAcceptingDensity() {
		return this.incrementAcceptingDensity;
	}

	/**
	 * @return the initialTransitionDensity
	 */
	public double getInitialTransitionDensity() {
		return initialTransitionDensity;
	}

	/**
	 * @return the finalTransitionDensity
	 */
	public double getFinalTransitionDensity() {
		return finalTransitionDensity;
	}

	/**
	 * @return the incrementTransitionDensity
	 */
	public double getIncrementTransitionDensity() {
		return incrementTransitionDensity;
	}

	/**
	 * @return the finalNumberOfStates
	 */
	public int getFinalNumberOfStates() {
		return finalNumberOfStates;
	}

	/**
	 * @return the incrementNumberOfStates
	 */
	public int getIncrementNumberOfStates() {
		return incrementNumberOfStates;
	}

	/**
	 * @return the resultsFile
	 */
	public String getResultsFile() {
		return resultsFile;
	}

	/**
	 * @return the resultsFilePossibly
	 */
	public String getResultsFilePossibly() {
		return resultsFilePossibly;
	}

	/**
	 * @return the testDirectory
	 */
	public String getTestDirectory() {
		return testDirectory;
	}

	/**
	 * @return the numberOfTests
	 */
	public int getNumberOfTests() {
		return numberOfTests;
	}

	/**
	 * @return the initialNumberOfStates
	 */
	public int getInitialNumberOfStates() {
		return initialNumberOfStates;
	}

	/**
	 * @return the initialTransparentDensity
	 */
	public double getInitialTransparentDensity() {
		return initialTransparentDensity;
	}

	/**
	 * @return the finalTransparentDensity
	 */
	public double getFinalTransparentDensity() {
		return finalTransparentDensity;
	}

	/**
	 * @return the incrementTransparentDensity
	 */
	public double getIncrementTransparentDensity() {
		return incrementTransparentDensity;
	}

	/**
	 * @return the initialReplacementDensity
	 */
	public double getInitialReplacementDensity() {
		return initialReplacementDensity;
	}

	/**
	 * @return the finalreplacementDensity
	 */
	public double getFinalReplacementDensity() {
		return finalreplacementDensity;
	}

	/**
	 * @return the incrementReplacementDensity
	 */
	public double getIncrementReplacementDensity() {
		return incrementReplacementDensity;
	}

	/**
	 * @return the currentNumberOfStates
	 */
	public int getCurrentNumberOfStates() {
		return currentNumberOfStates;
	}

	/**
	 * @return the currentTransitionDensity
	 */
	public double getCurrentTransitionDensity() {
		return currentTransitionDensity;
	}

	/**
	 * @return the currentConfiguration
	 */
	public int getCurrentConfiguration() {
		return currentConfiguration;
	}
	
	/**
	 * @return the initialReplacementDensityPlugTransition
	 */
	public int getInitialReplacementDensityPlugTransition() {
		return initialReplacementDensityPlugTransition;
	}

	/**
	 * @param initialReplacementDensityPlugTransition the initialReplacementDensityPlugTransition to set
	 */
	public void setInitialReplacementDensityPlugTransition(
			int initialReplacementDensityPlugTransition) {
		this.initialReplacementDensityPlugTransition = initialReplacementDensityPlugTransition;
	}

	/**
	 * @return the finalreplacementDensityPlugTransition
	 */
	public int getFinalreplacementDensityPlugTransition() {
		return finalreplacementDensityPlugTransition;
	}

	/**
	 * @param finalreplacementDensityPlugTransition the finalreplacementDensityPlugTransition to set
	 */
	public void setFinalreplacementDensityPlugTransition(
			int finalreplacementDensityPlugTransition) {
		this.finalreplacementDensityPlugTransition = finalreplacementDensityPlugTransition;
	}

	/**
	 * @return the incrementReplacementDensityPlugTransition
	 */
	public int getIncrementReplacementDensityPlugTransition() {
		return incrementReplacementDensityPlugTransition;
	}

	/**
	 * @param incrementReplacementDensityPlugTransition the incrementReplacementDensityPlugTransition to set
	 */
	public void setIncrementReplacementDensityPlugTransition(
			int incrementReplacementDensityPlugTransition) {
		this.incrementReplacementDensityPlugTransition = incrementReplacementDensityPlugTransition;
	}

}
