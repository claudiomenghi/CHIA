package it.polimi.chia.scalability.parsers;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * The parser to be used to load the parsing parameters
 * 
 * @author Claudio Menghi
 *
 */
public class Ex3bConfParser extends ConfParser{

	
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
		super(confFile);
	}


	@Override
	protected void parseLine(String line) {
		super.parseLine(line);
		
		checkReplacementDensityPlugTransitions(line);
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
