package net.rdrei.simplex.test;

import org.junit.Test;

import net.rdrei.simplex.lib.InitialSimplexTableau;
import net.rdrei.simplex.lib.SimplexProblem;
import net.rdrei.simplex.lib.SimplexRestriction;
import net.rdrei.simplex.lib.SimplexRestrictionSet;
import net.rdrei.simplex.lib.SimplexTableau;

public class SimplexTableauTestCase {
	/**
	 * Mock for a simplex problem.
	 */
	class SimplexProblemMock implements SimplexProblem {
		private int[] baseVariables;
		private SimplexRestrictionSet restrictionSet;
		public SimplexProblemMock(int[] baseVariables,
				SimplexRestrictionSet restrictionSet) {
			super();
			this.baseVariables = baseVariables;
			this.restrictionSet = restrictionSet;
		}
		public int[] getBaseVariables() {
			return baseVariables;
		}
		public SimplexRestrictionSet getRestrictionSet() {
			return restrictionSet;
		}
		public void setBaseVariables(int[] baseVariables) {
			this.baseVariables = baseVariables;
		}
		public void setRestrictionSet(SimplexRestrictionSet restrictionSet) {
			this.restrictionSet = restrictionSet;
		}
	}
	
	
	/**
	 * Test fixture for a valid simplex problem.
	 */
	public SimplexProblem getSimplexProblem() {
		SimplexRestriction rest1 = new SimplexRestriction(new int[]{1, 0},
				10);
		SimplexRestriction rest2 = new SimplexRestriction(new int[]{0, 1},
				6);
		SimplexRestriction rest3 = new SimplexRestriction(new int[]{2, 4},
				32);
		
		int[] baseVariables = new int[] {30, 20};
		
		SimplexRestrictionSet restrictionSet = new SimplexRestrictionSet();
		restrictionSet.add(rest1);
		restrictionSet.add(rest2);
		restrictionSet.add(rest3);
		SimplexProblem result = new SimplexProblemMock(baseVariables,
				restrictionSet);
		return result;
	}
	
	@Test
	public void createInitialSimplexTableau() {
		SimplexProblem problem = this.getSimplexProblem();
		SimplexTableau tabl = new InitialSimplexTableau(problem);
		System.out.println(tabl);
	}
}
