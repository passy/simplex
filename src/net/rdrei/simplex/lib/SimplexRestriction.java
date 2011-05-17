package net.rdrei.simplex.lib;

/**
 * Data structure for storing a standard maximum restriction.
 * 
 * @author pascal
 * 
 */
public class SimplexRestriction {
	/**
	 * Contains the coefficients for the base variables.
	 */
	private int[] baseVariableCoefficients;

	/**
	 * Stores the right-side result of the restriction.
	 */
	private int result;

	/**
	 * Creates a new standard maximum restriction. Parameters are explained
	 * on this example:
	 * 5x1 - 3x2 ≤ 23
	 * 
	 * @param baseVariableCoefficients [5, -3]
	 * @param result 23
	 */
	public SimplexRestriction(int[] baseVariableCoefficients, int result) {
		super();
		this.baseVariableCoefficients = baseVariableCoefficients;
		this.result = result;
	}

	public int[] getBaseVariableCoefficients() {
		return baseVariableCoefficients;
	}

	public int getResult() {
		return result;
	}

	public void setBaseVariableCoefficients(int[] baseVariableCoefficients) {
		this.baseVariableCoefficients = baseVariableCoefficients;
	}
	
	/**
	 * Returns an array of coefficients, interpreting the restriction as n-th
	 * restriction, where index specifies the n adding slack variables.
	 * 
	 * E.g. Base restriction: x1 - 3x2 ≤ 5 as 2nd restriction with the 
	 * identity matrix [[1, 0], [0, 1]].
	 * 
	 * With added slag variables the corresponding equation is
	 * x1 - 3x2 + 0s1 + 1s2 = 5
	 * 
	 * @param index The index of the restriction within the restriction
	 * system, 0-based.
	 */
	public int[] getEquationCoefficients(int index) {
		// TODO: Stub for test.
		return new int[] {1, 2, 3};
	}

	public void setResult(int result) {
		this.result = result;
	}
	
	public String toString() {
		int count = this.baseVariableCoefficients.length;
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < count; i += 1) {
			int coefficient = this.baseVariableCoefficients[i];
			
			builder.append(coefficient);
			builder.append('x');
			builder.append(i + 1);
			// Not for the last element
			if (i != (count - 1)) {
				builder.append(" + ");
			}
		}
		
		builder.append("≤ ");
		builder.append(this.result);
		return builder.toString();
	}
}
