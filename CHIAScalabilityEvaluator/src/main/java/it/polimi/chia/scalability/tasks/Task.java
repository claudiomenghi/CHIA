package it.polimi.chia.scalability.tasks;

public abstract class Task {

	private long taskTime=-1;
	private int taskSpace=-1;
	
	/**
	 * @return the taskTime
	 */
	public long getTaskTime() {
		return taskTime;
	}
	/**
	 * @param taskTime the taskTime to set
	 */
	public void setTaskTime(long taskTime) {
		if(taskTime==-1){
			throw new IllegalStateException("You must set the value of the time before getting it");
		}
		this.taskTime = taskTime;
	}
	/**
	 * @return the taskSpace
	 */
	public int getTaskSpace() {
		if(taskSpace==-1){
			throw new IllegalStateException("You must set the value of the space before getting it");
		}
		return taskSpace;
	}
	/**
	 * @param taskSpace the taskSpace to set
	 */
	public void setTaskSpace(int taskSpace) {
		this.taskSpace = taskSpace;
	}
	
}
