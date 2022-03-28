package com.aim.lab00;

import java.util.Random;

import uk.ac.nott.cs.aim.domains.chesc2014_SAT.SAT;
import uk.ac.nott.cs.aim.satheuristics.SATHeuristic;


/**
 * A heuristic to flip a random bit.
 * @author Warren G. Jackson
 */
public class RandomBitFlipHeuristic extends SATHeuristic {

	public RandomBitFlipHeuristic(Random random) {
		
		super(random);
	}

	@Override
	public void applyHeuristic(SAT problem) {
		
		// select a random bit in the solution
		int bitIndex = random.nextInt(problem.getNumberOfVariables());
		
		// flip the bit
		problem.bitFlip(bitIndex);
	}

	@Override
	public String getHeuristicName() {
		
		return "Random Bit Flip";
	}
}
