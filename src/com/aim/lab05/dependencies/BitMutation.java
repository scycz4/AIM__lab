package com.aim.lab05.dependencies;

import java.util.Random;

import uk.ac.nott.cs.aim.domains.chesc2014_SAT.SAT;
import uk.ac.nott.cs.aim.satheuristics.genetics.PopulationHeuristic;

/**
 * 
 * @author Warren
 *
 */
public class BitMutation extends PopulationHeuristic {
	
	/**
	 * 
	 */
	private double m_mutationRate;
	
	/**
	 * 
	 */
	private final int m_variables;
	
	/**
	 * 
	 */
	private static final int ONE_FLIP_PER_N_VARIABLES = 1;

	/**
	 * 
	 * @param oProblem
	 * @param oRandom
	 */
	public BitMutation(SAT oProblem, Random oRandom) {
		super(oProblem, oRandom);

		this.m_variables = oProblem.getNumberOfVariables();
		setMutationRate(ONE_FLIP_PER_N_VARIABLES);
	}

	/**
	 * 
	 * @param iIntensityOfMutation
	 */
	public void setMutationRate(int iIntensityOfMutation) {
		
		this.m_mutationRate = (iIntensityOfMutation / this.m_variables);
	}

	@Override
	public void applyHeuristic(int iSolutionMemoryIndex) {
		
		for (int i = 0; i < this.problem.getNumberOfVariables(); i++) {
			
			if (this.random.nextDouble() < this.m_mutationRate) {
				
				this.problem.bitFlip(i, iSolutionMemoryIndex);
			}
		}
	}
}
