package net.rdrei.simplex.lib;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimplexTableauIterator implements Iterator<SimplexTableau> {
	
	/**
	 * Reference to the previous simplex tableau.
	 */
	private SimplexTableau tableau;

	public SimplexTableauIterator(SimplexTableau tableau) {
		super();
		// Up-cast to internal class.
		this.tableau = (SimplexTableau) tableau;
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
		this.tableau = newTableau;
		
		return newTableau;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
