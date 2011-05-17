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

	public void setResult(int result) {
		this.result = result;
	}
}
