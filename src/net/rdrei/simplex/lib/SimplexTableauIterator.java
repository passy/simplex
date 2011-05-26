package net.rdrei.simplex.lib;

import java.util.Iterator;

public class SimplexTableauIterator implements Iterator<SimplexTableau> {
	
	/**
	 * Reference to the previous simplex tableau.
	 */
	private SimplexTableau tableau;

	public SimplexTableauIterator(SimplexTableau tableau) {
		super();
		this.tableau = tableau;
	}

	@Override
	public boolean hasNext() {
		// We have a next tableau if the current is not optimal.
		return !this.tableau.isOptimal();
	}

	@Override
	public SimplexTableau next() {
		// Create the next tableau.
		return null;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
