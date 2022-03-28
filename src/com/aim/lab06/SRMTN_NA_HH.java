package com.aim.lab06;

import com.aim.hyflex.HyFlexUtilities;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import AbstractClasses.ProblemDomain.HeuristicType;

/**
 * 
 * @author Warren G. Jackson
 * 
 * A simple random heuristic selection from the set of mutation 
 * type heuristics with a naive move acceptance strategy.
 *
 */
public class SRMTN_NA_HH extends HyperHeuristic {
	
	public SRMTN_NA_HH(long seed) {
		
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
	  *     accept <- acceptMoveUsingNA( f( s ), f( s' ), f( s_best ) );
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
		
		int iCurrentIndex = 0, iCandidateIndex = 1;
		int[] aiMutationHeuristicIds = HyFlexUtilities.getHeuristicSetOfTypes(problem, HeuristicType.MUTATION);
		
		problem.setMemorySize(2);
		problem.initialiseSolution(iCurrentIndex);
		
		double dCurrentSolutionCost, dCandidateSolutionCost;
		dCurrentSolutionCost = problem.getFunctionValue(iCurrentIndex);
		
		while(!hasTimeExpired()) {
			
			// simple random heuristic selection from mutation operators
			int h = aiMutationHeuristicIds[rng.nextInt(aiMutationHeuristicIds.length)];
			
			// apply heuristic 'h' to solution in iCurrentIndex and save in iCandidateIndex
			dCandidateSolutionCost = problem.applyHeuristic(h, iCurrentIndex, iCandidateIndex);
			
			// naive acceptance
			if(dCandidateSolutionCost <= dCurrentSolutionCost || rng.nextDouble() < 0.5) {
				
				// accept
				problem.copySolution(iCandidateIndex, iCurrentIndex);
				dCurrentSolutionCost = dCandidateSolutionCost;
				
			} else {
				
				// reject
				problem.copySolution(iCurrentIndex, iCandidateIndex);
				// 'dCurrentSolutionCost' stays the same!
			}
		}
	}
	
	public String toString() {

		return "SR-RRT-HH";
	}

}
