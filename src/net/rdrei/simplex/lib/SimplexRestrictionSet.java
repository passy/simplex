package net.rdrei.simplex.lib;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * An ordered list of restrictions building a restriction set.
 * @author pascal
 */
public class SimplexRestrictionSet implements Iterable<EnumeratedSimplexRestriction> {
	ArrayList<SimplexRestriction> list;

	public SimplexRestrictionSet() {
		super();
		
		this.list = new ArrayList<SimplexRestriction>();
	}
	
	public void add(SimplexRestriction restriction) {
		this.checkCount(restriction);
		this.list.add(restriction);
	}
	
	/**
	 * Check whether the given SimplexRestriction can be stored in this
	 * set, i.e. the variable count matches the previous restrictions'.
	 */
	private void checkCount(SimplexRestriction restriction) {
		if (this.list.size() == 0) {
			return;
		}
		
		if (this.list.get(0).getBaseVariablesCount() !=
			restriction.getBaseVariablesCount()) {
			throw new RuntimeException("Given restriction does not match " +
					"the expected count of base variables for this set.");
		}
	}
	
	public int size() {
		return this.list.size();
	}
	
	public ArrayList<SimplexRestriction> getList() {
		return list;
	}

	@Override
	public Iterator<EnumeratedSimplexRestriction> iterator() {
		return new EnumeratedSimplexRestrictionIterator(this);
	}
}
