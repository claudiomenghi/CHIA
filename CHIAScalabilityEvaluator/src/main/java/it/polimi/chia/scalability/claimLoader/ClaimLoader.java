package it.polimi.chia.scalability.claimLoader;

import it.polimi.automata.BA;
import it.polimi.automata.io.in.ClaimReader;
import it.polimi.model.ltltoba.LTLtoBATransformer;

import java.util.ArrayList;
import java.util.List;

public class ClaimLoader {

	private final static String claim0 = "<>(a->(<>b))";
	private final static String claim1 = "[](a-><>(a^(<>b)))";
	private final static String claim2 = "<>(a^X(a^Xa))^<>(b^X(b^Xb))";

	public List<BA> getClaimToBeConsidered() throws Exception {

		List<BA> claims = new ArrayList<BA>();

		claims.add(new LTLtoBATransformer(claim0).perform());
		claims.add(new LTLtoBATransformer(claim1).perform());
		claims.add(new LTLtoBATransformer(claim2).perform());

		return claims;

	}
	
	
}
