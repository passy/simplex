package net.rdrei.simplex.lib;

/**
 * Exception thrown during pivot operations like determining pivot row, column
 * or executing the pivot step.
 * 
 * @author pascal
 */
public class SimplexPivotException extends Exception {
	private static final long serialVersionUID = -7998485637934538369L;

	public SimplexPivotException() {
		super();
	}

	public SimplexPivotException(String message) {
		super(message);
	}

}
