package com.aim.lab05.dependencies;

import java.util.Random;
import java.util.stream.IntStream;

import uk.ac.nott.cs.aim.domains.chesc2014_SAT.SAT;
import uk.ac.nott.cs.aim.helperfunctions.ArrayMethods;

public class TournamentSelection {
	private final int POPULATION_SIZE;
	private final Random rng;
	private final SAT problem;

	public TournamentSelection(int iPopulationSize, Random oRandom, SAT oProblem) {
		this.POPULATION_SIZE = iPopulationSize;
		this.rng = oRandom;
		this.problem = oProblem;
	}

	public int tournamentSelection(int iTournamentSize) {
		int i = -1;
		double d1 = Double.MAX_VALUE;
		int[] arrayOfInt = ArrayMethods.shuffle(IntStream.range(0, this.POPULATION_SIZE).toArray(), this.rng);
		for (int j = 0; j < iTournamentSize; j++) {
			int k = arrayOfInt[j];
			double d2;
			if ((d2 = this.problem.getObjectiveFunctionValue(k)) < d1) {
				d1 = d2;
				i = k;
			}
		}
		return i;
	}
}
