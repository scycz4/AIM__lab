package com.aim.lab02;

import java.util.Random;

import uk.ac.nott.cs.aim.domains.chesc2014_SAT.SAT;
import uk.ac.nott.cs.aim.satheuristics.SATHeuristic;

public class RandomMutation extends SATHeuristic {
	
	public RandomMutation(Random oRandom) {
		
		super(oRandom);
	}

	/**
	 * PSEUDO-CODE
	 * i <- random \in [0, N-1];
	 * s' <- flip(i, s);
	 * 
	 * @param oProblem The problem to be solved.
	 */
	@Override
	public void applyHeuristic(SAT oProblem) {
		int i = random.nextInt(oProblem.getNumberOfVariables());
		oProblem.bitFlip(i);
	}

	@Override
	public String getHeuristicName() {

		return "Single-perturbative Random Mutation";
	}

}
