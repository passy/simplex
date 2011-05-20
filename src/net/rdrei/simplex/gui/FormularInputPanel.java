package net.rdrei.simplex.gui;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import net.rdrei.simplex.lib.SimplexProblem;
import net.rdrei.simplex.lib.SimplexRestriction;
import net.rdrei.simplex.lib.SimplexRestrictionSet;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * @author pascal
 *
 */
public class FormularInputPanel extends JPanel implements SimplexProblem {
	private static final short defaultNumberOfRestrictions = 2;
	
	private static final short defaultNumberOfVariables = 2;
	private static final long serialVersionUID = 1L;
	
	private JSpinner[] baseVariableSpinners;
	private int numberOfRestrictions;
	
	private int numberOfVariables;
	private JSpinner[][] restrictionVariableSpinners;

	/// Constructor providing the default values for variables and restrictions.
	public FormularInputPanel() {
		this(defaultNumberOfVariables, defaultNumberOfRestrictions);
	}
	
	public FormularInputPanel(int numberOfVariables, int numberOfRestrictions) {
		this.numberOfVariables = numberOfVariables;
		this.numberOfRestrictions = numberOfRestrictions;
		
		this.baseVariableSpinners = new JSpinner[numberOfVariables];
		this.restrictionVariableSpinners = 
			new JSpinner[numberOfRestrictions][numberOfVariables + 1];
		
		buildLayout();
		buildLayoutWidgets();
	}
	
	/**
	 * Populates the list of spinners for the target function equation.
	 */
	private void buildBaseVariableSpinners() {
		// Create one spinner for each coefficient.
		for (int i = 0; i < this.numberOfVariables; i += 1) {
			JSpinner spinner = new JSpinner();
			// Set the default value to 1, and step size to 1.
			SpinnerNumberModel model = new SpinnerNumberModel(1, null,
					null, 1);
			spinner.setModel(model);
			this.baseVariableSpinners[i] = spinner;
		}
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
			builder.append('x');
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
		// There must be two columns per variable plus two additional columns.
		final int columnCount = (2 + (this.numberOfVariables * 2)) * 2;
		ColumnSpec[] cspec = new ColumnSpec[columnCount];
		
		for (int i = 0; i < columnCount; i += 2) {
			cspec[i] = FormFactory.RELATED_GAP_COLSPEC;
			cspec[i + 1] = FormFactory.DEFAULT_COLSPEC;
		}
		
		// We have 6 static rows and one for each restrictions.
		final int rowCount = (6 + this.numberOfRestrictions) * 2;
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
		// Iterates through all created spinners and adds them to the layout
		// with a label afterwards showing their variable name.
		{
			int spinnerCount = this.baseVariableSpinners.length;
			for(int i = 0; i < spinnerCount; i += 1) {
				JSpinner spinner = this.baseVariableSpinners[i];
				// The coordinates require that the above column count
				// calculations were correct. Otherwise we might run into
				// a NullPointer condition here.
				int xCoord = (4 + 4 * i);
				add(spinner, String.format("%d, 6", xCoord));
				
				String varName = "x" + (i + 1);
				
				// Plus signs as connector if it's not the last one.
				if (i < spinnerCount - 1) {
					varName += " +";
				}
				JLabel varLabel = new JLabel(varName);
				add(varLabel, String.format("%d, 6", xCoord + 2));
			}
		}
		
		// Restrictions
		
		buildRestrictionVariableSpinners();
		{
			JLabel lblRestrictions = new JLabel("Restriktionen");
			add(lblRestrictions, "2, 8");
			
			// Create a new row for each restriction with
			// (numberOfVariables + 1) in it.
			for (int i = 0; i < this.numberOfRestrictions; i += 1) {
				for (int j = 0; j <= this.numberOfVariables; j += 1) {
					// Attach the spinner first.
					JSpinner spinner = this.restrictionVariableSpinners[i][j];
					int xCoord, yCoord;
					
					xCoord = 2 + (j * 4);
					yCoord = 10 + (i * 2);
					add(spinner, String.format("%d, %d", xCoord, yCoord));
					
					// The last iteration does not have a label attached.
					if (j == this.numberOfVariables) {
						break;
					}
					// Add the label, depending on whether this is the last
					// spinner in the row (right side) or not.
					String varText;
					if (j == this.numberOfVariables - 1) {
						// Second to last variable, so we need the <= sign in.
						varText = "x" + (j + 1) + " ≤ ";						
					} else {
						varText = "x" + (j + 1) + " + ";
					}
					
					JLabel label = new JLabel(varText);
					// Override xCoord, keep yCoord.
					xCoord = 4 + (j * 4);
					add(label, String.format("%d, %d", xCoord, yCoord));
				}
			}
		}
		
		{
			// Not negativity restriction
			JLabel lblNotNegative = new JLabel("NNB");
			int yCoord = (10 + (2 * this.numberOfRestrictions));
			add(lblNotNegative, String.format("2, %d", yCoord));
			
			JLabel lblNotNegativeText = new JLabel(this.buildNNR());
			yCoord += 2;
			add(lblNotNegativeText, String.format("2, %d", yCoord));
		}
	}

