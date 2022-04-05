package com.aim.lab08;


public class SimplifiedChoiceFunction {

	private Heuristic[] heuristics;

	private double phi;

	public SimplifiedChoiceFunction(Heuristic[] heuristics) {

		this.heuristics = heuristics;
		this.phi = 0.99;
	}

	/**
	 *
	 * @param heuristic
	 * @param timeApplied Current time in nanoseconds.
	 * @param timeTaken Time taken to apply <code>heuristic</code> in nanoseconds.
	 * @param current Objective value of the current solution, f(s_i).
	 * @param candidate Objective value of the candidate solution f(s'_i).
	 */
	public void updateHeuristicData(Heuristic heuristic, long timeApplied, long timeTaken, double current, double candidate) {

		// TODO
		heuristic.getData().setTimeLastApplied(timeApplied);
		heuristic.getData().setPreviousApplicationDuration(timeTaken);
		heuristic.getData().setF_delta(candidate-current);
		if(current<candidate){
			this.phi=Math.max(0.01,this.phi-0.01);
		}
		else{
			this.phi=0.99;
		}
	}

	public Heuristic selectHeuristicToApply() {

		// TODO
		int bestIndex=0;
		for(int i=0;i< heuristics.length;i++){
			double s1=calculateScore(heuristics[i],System.nanoTime());
			double best=calculateScore(heuristics[bestIndex],System.nanoTime());
			if(s1>best){
				bestIndex=i;
			}
		}
		return heuristics[bestIndex];
	}

	public double calculateScore(Heuristic h, long currentTime) {

		// TODO
		double I=1/h.getData().getF_delta();
		double f1=I/((currentTime-h.getData().getTimeLastApplied())/1000000000);

		double f3=(currentTime-h.getData().getTimeLastApplied())/1000000000;

		double epusi=1-this.phi;

		double Ft=phi*f1+epusi*f3;

		return Ft;
	}

}
