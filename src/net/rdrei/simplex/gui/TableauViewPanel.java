package net.rdrei.simplex.gui;

import java.util.Iterator;

import javax.swing.JTable;

import net.rdrei.simplex.lib.SimplexTableau;

public class TableauViewPanel extends SimplexStepPanel {
	private static final long serialVersionUID = -5784588577474570460L;
	private JTable table;
	private Iterator<SimplexTableau> iterator;
	/**
	 * Create the panel.
	 */
	public TableauViewPanel(SimplexTableau tableau) {
		table = new JTable();
		System.out.println(tableau.toString());
		this.iterator = tableau.iterator();
		add(table);
	}

	@Override
	public boolean hasNextStep() {
		return iterator.hasNext();
	}

	@Override
	public SimplexStepPanel nextStep() {
		SimplexTableau newTableau = iterator.next();
		SimplexStepPanel panel = new TableauViewPanel(newTableau);
		return panel;
	}
}
