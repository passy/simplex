package net.rdrei.simplex.lib;

import java.util.HashMap;

public class SimplexTableau {
	/**
	 * Stores all the field values in the complete tableau, except for the 
	 * q_i value as it's not really part of the table.
	 */
	protected float[][] cells;
	/**
	 * Stores the variables in the base of the current tableau.
	 */
	protected SimplexBaseVariable[] baseVariables;
	int columnCount;
	int rowCount;

	protected SimplexTableau(int restrictionCount, int baseVariableCount) {
		super();
		
		// Rows is one for each restriction plus the target function
		// Columns is one for each base variable + the not-base variables + 
		// target column and the equation's right side.
		this.rowCount = restrictionCount + 1;
		this.columnCount = baseVariableCount + restrictionCount + 2;
		this.cells = new float[this.columnCount][this.rowCount];
		this.baseVariables = new SimplexBaseVariable[this.rowCount];
	}
	
	protected int getColumnCount() {
		return columnCount;
	}

	protected int getRowCount() {
		return rowCount;
	}
	
	/**
	 * Get the column with the highest negative coefficient in the target
	 * function. If there is no negative coefficient, -1 is returned and the
	 * tableau is optimal.
	 * @return positive index (0-based) or -1 if tableau is optimal.
	 */
	public int getPivotColumn() {
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
		
		return min;
	}
	
	public boolean isOptimal() {
		return this.getPivotColumn() == -1;
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
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
		
		for (int i = 0; i < this.columnCount * 8; i += 1) {
			result.append('-');
		}
		
		return result.toString();
	}
	
	/**
	 * Returns the base result vector of the current tableu.
	 * @return key is the name of the variable (like x1, s2, Z, ...) and
	 * the value is the corresponding value of the solution vector.
	 */
	public HashMap<String, Float> getBaseResult() {
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
				key = this.baseVariables[i].toString();				
			} else {
				// The target function.
				key = "Z";
			}
			
			result.put(key, value);
		}
		
		// XXX: This does not work. I have no good way to figure out which
		// variables are NOT in the base and are 0. The list should probably
		// better contain all variables, not only those from the base, and be
		// orderes so that the first (rowCount - 1) elements are in the base
		// and the correct order. Alternatively, the order could be an
		// attribute.
		
		return result;
	}
}
