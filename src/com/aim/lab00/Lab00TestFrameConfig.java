package com.aim.lab00;

import com.aim.TestFrameConfig;

/**
 * Test frame/experimental configuration for lab 0.
 * 
 * @author Warren G. Jackson
 * @since 31/01/2021
 * @version 1.0.0
 *
 */
public class Lab00TestFrameConfig extends TestFrameConfig {

	/**
	 * The experimental seed, set as the date this lab was released.
	 */
	private static final long[] m_seeds = { 02022022, 020220221 , 020220221 , 020220221 , 020220221 , 020220221 , 020220221 , 1 , 123 , 456 };

	/*
	 * permitted instance ID's = 1
	 */
	private final int INSTANCE_ID = 1;

	/*
	 * permitted run times (seconds) = 10
	 */
	private final int RUN_TIME = 10;

	/**
	 * 
	 */
	private final int TRIALS_PER_TEST = m_seeds.length;

	/**
	 * 
	 */
	private static Lab00TestFrameConfig oThis;

	/**
	 * 
	 */
	private Lab00TestFrameConfig() {

		super(m_seeds);
	}

	/**
	 * 
	 * @return The configuration instance
	 */
	public synchronized static Lab00TestFrameConfig getInstance() {

		if (oThis == null) {
			oThis = new Lab00TestFrameConfig();
		}

		return oThis;
	}

	@Override
	public int getInstanceId() {
		return INSTANCE_ID;
	}

	@Override
	public int getRunTime() {
		return RUN_TIME;
	}

	@Override
	public String getMethodName() {
		return "Random Walk";
	}

	@Override
	public String getConfigurationAsString() {

		return String.format("SAT instance #%d with a run time of %d nominal seconds.", INSTANCE_ID, RUN_TIME);
	}

	@Override
	public int getTotalRuns() {
		return TRIALS_PER_TEST;
	}

}
