package com.aim.lab05;

import java.util.Random;

import com.aim.lab05.dependencies.BitMutation;
import com.aim.lab05.dependencies.DBHC_IE;
import com.aim.lab05.dependencies.DBHC_OI;
import com.aim.lab05.dependencies.MemeplexInheritanceMethod;
import com.aim.lab05.dependencies.PTX1;
import com.aim.lab05.dependencies.SDHC_IE;
import com.aim.lab05.dependencies.SDHC_OI;
import com.aim.lab05.dependencies.TournamentSelection;
import com.aim.lab05.dependencies.TransGenerationalReplacementWithElitism;

import uk.ac.nott.cs.aim.domains.chesc2014_SAT.Meme;
import uk.ac.nott.cs.aim.domains.chesc2014_SAT.SAT;
import uk.ac.nott.cs.aim.satheuristics.genetics.CrossoverHeuristic;
import uk.ac.nott.cs.aim.satheuristics.genetics.PopulationHeuristic;
import uk.ac.nott.cs.aim.satheuristics.genetics.PopulationReplacement;
import uk.ac.nott.cs.aim.searchmethods.PopulationBasedSearchMethod;

public class MultiMeme extends PopulationBasedSearchMethod {

	/**
	 * The innovation rate setting
	 */
	private final double innovationRate;
	
	private final CrossoverHeuristic crossover;
	private final BitMutation mutation;
	private final PopulationReplacement replacement;
	private final TournamentSelection selection;
	
	private final MemeplexInheritanceMethod inheritance;
	
	/**
	 * The possible local search operators to use.
	 */
	private final PopulationHeuristic[] lss; 
	
	// Constructor used for testing. Please do not remove!
	public MultiMeme(SAT problem, Random rng, int populationSize, double innovationRate, CrossoverHeuristic crossover, 
			BitMutation mutation, PopulationReplacement replacement, TournamentSelection selection, MemeplexInheritanceMethod inheritance,
			PopulationHeuristic[] lss) {
		
		super(problem, rng, populationSize);
		
		this.innovationRate = innovationRate;
		this.crossover = crossover;
		this.mutation = mutation;
		this.replacement = replacement;
		this.selection = selection;
		this.inheritance = inheritance;
		this.lss = lss;
	}
	
	public MultiMeme(SAT problem, Random rng, int populationSize, double innovationRate) {
		
		this(
			problem,
			rng,
			populationSize,
			innovationRate,
			new PTX1(problem, rng), // crossover
			new BitMutation(problem, rng), // mutation 
			new TransGenerationalReplacementWithElitism(), // replacement 
			new TournamentSelection(populationSize, rng, problem), // parent selection
			new SimpleInheritanceMethod(problem, rng), // memeplex inheritance
			new PopulationHeuristic[] { // create mapping for local search operators used for meme in meme index 1
				new DBHC_OI(problem, rng), // [0]
				new DBHC_IE(problem, rng), // [1]
				new SDHC_OI(problem, rng), // [2]
				new SDHC_IE(problem, rng)  // [3]
			}
		);
	}

	/**
	 * MMA PSEUDOCODE:
	 * 
	 * INPUT: PopulationSize, MaxGenerations, InnovationRate
	 * 
	 * generateInitialPopulation();
	 * FOR 0 -> MaxGenerations
	 * 
	 *     FOR 0 -> PopulationSize / 2
	 *         select parents using tournament selection with tournament size = 3
	 *         apply crossover to generate offspring
	 ####### BEGIN IMPLEMENTING HERE #######
	 *         inherit memeplex using simple inheritance method
	 *         mutate the memes within each memeplex of each child with  probability dependent on the innovation rate
	 *         apply mutation to offspring with intensity of mutation set for each solution dependent on its meme option
	 *         apply local search to offspring with choice of operator dependent on each solution�s meme option
	 *     ENDFOR
	 *     do population replacement
	 ####### STOP IMPLEMENTING HERE #######
	 * ENDFOR
	 * return s_best;
	 */
	public void runMainLoop() {
		
		// set tournament size equal to 3
		final int tSize = 3;

		// TODO implementation of MMA
		for(int i = 0; i < POPULATION_SIZE; i+=2) {
			
			// select two parents. we don't care if they are the same this week
			int p1 = selection.tournamentSelection(tSize);
			int p2 = selection.tournamentSelection(tSize);

			// calculate child indices
			int c1 = i + POPULATION_SIZE;
			int c2 = c1 + 1;
			
			// apply crossover
			crossover.applyHeuristic(p1, p2, c1, c2);

			// TODO implementation of multimeme part
			inheritance.performMemeticInheritance(p1,p2,c1,c2);

			performMutationOfMemeplex(c1);
			performMutationOfMemeplex(c2);


			applyMutationForChildDependentOnMeme(c1,0);
			applyMutationForChildDependentOnMeme(c2,0);


			applyLocalSearchForChildDependentOnMeme(c1,1);
			applyLocalSearchForChildDependentOnMeme(c2,1);

		}
		
		// perform replacement
		replacement.doReplacement(problem, POPULATION_SIZE);
	}
	
	/**
	 * Applies mutation to the child dependent on its current meme option for mutation.
	 * Mapping of meme option to IOM: IntensityOfMutation <- memeOption;
	 * 
	 * @param childIndex The solution memory index of the child to mutate.
	 * @param memeIndex The meme index used for storing the meme relating to the intensity of mutation setting.
	 */
	public void applyMutationForChildDependentOnMeme(int childIndex, int memeIndex) {
		
		// TODO implementation of mutation embedding intensity of mutation from memes
		mutation.setMutationRate(problem.getMeme(childIndex,memeIndex).getMemeOption());
		mutation.applyHeuristic(childIndex);
	}
	
	/**
	 * Applies the local search operator to the child as specified by its current meme option.
	 * 
	 * @param childIndex The solution memory index of the child to mutate.
	 * @param memeIndex The meme index used for storing the meme relating to the local search operator setting.
	 */
	public void applyLocalSearchForChildDependentOnMeme(int childIndex, int memeIndex) {
		
		// TODO implementation of local search dependent on memes
		lss[problem.getMeme(childIndex,memeIndex).getMemeOption()].applyHeuristic(childIndex);
	}
	
	/**
	 * Applies mutation to each meme within the memeplex of the specified solution with probability
	 * dependent on the innovation rate.
	 * 
	 * HINT: mutation does not mean bit flip; it only means in this case 
	 * 		that you should MODIFY the current value of the meme option
	 * 		subject to the above definition.
	 * 
	 * @param solutionIndex The solution memory index of the solution to mutate the memeplex of.
	 */
	public void performMutationOfMemeplex(int solutionIndex) {
		
		// TODO implementation of mutation of memeplex
		if(rng.nextDouble()<innovationRate){
			int option1;
			int option2;
			do{
				option1=rng.nextInt(problem.getMeme(solutionIndex,0).getTotalOptions());
			}
			while(option1==problem.getMeme(solutionIndex,0).getMemeOption());
			do{
				option2=rng.nextInt(problem.getMeme(solutionIndex,1).getTotalOptions());
			}
			while(option2==problem.getMeme(solutionIndex,1).getMemeOption());
			problem.getMeme(solutionIndex,0).setMemeOption(option1);
			problem.getMeme(solutionIndex,1).setMemeOption(option2);
		}
	}
	
	public String toString() {
		
		return "Multimeme Memetic Algorithm";
	}
	
}
