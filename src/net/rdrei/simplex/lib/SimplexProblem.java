package net.rdrei.simplex.lib;

public interface SimplexProblem {
	int[] getBaseVariables();
	
	SimplexRestriction[] getRestrictions();
}
