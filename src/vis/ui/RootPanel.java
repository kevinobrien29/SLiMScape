package vis.ui;

import javax.swing.JPanel;

public class RootPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public RootPanel(JPanel tabs, JPanel options) {
		setLayout(null);
		
		JPanel panel = options;
		panel.setBounds(12, 25, 387, 318);
		add(panel);
		
		JPanel panel_1 = tabs;
		panel_1.setBounds(12, 355, 387, 376);
		add(panel_1);

	}
}
