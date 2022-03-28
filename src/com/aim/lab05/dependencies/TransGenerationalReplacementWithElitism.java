package com.aim.lab05.dependencies;

import java.util.stream.IntStream;

import uk.ac.nott.cs.aim.domains.chesc2014_SAT.SAT;
import uk.ac.nott.cs.aim.satheuristics.genetics.PopulationReplacement;

public class TransGenerationalReplacementWithElitism extends PopulationReplacement {
	protected int[] getNextGeneration(SAT oProblem, int iPopulationSize) {
		int[] arrayOfInt = IntStream.range(iPopulationSize, iPopulationSize << 1).toArray();
		double[] arrayOfDouble = new double[iPopulationSize << 2];
		double d1 = Double.MAX_VALUE;
		double d2 = -Double.MAX_VALUE;
		int i = -1;
		int j = -1;
		for (int k = 0; k < iPopulationSize << 1; k++) {
			double d3 = oProblem.getObjectiveFunctionValue(k);
			arrayOfDouble[k] = d3;
			if (d3 <= d1) {
				d1 = d3;
				i = k;
			}
			if ((k >= iPopulationSize) && (d3 > d2)) {
				j = k;
				d2 = d3;
			}
		}
		if (i < iPopulationSize) {
			arrayOfInt[(j - iPopulationSize)] = i;
		}
		return arrayOfInt;
	}
}
