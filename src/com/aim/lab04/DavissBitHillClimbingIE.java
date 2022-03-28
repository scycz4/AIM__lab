package com.aim.lab04;

import java.util.Random;
import java.util.stream.IntStream;

import uk.ac.nott.cs.aim.domains.chesc2014_SAT.SAT;
import uk.ac.nott.cs.aim.helperfunctions.ArrayMethods;
import uk.ac.nott.cs.aim.satheuristics.genetics.PopulationHeuristic;

public class DavissBitHillClimbingIE extends PopulationHeuristic {

	public DavissBitHillClimbingIE(SAT oProblem, Random oRandom) {
		
		super(oProblem, oRandom);
	}

	@Override
	public void applyHeuristic(int iMemoryIndex) {
		
		double dBestEval = problem.getObjectiveFunctionValue(iMemoryIndex);
		int[] aiIndicies = IntStream.range(0, problem.getNumberOfVariables()).toArray();
		int[] aiPerm = ArrayMethods.shuffle(aiIndicies, random);
		
		for(int iIndexCounter = 0; iIndexCounter < problem.getNumberOfVariables(); iIndexCounter++) {

			problem.bitFlip(aiPerm[iIndexCounter], iMemoryIndex);
			double dTempEval =  problem.getObjectiveFunctionValue(iMemoryIndex);
			
			if (dTempEval < dBestEval) {
				
				dBestEval = dTempEval;
				
			} else {
				
				problem.bitFlip(aiPerm[iIndexCounter], iMemoryIndex);
			}
		}
	}
}
