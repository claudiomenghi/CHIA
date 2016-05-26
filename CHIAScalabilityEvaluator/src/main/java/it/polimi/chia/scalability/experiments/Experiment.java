/**
 * 
 */
package it.polimi.chia.scalability.experiments;

import it.polimi.automata.BA;
import it.polimi.chia.scalability.claimLoader.ClaimLoader;
import it.polimi.chia.scalability.experiments.out.ExperimentFolderStructureCreator;
import it.polimi.chia.scalability.experiments.out.ResultStringAdapter;
import it.polimi.chia.scalability.parsers.ConfParser;

import java.io.PrintStream;
import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;

/**
 * @author Claudio Menghi
 *
 */
public abstract class Experiment {

	protected final ConfParser confParser;
	protected final PrintStream out;
	
	protected final ResultStringAdapter adapter=new ResultStringAdapter();

	/**
	 * creates a new experiment
	 * 
	 * @param confParser
	 *            the parser to load configurations
	 * @param out
	 *            the stream to be used to write results
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public Experiment(ConfParser confParser, PrintStream out) {
		Preconditions.checkNotNull(confParser, "The configuration parser");
		Preconditions.checkNotNull(out, "The output stream cannot be null");

		this.confParser = confParser;
		this.out = out;
		out.println("Test directory: " + confParser.getTestDirectory());
	}

	/**
	 * runs the tests
	 * 
	 * @throws Exception
	 */
	public void performTests() throws Exception {
		out.println("--------------------------- STARTING THE TEST: ------------------------");



		List<BA> claims = new ClaimLoader().getClaimToBeConsidered();
		new ExperimentFolderStructureCreator().createFolderStructure(claims,
				this.confParser);

		Stopwatch timer = Stopwatch.createUnstarted();
		test(timer);
	}
	
	
	protected abstract void test(Stopwatch timer) throws Exception;
}
