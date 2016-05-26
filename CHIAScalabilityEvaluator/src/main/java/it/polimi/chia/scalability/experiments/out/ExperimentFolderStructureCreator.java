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

	
	public void createFolderStructure(List<BA> claims, ConfParser confParser) throws IOException{
		for (int testNumber = 1; testNumber <= confParser.getNumberOfTests(); testNumber++) {

			File dir = new File(confParser.getTestDirectory() + "/Test"
					+ testNumber);
			dir.mkdir();
			for (int claimNum = 0; claimNum < claims.size(); claimNum++) {
				File claimdir = new File(confParser.getTestDirectory()
						+ "/Test" + testNumber + "/Claim" + claimNum);
				claimdir.mkdir();
				File file = new File(confParser.getTestDirectory() + "/Test"
						+ testNumber + "/Claim" + claimNum + "/"
						+ confParser.getResultsFile());
				file.createNewFile();

			}
		}
	}
}
