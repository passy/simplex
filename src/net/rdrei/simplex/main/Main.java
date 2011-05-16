package net.rdrei.simplex.main;
import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

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
				Logger jlog = Logger.getLogger("net.rdrei.simplex.main");
				jlog.log(Level.INFO, "SimplexRechner started.");
				try {
					new MainWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
