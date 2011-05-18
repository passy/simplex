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
		this.list.add(restriction);
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
