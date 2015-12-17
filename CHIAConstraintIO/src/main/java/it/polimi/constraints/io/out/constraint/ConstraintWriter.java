package it.polimi.constraints.io.out.constraint;

import it.polimi.action.CHIAAction;
import it.polimi.constraints.Constraint;

import java.io.File;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;

import com.google.common.base.Preconditions;

/**
 * 
 * @author claudiomenghi
 *
 */
public class ConstraintWriter extends CHIAAction<Void> {

	/**
	 * is the logger of the SubAutomataIdentifier class
	 */
	private static final Logger logger = Logger
			.getLogger(ConstraintWriter.class);

	
	
	/**
	 * contains the components to be written
	 */
	private Constraint constraint;
	
	/**
	 * contains the file where the intersection automaton must be written
	 */
	private File file;
	
	/**
	 * creates a new Writer which is in charge of writing the constraint to an XML file
	 * @param constraint is the constraint to be written
	 * @param filePath is the path where the constraint must be written
	 * @param model is the model to be considered
	 * @param intersectionBA
	 */
	public ConstraintWriter(Constraint constraint, String filePath){
		super("CONSTRAINT WRITING");
		Preconditions.checkNotNull(constraint,
				"The constraint cannot be null");
		Preconditions.checkNotNull(filePath,
				"The file where the constraint must be written cannot be null");

		this.constraint = constraint;
		this.file = new File(filePath);
	}

	/**
	 * creates a new RefinementWriter for the set of components
	 * 
	 * @param components
	 *            is the set of components to be written
	 * @param f
	 *            is the file to which the intersection automaton must be
	 *            written
	 * @throws NullPointerException
	 *             if the components or the file is null
	 * 
	 */
	public ConstraintWriter(Constraint constraint, File f) {
		super("CONSTRAINT WRITING");
		Preconditions.checkNotNull(constraint,
				"The intersection automaton cannot be null");
		Preconditions.checkNotNull(f,
				"The file where the automaton must be written cannot be null");

		this.constraint = constraint;
		this.file = f;
	}

	public Void perform() throws Exception {

		logger.debug("Writing the constraint automaton");
		try {

			Element constraintElement=new ConstraintToElementTransformer().transform(this.constraint);

			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(constraintElement);
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);
			logger.debug("Constraint written");

		} catch (ParserConfigurationException pce) {
			logger.error(pce.getMessage());
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			logger.error(tfe.getMessage());
			tfe.printStackTrace();
		}
		return null;
	}


}
