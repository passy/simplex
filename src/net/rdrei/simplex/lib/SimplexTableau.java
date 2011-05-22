package net.rdrei.simplex.lib;

import java.util.Arrays;
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
	protected SimplexVariable[] variables;
	protected int columnCount;
	protected int rowCount;
	protected int restrictionCount;
	protected int problemVariableCount;

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
	
	/**
	 * Shortcut to check for the optimum criteria.
	 */
	public boolean isOptimal() {
		return this.getPivotColumn() == -1;
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
	
	protected void orderVariables() {
		// This is obviously not thread-safe as this stores the information
		// unlocked in the object context.
		Arrays.sort(this.variables);
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
}
