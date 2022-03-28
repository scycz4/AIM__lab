package com.aim.lab01;

import java.util.Random;

import com.aim.TestFrameConfig;

import uk.ac.nott.cs.aim.satheuristics.SATHeuristic;

/**
 * Test frame/experimental configuration for lab 1.
 * 
 * @author Warren G. Jackson
 * @since 31/01/2021
 * @version 1.0.0
 *
 */
public class Lab01TestFrameConfig extends TestFrameConfig {

	/**
	 * The experimental seed, set as the date this lab was released.
	 */
	private static final long m_parentSeed = 12022021l;

	/*
	 * permitted instance ID's = 1, 9
	 */
	private final int INSTANCE_ID = 9;

	/*
	 * permitted run times (seconds) = 1, 5, 10, 20
	 */
	private final int RUN_TIME = 1;

	/**
	 * 
	 */
	private final int TRIALS_PER_TEST = 11;

	/**
	 * 
	 */
	private static Lab01TestFrameConfig oThis;

	/**
	 * 
	 */
	private Lab01TestFrameConfig() {

		super(new long[] {m_parentSeed});
	}

	/**
	 * 
	 * @return
	 */
	public synchronized static Lab01TestFrameConfig getInstance() {

		if (oThis == null) {
			oThis = new Lab01TestFrameConfig();
		}

		return oThis;
	}

	@Override
	public int getInstanceId() {
		return this.INSTANCE_ID;
	}

	@Override
	public int getRunTime() {
		return this.RUN_TIME;
	}

	@Override
	public String getMethodName() {
		return "Davis's Bit Hill Climbing and Steepest Descent";
	}

	@Override
	public String getConfigurationAsString() {

		return String.format("SAT instance #%d with a run time of %d nominal seconds.", INSTANCE_ID, RUN_TIME);
	}

	@Override
	public int getTotalRuns() {

		return TRIALS_PER_TEST;
	}

	/**
	 * This method should not be changed but is intended for personal use if you
	 * wish to try with other heuristics of your own making.
	 * 
	 * @param heuristicID 0 for the first heuristic, or 1 for the second.
	 * @param random      The random number generator used by all SATHeuristic's
	 * @return The corresponding SAT heuristic
	 */
	public static SATHeuristic getSATHeuristic(int heuristicID, Random random) {

		SATHeuristic heuristic = null;

		switch (heuristicID) {
		case 0:
			heuristic = new DavissBitHC(random);
			break;
		case 1:
			heuristic = new SteepestDescentHC(random);
			break;
		default:
			System.err.println("Request for more than 2 heuristics at a time!");
			System.exit(0);
		}

		return heuristic;
	}
}
