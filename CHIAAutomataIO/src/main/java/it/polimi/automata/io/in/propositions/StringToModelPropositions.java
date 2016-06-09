package it.polimi.automata.io.in.propositions;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.io.Transformer;

import java.util.HashSet;
import java.util.Set;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

import com.google.common.base.Preconditions;

/**
 * Transforms the input string into the corresponding transition label, i.e., it
 * computes the set of propositions that must label the transition of the model
 * 
 * @author Claudio Menghi
 *
 */
public class StringToModelPropositions implements
		Transformer<String, Set<IGraphProposition>> {

	/**
	 * Starting from the string computes the corresponding proposition. The
	 * string must satisfy the regular expression
	 * {@link AutomataIOConstants#APREGEX} or the regular expression
	 * {@link AutomataIOConstants#NOTAPREGEX}
	 * 
	 * @param input
	 *            is the input to be computed
	 * @throws NullPointerException
	 *             if the input is null
	 * @throws IllegalArgumentException
	 *             if the input does not match the regular expression
	 *             {@link AutomataIOConstants#APREGEX} or the regular expression
	 *             {@link AutomataIOConstants#NOTAPREGEX}
	 */
	@Override
	public Set<IGraphProposition> transform(String input) {

		Preconditions.checkNotNull(input, "The label of the transition must be not null");
		Preconditions.checkArgument(
				input.matches(AutomataIOConstants.MODEL_PROPOSITIONS),
				"The label of the transition ``" + input + " must match the regular expression: "
						+ AutomataIOConstants.MODEL_PROPOSITIONS+" of the label of the modle of hte transitions");

		Set<IGraphProposition> propositions = new HashSet<>();

		String[] apsStrings = input.split(AutomataIOConstants.AND);

		for (String ap : apsStrings) {
			propositions.add(new GraphProposition(ap, false));
		}

		return propositions;
	}
}
