package it.polimi.checker;

/**
 * Contains the report of the model checking parameters
 * 
 * @author Claudio Menghi 
 */
public class ModelCheckingInfo {

	/**
	 * contains the result of the verification
	 */
	private SatisfactionValue result;

	/**
	 * the violationTime the time to check whether the claim is not satisfied in
	 * the model. The time to perform the steps 2 and 3 of the paper.
	 */
	private long violationTime;
	/**
	 * the possibleViolationTime the time to check whether the claim is possibly
	 * satisfied in the model. The time to perform the steps 4 and 5 of the
	 * paper.
	 */
	private long possibleViolationTime;
	
	/**
	 * contains the time that is necessary to compute the constraints
	 */
	private long subPropertyComputationTime;

	private long simplificationTime;

	// specification
	/**
	 * contains the number of the states of the specification
	 */
	private int numStatesSpecification;
	/**
	 * contains the number of accepting states of the specification
	 */
	private int numAcceptStatesSpecification;
	/**
	 * contains the number of the transitions of the specification
	 */
	private int numTransitionSpecification;

	private long totalTime;

	// model
	/**
	 * contains the number of the states of the model
	 */
	private int numStatesModel;
	/**
	 * contains the number of accepting states of the model
	 */
	private int numAcceptStatesModel;
	/**
	 * contains the number of the transitions of the model
	 */
	private int numTransitionModel;
	/**
	 * contains the number of the black box states of the model
	 */
	private int numBlackBoxStatesModel;

	// Intersection
	/**
	 * contains the number of the states of the intersection
	 */
	private int numStatesIntersection;
	/**
	 * contains the number of the accepting states of the intersection
	 */
	private int numAcceptingStatesIntersection;
	/**
	 * contains the number of the initial states of the intersection
	 */
	private int numInitialStatesIntersection;
	/**
	 * @return the portReachabilityTime
	 */
	public long getPortReachabilityTime() {
		return portReachabilityTime;
	}

	/**
	 * @param portReachabilityTime the portReachabilityTime to set
	 */
	public void setPortReachabilityTime(long portReachabilityTime) {
		this.portReachabilityTime = portReachabilityTime;
	}

	/**
	 * contains the number of the mixed states of the intersection
	 */
	private int numMixedStatesIntersection;
	
	private long portReachabilityTime;
	
	private final boolean checking;
	private final boolean subproperties;
	private final boolean portReachability;

	public ModelCheckingInfo(boolean checking, boolean subproperties, boolean portReachability) {
		this.checking=checking;
		this.subproperties=subproperties;
		this.portReachability=portReachability;
		
		this.setResult(SatisfactionValue.NOTSATISFIED);
		this.setViolationTime(0);
		this.setPossibleViolationTime(0);
		this.setPortReachabilityTime(0);
		this.setSubPropertyComputationTime(0);
		this.setNumStatesSpecification(0);
		this.setNumAcceptStatesSpecification(0);
		this.setNumTransitionSpecification(0);
		this.setNumStatesModel(0);
		this.setNumAcceptStatesModel(0);
		this.setNumTransitionModel(0);
		this.setNumBlackBoxStatesModel(0);
		this.setNumStatesIntersection(0);
		this.setNumAcceptingStatesIntersection(0);
		this.setNumInitialStatesIntersection(0);
		this.setNumMixedStatesIntersection(0);
		this.setTotalTime(0);
		this.setSimplificationTime(0);
	}

	public void reset() {
		this.setResult(SatisfactionValue.NOTSATISFIED);
		this.setViolationTime(0);
		this.setPossibleViolationTime(0);
		this.setSubPropertyComputationTime(0);
		this.setNumStatesSpecification(0);
		this.setNumAcceptStatesSpecification(0);
		this.setNumTransitionSpecification(0);
		this.setPortReachabilityTime(0);
		this.setNumStatesModel(0);
		this.setNumAcceptStatesModel(0);
		this.setNumTransitionModel(0);
		this.setNumBlackBoxStatesModel(0);
		this.setNumStatesIntersection(0);
		this.setNumAcceptingStatesIntersection(0);
		this.setNumInitialStatesIntersection(0);
		this.setNumMixedStatesIntersection(0);
		this.setSimplificationTime(0);
	}

