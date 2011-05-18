package net.rdrei.simplex.lib;

/**
 * Wraps a SimplexRestriction object and provides index and complete amount
 * of restrictions in the current set. These objects are mostly used
 * temporarily in Iterators.
 * @author pascal
 *
 */
public class EnumeratedSimplexRestriction {
	private int index;
	private SimplexRestriction restriction;
	private int restrictionCount;
	
	/**
	 * @param restriction The SimplexRestriction object to be wrapped.
	 * @param index 0-based index of the restriction within the set.
	 * @param restrictionCount Total count of restrictions.
	 */
	public EnumeratedSimplexRestriction(SimplexRestriction restriction,
			int index, int restrictionCount) {
		super();
		this.restriction = restriction;
		this.index = index;
		this.restrictionCount = restrictionCount;
	}
	
	/**
	 * Returns the equation coefficients respecting the position of the
	 * equation and the index of it in the complete restriction set.
	 * @return array of integers (very likely to change to floats)
	 */
	public int[] getEquationCoefficients() {
		return this.restriction.getEquationCoefficientsForIndex(
				this.index, this.restrictionCount);
	}
	
	public int getIndex() {
		return index;
	}

	public SimplexRestriction getRestriction() {
		return restriction;
	}

	public int getRestrictionCount() {
		return restrictionCount;
	}

	public int getResult() {
		return this.restriction.getResult();
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setRestriction(SimplexRestriction restriction) {
		this.restriction = restriction;
	}

	public void setRestrictionCount(int restrictionCount) {
		this.restrictionCount = restrictionCount;
	}
}
