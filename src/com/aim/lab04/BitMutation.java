package com.aim.lab04;

import java.util.Random;

import uk.ac.nott.cs.aim.domains.chesc2014_SAT.SAT;
import uk.ac.nott.cs.aim.satheuristics.genetics.PopulationHeuristic;

public class BitMutation extends PopulationHeuristic {

	private final double MUTATION_RATE;
	
	public BitMutation(SAT problem, Random random) {
		
		super(problem, random);
		this.MUTATION_RATE = 1.0d / problem.getNumberOfVariables();
	}

	/**
	 * ==========
	 * PSEUDOCODE
	 * ==========
	 * 
	 * INPUT: s // solutionIndex
	 * j = 0
	 * REPEAT chromosome_length TIMES
	 *     IF random < 1.0 / chromosome_length THEN // NOTE: this is the MUTATION_RATE above!
	 *         bitFlip(s, j)
	 *     ENDIF
	 *     j++
	 */
	public void applyHeuristic(int solutionIndex) {
		for (int j = 0; j < problem.getNumberOfVariables(); j++) {
			if (random.nextDouble() < MUTATION_RATE) {
				problem.bitFlip(j, solutionIndex);
			}
		}
	}
}
