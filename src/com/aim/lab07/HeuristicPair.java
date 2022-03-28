package com.aim.lab07;

/**
 * 
 * Wrapper class used to pair together heuristic IDs 
 * for an ILS-based hyper-heuristic framework.
 * Note that this implementation is complete and you
 * do not need to, and should not, change anything here!
 * 
 * @author Warren G. Jackson
 *
 */
public class HeuristicPair {

	private final int h1;
	
	private final int h2;
	
	public HeuristicPair(int h1, int h2) {
		
		this.h1 = h1;
		this.h2 = h2;
	}
	
	/**
	 * Returns the first heuristic in the pair.
	 * @return The first heuristic in the pair
	 */
	public int getFirst() {
		
		return this.h1;
	}
	
	/**
	 * Returns the last heuristic in the pair.
	 * @return The last heuristic in the pair
	 */
	public int getLast() {
		
		return this.h2;
	}
}
