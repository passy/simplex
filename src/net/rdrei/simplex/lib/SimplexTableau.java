package net.rdrei.simplex.lib;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

public class SimplexTableau implements Iterable<SimplexTableau> {
	/**
	 * Stores all the field values in the complete tableau, except for the 
	 * q_i value as it's not really part of the table.
	 */
	protected float[][] cells;
	protected int columnCount;

	protected int problemVariableCount;
	protected int restrictionCount;
	protected int rowCount;
	/**
	 * Stores the variables in the base of the current tableau.
	 */
	protected SimplexVariable[] variables;
	protected SimplexTableau(int restrictionCount, int problemVariableCount) {
		super();
		
		// Rows is one for each restriction plus the target function
		// Columns is one for each base variable + the not-base variables + 
		// target column and the equation's right side.
		this.problemVariableCount = problemVariableCount;
		this.restrictionCount = restrictionCount;
		this.rowCount = restrictionCount + 1;
		this.columnCount = problemVariableCount + restrictionCount + 2;
		this.cells = new float[this.columnCount][this.rowCount];
		this.variables =
			new SimplexVariable[this.problemVariableCount +
	    		                this.restrictionCount];
	}

	/**
	 * Returns the base result vector of the current tableau.
	 * @return key is the name of the variable (like x1, s2, Z, ...) and
	 * the value is the corresponding value of the solution vector.
	 */
	public HashMap<String, Float> getBaseResult() {
		// We need the variables to be ordered by there index in order
		// to find out which are in the base and which aren't.
		this.orderVariables();
		// Set the initial capacity to the column count without the b-column.
		HashMap<String, Float> result =
			new HashMap<String, Float>(this.columnCount - 1);
		
		// Iterate through the base and result row.
		for (int i = 0; i < this.rowCount; i += 1) {
			String key;
			// Access the last column with the current row index.
			float value = this.cells[this.columnCount - 1][i];
			if (i < (this.rowCount - 1)) {
				// The base variables
				key = this.variables[i].toString();				
			} else {
				// The target function.
				key = "Z";
			}
			
			result.put(key, value);
		}
		
		// Iterate through the not-base variables which are 0.
		for (int i = 0; i < this.problemVariableCount; i += 1) {
			String key;
			key = this.variables[this.restrictionCount + i].toString();
			result.put(key, 0f);			
		}
		
		return result;
	}
	
	public float[][] getCells() {
		return cells;
	}

	/**
	 * Get the column with the highest negative coefficient in the target
	 * function. If there is no negative coefficient, -1 is returned and the
	 * tableau is optimal.
	 * @return positive index (0-based) or -1 if tableau is optimal.
	 * @throws SimplexPivotException 
	 */
	public int getPivotColumn() throws SimplexPivotException {
		int min = -1;
		int row = this.rowCount - 1;
		// The relevant coefficients include the base variables and not-base
		// variables, so all values except for the last two.
		for (int i = 0; i < this.columnCount - 2; i += 1) {
			float value = this.cells[i][row];
			if (value < 0) {
				if (min == -1) {
					min = i;
				} else if (this.cells[min][row] > value) {
					min = i;					
				}
			}
		}
		
		if (min == -1) {
			throw new SimplexPivotException("There is no negative element " +
					"within the target function's coefficient. The tableau" +
					"is optimal.");
		}
		
		return min;
	}
	
	/**
	 * Access the pivot row without prior knowledge of the pivot column.
	 * For performance reasons, it's advised you use getPivotRow(int)
	 * instead.
	 * @return
	 * @throws SimplexPivotException 
	 */
	public int getPivotRow() throws SimplexPivotException {
		int pivotColumn = this.getPivotColumn();
		return getPivotRow(pivotColumn);
	}
	
	/**
	 * Find the 'bottleneck' condition for the tableau on hand.
	 * There is an overloaded method over this, not requiring the pivotColumn
	 * to be provided.
	 * @param pivotColumn
	 * @return integer, 0-based index of the pivot row
	 * @throws SimplexPivotException 
	 */
	public int getPivotRow(int pivotColumn) throws SimplexPivotException {
		int pivotRow = -1;
		float minimum = 0;
		
		// Build q_i = k_i / a_{ik} for each element of the pivot column.
		for (int i = 0; i < this.restrictionCount; i += 1) {
			// Variable names correspond to column names x being the pivot
			// column's value while b is the equation's right side value.
			float x = this.cells[pivotColumn][i];
			float b = this.cells[this.columnCount - 1][i];
			float q;
			
			// To pass the bottleneck condition, the element must be positive
			// and must not be 0.
			if (x > 0 && b > 0) {
				q = b / x;
				
				// Minimum defaults to 0, which can never be reached.
				if (minimum == 0 || q < minimum) {
					// Set new minimum and pivot row.
					minimum = q;
					pivotRow = i;
				}
			}
		}
		
		// No element matched, raise exception.
		if (pivotRow == -1) {
			throw new SimplexPivotException("None of the rows in the " +
					"given pivot column fulfills the " +
					"bottleneck restriction.");
		}
		
		return pivotRow;
	}
	
	public PivotElement getPivotElement() throws SimplexPivotException {
		int pivotX = this.getPivotColumn();
		int pivotY = this.getPivotRow(pivotX);
		
		return new PivotElement(pivotX, pivotY);
	}
	
	protected int getRowCount() {
		return rowCount;
	}
	
	/**
	 * Shortcut to check for the optimum criteria.
	 */
	public boolean isOptimal() {
		try {
			this.getPivotColumn();
		} catch (SimplexPivotException e) {
			return true;
		}
		return false;
	}
	
	protected void orderVariables() {
		// This is obviously not thread-safe as this stores the information
		// unlocked in the object context.
		Arrays.sort(this.variables);
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		// Some separation line
		for (int i = 0; i < this.columnCount * 8; i += 1) {
			result.append('-');
		}
		result.append('\n');
		
		for (int i = 0; i < this.rowCount; i += 1) {
			for (int j = 0; j < this.columnCount; j += 1) {
				result.append(this.cells[j][i]);
				result.append('\t');
			}
			result.append('\n');
		}
		
		// Another separation line.
		for (int i = 0; i < this.columnCount * 8; i += 1) {
			result.append('-');
		}
		
		return result.toString();
	}
	
	/**
	 * Creates a new tableau with the same internal state except
	 * for the cells
	 * @param cells New matrix that must match the previous matrix'
	 * dimensions.
	 */
	SimplexTableau createNewInstanceFromCells(float[][] cells) {
		SimplexTableau tableau = new SimplexTableau(this.restrictionCount,
				this.problemVariableCount);
		
		tableau.cells = cells;
		return tableau;
	}

	@Override
	public Iterator<SimplexTableau> iterator() {
		return new SimplexTableauIterator(this);
	}
}
