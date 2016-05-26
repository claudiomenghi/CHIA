package it.polimi.chia.scalability.claimLoader;

import it.polimi.automata.BA;
import it.polimi.automata.io.in.ClaimReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClaimLoader {


	public List<BA> getClaimToBeConsidered() throws Exception {

		List<BA> claims = new ArrayList<>();
		claims.add(new ClaimReader(new File(ClassLoader.getSystemResource("Claim1.xml").toURI())).perform());
		claims.add(new ClaimReader(new File(ClassLoader.getSystemResource("Claim2.xml").toURI())).perform());
		claims.add(new ClaimReader(new File(ClassLoader.getSystemResource("Claim3.xml").toURI())).perform());
		return claims;
	}

}
