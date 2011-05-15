package net.rdrei.simplex.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NewProblemDialog extends JDialog {
	private static final long serialVersionUID = -210925167785406768L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public NewProblemDialog() {
		setTitle("Neues Standard-Maximum-Problem");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		{
			JLabel lblNeuesProblemAnlegen = new JLabel("Neues Problem");
			lblNeuesProblemAnlegen.setFont(new Font("Dialog", Font.BOLD, 16));
			contentPanel.add(lblNeuesProblemAnlegen, "2, 2");
		}
		{
			JLabel lblVariablen = new JLabel("Variablen");
			contentPanel.add(lblVariablen, "2, 6");
		}
		{
			JSpinner spinner = new JSpinner();
			spinner.setModel(new SpinnerNumberModel(1, 1, 5, 1));
			contentPanel.add(spinner, "4, 6");
		}
		{
			JLabel lblNewLabel = new JLabel("Restriktionen");
			contentPanel.add(lblNewLabel, "2, 8");
		}
		{
			JSpinner spinner = new JSpinner();
			spinner.setModel(new SpinnerNumberModel(1, 1, 5, 1));
			contentPanel.add(spinner, "4, 8");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						NewProblemDialog.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
