package com.aim.lab05.dependencies;

import java.util.Random;

import uk.ac.nott.cs.aim.domains.chesc2014_SAT.SAT;

public class DBHC_OI extends DavissBitHillClimbing {
	
	public DBHC_OI(SAT oProblem, Random oRandom) {
		
		super(oProblem, oRandom);
	}

	public boolean acceptMove(double dCurrentSolutionFitness, double dCandidateSolutionFitness) {
		
		return dCandidateSolutionFitness < dCurrentSolutionFitness;
	}
}
