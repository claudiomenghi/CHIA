package it.polimi.automata.io.out.states;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.io.Transformer;
import it.polimi.automata.state.State;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.base.Preconditions;

/**
 * Transforms a State into the corresponding XML element
 * 
 * @author Claudio Menghi
 *
 */
public class StateToElementTrasformer implements Transformer<State, Element> {

	/**
	 * is the document where the XML element must be added
	 */
	protected final Document doc;

	/**
	 * creates a new state transformer
	 * 
	 * @param doc
	 *            is the document that must be modified by the transformer
	 * @throws NullPointerException
	 *             if the document is null
	 */
	public StateToElementTrasformer(Document doc) {
		Preconditions.checkNotNull(doc, "The document cannot be null");

		this.doc = doc;
	}

	/**
	 * transforms the state into the corresponding element
	 * 
	 * @param input
	 *            is the state to be transformed
	 * @throws NullPointerException
	 *             if the state to be transformed is null
	 */
	@Override
	public Element transform(State input) {
		Preconditions.checkNotNull(input, "The input state cannot be null");

		Element stateXMLElement = doc
				.createElement(AutomataIOConstants.XML_ELEMENT_STATE);

		// adding the id
		Attr id = doc.createAttribute(AutomataIOConstants.XML_ATTRIBUTE_ID);
		id.setValue(Integer.toString(input.getId()));
		stateXMLElement.setAttributeNode(id);

		// adding the name
		Attr name = doc.createAttribute(AutomataIOConstants.XML_ATTRIBUTE_NAME);
		name.setValue(input.getName());
		stateXMLElement.setAttributeNode(name);

		return stateXMLElement;
	}
}
