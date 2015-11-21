package it.polimi.constraints.io.in.constraint;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.BA;
import it.polimi.automata.io.Transformer;
import it.polimi.automata.io.in.ElementToBATransformer;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.constraints.components.SubProperty;
import it.polimi.constraints.io.ConstraintsIOConstants;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.google.common.base.Preconditions;

/**
 * Transforms an XML element passed as input into the corresponding sub-property
 * 
 * @author Claudio Menghi
 *
 */
public class ElementToSubPropertyTransformer implements
		Transformer<Element, SubProperty> {

	/**
	 * Creates a new ElementToSubPropertyTransformer. The transformer given an
	 * XML element generates the corresponding sub-property
	 */
	public ElementToSubPropertyTransformer() {

	}

	/**
	 * Given an XML element returns the corresponding sub-property
	 * 
	 * @param input
	 *            the XML input element to be transformed
	 * @throws NullPointerException
	 *             if the element in input is null
	 */
	@Override
	public SubProperty transform(Element input) {

		Preconditions.checkNotNull(input,
				"The subproperty to be converted cannot be null");
		int componentId = Integer
				.parseInt(input
						.getAttribute(ConstraintsIOConstants.XML_ATTRIBUTE_MODEL_STATE_ID));

		boolean indispensable = Boolean
				.parseBoolean(input
						.getAttribute(ConstraintsIOConstants.XML_ATTRIBUTE_SUBPROPERTY_INDISPESNABLE));

		String stateName = input
				.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_NAME);

		ElementToBATransformer elementToBATransformer = new ElementToBATransformer();

		NodeList list = input
				.getElementsByTagName(AutomataIOConstants.XML_ELEMENT_BA);
		if (list.getLength() == 0) {
			throw new InternalError("No BA element inside the component");
		}
		BA ba = elementToBATransformer.transform((Element) (input
				.getElementsByTagName(AutomataIOConstants.XML_ELEMENT_BA)
				.item(0)));

		State modelState = new StateFactory().create(stateName, componentId);
		SubProperty subproperty = new SubProperty(modelState, ba);
		subproperty.setIndispensable(indispensable);
		return subproperty;
	}

}
