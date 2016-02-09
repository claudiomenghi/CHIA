package it.polimi.constraints.components;

import org.checkerframework.checker.igj.qual.Immutable;
import org.checkerframework.checker.nullness.qual.NonNull;

import it.polimi.automata.state.State;

import com.google.common.base.Preconditions;

/**
 * The Component class is an abstract class which is used to describe an element
 * (a sub-property or a replacement) which refers to a state of the model. The
 * modelState and the id attributes stores the state of the model associated
 * with the component and its id, respectively. The inComingPorts and the
 * outComingPorts attributes specify how the component is plugged into the
 * model, using ports.
 */
public abstract class Component {

	/**
	 * contains the counter which is used to associate to a new component
	 * (sub-property or replacement) a new id
	 */
	private static int counter = 0;

	/**
	 * contains the id of the component
	 */
	private final int id;

	/**
	 * is the state of the original model to which this component is associated
	 */
	private @Immutable final State modelState;

	/**
	 * creates a new component which refers to a specific model state has a set
	 * of incomingPorts and out-comingPorts
	 * 
	 * @param modelState
	 *            is the state of the model to which the component refers to
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public Component(@NonNull State modelState) {
		Preconditions.checkNotNull(modelState,
				"The name of the state cannot be null");

		this.id = counter;
		counter++;
		this.modelState = modelState;
	}

	/**
	 * returns the id of the component
	 * 
	 * @return the id of the component
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * returns the state of the model to which the component refers to
	 * 
	 * @return the state of the model to which the component refers to
	 */
	public State getModelState() {
		return modelState;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getId() + ": " + this.getModelState().getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + modelState.hashCode();
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Component other = (Component) obj;
		if (!modelState.equals(other.modelState))
			return false;
		if (id != other.id)
			return false;
		return true;
	}
}
