package com.aim.lab06;

import com.aim.hyflex.HyFlexTestFrame;

public class Lab06TestFrameConfig extends HyFlexTestFrame {
	
	/*
	 * permitted run time(s) in nominal chesc seconds = { 60 }
	 */
	protected final long RUN_TIME_IN_SECONDS = 30;;//60;
	
	/*
	 * permitted problem domain(s) = { SAT }
	 */
	protected final String[] PROBLEM_DOMAINS = { "BP", "SAT", "TSP" };
	/*
	 * permitted instance ID's:
	 * 		BP  = { 7, 11 }
	 *		SAT = { 3, 11 }
	 *		TSP = { 0, 6 }
	 */
	protected final int[][] INSTANCE_IDs = { { 7, 11 }, { 3, 11 }, { 0, 6 } };
	
	@Override
	public String[] getDomains() {
		
		return this.PROBLEM_DOMAINS;
	}

	@Override
	public int[][] getInstanceIDs() {
		
		return this.INSTANCE_IDs;
	}

	@Override
	public long getRunTime() {
		
		return (MILLISECONDS_IN_TEN_MINUTES * RUN_TIME_IN_SECONDS) / 600;
	}

	
}