	/**
	 * Create a String for the given count of variables representing
	 * the not negativity restriction.
	 * e.g. x1, x2, x3 ≥ 0
	 */
	private String buildNNR() {
		StringBuilder builder = new StringBuilder();
		for (int i = 1; i <= this.numberOfVariables; i += 1) {
			builder.append('x');
			builder.append(i);
			
			// For all but the last iteration
			if (i != this.numberOfVariables) {
				builder.append(", ");
			} else {
				builder.append(" ≥ 0");
			}
		}
		
		return builder.toString();
	}
	
	/**
	 * Populates the list of restriction spinners for the tar function equation.
	 */
	private void buildRestrictionVariableSpinners() {
		for (int i = 0; i < this.numberOfRestrictions; i += 1) {
			// Notice the <= here. We need to store the right side of the
			// equation as well.
			for (int j = 0; j <= this.numberOfVariables; j += 1) {
				JSpinner spinner = new JSpinner();
				// Set the default value to 1, and step size to 1.
				SpinnerNumberModel model;
				
				if (j < (this.numberOfVariables)) {
					model = new SpinnerNumberModel(1, null, null, 1);
				} else {
					// The right hand side must be ≥ 0 for SMP.
					model = new SpinnerNumberModel(3, 0, null, 1);
				}
				
				spinner.setModel(model);
				this.restrictionVariableSpinners[i][j] = spinner;
			}
		}
	}

	@Override
	public int[] getBaseVariables() {
		int[] result = new int[this.numberOfVariables];
		int i = 0;
		
		for (JSpinner spinner : this.baseVariableSpinners) {
			result[i++] = (Integer) spinner.getValue();
		}
		
		return result;
	}
	
	/**
	 * Get the coefficients for the restriction addressed by the given index.
	 * @param restrictionIndex
	 */
	public int[] getRestrictionCoefficients(int restrictionIndex) {
		if (restrictionIndex > this.numberOfRestrictions) {
			throw new ArrayIndexOutOfBoundsException(restrictionIndex);
		}
		
		int[] results = new int[this.numberOfVariables];
		JSpinner[] spinners = 
			this.restrictionVariableSpinners[restrictionIndex];
		
		/*
		 * We won't use foreach here, because the last element of the array
		 * contains the equation's right side which is not asked here.
		 */
		for (int i = 0; i < this.numberOfVariables; i += 1) {
			results[i] = (Integer) spinners[i].getValue();
		}
		
		return results;
	}

	@Override
	public SimplexRestrictionSet getRestrictionSet() {
		SimplexRestrictionSet restrictions = new SimplexRestrictionSet();
		int i = 0;
		
		for (JSpinner[] spinners : this.restrictionVariableSpinners) {
			int[] baseVariableCoefficients = 
				this.getRestrictionCoefficients(i);
			
			// The last element is the equation's right-side.
			int restrictionResult = 
				(Integer) spinners[spinners.length - 1].getValue();
			
			SimplexRestriction restriction = new SimplexRestriction(
					baseVariableCoefficients, restrictionResult);
			
			restrictions.add(restriction);
			i += 1;
		}
		
		return restrictions;
	}
}
