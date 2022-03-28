package com.aim.lab06;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import com.aim.hyflex.HyFlexUtilities;

public class SR_RRT_HH extends HyperHeuristic {
	
	public SR_RRT_HH(long seed) {
		
		super(seed);
	}

	/**
	  * TODO - Implement a simple random record-to-record travel selection hyper-heuristic
	  *
	  * PSEUDOCODE:
	  *
	  * INPUT: problem_instance
	  * hs <- HyFlexUtilities.getHeuristicSetOfTypes( problem, HeuristicType, HeuristicType... );
	  * s <- initialiseSolution();
	  *
	  * WHILE termination criterion is not met DO
	  *
	  *     h <- getNextHeuristicUsingSimpleRandom( hs );
	  *     s' <- h( s );
	  *     accept <- acceptMoveUsingRRT( f( s ), f( s' ), f( s_best ) );
	  *
	  *     IF accept THEN
	  *         s <- s'; 						// HINT :: You will have to create a copy of the solution here!
	  *     END_IF
	  *
	  *		### WARNING! HYFLEX ONLY UPDATES THE BEST SOLUTION VALUE AFTER hasTimeExpired() IS CALLED! ###
	  *
	  *     IF f( s' ) < f( s_{best} ) THEN		// HINT :: This is handled by HyFlex
	  *         s_{best} <- s';
	  *     END_IF
	  *
	  * END_WHILE
	  *
	  * return s_{best};						// HINT :: Also handled by HyFlex
	  */
	public void solve(ProblemDomain problem) {
		
		// TODO ...
		int iCurrentIndex=0;
		int iCandidateIndex=1;

		int[] hs=HyFlexUtilities.getHeuristicSetOfTypes(problem, ProblemDomain.HeuristicType.MUTATION,
				ProblemDomain.HeuristicType.LOCAL_SEARCH, ProblemDomain.HeuristicType.RUIN_RECREATE);

		problem.setMemorySize(2);
		problem.initialiseSolution(iCurrentIndex);

		while(!hasTimeExpired()){
			int heuristic=getNextHeuristicUsingSimpleRandom(hs);
			problem.applyHeuristic(heuristic,iCurrentIndex,iCandidateIndex);

			boolean accept=acceptMoveUsingRRT(problem.getFunctionValue(iCurrentIndex),problem.getFunctionValue(iCandidateIndex),problem.getBestSolutionValue());

			if(accept){
				problem.copySolution(iCandidateIndex,iCurrentIndex);
			}
			else{
				problem.copySolution(iCurrentIndex,iCandidateIndex);
			}

		}
	}
	
	/**
	 * Simple random heuristic selection. This should not "apply" the heuristic,
	 * only choose it so that it can be applied within the main hyper-heuristic loop.
	 *
	 * PSEUDOCODE:
	 *
	 * INPUT: set_of_heuristics
	 * h <- random \in set_of_heuristics;
	 * return h;
	 * 
	 * @param setOfHeuristics The set of heuristics to choose from.
	 * @return The (ID of the) heuristic to apply.
	 */
	public int getNextHeuristicUsingSimpleRandom(int[] setOfHeuristics) {
		
		// NOTE: the random Object instance can be accessed as the variable "rng".
		
		// TODO implementation of simple random heuristic selection
		int h=rng.nextInt(setOfHeuristics.length);
		return h;
	}
	
	/**
	 * Record-to-record Travel for move acceptance.
	 *
	 * ( see exercise sheet for formula for calculating threshold value )
	 *
	 * PSEUDOCODE for non-stochastic threshold move acceptance accepting IE moves:
	 *
	 * tau <- currentThresholdValue(); // calculated using RRT!
	 * IF f( s' ) <= max( f( s ), tau ) THEN
	 *     "accept()";
	 * ELSE
	 *     "reject()";
     * END_IF;
	 * 
	 * @param currentSolutionFitness The objective value of the current solution (s).
	 * @param candidateSolutionFitness The objective value of the candidate solution (s').
	 * @param bestSolutionFitness The objective value of the best solution found so far (s_best)
	 * @return Whether to accept (true) or reject (false) the candidate solution.
	 */
	public boolean acceptMoveUsingRRT(double currentSolutionFitness, double candidateSolutionFitness,
			double bestSolutionFitness ) {

		// TODO implementation of the record-to-record travel algorithm.
		double threshold=bestSolutionFitness*1.5;
		double max=Math.max(currentSolutionFitness,threshold);
		if(candidateSolutionFitness<=max){
			return true;
		}
		else{
			return false;
		}
	}
	
	public String toString() {

		return "SR-RRT-HH";
	}

}
