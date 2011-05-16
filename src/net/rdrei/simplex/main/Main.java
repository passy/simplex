package net.rdrei.simplex.main;
import java.awt.EventQueue;

import net.rdrei.simplex.gui.MainWindow;

public class Main {
	public static void main(String[] args) {
		// Delegate to GUI. Would allow us to create a CLI version.
		Main.launchGUI();
	}
	
	/**
	 * Launch the application GUI.
	 */
	public static void launchGUI() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new MainWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
