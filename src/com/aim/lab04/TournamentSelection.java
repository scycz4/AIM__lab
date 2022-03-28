package com.aim.lab04;

import java.util.Random;
import java.util.stream.IntStream;

import uk.ac.nott.cs.aim.domains.chesc2014_SAT.SAT;
import uk.ac.nott.cs.aim.helperfunctions.ArrayMethods;

/**
 * @author Warren G. Jackson
 */
public class TournamentSelection {
	
	private Random rng;
	private int POPULATION_SIZE;
	private SAT problem;
	
	public TournamentSelection(SAT problem, Random rng, int POPULATION_SIZE) {
		
		this.problem = problem;
		this.rng = rng;
		this.POPULATION_SIZE = POPULATION_SIZE;
	}

	/**
	  * @return The index of the chosen parent solution.
	  *
	  * PSEUDOCODE
	  *
	  * INPUT: parent_pop, tournament_size
	  * solutions = getUniqueRandomSolutions(tournament_size); 
	  * bestSolution = getBestSolution(solutions);
	  * index = indexOf(bestSolution);
	  * return index;
	  */
	public int tournamentSelection(int tournamentSize) {
		
		int bestIndex = -1;
		double bestFitness = Double.MAX_VALUE;
		
		//create list of random indices
		int[] indices = ArrayMethods.shuffle(IntStream.range(0, POPULATION_SIZE).toArray(), rng);

		//select tournamentSize elements
		for(int i = 0; i < tournamentSize; i++) {
			int sol = indices[i];
			double fitness = problem.getObjectiveFunctionValue(sol);
			
			if(fitness < bestFitness) {
				bestFitness = fitness;
				bestIndex = sol;
			}
		}
		
		return bestIndex;
	}
}
