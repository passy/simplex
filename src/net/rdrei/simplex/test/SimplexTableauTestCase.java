package net.rdrei.simplex.test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

import net.rdrei.simplex.lib.InitialSimplexTableau;
import net.rdrei.simplex.lib.PivotElement;
import net.rdrei.simplex.lib.SimplexPivotException;
import net.rdrei.simplex.lib.SimplexPivotStep;
import net.rdrei.simplex.lib.SimplexProblem;
import net.rdrei.simplex.lib.SimplexRestriction;
import net.rdrei.simplex.lib.SimplexRestrictionSet;
import net.rdrei.simplex.lib.SimplexTableau;

public class SimplexTableauTestCase extends TestCase {
	/**
	 * Mock for a simplex problem.
	 */
	class SimplexProblemMock implements SimplexProblem {
		private int[] problemVariables;
		private SimplexRestrictionSet restrictionSet;
		public SimplexProblemMock(int[] baseVariables,
				SimplexRestrictionSet restrictionSet) {
			super();
			this.problemVariables = baseVariables;
			this.restrictionSet = restrictionSet;
		}
		public int[] getProblemVariables() {
			return problemVariables;
		}
		public SimplexRestrictionSet getRestrictionSet() {
			return restrictionSet;
		}
		public void setBaseVariables(int[] baseVariables) {
			this.problemVariables = baseVariables;
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
	public void testCreateInitialSimplexTableau() {
		SimplexProblem problem = this.getSimplexProblem();
		SimplexTableau tabl = new InitialSimplexTableau(problem);
		
		String output = "" +
		"--------------------------------------------------------\n" +
		"*1.0*\t0.0\t1.0\t0.0\t0.0\t0.0\t10.0\t\n" +
		"0.0\t1.0\t0.0\t1.0\t0.0\t0.0\t6.0\t\n" +
		"2.0\t4.0\t0.0\t0.0\t1.0\t0.0\t32.0\t\n" +
		"-30.0\t-20.0\t0.0\t0.0\t0.0\t1.0\t0.0\t\n" +
		"--------------------------------------------------------";
		
		TestCase.assertEquals(output, tabl.toString());
	}
	
	@Test
	public void testGetTargetFunctionCoefficients() {
		InitialSimplexTableauMock tabl = this.getSimplexTableau();
		int[] results = tabl._getTargetFunctionCoefficients();
		int[] expecteds = new int[] {
				-30, -20, 0, 0, 0, 1
		};
		Assert.assertArrayEquals(expecteds, results);
	}
	
	@Test
	public void testGetPivotColumn() throws SimplexPivotException {
		SimplexTableau tabl = this.getSimplexTableau();
		
		int pivotColumn = tabl.getPivotColumn();
		Assert.assertEquals(0, pivotColumn);
	}
	
	@Test
	public void testIsOptimal() {
		SimplexTableau tabl = this.getSimplexTableau();
		
		Assert.assertFalse(tabl.isOptimal());
	}
	
	/**
	 * Receive and validate a base result from the initial tableau.
	 */
	@Test
	public void testGetBaseResult() {
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
	public void testGetPivotRow() {
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
	
	@Test
	public void testPivotStepMakesPivotElementOne() {
		// XXX: This forged table might fail after the step is completely
		// implemented.
		float[][] cells = new float[2][2];
		cells[0][0] = 1;
		cells[0][1] = 2;
		cells[1][0] = 3;
		cells[1][1] = 4;
		
		SimplexPivotStep step = new SimplexPivotStep(cells,
				new PivotElement(1, 1));
		float[][] newCells = step.run();
		
		Assert.assertEquals(1, newCells[1][1], 0);
	}
	
	@Test
	public void testFirstSimplexStep() throws SimplexPivotException {
		SimplexTableau tabl = this.getSimplexTableau();
		float[][] cells = tabl.getCells();
		
		// Not a good unit test. This is not the test's subject, but we could
		// count this as an integration test, I guess.
		final PivotElement pElement = new PivotElement(tabl.getPivotColumn(),
				tabl.getPivotRow());
		SimplexPivotStep step = new SimplexPivotStep(cells, pElement);
		
		Assert.assertEquals(0, pElement.getX());
		Assert.assertEquals(0, pElement.getY());
		
		float[][] newCells = step.run();
		
		// Pivot element is 1.
		Assert.assertEquals(newCells[0][0], 1, 0);
		// Everything thing else in the column is 0. Starting
		// with 2, because 1 is 0 in the first place.
		Assert.assertEquals(newCells[0][2], 0, 0);
		Assert.assertEquals(newCells[0][3], 0, 0);
		
		// Now check the results of the circle rule.
		Assert.assertEquals(12, newCells[6][2], 0);
	}
	
	@Test
	public void testCompleteTableauIteratorRun() {
		SimplexTableau tabl = this.getSimplexTableau();
		
		int i = 0;
		SimplexTableau lastTabl = null;
		for(SimplexTableau newTabl : tabl) {
			i += 1;
			lastTabl = newTabl;
			
			if (i > 3) {
				Assert.fail("Looks life we have an endless loop here, chief.");
				break;
			}
		}
		
		// There are three runs necessary to get the optimal solution.
		Assert.assertEquals(2, i);
		
		HashMap<String, Float> actualResult = lastTabl.getBaseResult();
		HashMap<String, Float> expectedResult = new HashMap<String, Float>();
		
		expectedResult.put("x1", 10f);
		expectedResult.put("x2", 3f);
		expectedResult.put("s1", 0f);
		expectedResult.put("s2", 3f);
		expectedResult.put("s3", 0f);
		expectedResult.put("Z", 360f);
		
		Set<String> keys = expectedResult.keySet();
		for (String key : keys) {
			Assert.assertEquals(expectedResult.get(key),
					actualResult.get(key), 0);
		}
	}
	
	@Test
	public void testGetHorizontalVariableNames() {
		SimplexTableau tabl = this.getSimplexTableau();
		
		String[] horizontalVariableNames = tabl.getHorizontalVariableNames();
		String[] expectedhorizontalVariableNames = new String[] {
			"", "x1", "x2", "s1", "s2", "s3", "Z", "b"
		};
		Assert.assertArrayEquals(expectedhorizontalVariableNames,
				horizontalVariableNames);
	}
	
	@Test
	public void testGetTableData() {
		SimplexTableau tabl = this.getSimplexTableau();
		
		Object[][] tableData = tabl.getTableData();
		Object[][] expectedTableData = new Object[][] {
			{"s1", 1f, 0f, 1f, 0f, 0f, 0f, 10f},
			{"s2", 0f, 1f, 0f, 1f, 0f, 0f, 6f},
			{"s3", 2f, 4f, 0f, 0f, 1f, 0f, 32f},
			{"Z", -30f, -20f, 0f, 0f, 0f, 1f, 0f}
		};
		
		Assert.assertArrayEquals(expectedTableData, tableData);
	}
	
	@Test
	public void testVariableSwapRegression() {
		SimplexRestriction rest1 = new SimplexRestriction(new int[]{1}, 3);
		
		int[] baseVariables = new int[] {1};
		
		SimplexRestrictionSet restrictionSet = new SimplexRestrictionSet();
		restrictionSet.add(rest1);
		
		SimplexProblem problem = new SimplexProblemMock(baseVariables,
				restrictionSet);
		SimplexTableau tableau = new InitialSimplexTableauMock(problem);
		
		SimplexTableau firstTableau = tableau.iterator().next();
		Assert.assertTrue(firstTableau.isOptimal());
	}
}
