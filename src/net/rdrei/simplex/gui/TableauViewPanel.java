package net.rdrei.simplex.gui;

import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.rdrei.simplex.lib.SimplexTableau;
import java.awt.Font;
import net.miginfocom.swing.MigLayout;

public class TableauViewPanel extends SimplexStepPanel {
	private static final long serialVersionUID = -5784588577474570460L;
	private Iterator<SimplexTableau> iterator;
	private SimplexTableau tableau;
	
	/**
	 * Counter of the tableau needed for the heading.
	 */
	private int index = 0;
	
	/**
	 * Create the panel.
	 * @wbp.parser.constructor
	 */
	public TableauViewPanel(SimplexTableau tableau) {
		this.iterator = tableau.iterator();
		this.tableau = tableau;
		buildLayout();
	}
	
	public TableauViewPanel(SimplexTableau tableau, int index) {
		this.index = index;
		this.iterator = tableau.iterator();
		this.tableau = tableau;
		buildLayout();
	}
	
	private void buildLayout() {
		setLayout(new MigLayout("", "[10px][grow]", "[48px][][]"));
		this.buildHeading();
		this.buildTable();
		this.buildBaseResult();
	}
	
	private String getHeading() {
		if (this.index == 0) {
			return "Ausgangs-Tableau";
		}
		
		return index + ". Tableau";
	}
	
	private void buildHeading() {
		JLabel label = new JLabel(this.getHeading());
		label.setFont(new Font("Dialog", Font.BOLD, 14));
		add(label, "cell 1 0,alignx left,aligny center");
	}
	
	/**
	 * Creates a JTable and adds it via a ScrollPane to the layout.
	 */
	private void buildTable() {
		JTable table = new JTable(this.tableau.getTableData(),
				this.tableau.getHorizontalVariableNames());
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		
		add(scrollPane, "cell 1 1,alignx left,aligny top");
	}
	
	private void buildBaseResult() {
		String labelStr = "Zulässige Basislösung: " + this.tableau.getBaseResult();
		
		JLabel label = new JLabel(labelStr);
		add(label, "cell 1 2");
		
		if (this.tableau.isOptimal()) {
			add(new JLabel("Die Lösung ist optimal."), "cell 1 3");
		}
	}
	
	@Override
	public boolean hasNextStep() {
		return iterator.hasNext();
	}

	@Override
	public SimplexStepPanel nextStep() {
		SimplexTableau newTableau = iterator.next();
		SimplexStepPanel panel = new TableauViewPanel(newTableau,
				this.index + 1);
		return panel;
	}
}
