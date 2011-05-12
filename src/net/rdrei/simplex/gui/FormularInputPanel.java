package net.rdrei.simplex.gui;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;

/**
 * @author pascal
 *
 */
public class FormularInputPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static final short defaultNumberOfVariables = 2;
	private static final short defaultNumberOfRestrictions = 2;
	
	private short numberOfVariables;
	private short numberOfRestrictions;
	
	private ArrayList<JSpinner> baseVariableSpinners;

	
	/// Constructor providing the default values for variables and restrictions.
	public FormularInputPanel() {
		this(defaultNumberOfVariables, defaultNumberOfRestrictions);
	}
	
	public FormularInputPanel(short numberOfVariables, short numberOfRestrictions) {
		this.numberOfVariables = numberOfVariables;
		this.numberOfRestrictions = numberOfRestrictions;
		
		this.baseVariableSpinners = new ArrayList<JSpinner>();
		
		buildLayout();
	}
	
	/**
	 * Builds the target equation's function head in respect to the number
	 * of variables.
	 * 
	 * Example output for this.numberOfVariables = 3:
	 * "Z(x1, x2, x3)"
	 */
	private String buildFnHead() {
		StringBuilder builder = new StringBuilder();
		builder.append("Z(");
		
		for (int i = 1; i <= this.numberOfVariables; i += 1) {
			// Unfortunately, 
			builder.append("x");
			builder.append(i);
			
			// Not for the last iteration.
			if (i != this.numberOfVariables) {
				builder.append(", ");
			}
		}
		
		builder.append(")");
		return builder.toString();
	}
	
	private void buildLayout() {
		GroupLayout groupLayout = new GroupLayout(this);
		
		JLabel lblHeadline = new JLabel("1. Schritt");
		lblHeadline.setFont(new Font("Dialog", Font.BOLD, 16));
		
		JLabel lblTargetfn = new JLabel("Zielfunktion");
		JLabel lblZx = new JLabel(this.buildFnHead());
		
		buildBaseVariableSpinners();
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(new Integer(1), null, null, new Integer(1)));
		
		JLabel lblX = new JLabel("x1 +");
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setModel(new SpinnerNumberModel(new Integer(1), null, null, new Integer(1)));
		
		JLabel lblX_1 = new JLabel("x2 +");
		
		JLabel lblRestriktionen = new JLabel("Restriktionen");
		
		JSpinner spinner_2 = new JSpinner();
		
		JLabel lblX_2 = new JLabel("x1 + ");
		
		JSpinner spinner_3 = new JSpinner();
		
		JSpinner spinner_4 = new JSpinner();
		
		JLabel lblX_3 = new JLabel("x2 ≤");
		
		JSpinner spinner_5 = new JSpinner();
		
		JLabel lblNichtnegativittsbedingung = new JLabel("Nicht-Negativitätsbedingung");
		
		JLabel lblXX = new JLabel("x1, x2 ≥ 0");
		
		JSpinner spinner_6 = new JSpinner();
		
		JLabel label = new JLabel("x1 + ");
		
		JSpinner spinner_7 = new JSpinner();
		
		JLabel label_1 = new JLabel("x2 ≤");
		
		JSpinner spinner_8 = new JSpinner();
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblHeadline)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblZx)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(12)
							.addComponent(lblX)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblX_1)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spinner_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblRestriktionen)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(spinner_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblX_2)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spinner_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblX_3)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spinner_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblNichtnegativittsbedingung)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(spinner_6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(12)
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
							.addGap(12)
							.addComponent(spinner_7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(12)
							.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
							.addGap(12)
							.addComponent(spinner_8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblTargetfn)
						.addComponent(lblXX))
					.addContainerGap(196, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblHeadline)
					.addGap(12)
					.addComponent(lblTargetfn)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblZx)
						.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblX)
						// .addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblX_1)
						.addComponent(spinner_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(lblRestriktionen)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(spinner_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblX_2)
						.addComponent(spinner_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblX_3)
						.addComponent(spinner_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(spinner_6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(1)
							.addComponent(label))
						.addComponent(spinner_7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(1)
							.addComponent(label_1))
						.addComponent(spinner_8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(lblNichtnegativittsbedingung)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblXX)
					.addContainerGap(49, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
	}

	/**
	 * Populates the list of spinners for the tar function equation.
	 */
	private void buildBaseVariableSpinners() {
		for (int i = 0; i < this.numberOfVariables; i += 1) {
			JSpinner spinner = new JSpinner();
			// Set the minimum and default value to 1, and step size to 1.
			SpinnerNumberModel model = new SpinnerNumberModel(1, 1, null, 1);
			spinner.setModel(model);
			this.baseVariableSpinners.add(spinner);
		}
	}
}
