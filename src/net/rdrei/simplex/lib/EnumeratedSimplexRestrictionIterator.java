package net.rdrei.simplex.lib;

import java.util.Iterator;

/**
 * Iterator that wraps a normal simplex restriction iterator and adds an
 * index that a restriction for itself is not aware of. This allows
 * iterating over * restrictions with the correct index and thus the
 * correct slag variables.
 * 
 * @author pascal
 */
public class EnumeratedSimplexRestrictionIterator implements Iterator<EnumeratedSimplexRestriction> {
	
	private SimplexRestrictionSet restrictionSet;
	private int index = 0;
	private Iterator<SimplexRestriction> baseIterator;
	
	public EnumeratedSimplexRestrictionIterator(SimplexRestrictionSet restrictionSet) {
		this.restrictionSet = restrictionSet;
		this.baseIterator = restrictionSet.getList().iterator();
	}

	@Override
	public boolean hasNext() {
		return this.baseIterator.hasNext();
	}

	@Override
	public EnumeratedSimplexRestriction next() {
		SimplexRestriction restriction = this.baseIterator.next();
		EnumeratedSimplexRestriction erestriction = 
			new EnumeratedSimplexRestriction(restriction, this.index,
				this.restrictionSet.size());
		
		this.index += 1;
		return erestriction;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
	
}
