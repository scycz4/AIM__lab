package com.aim.lab04;

import com.aim.TestFrameConfig;

/**
 * Test frame/experimental configuration for lab 4.
 * 
 * @author Warren G. Jackson
 * @since 31/01/2021
 * @version 1.0.0
 *
 */
public class Lab04TestFrameConfig extends TestFrameConfig {

	/*
	 * permitted values = { Mode.GA, Mode.MA } Mode.GA = genetic algorithm ( local
	 * search <- NOOP ) Mode.MA = memetic algorithm ( local search <- DBHC_IE )
	 * 
	 */
	protected final Mode MODE = Mode.GA;

	/*
	 * permitted total runs = 11
	 */
	protected final int TOTAL_RUNS = 11;

	/*
	 * permitted instance ID's = 1
	 */
	protected final int INSTANCE_ID = 1;

	/*
	 * permitted population sizes = 4, 8, 16
	 */
	public final int POP_SIZE = 16;

	/**
	 * The experimental seed, set as the date this lab was released.
	 */
	private static final long m_parentSeed = 12022021l;
	
	/**
	 * 
	 */
	private static Lab04TestFrameConfig oThis;

	/**
	 * 
	 */
	private Lab04TestFrameConfig() {

		super(new long[] {m_parentSeed});
	}

	/**
	 * 
	 * @return
	 */
	public synchronized static Lab04TestFrameConfig getInstance() {

		if (oThis == null) {
			oThis = new Lab04TestFrameConfig();
		}

		return oThis;
	}

	@Override
	public int getTotalRuns() {
		return this.TOTAL_RUNS;
	}

	@Override
	public int getInstanceId() {
		return this.INSTANCE_ID;
	}

	@Override
	public int getRunTime() {
		return Integer.MAX_VALUE;
	}

	@Override
	public String getMethodName() {
		return "Memetic Algorithm";
	}

	public int getPopulationSize() {
		return this.POP_SIZE;
	}

	@Override
	public String getConfigurationAsString() {
		return getMethodName() + " ( " + MODE.toString() + "_Mode ) given " + MODE.getGenerations() 
		+ " generations for solving instance ID " + getInstanceId()  + " over " + getTotalRuns() + " runs"
		+ (" with Population size = " + getPopulationSize() + ".");
	}

	/**
	 * Execution mode for the Memetic Algorithm.
	 * 
	 * @author Warren G. Jackson
	 * @since 31/01/2021
	 * @version 1.0.0
	 *
	 */
	public enum Mode {

		GA(5000), MA(100);

		private final int generations;

		Mode(int generations) {
			this.generations = generations;
		}

		public int getGenerations() {
			return generations;
		}

	}

}
