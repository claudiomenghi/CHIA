package it.polimi.chia.scalability.tasks;

import it.polimi.chia.scalability.configuration.Configuration;

import com.google.common.base.Preconditions;

/**
 * Contains the description of a task
 * @author Claudio Menghi
 *
 */
public abstract class Task {

	private long taskTime = -1;
	private int taskSpace = -1;

	private final Configuration configuration;

	/**
	 * creates a new Task
	 * 
	 * @param configuration
	 *            the configuration used to create the task
	 * @throws NullPointerException
	 *             if the configuration is null
	 */
	public Task(Configuration configuration) {
		Preconditions.checkNotNull(configuration,
				"The configuration cannot be null");
		this.configuration = configuration;
	}

	/**
	 * @return the taskTime
	 */
	public long getTaskTime() {
		if (taskTime == -1) {
			throw new IllegalStateException(
					"You must set the value of the time before getting it");
		}
		return taskTime;
	}

	/**
	 * @param taskTime
	 *            the taskTime to set
	 */
	public void setTaskTime(long taskTime) {

		this.taskTime = taskTime;
	}

	/**
	 * @return the taskSpace
	 */
	public int getTaskSpace() {
		if (taskSpace == -1) {
			throw new IllegalStateException(
					"You must set the value of the space before getting it");
		}
		return taskSpace;
	}

	/**
	 * @param taskSpace
	 *            the taskSpace to set
	 */
	public void setTaskSpace(int taskSpace) {

		this.taskSpace = taskSpace;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

}
