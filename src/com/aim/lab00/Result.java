package com.aim.lab00;

public class Result {

	private final int m_trialId;
	
	private final long m_seed;

	private final double m_best;

	private final double m_timeTaken;

	public Result(int trialId, long seed, double best, double timeTaken) {

		this.m_trialId = trialId;
		this.m_seed = seed;
		this.m_best = best;
		this.m_timeTaken = timeTaken;
	}
	
	public int getTrialId() {
		return m_trialId;
	}
	public long getSeed() {
		return m_seed;
	}

	public double getBest() {
		return m_best;
	}

	public double getTimeTaken() {
		return m_timeTaken;
	}
}