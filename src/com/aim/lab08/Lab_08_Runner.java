package com.aim.lab08;


import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import BinPacking.BinPacking;
import SAT.SAT;
import travelingSalesmanProblem.TSP;


public class Lab_08_Runner {
	
	final Lab08TestFrameConfig config;
	
	final int TOTAL_RUNS;
	final String[] DOMAINS;
	final int[][] INSTANCE_IDs;
	final long RUN_TIME;
	final long[] SEEDS;
	
	public Lab_08_Runner(Lab08TestFrameConfig config) {
	
		this.config = config;
		
		this.TOTAL_RUNS = config.getTotalRuns();
		this.DOMAINS = config.PROBLEM_DOMAINS;
		this.INSTANCE_IDs = config.getInstanceIDs();
		this.SEEDS = config.getSeeds();
		this.RUN_TIME = config.getRunTime();
	}

	public void runTests() {
		
		String hyperHeuristicName = null;
		String problemDomain = null;
		double[] bestSolutionFitness_s = new double[TOTAL_RUNS];
		
		for(int domain = 0; domain < DOMAINS.length; domain++) {

			for(int instance = 0; instance < INSTANCE_IDs[domain].length; instance++) {
				
				int instanceID = INSTANCE_IDs[domain][instance];
				
				for(int run = 0; run < TOTAL_RUNS; run++) {
					
					long seed = SEEDS[run];
					
					HyperHeuristic hh = new SCF_AM_HH(seed);
					ProblemDomain problem = getNewDomain(DOMAINS[domain], seed);
					problem.loadInstance(instanceID);
					hh.setTimeLimit(RUN_TIME);	
					hh.loadProblemDomain(problem);
					hh.run();
					
					hyperHeuristicName = hh.toString();
					problemDomain = problem.getClass().getSimpleName();
					bestSolutionFitness_s[run] = hh.getBestSolutionValue();
					System.out.println("Instance ID: " + instanceID + "\tTrial: " + run + "\tf(s_{best}) = " + hh.getBestSolutionValue());
				}
				
				//print or save results
				StringBuilder sb = new StringBuilder();
				sb.append(hyperHeuristicName + "," + RUN_TIME + "," + problemDomain + "," + instanceID);
				for(double ofv : bestSolutionFitness_s) {
					sb.append("," + ofv);
				}
				
				config.saveData(hyperHeuristicName + ".csv", sb.toString());
			}
		}
	}
	
	public ProblemDomain getNewDomain(String domain, long seed) {
		
		ProblemDomain domainObject;
		
		switch(domain.toUpperCase()) {
		case "SAT":
			domainObject = new SAT(seed);
			break;
		case "BP":
			domainObject = new BinPacking(seed);
			break;
		case "TSP":
			domainObject = new TSP(seed);
			break;
		default:
			domainObject = null;
		}
		
		return domainObject;
	}
	
	public static void main(String [] args) {
		
		new Lab_08_Runner(new Lab08TestFrameConfig()).runTests();
	}
}
