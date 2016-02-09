package it.polimi.chia.scalability.configuration;

import it.polimi.automata.BA;

public class Ex3Configuration extends Configuration {
	
	private final int numberOfreplacementPlugTransitions;
	
	public Ex3Configuration(int configurationId, int nStates,
			double transitionDensity, double acceptingDensity,
			double transparentDensity, double replacementDensity,
			int replacementPlugDensity,
			BA currentClaim, int testNumber, int claimNumber) {
		super(configurationId, nStates, transitionDensity, acceptingDensity,
				transparentDensity, replacementDensity, currentClaim, testNumber,
				claimNumber);
		this.numberOfreplacementPlugTransitions=replacementPlugDensity;
		
	}

	/**
	 * @return the replacementPlugDensity
	 */
	public int getReplacementPlugDensity() {
		return numberOfreplacementPlugTransitions;
	}
	
	@Override
	public String toString() {
		return configurationId + "\t" + testNumber + "\t" + claimNumber + "\t"
				+ nStates + "\t" + +transitionDensity + "\t" + acceptingDensity
				+ "\t" + transparentDensity + "\t" + replacementDensity + "\t"
				+ propositions + "\t" +numberOfreplacementPlugTransitions+"\t";
	}
}
