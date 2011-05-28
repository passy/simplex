package net.rdrei.simplex.lib;

public interface SimplexProblem {
	int[] getProblemVariables();
	
	SimplexRestrictionSet getRestrictionSet();
}
