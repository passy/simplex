package net.rdrei.simplex.lib;

/**
 * Data structure to keep track of variables in the tableau and whether they
 * are in the base or not.
 */
public class SimplexVariable implements Comparable<SimplexVariable> {
	/**
	 * Index of the variable, 0-based. So x1 got an index of 0, x2 of 1 and
	 * so on.
	 */
	private int index;
	private boolean isSlag = false;
	/**
	 * The position of the element, 0-based. If it's in the base for the 
	 * second restriction, it' 1.
	 */
	private int position;

	private final char problemSymbol = 'x';

	private final char slagSymbol = 's';
	public SimplexVariable(int index, int position, boolean isSlag) {
		super();
		this.setIndex(index);
		this.setSlag(isSlag);
	}
	@Override
	public int compareTo(SimplexVariable o) {
		return this.getPosition() - o.getPosition();
	}
	
	public int getIndex() {
		return index;
	}

	public int getPosition() {
		return position;
	}

	public boolean isSlag() {
		return isSlag;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
	public void setSlag(boolean isSlag) {
		this.isSlag = isSlag;
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
