package net.rdrei.simplex.lib;


/**
 * Low-level helper class to calculate the simplex step.
 * @author pascal
 */
public class SimplexPivotStep {
	/**
	 * Stores a copy(!) of the simplex tableau.
	 */
	private float[][] cells;
	/**
	 * Coordinates within the given matrix specifying the pivot element.
	 */
	private PivotElement pivotElement;
	private float pivotValue;
	
	/**
	 * Initializes the pivot step with a matrix and the coordinates to
	 * the pivot element.
	 * 
	 * <b>Note:</b> This works on a copy of the cells so the iterations can be
	 * contained in separate objects.
	 * 
	 * @param cells matrix of the simplex tableau.
	 * @param pivotElement 
	 */
	public SimplexPivotStep(float[][] cells, PivotElement pivotElement) {
		super();
		this.cells = copy2d(cells);
		this.pivotElement = pivotElement;
		this.pivotValue = 
			this.cells[pivotElement.getX()][pivotElement.getY()];
	}
	
	/**
	 * Execute the simplex pivot step and return the new tableau.
	 * As this is supposed to be a low-level helper, there is no
	 * input validation. Invalid choice of the pivot element may cause
	 * undefined behavior. Invalid matrix (0 size in one dimension) may cause
	 * undefined behavior. Changing the matrix after initialization of this
	 * class or multi-threading may lead to undefined behavior.
	 * 
	 * @return new tableau
	 */
	public float[][] run() {
		this.divideRow();
		this.createUnitVector();
		return this.cells;
	}
	
	/**
	 * Divides the pivot row through the pivot element.
	 */
	private void divideRow() {
		int row = this.pivotElement.getX();
		int columnCount = this.cells.length;
		
		for (int i = 0; i < columnCount; i += 1) {
			// We ignore the target column here, it's result is 0, anyway.
			this.cells[i][row] /= this.pivotValue;
		}
	}
	
	/**
	 * Create a unit vector in the pivot column.
	 */
	private void createUnitVector() {
		int column = this.pivotElement.getY();
		int pivotX = this.pivotElement.getX();
		// It's an even matrix and it must have a length of at least
		// 1x1, so this is safe to do.
		int rowCount = this.cells[0].length;
		
		for (int i = 0; i < rowCount; i += 1) {
			if (i == pivotX) {
				// Is already 1, no need to re-set.
				continue;
			}
			
			this.cells[column][i] = 0;
		}
	}
	
	/**
	 * Deep-copies a 2-dimensional array with variable-length sub-arrays.
	 * @param nums
	 * @return
	 */
	private static float[][] copy2d(float[][] nums) {
        float[][] copy = new float[nums.length][];

        for (int i = 0; i < copy.length; i++) {
                float[] member = new float[nums[i].length];
                System.arraycopy(nums[i], 0, member, 0, nums[i].length);
                copy[i] = member;
        }

        return copy;
	}	
}
