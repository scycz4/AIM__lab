package com.aim.lab00;

import java.util.Random;
import java.util.stream.IntStream;

import com.aim.TestFrame;
import com.aim.TestFrameConfig;

import uk.ac.nott.cs.aim.domains.chesc2014_SAT.SAT;

/**
 * Class for running the experiment(s) in Lab 0.
 *
 * @author Warren G. Jackson
 */
public class Lab_00_Runner extends TestFrame {

	/**
	 * 
	 */
	public Lab_00_Runner() {

		super(Lab00TestFrameConfig.getInstance());
	}

	/**
	 * 
	 * @param seed
	 * @param instance
	 * @param timeLimit
	 * @return
	 */
	public Result runTest(int trialId, long seed, int instance, int timeLimit) {

		Random random = new Random(seed);
		SAT sat = new SAT(instance, timeLimit, random);
		RandomWalk rw = new RandomWalk(sat, random);
		rw.run();

		return new Result(trialId, seed, sat.getBestSolutionValue(), rw.getTimeTaken());
	}

	/**
	 * 
	 * @param result
	 */
	private void printResult(Result result) {

		System.out.println(result.getTrialId() + "," + result.getSeed() + "," + result.getBest() + "," + result.getTimeTaken());
	}

	@Override
	public void runTests() {

		TestFrameConfig oTestConfiguration = getTestConfiguration();

		final int instance = oTestConfiguration.getInstanceId();
		final int timeLimit = oTestConfiguration.getRunTime();
		final int totalRuns = oTestConfiguration.getTotalRuns();
		final long[] seeds = getExperimentalSeeds();

		System.out.println(getTestConfiguration().getConfigurationAsString());
		System.out.println("trial,seed,f_best,time_taken(seconds)");

		runUsingExperimentalParallelism(IntStream.range(0, totalRuns).boxed())
				.map(i -> runTest(i, seeds[i], instance, timeLimit)).forEachOrdered(this::printResult);
	}

	/**
	 * 
	 * @param args The program arguments, not used.
	 */
	public static void main(String[] args) {

		Lab_00_Runner runner = new Lab_00_Runner();
		runner.runTests();
	}
}
