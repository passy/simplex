package net.rdrei.simplex.gui;

import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle.ComponentPlacement;

public class MainWindow {

	private JFrame mainFrame;
	private final static Logger LOGGER = Logger.getLogger(
			MainWindow.class.getName());
	
	class NewDialogWindowListener extends WindowAdapter {
		@Override
		public void windowClosed(WindowEvent e) {
			Object source = e.getWindow();
			if (source instanceof NewProblemDialog) {
				NewProblemDialog dialog = (NewProblemDialog) source;
				if (dialog.isAccepted()) {
					// Get count of variables and restrictions and set it as
					// new main panel.
					int numberOfVariables = dialog.getVariablesCount();
					int numberOfRestrictions = dialog.getRestrictionsCount();
					LOGGER.log(Level.INFO, String.format(
							"Creating new panel with %d variables and " + 
							"%d restrictions.", numberOfVariables,
							numberOfRestrictions));
					
					FormularInputPanel panel = new FormularInputPanel(
							numberOfVariables, numberOfRestrictions
					);
					MainWindow.this.setMainPanel(panel);
				}
			}
		}
	}
	
	class NewDialogActionListener implements ActionListener {

		@Override
		/**
		 * Open the New Problem Dialog.
		 */
		public void actionPerformed(ActionEvent e) {
			// Show the NewProblemDialog
			NewProblemDialog dialog = new NewProblemDialog(
					mainFrame, true);
			dialog.setModalityType(ModalityType.APPLICATION_MODAL);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.addWindowListener(new NewDialogWindowListener());
			dialog.setVisible(true);
		}
	};


	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
		loadDefaultPanel();
		mainFrame.setVisible(true);
	}
	
	private void setMainPanel(JPanel panel) {
		mainFrame.getContentPane().removeAll();
		JButton btnNewButton = new JButton("Weiter");
		GroupLayout groupLayout = new GroupLayout(mainFrame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 628, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(382, Short.MAX_VALUE)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 234, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		mainFrame.getContentPane().setLayout(groupLayout);
	}
	
	private void loadDefaultPanel() {
		FormularInputPanel panel = new FormularInputPanel();
		setMainPanel(panel);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		mainFrame = new JFrame();
		mainFrame.setTitle("SIMPLEX-Rechner");
		mainFrame.setBounds(100, 100, 630, 400);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		mainFrame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("Datei");
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("Neu");
		mntmNew.addActionListener(new NewDialogActionListener());
		mntmNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		mnFile.add(mntmNew);
		
		JMenuItem mntmExit = new JMenuItem("Beenden");
		mntmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Exit with successful return code.
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);
		
		JMenu mnberAbout = new JMenu("Über");
		menuBar.add(mnberAbout);
	}
}
