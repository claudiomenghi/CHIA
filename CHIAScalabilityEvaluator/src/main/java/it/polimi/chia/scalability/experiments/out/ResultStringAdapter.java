package it.polimi.chia.scalability.experiments.out;

import it.polimi.checker.SatisfactionValue;

/**
 * 
 * @author Claudio Menghi
 *
 */
public class ResultStringAdapter {

	public String adapt(SatisfactionValue satisfactionValue){
		if(satisfactionValue.equals(SatisfactionValue.SATISFIED)){
			return "Y";
		}
		if(satisfactionValue.equals(SatisfactionValue.NOTSATISFIED)){
			return "N";
		}
		return "M";
	}
}
