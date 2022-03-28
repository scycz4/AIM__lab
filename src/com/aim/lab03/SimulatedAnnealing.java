package com.aim.lab03;


import java.util.Random;

import uk.ac.nott.cs.aim.domains.chesc2014_SAT.SAT;
import uk.ac.nott.cs.aim.searchmethods.SinglePointSearchMethod;


public class SimulatedAnnealing extends SinglePointSearchMethod {
	
	private CoolingSchedule oCoolingSchedule;
	
	public SimulatedAnnealing(CoolingSchedule schedule, SAT problem, Random random) {
		
		super(problem, random);
		
		this.oCoolingSchedule = schedule;
	}

	/**
	 * ================================================================
	 * NOTE: In the same way as last week's exercise, you only need
	 *       to implement the code WITHIN the loop for runMainLoop().
	 *       Everything else is handled by the framework/Lab_03_Runner.
	 * ================================================================
	 * 
	 * PSEUDOCODE for Simulated Annealing:
	 *
	 * INPUT : T_0 and any other parameters of the cooling schedule
	 * s_0 = generateInitialSolution();
	 * Temp <- T_0;
	 * s_{best} <- s_0;
	 * s' <- s_0;
	 *
	 * REPEAT
	 *     s' <- randomBitFlip(s);
	 *     delta <- f(s') - f(s);
	 *     r <- random \in [0,1];
	 *     IF delta < 0 OR r < P(delta, Temp) THEN
	 *         s <- s';
	 *     ENDIF
	 *     s_{best} <- updateBest(); // NOTE: this step is already handled by the framework!
	 *     Temp <- advanceTemperature();
	 * UNTIL termination conditions are satisfied;
	 *
	 * RETURN s_{best};
	 * 
	 * 
	 * REMEMBER That the solutions in the CURRENT_SOLUTION_INDEX and BACKUP_SOLUTION_INDEX
	 * 	should be the same before returning from 'runMainLoop()'!
	 */
	protected void runMainLoop() {
		// TODO implementation of Simulated Annealing
		// using 'oCoolingSchedule' as the cooling schedule
		
		int len = problem.getNumberOfVariables();
		int rndIndex = random.nextInt(len);
		problem.bitFlip(rndIndex, CURRENT_SOLUTION_INDEX);
		
		double s2 = problem.getObjectiveFunctionValue(CURRENT_SOLUTION_INDEX);
		double s = problem.getObjectiveFunctionValue(BACKUP_SOLUTION_INDEX);
		double delta = s2 - s;
		
		double r = random.nextDouble();
		
		double pValue = Math.exp(delta * -1 / oCoolingSchedule.getCurrentTemperature());
		if (delta < 0 || r < pValue) {
			problem.copySolution(CURRENT_SOLUTION_INDEX, BACKUP_SOLUTION_INDEX);
		} else {
			problem.copySolution(BACKUP_SOLUTION_INDEX, CURRENT_SOLUTION_INDEX);
		}
		
		oCoolingSchedule.advanceTemperature();
	}
		
	public String toString() {
		return "Simulated Annealing with " + oCoolingSchedule.toString();
	}
}












