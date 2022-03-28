package com.aim.lab03;

public class LundyAndMees implements CoolingSchedule {
	
	private double dCurrentTemperature;
	
	private final double dBeta;
	
	/**
	 * 
	 * @param initialSolutionFitness
	 *            The objective value of the initial solution. Maybe useful (or
	 *            not) for some setting?
	 */
	public LundyAndMees(double initialSolutionFitness) {
		
		double c = 1.0d;
		this.dCurrentTemperature = c * initialSolutionFitness;
		
		// TODO You will need to find a suitable value for beta 
		//      through prior knowledge and experimentation!
		this.dBeta = 0.0001d;
	}
	
	@Override
	public double getCurrentTemperature() {
		
		return dCurrentTemperature;
	}

	/**
	 * DEFINITION: T_{i + 1} = T_i / ( 1 + beta * T_i )
	 */
	@Override
	public void advanceTemperature() {
		
		// TODO update the value of the current temperature, 'dCurrentTemperature'
	
		dCurrentTemperature = dCurrentTemperature / (1 + dBeta * dCurrentTemperature);
	}
	
	@Override
	public String toString() {
		
		return "Lundy and Mees";
	}

}
