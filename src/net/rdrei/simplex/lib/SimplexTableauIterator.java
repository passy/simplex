package net.rdrei.simplex.lib;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimplexTableauIterator implements Iterator<SimplexTableau> {
	
	/** Internal class that allows creation of a new tableau based on the
	 * current one.
	 */
	class ReplaceableSimplexTableau extends SimplexTableau {
		protected ReplaceableSimplexTableau(int restrictionCount,
				int problemVariableCount) {
			super(restrictionCount, problemVariableCount);
		}
		
		/** Creates a new tableau with the same internal state except
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
	}
	
	/**
	 * Reference to the previous simplex tableau.
	 */
	private ReplaceableSimplexTableau tableau;

	public SimplexTableauIterator(SimplexTableau tableau) {
		super();
		// Up-cast to internal class.
		this.tableau = (ReplaceableSimplexTableau) tableau;
	}

	@Override
	public boolean hasNext() {
		// We have a next tableau if the current is not optimal.
		return !this.tableau.isOptimal();
	}
	
	private PivotElement getPivotElement() throws NoSuchElementException {
		// Get the pivot element.
		int pivotX, pivotY;
		
		try {
			pivotX = this.tableau.getPivotColumn();
			pivotY = this.tableau.getPivotRow(pivotX);
		} catch (SimplexPivotException e) {
			throw new NoSuchElementException("Tableau is optimal.");
		}	
		
		return new PivotElement(pivotX, pivotY);
	}

	@Override
	/**
	 * Create the next tableau. Might raise an exception if not possible.
	 */
	public SimplexTableau next() {
		// Get the pivot element, might raise a NoSuchElementException.
		PivotElement pivotElement = this.getPivotElement();		
		// Create the pivot step element.
		SimplexPivotStep step = new SimplexPivotStep(this.tableau.getCells(),
				pivotElement);
		// Get the new cells.
		float[][] newCells = step.run();
		
		// Create the new tableau and make it the current for this iterator.
		SimplexTableau newTableau = 
			this.tableau.createNewInstanceFromCells(newCells);
		return newTableau;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
