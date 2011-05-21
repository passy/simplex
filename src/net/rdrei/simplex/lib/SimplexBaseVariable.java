package net.rdrei.simplex.lib;

/**
 * Data structure to keep track of variables in the tableau's base and verify
 * if they are slag variables or problem variables.
 */
public class SimplexBaseVariable {
	/**
	 * Index of the variable, 0-based. So x1 got an index of 0, x2 of 1 and
	 * so on.
	 */
	private int index;
	private boolean isSlag = false;
	private final char slagSymbol = 's';
	private final char problemSymbol = 'x';
	
	public SimplexBaseVariable(int index, boolean isSlag) {
		super();
		this.setIndex(index);
		this.setSlag(isSlag);
	}

	public void setSlag(boolean isSlag) {
		this.isSlag = isSlag;
	}

	public boolean isSlag() {
		return isSlag;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}
	
	public String toString() {
		char symbol;
		if (this.isSlag) {
			symbol = slagSymbol;
		} else {
			symbol = problemSymbol;
		}
		
		return "" + symbol + (this.index + 1);
	}
}
