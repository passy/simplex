package net.rdrei.simplex.lib;

public interface SimplexProblem {
	int getNumberOfBaseVariables();
	
	int[] getBaseVariables();
	
	int getNumberOfRestrictions();
	
	int[][] getRestrictions();
}
