
package com.aim.lab04;

import java.util.stream.IntStream;

import uk.ac.nott.cs.aim.domains.chesc2014_SAT.SAT;
import uk.ac.nott.cs.aim.satheuristics.genetics.PopulationReplacement;

public class TransGenerationalReplacementWithElitistReplacement extends PopulationReplacement {

	/**
	 * Replaces the current population with the offspring and replaces the worst
	 * offspring with the best solution if the best is not contained in the offspring.
	 *
	 * @return The indices of the solutions to use in the next generation.
	 *
	 * PSEUDOCODE
	 *
	 * INPUT current_pop, offspring_pop
	 * fitnesses <- evaluate( current_pop U offspring_pop );
	 * best <- min( fitnesses );
	 * next_pop <- indicesOf( offspring_pop );
	 * IF best \notin offspring_pop THEN
	 *     next_pop.replace( worst, best );
	 * ENDIF
	 * OUTPUT: next_pop; // return the indices of the next population
	 */
	@Override
	protected int[] getNextGeneration(SAT oProblem, int iPopulationSize) {
		
		// total population size is size of parent population plus size of offspring population
		int iTotalPopulationSize = iPopulationSize << 1;
		
		// offspring indices are from 'populationSize' inclusive to 'populationSize * 2' exclusive.
		int[] aiOffpsringMemoryIndices = IntStream.range(iPopulationSize, iTotalPopulationSize).toArray();
		
		// elitism replacing worst offspring with best solution if not in offspring
		double[] adTotalPopulationCosts = new double[iTotalPopulationSize];
		
		double dBestSolutionCost = Double.MAX_VALUE;
		double dWorstOffspringCost = -Double.MAX_VALUE;
		int bestIndex = -1;
		int worstOffspringIndex = -1;
		
		// evaluate the objective function value (cost) of each solution from both parent and offspring populations
		for(int iMemoryIndex = 0; iMemoryIndex < iTotalPopulationSize; iMemoryIndex++) {
			
			double dSolutionCost = oProblem.getObjectiveFunctionValue(iMemoryIndex);
			adTotalPopulationCosts[iMemoryIndex] = dSolutionCost;
			
			// update index of best solution, favouring offspring solutions
			if( dSolutionCost <= dBestSolutionCost ) {
				dBestSolutionCost = dSolutionCost;
				bestIndex = iMemoryIndex;
			}
			
			// keep track of the worst solution in the offspring population
			if( iMemoryIndex >= iPopulationSize && dSolutionCost > dWorstOffspringCost) {
				
				worstOffspringIndex = iMemoryIndex;
				dWorstOffspringCost = dSolutionCost;
			}
		}
		
		// if best solution is in parent population, replace worst in offspring with best from parents
		if(bestIndex < iPopulationSize) { 
			
			aiOffpsringMemoryIndices[worstOffspringIndex - iPopulationSize] = bestIndex;
		} 
		
		// return array of memory locations for replacement
		return aiOffpsringMemoryIndices;
	}

}
