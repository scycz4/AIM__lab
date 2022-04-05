package com.aim.lab08;



import com.aim.hyflex.HyFlexUtilities;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import AbstractClasses.ProblemDomain.HeuristicType;

import java.util.ArrayList;

public class SCF_AM_HH extends HyperHeuristic {

	public SCF_AM_HH(long seed) {

		super(seed);
	}

	/**
	 * TODO - Implement a selection hyper-heuristic using the simplified choice function (SCF)
	 * 		  heuristic selection method accepting all moves (AM).
	 *
	 * PSEUDOCODE:
	 *
	 * INPUT: problem_instance
	 * hs <- { MTN } U { RR } U { LS }
	 * s <- initialiseSolution();
	 * scf <- initialiseNewSimplifiedChoiceFunctionMethod();
	 *
	 * WHILE termination criterion is not met DO
	 *
	 *     h <- scf.selectHeuristicToApply();
	 *     setHeuristicParameterSettings(iom, dos);
	 *     s' <- h(s);
	 *
	 *     scf.updateHeuristicData(h_i, t, t_delta, f(s), f(s'));
	 *
	 *     accept();
	 *
	 * END_WHILE
	 *
	 * return s_{best};
	 */
	public void solve(ProblemDomain problem) {

		// TODO implementation of SCF_AM_HH
		int[] mtn=HyFlexUtilities.getHeuristicSetOfTypes(problem,HeuristicType.MUTATION);
		int[] rr=HyFlexUtilities.getHeuristicSetOfTypes(problem,HeuristicType.RUIN_RECREATE);
		int[] ls=HyFlexUtilities.getHeuristicSetOfTypes(problem,HeuristicType.LOCAL_SEARCH);

		int numberOfOptions=5;
		ArrayList<Heuristic> arrayList=new ArrayList<>(numberOfOptions*(mtn.length+rr.length+ls.length));


		for(int i=1;i<=numberOfOptions;i++){
			double dos=i*0.2;
			double iom=i*0.2;
			HeuristicConfiguration heuristicConfiguration=new HeuristicConfiguration(iom,dos);
			for(int j=0;j<mtn.length;j++){
				Heuristic heuristic=new Heuristic(heuristicConfiguration,mtn[j],System.nanoTime());
				arrayList.add(heuristic);
			}
			for(int j=0;j<rr.length;j++){
				Heuristic heuristic=new Heuristic(heuristicConfiguration,rr[j],System.nanoTime());
				arrayList.add(heuristic);
			}
			for(int j=0;j<ls.length;j++){
				Heuristic heuristic=new Heuristic(heuristicConfiguration,ls[j],System.nanoTime());
				arrayList.add(heuristic);
			}
		}
		Heuristic[] hs= arrayList.toArray(new Heuristic[0]);


		problem.initialiseSolution(0);

		SimplifiedChoiceFunction scf=new SimplifiedChoiceFunction(hs);
		while(!hasTimeExpired()){
			Heuristic h=scf.selectHeuristicToApply();
			problem.setDepthOfSearch(h.getConfiguration().getDos());
			problem.setIntensityOfMutation(h.getConfiguration().getIom());
			long timeApplied=System.nanoTime();
			problem.applyHeuristic(h.getHeuristicId(),0,1);
			long exit=System.nanoTime();
			scf.updateHeuristicData(h,timeApplied,exit-timeApplied, problem.getFunctionValue(0),problem.getFunctionValue(1));

			problem.copySolution(1,0);
		}
	}

	public String toString() {

		return "SCF_AM_HH";
	}

}
