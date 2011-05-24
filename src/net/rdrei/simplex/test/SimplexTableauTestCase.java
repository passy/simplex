package net.rdrei.simplex.test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

import net.rdrei.simplex.lib.InitialSimplexTableau;
import net.rdrei.simplex.lib.SimplexPivotException;
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
	
	
	class InitialSimplexTableauMock extends InitialSimplexTableau {
		private SimplexProblem problem;
		
		public InitialSimplexTableauMock(SimplexProblem problem) {
			super(problem);
			this.problem = problem;
		}

		/**
		 * Overrides the 'protected' visibility. I don't know how
		 * to do this properly in java, unfortunately.
		 */
		public int[] _getTargetFunctionCoefficients() {
			return this.getTargetFunctionCoefficients(this.problem);
		}
	}
	
	
	/**
	 * Test fixture for a valid simplex problem.
	 */
	private SimplexProblem getSimplexProblem() {
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
	
	private InitialSimplexTableauMock getSimplexTableau() {
		SimplexProblem problem = this.getSimplexProblem();
		InitialSimplexTableauMock tabl =
			new InitialSimplexTableauMock(problem);
		
		return tabl;
	}
	
	@Test
	public void createInitialSimplexTableau() {
		SimplexProblem problem = this.getSimplexProblem();
		SimplexTableau tabl = new InitialSimplexTableau(problem);
		
		String output = "" +
		"--------------------------------------------------------\n" +
		"1.0\t0.0\t1.0\t0.0\t0.0\t0.0\t10.0\t\n" +
		"0.0\t1.0\t0.0\t1.0\t0.0\t0.0\t6.0\t\n" +
		"2.0\t4.0\t0.0\t0.0\t1.0\t0.0\t32.0\t\n" +
		"-30.0\t-20.0\t0.0\t0.0\t0.0\t1.0\t0.0\t\n" +
		"--------------------------------------------------------";
		
		TestCase.assertEquals(output, tabl.toString());
	}
	
	@Test
	public void getTargetFunctionCoefficients() {
		InitialSimplexTableauMock tabl = this.getSimplexTableau();
		int[] results = tabl._getTargetFunctionCoefficients();
		int[] expecteds = new int[] {
				-30, -20, 0, 0, 0, 1
		};
		Assert.assertArrayEquals(expecteds, results);
	}
	
	@Test
	public void getPivotColumn() {
		SimplexTableau tabl = this.getSimplexTableau();
		
		int pivotColumn = tabl.getPivotColumn();
		Assert.assertEquals(0, pivotColumn);
	}
	
	@Test
	public void isOptimal() {
		SimplexTableau tabl = this.getSimplexTableau();
		
		Assert.assertFalse(tabl.isOptimal());
	}
	
	/**
	 * Receive and validate a base result from the initial tableau.
	 */
	@Test
	public void getBaseResult() {
		SimplexTableau tabl = this.getSimplexTableau();
		HashMap<String, Float> results = tabl.getBaseResult();
		
		Assert.assertEquals(6, results.size());
		Set<String> keys = results.keySet();
		Collection<Float> values = results.values();
		
		Assert.assertArrayEquals(new String[] {
			"s2", "s1", "x2", "s3", "x1", "Z"
		}, keys.toArray());
		Assert.assertArrayEquals(new Float[] {
			6f, 10f, 0f, 32f, 0f, 0f
		}, values.toArray());
	}
	
	@Test
	public void getPivotRow() {
		SimplexTableau tabl = this.getSimplexTableau();
		int row;
		try {
			row = tabl.getPivotRow();
			Assert.assertEquals(0, row);
		} catch (SimplexPivotException e) {
			e.printStackTrace();
			Assert.fail(e.toString());
		}
	}
}
