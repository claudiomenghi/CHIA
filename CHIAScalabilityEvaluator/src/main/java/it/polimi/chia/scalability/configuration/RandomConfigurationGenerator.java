package it.polimi.chia.scalability.configuration;

import it.polimi.automata.BA;

import java.util.Iterator;
import java.util.List;

import com.google.common.base.Preconditions;

public class RandomConfigurationGenerator implements Iterator<Configuration> {

	// NUMBER OF STATES
	/**
	 * contains the initial number of states
	 */
	protected final int initialNumberOfStates;

	/**
	 * contains the final number of states
	 */
	protected final int finalNumberOfStates;

	/**
	 * contains the increment that is performed over the number of states
	 */
	protected final int incrementNumberOfStates;

	// TRANSITION DENSITY
	/**
	 * contains the initial number of transitions
	 */
	protected final double initialTransitionDensity;
	/**
	 * contains the final transition density
	 */
	protected final double finalTransitionDensity;
	/**
	 * contains the increment over the transition density
	 */
	protected final double incrementTransitionDensity;

	// ACCEPTING DENSITIES
	/**
	 * the initial density of accepting states
	 */
	protected final double initialAcceptingDensity;
	/**
	 * the final density of accepting states
	 */
	protected final double finalAcceptingDensity;
	/**
	 * the increment over the number of accepting states
	 */
	protected final double incrementAcceptingDensity;

	// TRANSPARENT DENSITIES
	/**
	 * the initial density of transparent states
	 */
	protected double initialTransparentDensity;
	/**
	 * the final density of transparent states
	 */
	protected double finalTransparentDensity;
	/**
	 * the increment over the density of transparent states
	 */
	protected double incrementTransparentDensity;

	// REPLACEMENT DENSITIES
	/**
	 * the initial replacement density
	 */
	protected double initialReplacementDensity;
	/**
	 * the final replacement density
	 */
	protected double finalreplacementDensity;
	/**
	 * increment replacement density
	 */
	protected double incrementReplacementDensity;

	protected int numberOfTests;

	// CURRENT configuration
	protected int configurationNumber;

	/**
	 * @return the configurationNumber
	 */
	public int getConfigurationNumber() {
		return configurationNumber;
	}

	// REPLACEMENT DENSITIES
	protected int totalCondigurations;

	protected List<BA> claims;

	public RandomConfigurationGenerator(List<BA> claims,
			int initialNumberOfStates, int finalNumberOfStates,
			int incrementNumberOfStates, double initialTransitionDensity,
			double finalTransitionDensity, double incrementTransitionDensity,
			double initialAcceptingDensity, double finalAcceptingDensity,
			double incrementAcceptingDensity, double initialTransparentDensity,
			double finalTransparentDensity, double incrementTransparentDensity,
			double initialReplacementDensity, double finalreplacementDensity,
			double incrementReplacementDensity, int numberOfTests,
			int currentConfiguration) {
		Preconditions.checkNotNull(claims,
				"The list of the claims cannot be null");
		Preconditions.checkArgument(claims.size() >= 1,
				"There must be at least a claim in the list");
		this.claims = claims;

		this.configurationNumber = currentConfiguration;
		this.initialNumberOfStates = initialNumberOfStates;
		this.finalNumberOfStates = finalNumberOfStates;
		this.incrementNumberOfStates = incrementNumberOfStates;
		this.initialTransitionDensity = initialTransitionDensity;
		this.finalTransitionDensity = finalTransitionDensity;
		this.incrementTransitionDensity = incrementTransitionDensity;
		// accepting density
		this.initialAcceptingDensity = initialAcceptingDensity;
		this.finalAcceptingDensity = finalAcceptingDensity;
		this.incrementAcceptingDensity = incrementAcceptingDensity;

		// transparent density
		this.initialTransparentDensity = initialTransparentDensity;
		this.finalTransparentDensity = finalTransparentDensity;
		this.incrementTransparentDensity = incrementTransparentDensity;

		// replacement density
		this.initialReplacementDensity = initialReplacementDensity;
		this.finalreplacementDensity = finalreplacementDensity;
		this.incrementReplacementDensity = incrementReplacementDensity;

		this.numberOfTests = numberOfTests;
		this.totalCondigurations = this.getNumberOfPossibleConfigurations();

	}

	@Override
	public boolean hasNext() {
		return configurationNumber <= totalCondigurations;
	}

