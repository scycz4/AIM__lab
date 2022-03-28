package com.aim.lab01;

import java.util.Random;
import java.util.stream.IntStream;

import uk.ac.nott.cs.aim.domains.chesc2014_SAT.SAT;
import uk.ac.nott.cs.aim.helperfunctions.ArrayMethods;
import uk.ac.nott.cs.aim.satheuristics.SATHeuristic;


public class DavissBitHC extends SATHeuristic {

	public DavissBitHC(Random random) {
		
		super(random);
	}

	/**
	  * DAVIS's BIT HILL CLIMBING LECTURE SLIDE PSEUDO-CODE
	  *
	  * bestEval = evaluate(currentSolution);
	  * perm = createRandomPermutation();
	  * for(j = 0; j < length[currentSolution]; j++) {
	  * 
	  *     bitFlip(currentSolution, perm[j]); 		//flips j^th bit from permutation of solution producing s' from s
	  *     tmpEval = evaluate(currentSolution);
	  *
	  *     if(tmpEval < bestEval) { 				// if there is improvement (strict improvement)
	  *
	  *         bestEval = tmpEval; 				// accept the best flip
	  *         
	  *     } else { 								// if there is no improvement, reject the current bit flip
	  *
	  *          bitFlip(solution, perm[j]); 		//go back to s from s'
	  *     }
	  * }
	  *
	  * @param problem The problem to be solved.
	  */
	
	private int[] createRandomPermutation(SAT problem, int length) {
		int[] perm = new int[length];
		for (int i = 0; i < length; i++) {
			perm[i] = i;
		}
		perm=ArrayMethods.shuffle(perm, random);
		return perm;
	}
	
	public void applyHeuristic(SAT problem) {
		double bestEval = problem.getObjectiveFunctionValue(CURRENT_SOLUTION_INDEX);
		int len = problem.getNumberOfVariables();
		int[] perm = createRandomPermutation(problem, len);
		for (int j = 0; j < len; j++) {
			problem.bitFlip(perm[j]);
			double tmpEval = problem.getObjectiveFunctionValue(CURRENT_SOLUTION_INDEX);
//			if (tmpEval < bestEval) {
			if (tmpEval <= bestEval) {
				bestEval = tmpEval;
			} else {
				problem.bitFlip(perm[j]);
			}
		}
	}

	public String getHeuristicName() {

		return "Davis's Bit HC";
	}

}
















