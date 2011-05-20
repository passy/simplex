package net.rdrei.simplex.lib;

public class SimplexTableau {
	protected float[][] cells;
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
	}
	
	protected int getColumnCount() {
		return columnCount;
	}

	protected int getRowCount() {
		return rowCount;
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
