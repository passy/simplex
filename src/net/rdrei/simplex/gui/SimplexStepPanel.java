package net.rdrei.simplex.gui;

import java.awt.LayoutManager;

import javax.swing.JPanel;

/**
 * A JPanel that is used within the MainWindow. It needs to implement
 * a certain set of methods to be able to continue to the next step.
 * 
 * @author pascal
 */
public abstract class SimplexStepPanel extends JPanel {

	private static final long serialVersionUID = -1867325162838167745L;
	
	public SimplexStepPanel() {
		super();
	}

	public SimplexStepPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}

	public SimplexStepPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
	}

	public SimplexStepPanel(LayoutManager layout) {
		super(layout);
	}
	
	/**
	 * Check if all requirements are given to proceed to the next step.
	 * e. g. this is not possible if the current tableau is optimal.
	 * @return
	 */
	public abstract boolean hasNextStep();
	public abstract void nextStep();
}
