package net.rdrei.simplex.lib;

import java.util.Iterator;

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
		throw new RuntimeException("remove() is not implemented for " + 
				"EnumeratedSimplexRestrictionInterator");
	}
	
}
