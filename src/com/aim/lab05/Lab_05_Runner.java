package com.aim.lab05;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Random;

import com.aim.TestFrame;

import uk.ac.nott.cs.aim.domains.chesc2014_SAT.SAT;

public class Lab_05_Runner extends TestFrame {

	private static final int HEURISTIC_TESTS = 1;
	
	public Lab_05_Runner(Lab05TestFrameConfig config) {
		super(config);
	}
	
	public void runTests() {
		
		Lab05TestFrameConfig config = (Lab05TestFrameConfig)getTestConfiguration();
		long[] SEEDS = getExperimentalSeeds();
		
		for(int heuristicTest = 0; heuristicTest < HEURISTIC_TESTS; heuristicTest++) {
			
			ArrayList<Double> runScores = new ArrayList<Double>();
			
			runUsingExperimentalParallelism(rangeAsStream(0, config.getTotalRuns()-1)).forEach( run -> {
				
				//generation based termination
				Random random = new Random(SEEDS[run]);
				SAT sat = new SAT(config.getInstanceId(), config.getRunTime(), random, config.getPopulationSize(), config.getMemeCount(), config.getOptionsPerMeme());
				
				ArrayList<ArrayList<Long>> memeUsage = new ArrayList<ArrayList<Long>>();
				
				//initialise allele counters to 0
				for(int i = 0; i < config.getMemeCount(); i++) {
					
					memeUsage.add(i, new ArrayList<Long>());
					for(int j = 0; j < config.getOptionsPerMeme()[i]; j++) {
						
						memeUsage.get(i).add(j, 0L);
					}
				}
				
				LinkedList<ArrayList<Double>> fitnessTrace = new LinkedList<ArrayList<Double>>();
				for(int i = 0; i < config.getPopulationSize(); i++) {
					fitnessTrace.add(new ArrayList<Double>());
				}
				
				MultiMeme mma = new MultiMeme(sat, random, config.getPopulationSize(), config.INNOVATION_RATE);
				
				int count = 0;
				while(!sat.hasTimeExpired() && count <= config.MAX_GENERATIONS) {
					
					mma.run();
					
					//add all of population
					PriorityQueue<Double> pq = new PriorityQueue<>();
					for(int i = 0; i < config.getPopulationSize(); i++) {
						pq.add(sat.getObjectiveFunctionValue(i));
					}
					
					for(int i = 0; i < config.getPopulationSize(); i++) {
						fitnessTrace.get(i).add(pq.remove());
					}
					
					for(int i = 0; i < config.getMemeCount(); i++) {

						for(int j = 0; j < config.getPopulationSize(); j++) {
							int allele = sat.getMeme(j, i).getMemeOption();
							long c = memeUsage.get(i).get(allele);
							memeUsage.get(i).set(allele, c + 1);
						}
					}
					
					count++;
				}
				
				double currentBestSolution = sat.getBestSolutionValue();
				runScores.add(currentBestSolution);
				
				synchronized (config) {
					
					System.out.println("Heuristic: " + mma.toString());
					System.out.println("Run ID: " + run);
					System.out.println("Best Solution Value: " + sat.getBestSolutionValue());
					System.out.println("Best Solution: " + sat.getBestSolutionAsString());
					
					for(int i = 0; i < config.getMemeCount(); i++) {
						
						System.out.println("MEME " + i + ":");
						for(int j = 0; j < config.getOptionsPerMeme()[i]; j++) {
							
							System.out.println("Allele " + j + " = " + memeUsage.get(i).get(j));
						}
					}
					
					System.out.println();
				}
				
			});
		}
	}
	
	public static void main(String [] args) {
		
		Lab05TestFrameConfig config = Lab05TestFrameConfig.getInstance();
		TestFrame runner = new Lab_05_Runner(config);
		runner.runTests();
	}
}
