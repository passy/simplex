package net.rdrei.simplex.gui;

import javax.swing.JPanel;
import javax.swing.JTable;

public class TableauViewPanel extends JPanel {
	private static final long serialVersionUID = -5784588577474570460L;
	private JTable table;

	/**
	 * Create the panel.
	 */
	public TableauViewPanel() {
		
		table = new JTable();
		add(table);
	}
}