	@Override
	public Configuration next() {

		int testNumber = (this.configurationNumber % this.numberOfTests) + 1;

		int currentClaim = (this.configurationNumber % (this.numberOfTests * this.claims
				.size())) / this.numberOfTests;

		
		int currentNumberOfStates = (this.configurationNumber % (this.numberOfTests
				* this.claims.size() * this.numberStatesConfigurations()))
				/ (this.numberOfTests * this.claims.size())
				* this.incrementNumberOfStates + this.initialNumberOfStates;

		double transitionDensity = (this.configurationNumber % (this.numberOfTests
				* this.claims.size() * this.numberStatesConfigurations() * this
					.transitionDensity()))
				/ (this.numberOfTests * this.claims.size() * this
						.numberStatesConfigurations())
				* this.incrementTransitionDensity
				+ this.initialTransitionDensity;

		double acceptingDensity = (this.configurationNumber % (this.numberOfTests
				* this.claims.size()
				* this.numberStatesConfigurations()
				* this.transitionDensity() * this
					.numberAcceptingStatesConfigurations()))
				/ (this.numberOfTests * this.claims.size()
						* this.numberStatesConfigurations() * this
							.transitionDensity())
				* this.incrementAcceptingDensity + this.initialAcceptingDensity;

		double transparentDensity = (this.configurationNumber % (this.numberOfTests
				* this.claims.size()
				* this.numberStatesConfigurations()
				* this.transitionDensity()
				* this.numberAcceptingStatesConfigurations() * this
					.transparentStatesConfigurations()))
				/ (this.numberOfTests * this.claims.size()
						* this.numberStatesConfigurations()
						* this.transitionDensity() * this
							.numberAcceptingStatesConfigurations())
				* this.incrementTransparentDensity
				+ this.initialTransparentDensity;

		double replacementDensity = (this.configurationNumber % (this.numberOfTests
				* this.claims.size()
				* this.numberStatesConfigurations()
				* this.transitionDensity()
				* this.numberAcceptingStatesConfigurations()
				* this.transparentStatesConfigurations() * this
					.replacementStatesConfigurations()))
				/ (this.numberOfTests * this.claims.size()
						* this.numberStatesConfigurations()
						* this.transitionDensity()
						* this.numberAcceptingStatesConfigurations() * this
							.transparentStatesConfigurations())
				* this.incrementReplacementDensity
				+ this.initialReplacementDensity;

		Configuration testConfiguration = new Configuration(
				configurationNumber, currentNumberOfStates,
				roundDouble(transitionDensity), roundDouble(acceptingDensity),
				roundDouble(transparentDensity),
				roundDouble(replacementDensity), this.claims.get(currentClaim),
				testNumber, currentClaim);

		this.configurationNumber++;

		return testConfiguration;

	}



	private double roundDouble(double number) {
		return Math.abs((number * 100.0) / 100.0);
	}

	public int getNumberOfPossibleConfigurations() {

		return this.numberOfTests * this.claims.size()
				* this.numberStatesConfigurations() * this.transitionDensity()
				* this.numberAcceptingStatesConfigurations()
				* this.transparentStatesConfigurations()
				* this.replacementStatesConfigurations();

	}

	protected int numberStatesConfigurations() {
		return ((this.finalNumberOfStates - this.initialNumberOfStates + this.incrementNumberOfStates) / this.incrementNumberOfStates);
	}

	protected int transitionDensity() {
		return (int) ((this.finalTransitionDensity
				- this.initialTransitionDensity + this.incrementTransitionDensity) / this.incrementTransitionDensity);
	}

	protected int numberAcceptingStatesConfigurations() {
		return (int) ((this.finalAcceptingDensity
				- this.initialAcceptingDensity + this.incrementAcceptingDensity) / this.incrementAcceptingDensity);
	}

	protected int transparentStatesConfigurations() {
		return (int) ((this.finalTransparentDensity
				- this.initialTransparentDensity + this.incrementTransparentDensity) / this.incrementTransparentDensity);
	}

	protected int replacementStatesConfigurations() {
		return (int) ((this.finalreplacementDensity
				- this.initialReplacementDensity + this.incrementReplacementDensity) / this.incrementReplacementDensity);
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return "RandomConfigurationGenerator [configurationNumber="
				+ configurationNumber + ", totalCondigurations="
				+ totalCondigurations + ", currentNumberOfStates=" + "]";
	}

}
