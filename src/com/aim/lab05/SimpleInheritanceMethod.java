package com.aim.lab05;

import java.util.Random;

import com.aim.lab05.dependencies.MemeplexInheritanceMethod;

import uk.ac.nott.cs.aim.domains.chesc2014_SAT.Meme;
import uk.ac.nott.cs.aim.domains.chesc2014_SAT.SAT;

public class SimpleInheritanceMethod implements MemeplexInheritanceMethod {

	private final SAT problem;
	private final Random rng;
	
	public SimpleInheritanceMethod(SAT problem, Random rng) {
		
		this.problem = problem;
		this.rng = rng;
	}
	
	/**
	 * Copies the memetic material of the parents to the children
	 * using the Simple Inheritance Method.
	 * 
	 * @param parent1 The solution memory index of parent 1.
	 * @param parent2 The solution memory index of parent 2.
	 * @param child1 The solution memory index of child 1.
	 * @param child2 The solution memory index of child 2.
	 * 
	 * Simple Inheritance Method PSEUDOCODE:
	 * 
	 * INPUT: parent1, parent2, child1, child2
	 * IF f( parent1 ) == f( parent2 ) THEN
	 * 
	 *     inherit = random \in { parent1, parent2 }
	 *     child1.memeplex <- inherit.memeplex;
	 *     child2.memeplex <- inherit.memeplex;
	 *     
	 * ELSEIF f( parent1 ) < f( parent2 ) THEN
	 * 
	 *     child1.memeplex <- parent1.memeplex;
	 *     child2.memeplex <- parent1.memeplex;
	 *     
	 * ELSE // parent2 is best
	 * 
	 *     child1.memeplex = parent2.memeplex;
	 *     child2.memeplex = parent2.memeplex;
	 *     
	 * ENDIF
	 * return;
	 */
	@Override
	public void performMemeticInheritance(int parent1, int parent2, int child1, int child2) {
		
		// TODO - implementation of simple inheritance method
		if(problem.getObjectiveFunctionValue(parent1)==problem.getObjectiveFunctionValue(parent2)){
			if(rng.nextDouble()>0.5){
				for(int i=0;i<problem.getNumberOfMemes();i++){
					this.problem.getMeme(child1,i).setMemeOption(this.problem.getMeme(parent2,i).getMemeOption());
					this.problem.getMeme(child2,i).setMemeOption(this.problem.getMeme(parent2,i).getMemeOption());
				}
			}
			else{
				for(int i=0;i<problem.getNumberOfMemes();i++){
					this.problem.getMeme(child1,i).setMemeOption(this.problem.getMeme(parent1,i).getMemeOption());
					this.problem.getMeme(child2,i).setMemeOption(this.problem.getMeme(parent1,i).getMemeOption());
				}
			}
		}
		else if(problem.getObjectiveFunctionValue(parent1)<problem.getObjectiveFunctionValue(parent2)){
			for(int i=0;i<problem.getNumberOfMemes();i++){
				this.problem.getMeme(child1,i).setMemeOption(this.problem.getMeme(parent1,i).getMemeOption());
				this.problem.getMeme(child2,i).setMemeOption(this.problem.getMeme(parent1,i).getMemeOption());
			}
		}
		else{
			for(int i=0;i<problem.getNumberOfMemes();i++){
				this.problem.getMeme(child1,i).setMemeOption(this.problem.getMeme(parent2,i).getMemeOption());
				this.problem.getMeme(child2,i).setMemeOption(this.problem.getMeme(parent2,i).getMemeOption());
			}
		}
	}
}
