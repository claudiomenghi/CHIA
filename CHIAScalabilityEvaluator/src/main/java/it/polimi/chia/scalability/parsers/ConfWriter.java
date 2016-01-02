package it.polimi.chia.scalability.parsers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;

import com.google.common.base.Preconditions;

import it.polimi.chia.scalability.configuration.RandomConfigurationGenerator;

/**
 * writes the current configuration over a file
 * @author Claudio Menghi
 *
 */
public class ConfWriter {

	/**
	 * is the random configuration generator
	 */
	private final RandomConfigurationGenerator randomGenerator;
	
	private final File file;
	
	private final ConfParser parser;
	
	public ConfWriter(ConfParser parser, RandomConfigurationGenerator randomGenerator, String filePath) throws URISyntaxException{
		Preconditions.checkNotNull(randomGenerator);
		Preconditions.checkNotNull(filePath, "The file path cannot be null");
		this.randomGenerator=randomGenerator;
		file=new File(filePath);
		
		this.parser=parser;
	}
	
	public void write() throws IOException{
		
		FileWriter fw=new FileWriter(file, false);
		fw.write("RESULTS: "+parser.getResultsFile()+"\n");
		fw.write("RESULTSPOSSIBLY: "+parser.getResultsFilePossibly()+"\n");
		fw.write("TESTDIRECTORY: "+parser.getTestDirectory()+"\n"+"\n");
		
		fw.write("N_TESTS: "+parser.getNumberOfTests()+"\n"+"\n");

		fw.write("# N STATES"+"\n");
		fw.write("INIT_NSTATES: "+parser.getInitialNumberOfStates()+"\n");
		fw.write("FINAL_NSTATES: "+parser.getFinalNumberOfStates()+"\n");
		fw.write("INCREMENT_NSTATES: "+parser.getIncrementNumberOfStates()+"\n"+"\n");

		fw.write("# TRANSITIONS DENSITIES"+"\n");
		fw.write("INIT_TRANSITION_DENSITY: "+parser.getInitialTransitionDensity()+"\n");
		fw.write("FINAL_TRANSITION_DENSITY: "+parser.getFinalTransitionDensity()+"\n");
		fw.write("INCREMENT_TRANSITION_DENSITY: "+parser.getIncrementTransitionDensity()+"\n"+"\n");

		fw.write("# ACCEPTING DENSITIES"+"\n");
		fw.write("INIT_TRANSITION_DENSITY: "+parser.getInitAcceptingDensity()+"\n");
		fw.write("FINAL_TRANSITION_DENSITY: "+parser.getFinalAcceptingDensity()+"\n");
		fw.write("INCREMENT_TRANSITION_DENSITY: "+parser.getIncrementAcceptingDensity()+"\n"+"\n");

		fw.write("# TRANSPARENT DENSITIES"+"\n");
		fw.write("INIT_TRANSPARENT_DENSITY: "+parser.getInitialTransparentDensity()+"\n");
		fw.write("FINAL_TRANSPARENT_DENSITY: "+parser.getFinalTransparentDensity()+"\n");
		fw.write("INCREMENT_TRANSPARENT_DENSITY: "+parser.getIncrementTransparentDensity()+"\n"+"\n");

		fw.write("# REPLACEMENT DENSITIES"+"\n");
		fw.write("INIT_REPLACEMENT_DENSITY: "+parser.getInitialReplacementDensity()+"\n");
		fw.write("FINAL_REPLACEMENT_DENSITY: "+parser.getFinalReplacementDensity()+"\n");
		fw.write("INCREMENT_REPLACEMENT_DENSITY: "+parser.getIncrementReplacementDensity()+"\n"+"\n");

		fw.write("# CURRENT CONFIGURATION"+"\n");
		fw.write("CURRENT_CONFIGURATION: "+this.randomGenerator.getConfigurationNumber()+"\n");
		fw.close();

		
	}
}