	/**
	 * gets the violationTime. The violationTime is the time necessary to check
	 * whether the claim is not satisfied in the model
	 * 
	 * @return the violationTime the time to check whether the claim is not
	 *         satisfied in the model
	 */
	public long getViolationTime() {
		return violationTime;
	}

	/**
	 * sets the violationTime. The violationTime is the time necessary to check
	 * whether the claim is not satisfied in the model
	 * 
	 * @param violationTime
	 *            the time to check whether the claim is not satisfied in the
	 *            model
	 */
	public void setViolationTime(long violationTime) {
		this.violationTime = violationTime;
	}

	/**
	 * return the possibleViolationTime the time to check whether the claim is
	 * possibly satisfied in the model. The time to perform the steps 4 and 5 of
	 * the paper.
	 * 
	 * @return the possibleViolationTime the time to check whether the claim is
	 *         possibly satisfied in the model. The time to perform the steps 4
	 *         and 5 of the paper.
	 */
	public long getPossibleViolationTime() {
		return possibleViolationTime;
	}

	/**
	 * sets the possibleViolationTime the time to check whether the claim is
	 * possibly satisfied in the model. The time to perform the steps 4 and 5 of
	 * the paper.
	 * 
	 * @param possibleViolationTime
	 *            the time to check whether the claim is possibly satisfied in
	 *            the model. The time to perform the steps 4 and 5 of the paper.
	 */
	public void setPossibleViolationTime(long possibleViolationTime) {
		this.possibleViolationTime = possibleViolationTime;
	}

	/**
	 * @return the result
	 */
	public SatisfactionValue getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(SatisfactionValue result) {
		this.result = result;
	}

	/**
	 * @return the constraintComputationTime
	 */
	public long getSubpropertyTime() {
		return subPropertyComputationTime;
	}

	/**
	 * @param subPropertyComputationTime
	 *            the constraintComputationTime to set
	 */
	public void setSubPropertyComputationTime(long subPropertyComputationTime) {
		this.subPropertyComputationTime = subPropertyComputationTime;
	}

	/**
	 * @return the numStatesSpecification
	 */
	public int getNumStatesSpecification() {
		return numStatesSpecification;
	}

	/**
	 * @param numStatesSpecification
	 *            the numStatesSpecification to set
	 */
	public void setNumStatesSpecification(int numStatesSpecification) {
		this.numStatesSpecification = numStatesSpecification;
	}

	/**
	 * @return the numAcceptStatesSpecification
	 */
	public int getNumAcceptStatesSpecification() {
		return numAcceptStatesSpecification;
	}

	/**
	 * @param numAcceptStatesSpecification
	 *            the numAcceptStatesSpecification to set
	 */
	public void setNumAcceptStatesSpecification(int numAcceptStatesSpecification) {
		this.numAcceptStatesSpecification = numAcceptStatesSpecification;
	}

	/**
	 * @return the numTransitionSpecification
	 */
	public int getNumTransitionSpecification() {
		return numTransitionSpecification;
	}

	/**
	 * @param numTransitionSpecification
	 *            the numTransitionSpecification to set
	 */
	public void setNumTransitionSpecification(int numTransitionSpecification) {
		this.numTransitionSpecification = numTransitionSpecification;
	}

	/**
	 * @return the numStatesModel
	 */
	public int getNumStatesModel() {
		return numStatesModel;
	}

	/**
	 * @param numStatesModel
	 *            the numStatesModel to set
	 */
	public void setNumStatesModel(int numStatesModel) {
		this.numStatesModel = numStatesModel;
	}

	/**
	 * @return the numAcceptStatesModel
	 */
	public int getNumAcceptStatesModel() {
		return numAcceptStatesModel;
	}

	/**
	 * @param numAcceptStatesModel
	 *            the numAcceptStatesModel to set
	 */
	public void setNumAcceptStatesModel(int numAcceptStatesModel) {
		this.numAcceptStatesModel = numAcceptStatesModel;
	}

	/**
	 * @return the numTransitionModel
	 */
	public int getNumTransitionModel() {
		return numTransitionModel;
	}

	/**
	 * @param numTransitionModel
	 *            the numTransitionModel to set
	 */
	public void setNumTransitionModel(int numTransitionModel) {
		this.numTransitionModel = numTransitionModel;
	}

