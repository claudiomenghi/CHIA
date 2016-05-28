/**
 * 
 */
package it.polimi.chia.scalability.experiments.out;

import it.polimi.automata.BA;
import it.polimi.chia.scalability.parsers.ConfParser;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * creates the folder structure to put the results of the experiments
 * 
 * @author Claudio Menghi
 *
 */
public class ExperimentFolderStructureCreator {


	/**
	 * creates a sub-folder structure for the test.
	 * <ul>
	 * <li>It creates a sub-folder for each test.</li>
	 * <li>inside each test folder it creates a sub-folder for each claim.</li>
	 * <li>inside the claim folder it creates a result file</li>
	 * </ul>
	 * 
	 * @param claims
	 *            the claims to be considered
	 * @param confParser
	 *            the parser used to load the test
	 * @throws IOException
	 *             an exception if an error occurs
	 */
	public void createFolderStructure(List<BA> claims, ConfParser confParser)
			throws IOException {
		for (int testNumber = 1; testNumber <= confParser.getNumberOfTests(); testNumber++) {

			File resultsDirectory = new File(confParser.getTestDirectory());
			resultsDirectory.mkdir();
			
			String testFolder=confParser.getTestDirectory() + "/Test"
					+ testNumber;
			File dir = new File(testFolder);
			dir.mkdir();
			for (int claimNum = 0; claimNum < claims.size(); claimNum++) {
				String claimFolder=testFolder+ "/Claim" + claimNum;
				
				File claimdir = new File(claimFolder);
				claimdir.mkdir();

				File file = new File(claimFolder+"/"
						+ confParser.getResultsFile());
				file.createNewFile();

			}
		}
	}
}
