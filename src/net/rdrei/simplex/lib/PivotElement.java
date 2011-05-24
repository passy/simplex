package net.rdrei.simplex.lib;

/**
 * Simple data structure holding an X and a Y element.
 * 
 * <b>Note:</b> Alternative would have been awt.Point. That class, however,
 * stores x and y as doubles and requires additional casts, not to mention
 * the overhead caused by that.
 * 
 * @author pascal
 */
public class PivotElement {
	private int x;
	private int y;
	public PivotElement(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
}
