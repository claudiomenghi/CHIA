package it.polimi.constraints.io.out.replacement;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.io.XMLTrasformer;
import it.polimi.automata.io.out.IBAToElementTrasformer;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.io.ConstraintsIOConstants;
import it.polimi.constraints.transitions.PluggingTransition;

import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.base.Preconditions;

public class ReplacementToElementTransformer extends XMLTrasformer<Replacement, Element> {

	
	public ReplacementToElementTransformer()
			throws ParserConfigurationException {
		super();
	}

	@Override
	public Element transform(Replacement input) throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder;
		docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		
		// root elements
		Element constraintElement = doc
				.createElement(ConstraintsIOConstants.XML_ELEMENT_REPLACEMENT);

		// adding the id
		Attr modelBlackBoxStateIDd = doc
				.createAttribute(ConstraintsIOConstants.XML_ATTRIBUTE_MODEL_STATE_ID);
		modelBlackBoxStateIDd.setValue(Integer.toString(input
				.getModelState().getId()));
		constraintElement.setAttributeNode(modelBlackBoxStateIDd);

		// adding the name of the state
		Attr modelBlackBoxStateName = doc
				.createAttribute(AutomataIOConstants.XML_ATTRIBUTE_NAME);
		modelBlackBoxStateName.setValue(input.getModelState().getName());
		constraintElement.setAttributeNode(modelBlackBoxStateName);

		Element baElement = new IBAToElementTrasformer(doc).transform(input
				.getAutomaton());
		constraintElement.appendChild(baElement);

		// adding the incoming Ports
		Element inComingPorts = doc
				.createElement(AutomataIOConstants.XML_ELEMENT_TRANSITIONS_IN);
		constraintElement.appendChild(inComingPorts);
		this.addPluggingTransition(inComingPorts, input.getIncomingTransitions(), doc);

		// adding the outComing Ports
		Element outComingPorts = doc
				.createElement(AutomataIOConstants.XML_ELEMENT_TRANSITIONS_OUT);
		constraintElement.appendChild(outComingPorts);
		this.addPluggingTransition(outComingPorts, input.getOutgoingTransitions(), doc);

		return constraintElement;
	}

	/**
	 * adds the set of the port to the specified portElement
	 * 
	 * @param portsElement
	 *            is the element where the ports must be added
	 * @param ports
	 *            the set of ports to be added
	 * @throws NullPointerException
	 *             if one of the argument is null
	 */
	private void addPluggingTransition(Element portsElement, Set<PluggingTransition> ports, Document doc) {
		Preconditions.checkNotNull(portsElement, "The element where the ports must be added cannot be null");
		Preconditions.checkNotNull(ports, "The set of the ports to be added cannot be null");
		// create a new port transformed
		PluggingTransitionToElementTransformer transformer = new PluggingTransitionToElementTransformer(
				doc);
		// transforms each port into the corresponding port element 
		for (PluggingTransition port : ports) {
			Element portElement = transformer.transform(port);
			portsElement.appendChild(portElement);
		}
	}
}
