package vis.ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class BorderLayoutWrapper extends JPanel {
	/**
	 * Create the panel.
	 */
	public BorderLayoutWrapper(RunsTable runsTable) {
		BorderLayout borderLayout = new BorderLayout();
		setLayout(borderLayout);
		add(runsTable.getTableHeader(), BorderLayout.NORTH);
		this.add(runsTable, BorderLayout.CENTER);
	}

}
