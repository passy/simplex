package net.rdrei.simplex.lib;

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
}
