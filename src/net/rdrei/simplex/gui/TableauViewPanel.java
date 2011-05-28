package net.rdrei.simplex.gui;

import javax.swing.JPanel;
import javax.swing.JTable;

import net.rdrei.simplex.lib.SimplexTableau;

public class TableauViewPanel extends JPanel {
	private static final long serialVersionUID = -5784588577474570460L;
	private JTable table;
	private SimplexTableau tableau;

	/**
	 * Create the panel.
	 */
	public TableauViewPanel(SimplexTableau tableau) {
		table = new JTable();
		this.tableau = tableau;
		add(table);
	}
}
