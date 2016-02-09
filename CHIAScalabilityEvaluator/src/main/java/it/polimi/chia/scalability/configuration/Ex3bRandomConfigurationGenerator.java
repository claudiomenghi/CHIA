package it.polimi.chia.scalability.configuration;

import it.polimi.automata.BA;

import java.util.Iterator;
import java.util.List;

public class Ex3bRandomConfigurationGenerator extends RandomConfigurationGenerator implements
		Iterator<Configuration> {


	// PLUG REPLACEMENT DENSITIES
	private int initialReplacementPugTransitionDensity=1;
	private int finalreplacementPlugTransitionDensity=1;
	private int incrementReplacementPlugTransitionDensity=1;

	private boolean initialized=false;
	public Ex3bRandomConfigurationGenerator(List<BA> claims,
			int initialNumberOfStates, int finalNumberOfStates,
			int incrementNumberOfStates, double initialTransitionDensity,
			double finalTransitionDensity, double incrementTransitionDensity,
			double initialAcceptingDensity, double finalAcceptingDensity,
			double incrementAcceptingDensity, double initialTransparentDensity,
			double finalTransparentDensity, double incrementTransparentDensity,
			double initialReplacementDensity, double finalreplacementDensity,
			double incrementReplacementDensity,
			int initialReplacementPugTransitionDensity,
			int finalreplacementPlugTransitionDensity,
			int incrementReplacementPlugTransitionDensity, 
			int numberOfTests,
			int currentConfiguration) {
		super(claims, initialNumberOfStates, finalNumberOfStates, incrementNumberOfStates, initialTransitionDensity, finalTransitionDensity, incrementTransitionDensity, initialAcceptingDensity, finalAcceptingDensity, incrementAcceptingDensity, initialTransparentDensity, finalTransparentDensity, incrementTransparentDensity, initialReplacementDensity, finalreplacementDensity, incrementReplacementDensity, numberOfTests, currentConfiguration);

		
		initialized=true;
		this.initialReplacementPugTransitionDensity = initialReplacementPugTransitionDensity;
		this.finalreplacementPlugTransitionDensity = finalreplacementPlugTransitionDensity;
		this.incrementReplacementPlugTransitionDensity = incrementReplacementPlugTransitionDensity;
		this.totalCondigurations= this.getNumberOfPossibleConfigurations();
	}



	@Override
	public Ex3Configuration next() {

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

		int plugReplacementDensity = (this.configurationNumber % (this.numberOfTests
				* this.claims.size()
				* this.numberStatesConfigurations()
				* this.transitionDensity()
				* this.numberAcceptingStatesConfigurations()
				* this.transparentStatesConfigurations()
				* this.replacementStatesConfigurations() * this
					.replacementStatesPlugConfigurations()))
				/ (this.numberOfTests * this.claims.size()
						* this.numberStatesConfigurations()
						* this.transitionDensity()
						* this.numberAcceptingStatesConfigurations()
						* this.transparentStatesConfigurations() * replacementStatesConfigurations())
				* this.incrementReplacementPlugTransitionDensity
				+ this.initialReplacementPugTransitionDensity;

		Ex3Configuration testConfiguration = new Ex3Configuration(
				configurationNumber, currentNumberOfStates,
				roundDouble(transitionDensity), roundDouble(acceptingDensity),
				roundDouble(transparentDensity),
				roundDouble(replacementDensity), 
				plugReplacementDensity, this.claims.get(currentClaim),
				testNumber, currentClaim);

		this.configurationNumber++;

		return testConfiguration;

	}

	private double roundDouble(double number) {
		return Math.abs((number * 100.0) / 100.0);
	}

	public int getNumberOfPossibleConfigurations() {

		if(!initialized){
			return super.getNumberOfPossibleConfigurations();
		}
		else{
			return this.numberOfTests * this.claims.size()
					* this.numberStatesConfigurations() * this.transitionDensity()
					* this.numberAcceptingStatesConfigurations()
					* this.transparentStatesConfigurations()
					* this.replacementStatesConfigurations()
					* this.replacementStatesPlugConfigurations();
		}
		

	}

	

	private int replacementStatesPlugConfigurations() {
		return (int) ((this.finalreplacementPlugTransitionDensity
				- this.initialReplacementPugTransitionDensity + this.incrementReplacementPlugTransitionDensity) / this.incrementReplacementPlugTransitionDensity);
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
