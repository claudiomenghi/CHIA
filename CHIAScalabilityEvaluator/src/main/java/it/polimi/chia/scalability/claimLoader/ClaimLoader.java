package it.polimi.chia.scalability.claimLoader;



import it.polimi.automata.BA;
import it.polimi.automata.io.in.ClaimReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * Loads the claims contained in the folder specified as parameter
 * 
 * @author Claudio Menghi
 *
 */
public class ClaimLoader {


	/**
	 * loads the claims from the XML files contained in the specified folder
	 * @param pathFolder the path of the folder that contains the claim
	 * @return a list of BA which contains the claims
	 * 
	 * @throws Exception
	 */
	public List<BA> getClaimToBeConsidered(String pathFolder) throws Exception {

		File folder = new File(pathFolder);
		if(!folder.exists()){
			throw new IllegalArgumentException("The folder "+pathFolder+" does not exists");
		}
		List<File> listOfFiles = Arrays.asList(folder.listFiles());
		
		List<BA> claims = new ArrayList<>();
		for(File f: listOfFiles){
			claims.add(new ClaimReader(f).perform());
			
		}
		return claims;
	}

}
