package vis.tools;

import java.awt.Component;

import javax.swing.JOptionPane;

public class Utilities {

	public static void showMessage(Component component, String message) {
		JOptionPane.showMessageDialog(component, message);
	}
}
