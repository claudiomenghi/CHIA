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
	
	private final List<BA> claims;


	/**
	 * @param args
	 */
	protected static void chekArgs(String[] args) {
		Preconditions.checkArgument(args.length==2, "You must specify the configuration file and the folder that contains the claims of interest");
		Preconditions.checkNotNull(args[0], "You must specify the configuration file, which specifies the parameters of the random generation procedure as first parameter");
		Preconditions.checkNotNull(args[1], "You must specify the folder which contains the claims of interest");
	}
	
	/**
	 * creates a new experiment
	 * 
	 * @param confParser
	 *            the parser to load configurations
	 * @param out
	 *            the stream to be used to write results
	 * @throws Exception 
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public Experiment(ConfParser confParser, PrintStream out, String claimFolder) throws Exception {
		Preconditions.checkNotNull(confParser, "The configuration parser");
		Preconditions.checkNotNull(out, "The output stream cannot be null");

		this.confParser = confParser;
		this.out = out;
		out.println("Test directory: " + confParser.getTestDirectory());
		claims=new ClaimLoader().getClaimToBeConsidered(claimFolder);
	}

	/**
	 * runs the tests
	 * 
	 * @throws Exception
	 */
	public void performTests() throws Exception {
		out.println("--------------------------- STARTING THE TEST: ------------------------");

		new ExperimentFolderStructureCreator().createFolderStructure(this.getClaims(),
				this.confParser);

		Stopwatch timer = Stopwatch.createUnstarted();
		test(timer);
	}
	
	
	protected abstract void test(Stopwatch timer) throws Exception;

	public List<BA> getClaims() {
		return claims;
	}
}
