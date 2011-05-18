package net.rdrei.simplex.test;

import junit.framework.TestCase;
import net.rdrei.simplex.lib.EnumeratedSimplexRestriction;
import net.rdrei.simplex.lib.SimplexRestriction;

import org.junit.Test;


public class SimplexRestrictionTestCase {
	
	@Test
	public void simplexEquationAddsSlagVarsFirst() {
		// 1. -2x1 + 3x2 ≤ 5
		SimplexRestriction restrict1 = new SimplexRestriction(
			new int[] {-2, 3}, 5
		);
		int coeffs[] = restrict1.getEquationCoefficientsForIndex(0, 2);
		TestCase.assertEquals(coeffs.length, 4);
		
		TestCase.assertEquals(coeffs[0], -2);
		TestCase.assertEquals(coeffs[1], 3);
		TestCase.assertEquals(coeffs[2], 1);
		TestCase.assertEquals(coeffs[3], 0);
	}
	
	@Test
	public void simplexEquationAddSlagVarsSecond() {
		// 2. x2 ≤ 1
		SimplexRestriction restrict1 = new SimplexRestriction(
			new int[] {0, 1}, 1
		);
		int coeffs[] = restrict1.getEquationCoefficientsForIndex(1, 2);
		TestCase.assertEquals(coeffs.length, 4);
		
		TestCase.assertEquals(coeffs[0], 0);
		TestCase.assertEquals(coeffs[1], 1);
		TestCase.assertEquals(coeffs[2], 0);
		TestCase.assertEquals(coeffs[3], 1);
	}

	@Test
	public void simplexEquationAddSlagVarsThirdOfThree() {
		// 2. 2x1 + x2 ≤ 5
		SimplexRestriction restrict = new SimplexRestriction(
			new int[] {2, 1}, 5
		);
		int coeffs[] = restrict.getEquationCoefficientsForIndex(2, 3);
		TestCase.assertEquals(coeffs.length, 5);
		
		TestCase.assertEquals(coeffs[0], 2);
		TestCase.assertEquals(coeffs[1], 1);
		TestCase.assertEquals(coeffs[2], 0);
		TestCase.assertEquals(coeffs[3], 0);
		TestCase.assertEquals(coeffs[4], 1);
	}
	
	@Test
	public void enumeratedSimplexRestriction() {
		SimplexRestriction restrict = new SimplexRestriction(
			new int[] {2, 1}, 5
		);
		EnumeratedSimplexRestriction erestrict = 
			new EnumeratedSimplexRestriction(restrict, 2, 3);
		
		int coeffs[] = erestrict.getEquationCoefficients();
		// Same as above.
		TestCase.assertEquals(coeffs.length, 5);
		
		TestCase.assertEquals(coeffs[0], 2);
		TestCase.assertEquals(coeffs[1], 1);
		TestCase.assertEquals(coeffs[2], 0);
		TestCase.assertEquals(coeffs[3], 0);
		TestCase.assertEquals(coeffs[4], 1);
	}
}
