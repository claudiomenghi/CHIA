package it.polimi.chia.scalability.results;

import it.polimi.checker.SatisfactionValue;
import it.polimi.chia.scalability.configuration.Configuration;

import com.google.common.base.Preconditions;

public class Record {

	private Configuration configuration;
	private SatisfactionValue initialSatisfactioValue;

	private SatisfactionValue finalSatisfactioValue;
	private int sizeOfTheRefinementVerification;
	private int sizeOfTheReplacementVerification;

	
	private SatisfactionValue replacementSatisfactionValue;
	private SatisfactionValue refinementSatisfactionValue;
	
	private boolean triviallySatisfied;
	private int numReplacementStates;
	private int numReplacementIncomingTransitions;
	private int numReplacementOutgoingTransitions;
	private int subPropertyStates;
	private int subPropertyGreenIncomingTransitions;
	private int subPropertyYellowIncomingTransitions;
	private int subPropertyNumIncomingTransitions;
	private int subPropertyRedOutgoingTransitions;
	private int subPropertyYellowOutgingTransition;
	private int subPropertyNumOutgoingTransition;
	private int sizeModel;
	private int numTransparentStatesModel;

	// time
	private long replacementVerificationTime;
	private long refinementVerificationTime;
	private long constraintComputationTime;

	public Record(Configuration configuration){
		this.configuration=configuration;
	}
	public Record(Configuration configuration,
			SatisfactionValue initialSatisfactioValue) {
		this(configuration, initialSatisfactioValue, null, false, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}

	public Record(Configuration configuration,
			SatisfactionValue initialSatisfactioValue,
			SatisfactionValue finalSatisfactioValue,
			boolean triviallySatisfied, int sizeOfTheRefinementVerification,
			int sizeOfTheReplacementVerification, int numReplacementStates,
			int numReplacementIncomingTransitions,
			int numReplacementOutgoingTransitions, int subPropertyStates,
			int subPropertyGreenIncomingTransitions,
			int subPropertyYellowIncomingTransitions,
			int subPropertyNumIncomingTransitions,
			int subPropertyRedOutgoingTransitions,
			int subPropertyYellowOutgingTransition,
			int subPropertyNumOutgoingTransition, int sizeModel,
			int numTransparentStatesModel, long refinementVerificationTime,
			long replacementVerificationTime, long constraintComputationTime) {
		Preconditions.checkNotNull(configuration,
				"The condiguration under analysis cannot be null");
		Preconditions.checkNotNull(initialSatisfactioValue,
				"The initial satisfaction value cannot be null");
		Preconditions
				.checkArgument(
						finalSatisfactioValue != null
								|| initialSatisfactioValue != SatisfactionValue.POSSIBLYSATISFIED,
						"The final satisfaction value cannot be null");
		Preconditions.checkArgument(sizeOfTheRefinementVerification >= 0,
				"The size of the refinement verification must be >=0");
		Preconditions.checkArgument(sizeOfTheReplacementVerification >= 0,
				"The size of the replacement verification must be >=0");
		Preconditions.checkArgument(refinementVerificationTime >= 0,
				"The verification of the refinement must take a time >=0");
		Preconditions.checkArgument(sizeOfTheReplacementVerification >= 0,
				"The verification of the replacement must take a time  >=0");
		this.configuration = configuration;
		this.initialSatisfactioValue = initialSatisfactioValue;
		this.finalSatisfactioValue = finalSatisfactioValue;
		this.sizeOfTheRefinementVerification = sizeOfTheRefinementVerification;
		this.sizeOfTheReplacementVerification = sizeOfTheReplacementVerification;
		this.triviallySatisfied = triviallySatisfied;
		this.numReplacementStates = numReplacementStates;
		this.numReplacementIncomingTransitions = numReplacementIncomingTransitions;
		this.numReplacementOutgoingTransitions = numReplacementOutgoingTransitions;
		this.subPropertyStates = subPropertyStates;

		this.replacementVerificationTime = replacementVerificationTime;
		this.refinementVerificationTime = refinementVerificationTime;
		this.constraintComputationTime = constraintComputationTime;

		if (!(subPropertyGreenIncomingTransitions
				+ subPropertyYellowIncomingTransitions <= subPropertyNumIncomingTransitions)) {
			throw new InternalError(
					"The sum of the green and yellow incoming transition cannot exceed the number of incoming transitions");
		}
		this.subPropertyGreenIncomingTransitions = subPropertyGreenIncomingTransitions;
		this.subPropertyYellowIncomingTransitions = subPropertyYellowIncomingTransitions;
		this.subPropertyNumIncomingTransitions = subPropertyNumIncomingTransitions;

		if (!(subPropertyRedOutgoingTransitions
				+ subPropertyYellowOutgingTransition <= subPropertyNumOutgoingTransition)) {
			throw new InternalError(
					"The sum of the red and yellow outgoing transition cannot exceed the number of outgoing transitions");
		}
		this.subPropertyRedOutgoingTransitions = subPropertyRedOutgoingTransitions;
		this.subPropertyYellowOutgingTransition = subPropertyYellowOutgingTransition;
		this.subPropertyNumOutgoingTransition = subPropertyNumOutgoingTransition;
		this.sizeModel = sizeModel;
		this.numTransparentStatesModel = numTransparentStatesModel;
	}

	/**
	 * @return the configuration
	 */
	public Configuration getConfiguration() {
		return configuration;
	}

	/**
	 * @return the initialSatisfactioValue
	 */
	public SatisfactionValue getInitialSatisfactioValue() {
		return initialSatisfactioValue;
	}

	/**
	 * @return the finalSatisfactioValue
	 */
	public SatisfactionValue getFinalSatisfactioValue() {
		return finalSatisfactioValue;
	}

	/**
	 * @return the replacementVerificationTime
	 */
	public long getReplacementVerificationTime() {
		return replacementVerificationTime;
	}

	public int getSizeOfTheRefinementVerification() {
		return sizeOfTheRefinementVerification;
	}

	public int getSizeOfTheReplacementVerification() {
		return sizeOfTheReplacementVerification;
	}

	public long getRefinementVerificationTime() {
		return refinementVerificationTime;
	}

	/**
	 * @return the triviallySatisfied
	 */
	public boolean isTriviallySatisfied() {
		return triviallySatisfied;
	}

	/**
	 * @return the numReplacementStates
	 */
	public int getReplacementAutomataSize() {
		return numReplacementStates;
	}

	/**
	 * @return the numReplacementIncomingTransitions
	 */
	public int getReplacementIncomingTransitions() {
		return numReplacementIncomingTransitions;
	}

	/**
	 * @return the subPropertyStates
	 */
	public int getSubPropertyAutomataSize() {
		return subPropertyStates;
	}

	/**
	 * @return the subPropertyYellowIncomingTransitions
	 */
	public int getSubPropertyYellowIncomingTransitions() {
		return subPropertyYellowIncomingTransitions;
	}

	/**
	 * @return the numReplacementOutgoingTransitions
	 */
	public int getReplacementOutgoingTransitions() {
		return numReplacementOutgoingTransitions;
	}

	/**
	 * @return the subPropertyGreenIncomingTransitions
	 */
	public int getSubPropertyGreenIncomingTransitions() {
		return subPropertyGreenIncomingTransitions;
	}

	/**
	 * @return the subPropertyNumIncomingTransitions
	 */
	public int getSubPropertyIncomingTransitions() {
		return subPropertyNumIncomingTransitions;
	}

	/**
	 * @return the subPropertyRedOutgoingTransitions
	 */
	public int getSubPropertyRedOutgoingTransitions() {
		return subPropertyRedOutgoingTransitions;
	}

	/**
	 * @return the subPropertyYellowOutgingTransition
	 */
	public int getSubPropertyYellowOutgingTransition() {
		return subPropertyYellowOutgingTransition;
	}

	/**
	 * @return the subPropertyNumOutgoingTransition
	 */
	public int getSubPropertyOutgoingTransitions() {
		return subPropertyNumOutgoingTransition;
	}

	/**
	 * @return the sizeModel
	 */
	public int getSizeModel() {
		return sizeModel;
	}

	/**
	 * @return the numTransparentStatesModel
	 */
	public int getNumTransparentStatesModel() {
		return numTransparentStatesModel;
	}

	/**
	 * @return the constraintComputationTime
	 */
	public long getConstraintComputationTime() {
		return constraintComputationTime;
	}
	
	/**
	 * @return the numReplacementStates
	 */
	public int getNumReplacementStates() {
		return numReplacementStates;
	}

	/**
	 * @param numReplacementStates the numReplacementStates to set
	 */
	public void setNumReplacementStates(int numReplacementStates) {
		this.numReplacementStates = numReplacementStates;
	}

	/**
	 * @return the numReplacementIncomingTransitions
	 */
	public int getNumReplacementIncomingTransitions() {
		return numReplacementIncomingTransitions;
	}

	/**
	 * @param numReplacementIncomingTransitions the numReplacementIncomingTransitions to set
	 */
	public void setNumReplacementIncomingTransitions(
			int numReplacementIncomingTransitions) {
		this.numReplacementIncomingTransitions = numReplacementIncomingTransitions;
	}

	/**
	 * @return the numReplacementOutgoingTransitions
	 */
	public int getNumReplacementOutgoingTransitions() {
		return numReplacementOutgoingTransitions;
	}

	/**
	 * @param numReplacementOutgoingTransitions the numReplacementOutgoingTransitions to set
	 */
	public void setNumReplacementOutgoingTransitions(
			int numReplacementOutgoingTransitions) {
		this.numReplacementOutgoingTransitions = numReplacementOutgoingTransitions;
	}

	/**
	 * @return the subPropertyStates
	 */
	public int getSubPropertyStates() {
		return subPropertyStates;
	}

	/**
	 * @param subPropertyStates the subPropertyStates to set
	 */
	public void setSubPropertyStates(int subPropertyStates) {
		this.subPropertyStates = subPropertyStates;
	}

	/**
	 * @return the subPropertyNumIncomingTransitions
	 */
	public int getSubPropertyNumIncomingTransitions() {
		return subPropertyNumIncomingTransitions;
	}

	/**
	 * @param subPropertyNumIncomingTransitions the subPropertyNumIncomingTransitions to set
	 */
	public void setSubPropertyNumIncomingTransitions(
			int subPropertyNumIncomingTransitions) {
		this.subPropertyNumIncomingTransitions = subPropertyNumIncomingTransitions;
	}

	/**
	 * @return the subPropertyNumOutgoingTransition
	 */
	public int getSubPropertyNumOutgoingTransition() {
		return subPropertyNumOutgoingTransition;
	}

	/**
	 * @param subPropertyNumOutgoingTransition the subPropertyNumOutgoingTransition to set
	 */
	public void setSubPropertyNumOutgoingTransition(
			int subPropertyNumOutgoingTransition) {
		this.subPropertyNumOutgoingTransition = subPropertyNumOutgoingTransition;
	}

	/**
	 * @param configuration the configuration to set
	 */
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	/**
	 * @param initialSatisfactioValue the initialSatisfactioValue to set
	 */
	public void setInitialSatisfactioValue(SatisfactionValue initialSatisfactioValue) {
		this.initialSatisfactioValue = initialSatisfactioValue;
	}

	/**
	 * @param finalSatisfactioValue the finalSatisfactioValue to set
	 */
	public void setFinalSatisfactioValue(SatisfactionValue finalSatisfactioValue) {
		this.finalSatisfactioValue = finalSatisfactioValue;
	}

	/**
	 * @param sizeOfTheRefinementVerification the sizeOfTheRefinementVerification to set
	 */
	public void setSizeOfTheRefinementVerification(
			int sizeOfTheRefinementVerification) {
		this.sizeOfTheRefinementVerification = sizeOfTheRefinementVerification;
	}

	/**
	 * @param sizeOfTheReplacementVerification the sizeOfTheReplacementVerification to set
	 */
	public void setSizeOfTheReplacementVerification(
			int sizeOfTheReplacementVerification) {
		this.sizeOfTheReplacementVerification = sizeOfTheReplacementVerification;
	}

	/**
	 * @param triviallySatisfied the triviallySatisfied to set
	 */
	public void setTriviallySatisfied(boolean triviallySatisfied) {
		this.triviallySatisfied = triviallySatisfied;
	}

	/**
	 * @param subPropertyGreenIncomingTransitions the subPropertyGreenIncomingTransitions to set
	 */
	public void setSubPropertyGreenIncomingTransitions(
			int subPropertyGreenIncomingTransitions) {
		this.subPropertyGreenIncomingTransitions = subPropertyGreenIncomingTransitions;
	}

	/**
	 * @param subPropertyYellowIncomingTransitions the subPropertyYellowIncomingTransitions to set
	 */
	public void setSubPropertyYellowIncomingTransitions(
			int subPropertyYellowIncomingTransitions) {
		this.subPropertyYellowIncomingTransitions = subPropertyYellowIncomingTransitions;
	}

	/**
	 * @param subPropertyRedOutgoingTransitions the subPropertyRedOutgoingTransitions to set
	 */
	public void setSubPropertyRedOutgoingTransitions(
			int subPropertyRedOutgoingTransitions) {
		this.subPropertyRedOutgoingTransitions = subPropertyRedOutgoingTransitions;
	}

	/**
	 * @param subPropertyYellowOutgingTransition the subPropertyYellowOutgingTransition to set
	 */
	public void setSubPropertyYellowOutgingTransition(
			int subPropertyYellowOutgingTransition) {
		this.subPropertyYellowOutgingTransition = subPropertyYellowOutgingTransition;
	}

	/**
	 * @param sizeModel the sizeModel to set
	 */
	public void setSizeModel(int sizeModel) {
		this.sizeModel = sizeModel;
	}

	/**
	 * @param numTransparentStatesModel the numTransparentStatesModel to set
	 */
	public void setNumTransparentStatesModel(int numTransparentStatesModel) {
		this.numTransparentStatesModel = numTransparentStatesModel;
	}

	/**
	 * @param replacementVerificationTime the replacementVerificationTime to set
	 */
	public void setReplacementVerificationTime(long replacementVerificationTime) {
		this.replacementVerificationTime = replacementVerificationTime;
	}

	/**
	 * @param refinementVerificationTime the refinementVerificationTime to set
	 */
	public void setRefinementVerificationTime(long refinementVerificationTime) {
		this.refinementVerificationTime = refinementVerificationTime;
	}

	/**
	 * @param constraintComputationTime the constraintComputationTime to set
	 */
	public void setConstraintComputationTime(long constraintComputationTime) {
		this.constraintComputationTime = constraintComputationTime;
	}
	/**
	 * @return the replacementSatisfactionValue
	 */
	public SatisfactionValue getReplacementSatisfactionValue() {
		return replacementSatisfactionValue;
	}
	/**
	 * @param replacementSatisfactionValue the replacementSatisfactionValue to set
	 */
	public void setReplacementSatisfactionValue(
			SatisfactionValue replacementSatisfactionValue) {
		this.replacementSatisfactionValue = replacementSatisfactionValue;
	}
	/**
	 * @return the refinementSatisfactionValue
	 */
	public SatisfactionValue getRefinementSatisfactionValue() {
		return refinementSatisfactionValue;
	}
	/**
	 * @param refinementSatisfactionValue the refinementSatisfactionValue to set
	 */
	public void setRefinementSatisfactionValue(
			SatisfactionValue refinementSatisfactionValue) {
		this.refinementSatisfactionValue = refinementSatisfactionValue;
	}

}
