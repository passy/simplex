package net.rdrei.simplex.gui;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

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
		buildLayoutWidgets();
	}
	
	/**
	 * Builds the target equation's function head in respect to the number
	 * of variables.
	 * 
	 * Example output for this.numberOfVariables = 3:
	 * "Z(x1, x2, x3) = "
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
		
		builder.append(") = ");
		return builder.toString();
	}
	
	private void buildLayout() {
		// There must be two columns per variable plus an additional column.
		final int columnCount = (1 + (this.numberOfVariables * 2)) * 2;
		ColumnSpec[] cspec = new ColumnSpec[columnCount];
		
		for (int i = 0; i < columnCount; i += 2) {
			cspec[i] = FormFactory.RELATED_GAP_COLSPEC;
			cspec[i + 1] = FormFactory.DEFAULT_COLSPEC;
		}
		
		// TODO: Make this rowCount depend on something else than a guess.
		final int rowCount = 5 * 2;
		RowSpec[] rspec = new RowSpec[rowCount];
		
		for (int i = 0; i < rowCount; i += 2) {
			rspec[i] = FormFactory.RELATED_GAP_ROWSPEC;
			rspec[i + 1] = FormFactory.DEFAULT_ROWSPEC;
		}
		
		FormLayout layout = new FormLayout(cspec, rspec);
		setLayout(layout);
	}
	
	private void buildLayoutWidgets() {
		
		// Headline
		{
			JLabel lblHeadline = new JLabel("1. Schritt");
			lblHeadline.setFont(new Font("Dialog", Font.BOLD, 16));
			add(lblHeadline, "2, 2");
		}
		
		// Target function start
		{
			JLabel lblTargetfn = new JLabel("Zielfunktion");
			JLabel lblZfn = new JLabel(this.buildFnHead());
			add(lblTargetfn, "2, 4");
			add(lblZfn, "2, 6");
		}
		
		// Target function variables
		buildBaseVariableSpinners();
		for(int i = 0; i < this.baseVariableSpinners.size(); i += 1) {
			JSpinner spinner = this.baseVariableSpinners.get(i);
			String varName = "x" + (i + 1);
			JLabel varLabel = new JLabel(varName);
			// The coordinates require that the above column count
			// calculations were correct. Otherwise we might run into
			// a NullPointer condition here.
			int xCoord = (4 + 4 * (i));
			add(spinner, String.format("%d, 6", xCoord));
			add(varLabel, String.format("%d, 6", xCoord + 2));
		}
		
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
