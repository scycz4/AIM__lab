package com.aim.lab08;


public class HeuristicConfiguration {

	private double iom, dos;
	
	public HeuristicConfiguration(double iom, double dos) {
		
		this.iom = iom;
		this.dos = dos;
	}

	public double getIom() {
		return iom;
	}

	public double getDos() {
		return dos;
	}
}
