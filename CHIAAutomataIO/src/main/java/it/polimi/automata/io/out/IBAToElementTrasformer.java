package it.polimi.automata.io.out;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.IBA;
import it.polimi.automata.io.XMLTrasformer;
import it.polimi.automata.io.out.propositions.PropositionToElementTrasformer;
import it.polimi.automata.io.out.states.IBAStateToElementTransformer;
import it.polimi.automata.io.out.transitions.BATransitionToElementTransformer;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.SigmaProposition;

public class IBAToElementTrasformer extends XMLTrasformer<IBA, Element> {

	public IBAToElementTrasformer(Document doc) throws ParserConfigurationException {
		super(doc);
	}
	
	public IBAToElementTrasformer() throws ParserConfigurationException {
		super();
	}
	
	@Override
	public Element transform(IBA input) throws ParserConfigurationException {

		Document doc=this.getDocument();
		Element baElement =
				doc.createElement(AutomataIOConstants.XML_ELEMENT_IBA);
		
		Element propositions=doc.createElement(AutomataIOConstants.XML_ELEMENT_PROPOSITIONS);
		baElement.appendChild(propositions);
		this.computingStatePropositions(doc, propositions, input);
		Element states=doc.createElement(AutomataIOConstants.XML_ELEMENT_STATES);
		baElement.appendChild(states);
		this.computingStateElements(doc, states, input);
		Element transitions=doc.createElement(AutomataIOConstants.XML_ELEMENT_TRANSITIONS);
		baElement.appendChild(transitions);
		this.computingTransitionElements(doc, transitions, input);
		return baElement;
		
	}
	private void computingStatePropositions(Document doc, Element rootElement,
			IBA input) {
		
		PropositionToElementTrasformer propositionTrasformer=new PropositionToElementTrasformer(doc);
		for(IGraphProposition p: input.getPropositions()){
			if(!(p instanceof SigmaProposition)){
				rootElement.appendChild(propositionTrasformer.transform(p));
			}
		}
	}
	private void computingStateElements(Document doc, Element rootElement,
			IBA input) {

		IBAStateToElementTransformer stateTransformer = new IBAStateToElementTransformer(
				input, doc);
		for (State s : input.getStates()) {
			Element xmlStateElement = stateTransformer.transform(s);
			rootElement.appendChild(xmlStateElement);
		}
	}

	private void computingTransitionElements(Document doc, Element rootElement,
			IBA input) {
		BATransitionToElementTransformer transitionTransformer = new BATransitionToElementTransformer(
				input, doc);
		for (Transition transition : input.getTransitions()) {
			Element transitionElement = transitionTransformer
					.transform(transition);
			rootElement.appendChild(transitionElement);
		}
	}

}
