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
