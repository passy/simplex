package net.rdrei.simplex.lib;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * An ordered list of restrictions building a restriction set.
 * @author pascal
 */
public class SimplexRestrictionSet implements Iterable<SimplexRestriction> {
	ArrayList<SimplexRestriction> list;

	public SimplexRestrictionSet() {
		super();
		
		this.list = new ArrayList<SimplexRestriction>();
	}
	
	public void add(SimplexRestriction restriction) {
		this.list.add(restriction);
	}

	@Override
	public Iterator<SimplexRestriction> iterator() {
		// This is really useless this way. We need a way to access the
		// coefficients and results easily, otherwise this whole class does
		// not really have a point.
		return list.iterator();
	}
}
