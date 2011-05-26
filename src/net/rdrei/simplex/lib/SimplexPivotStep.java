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
	private final PivotElement pivotElement;
	private final float pivotValue;
	
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
		this.runCircleRule();
		this.divideRow();
		this.createUnitVector();
		return this.cells;
	}
	
	/**
	 * Divides the pivot row through the pivot element.
	 */
	private void divideRow() {
		int row = this.pivotElement.getY();
		int columnCount = this.cells.length;
		
		for (int i = 0; i < columnCount; i += 1) {
			// We ignore the target column here, it's result is 0, anyway.
			System.out.printf("Dividing (%d/%d/%f) through %f.\n", i, row, this.cells[i][row], this.pivotValue);
			this.cells[i][row] /= this.pivotValue;
		}
	}
	
	/**
	 * Create a unit vector in the pivot column.
	 */
	private void createUnitVector() {
		final int column = this.pivotElement.getY();
		final int pivotX = this.pivotElement.getX();
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
	 * I couldn't find an equivalent in English for this term, so this
	 * is a direct translation of the "Kreisregel". It is applied all elements
	 * of the tableau except for those in the pivot column or row. 
	 * 
	 * The complexity here is x * y where x and y are the dimensions of the
	 * given simplex tableau.
	 */
	private void runCircleRule() {
		final int columnCount = this.cells.length;
		// Row count is the size of the array's second dimension.
		final int rowCount = this.cells[0].length;
		// Using x and y for column and row here, starting - as always -
		// in the upper, left-hand edge (0, 0).
		for (int x = 0; x < columnCount; x += 1) {
			for (int y = 0; y < rowCount; y += 1) {
				if (x != this.pivotElement.getX() &&
						y != this.pivotElement.getY()) {
					// Don't apply the circle rule step on cells that
					// are calculated by downstream steps.
					this.applyCircleRuleForCell(x, y);
				}
			}
		}
	}
	
	/**
	 * Apply the circle rule to a specific element in the matrix. This
	 * is a low-level interface and will not work without proper previous
	 * checks (like x, y being not of pivot row or column).
	 * 
	 * a_{jp}^{\text{new}} = a_{jp} - \frac{a_{jk} \cdot aj_{ip}}{a_{ik}}
	 * 
	 * @param x
	 * @param y
	 */
	private void applyCircleRuleForCell(int x, int y) {
		// Extra variables are defined for reasons of clarity and
		// comprehensibility.
		final float old = this.cells[x][y];
		final int pivotX = this.pivotElement.getX();
		final int pivotY = this.pivotElement.getY();
		this.cells[x][y] = old - (
				(this.cells[pivotX][y] * this.cells[x][pivotY]) / 
				this.pivotValue
		);
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
