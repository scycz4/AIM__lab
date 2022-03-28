package com.aim.lab04;

import java.util.Random;

import uk.ac.nott.cs.aim.domains.chesc2014_SAT.SAT;
import uk.ac.nott.cs.aim.satheuristics.genetics.CrossoverHeuristic;


public class UniformXO extends CrossoverHeuristic {

	public UniformXO(SAT problem, Random random) {
		
		super(problem, random);
	}

	/**
	 * ==========
	 * PSEUDOCODE
	 * ==========
	 * 
	 * INPUTS: p1, p2, c1, c2 // indices of the parents and where the children should be stored
	 * memory[c1] = copyOf(p1)
	 * memory[c2] = copyOf(p2)
	 * j = 0
	 * REPEAT chromosome_length TIMES
	 *     IF random < 0.5 THEN
	 *         exchange(c1, c2, j)
	 *     ENDIF
	 *     j++
	 *     
	 * ----------------------------------------------------------------------
	 * NOTES:
	 *     Here, memory relates to the solution memory within the SAT Object, and 
	 *     copyOf(i) relates to creating a copy of the solution i.
	 */
	public void applyHeuristic(int parent1Index, int parent2Index,
			int child1Index, int child2Index) {
		problem.copySolution(parent1Index, child1Index);
		problem.copySolution(parent2Index, child2Index);
		for (int j = 0; j < problem.getNumberOfVariables(); j++) {
			if (random.nextDouble() < 0.5) {
				problem.exchangeBits(child1Index, child2Index, j);
			}
		}

	}
}