	/**
	 * @return the numBlackBoxStatesModel
	 */
	public int getNumBlackBoxStatesModel() {
		return numBlackBoxStatesModel;
	}

	/**
	 * @param numBlackBoxStatesModel
	 *            the numBlackBoxStatesModel to set
	 */
	public void setNumBlackBoxStatesModel(int numBlackBoxStatesModel) {
		this.numBlackBoxStatesModel = numBlackBoxStatesModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toStringHeader() {
		return "1)numStatesMode, 2)result, 3)intersectionTime, 4)emptyTime, 5)constraintComputationTime, 6)numStatesSpecification, "
				+ "7)numAcceptStatesSpecification, "
				+ "8)numTransitionSpecification, 9)numAcceptStatesModel, 10)numTransitionModel, 11)numBlackBoxStatesModel, "
				+ "12)setNumStatesIntersection, 13)setNumAcceptingStatesIntersection, 14)setNumInitialStatesIntersection, 15)setNumMixedStatesIntersection, 16)BlackBoxStatesModel, 17)totalTime";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return numStatesModel + ", " + result + ", " + violationTime + ", "
				+ possibleViolationTime + ", " + subPropertyComputationTime
				+ ", " + numStatesSpecification + ", "
				+ numAcceptStatesSpecification + ", "
				+ numTransitionSpecification + ", " + numAcceptStatesModel
				+ ", " + numTransitionModel + ", " + numBlackBoxStatesModel
				+ ", " + numStatesIntersection + ", "
				+ numAcceptingStatesIntersection + ", "
				+ numInitialStatesIntersection + ", "
				+ numMixedStatesIntersection + ", " + numBlackBoxStatesModel
				+ ", " + this.getTotalVerificationTime();
	}

	/**
	 * @return the numStatesIntersection
	 */
	public int getNumStatesIntersection() {
		return numStatesIntersection;
	}

	/**
	 * @param numStatesIntersection
	 *            the numStatesIntersection to set
	 */
	public void setNumStatesIntersection(int numStatesIntersection) {
		this.numStatesIntersection = numStatesIntersection;
	}

	/**
	 * @return the numAcceptingStatesIntersection
	 */
	public int getNumAcceptingStatesIntersection() {
		return numAcceptingStatesIntersection;
	}

	/**
	 * @param numAcceptingStatesIntersection
	 *            the numAcceptingStatesIntersection to set
	 */
	public void setNumAcceptingStatesIntersection(
			int numAcceptingStatesIntersection) {
		this.numAcceptingStatesIntersection = numAcceptingStatesIntersection;
	}

	/**
	 * @return the numInitialStatesIntersection
	 */
	public int getNumInitialStatesIntersection() {
		return numInitialStatesIntersection;
	}

	/**
	 * @param numInitialStatesIntersection
	 *            the numInitialStatesIntersection to set
	 */
	public void setNumInitialStatesIntersection(int numInitialStatesIntersection) {
		this.numInitialStatesIntersection = numInitialStatesIntersection;
	}

	/**
	 * @return the numMixedStatesIntersection
	 */
	public int getNumMixedStatesIntersection() {
		return numMixedStatesIntersection;
	}

	/**
	 * @param numMixedStatesIntersection
	 *            the numMixedStatesIntersection to set
	 */
	public void setNumMixedStatesIntersection(int numMixedStatesIntersection) {
		this.numMixedStatesIntersection = numMixedStatesIntersection;
	}

	/**
	 * @return the totalTime
	 */
	public long getTotalVerificationTime() {
		return totalTime;
	}

	/**
	 * @param totalTime
	 *            the totalTime to set
	 */
	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}

	/**
	 * @return the simplificationTime
	 */
	public long getSimplificationTime() {
		return simplificationTime;
	}

	/**
	 * @param simplificationTime
	 *            the simplificationTime to set
	 */
	public void setSimplificationTime(long simplificationTime) {
		this.simplificationTime = simplificationTime;
	}

	/**
	 * @return the checking
	 */
	public boolean isChecking() {
		return checking;
	}

	/**
	 * @return the subproperties
	 */
	public boolean isSubproperties() {
		return subproperties;
	}

	/**
	 * @return the portReachability
	 */
	public boolean isPortReachability() {
		return portReachability;
	}
}
