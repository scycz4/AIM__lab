package com.aim.lab04;

import java.util.Random;

import uk.ac.nott.cs.aim.domains.chesc2014_SAT.SAT;
import uk.ac.nott.cs.aim.satheuristics.genetics.CrossoverHeuristic;
import uk.ac.nott.cs.aim.satheuristics.genetics.PopulationHeuristic;
import uk.ac.nott.cs.aim.satheuristics.genetics.PopulationReplacement;
import uk.ac.nott.cs.aim.searchmethods.PopulationBasedSearchMethod;

/**
 * Memetic Algorithm ( local search should to be added per the report exercise ).
 * 
 * @author Warren G. Jackson
 *
 */
public class MemeticAlgorithm extends PopulationBasedSearchMethod {

	private final CrossoverHeuristic crossover;
	private final PopulationHeuristic mutation;
	private final PopulationHeuristic localSearch;
	private final PopulationReplacement replacement;
	private final TournamentSelection selection;
	
	public MemeticAlgorithm(SAT problem, Random rng, int populationSize, CrossoverHeuristic crossover, 
			PopulationHeuristic mutation, PopulationHeuristic localSearch, PopulationReplacement replacement) {
		
		super(problem, rng, populationSize);
		
		this.crossover = crossover;
		this.mutation = mutation;
		this.localSearch = localSearch;
		this.replacement = replacement;
		this.selection = new TournamentSelection(problem, rng, populationSize);
	}

	/**
	  * Memetic Algorithm pseudocode
	  * Note there is no exact pseudocode since the purpose of this
	  * exercise is that you experiment with applying local search
	  * in different places of the MA.
	  *
	  * BASIC PSEUDO CODE (GA) not MA
	  * (population already initialised)
	  * 
	  * 1.	INPUT: PopulationSize, MaxGenerations
	  * 2.	generateInitialPopulation();
	  * 3.	REPEAT MaxGenerations TIMES
	  * ------------------------------------------------------------
	  * 4.	    REPEAT PopulationSize / 2 TIMES
	  * 5.	        select unique parents using tournament selection
	  * 6.	        apply crossover to generate offspring
	  * 7.	        apply mutation to offspring
	  * 8.	    do population replacement
	  * ------------------------------------------------------------
	  * 9.	return s_best;
	  * 
	  * NOTES:
	  * ----------------------------------------------------------------------
	  *     Here, the population size corresponds to the number of parent solutions. 
	  *     The solution memory is set by the framework as 2 x POPULATION_SIZE.
	  *
	  */
	public void runMainLoop() {

		// TODO implementation of a Memetic Algorithm	
		// note that the heuristics/operators are initialised as member variables at the top of this class!
		// Do NOT re-create your own.
		for (int i = 0; i < POPULATION_SIZE/2; i++) {
			int parent1 = selection.tournamentSelection(3);
			int parent2 = selection.tournamentSelection(3);
			// ensure parents are different
			while (parent2 == parent1) parent2 = selection.tournamentSelection(3);
			
			crossover.applyHeuristic(parent1, parent2, POPULATION_SIZE+i*2, POPULATION_SIZE+i*2+1);
			mutation.applyHeuristic(POPULATION_SIZE+i*2);
			mutation.applyHeuristic(POPULATION_SIZE+i*2+1);
			localSearch.applyHeuristic(POPULATION_SIZE+i*2);
			localSearch.applyHeuristic(POPULATION_SIZE+i*2+1);
		}
		replacement.doReplacement(problem, POPULATION_SIZE);

	}
	
	public int tournamentSelection(int tournamentSize) {
		
		return selection.tournamentSelection(tournamentSize);
	}
	
	@Override
	public String toString() {
		
		return "Memetic Algorithm";
	}
}
