package net.rdrei.simplex.lib;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

public class SimplexTableauIterator implements Iterator<SimplexTableau> {
	
	/**
	 * Reference to the previous simplex tableau.
	 */
	private SimplexTableau tableau;
	
	private static Logger LOGGER = 
		Logger.getLogger("net.rdrei.simplex.lib.SimplexTableauIterator");

	public SimplexTableauIterator(SimplexTableau tableau) {
		super();
		// Up-cast to internal class.
		this.tableau = tableau;
	}

	@Override
	public boolean hasNext() {
		// We have a next tableau if the current is not optimal.
		return !this.tableau.isOptimal();
	}

	@Override
	/**
	 * Create the next tableau. Might raise an exception if not possible.
	 */
	public SimplexTableau next() {
		PivotElement pivotElement;
		try {
			// Get the pivot element and stop the iterator
			// if we can't find the pivot element.
			pivotElement = this.tableau.getPivotElement();		
		}  catch (SimplexPivotException e) {
			LOGGER.warning("Encountered exception while retrieving next " +
					"tableau: " + e.toString());
			throw new NoSuchElementException(e.getMessage());
		}
		
		// Create the pivot step element.
		SimplexPivotStep step = new SimplexPivotStep(this.tableau.getCells(),
				pivotElement);
		// Get the new cells.
		float[][] newCells = step.run();
		
		// Create the new tableau and make it the current for this iterator.
		SimplexTableau newTableau = 
			this.tableau.createNewInstanceFromCells(newCells);
		
		// Swap variables according to pivot element.
		try {
			newTableau.swapVariable(pivotElement.getX(), pivotElement.getY());
		} catch(SimplexPivotException e) {
			e.printStackTrace();
			throw new NoSuchElementException("Tableau can't be optimized!");
		}
		this.tableau = newTableau;
		
		return newTableau;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
