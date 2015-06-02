package vis.slimfinder.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import vis.ui.RunsTable;
import vis.ui.TripleSplitPane;

public class RootSLiMFinderBuilderPanel extends JPanel implements ItemListener {
	private JPanel resultCards = null;
	private RunsTable runsTable = null;
	String[] columnNames = { "Status_", "Percentage" };
	JComboBox comboBox = null;

	/**
	 * Create the panel.
	 */
	public RootSLiMFinderBuilderPanel(RunSLiMFinderDisplayPanel runSLiMFinderPanel, TripleSplitPane simpleSplitPaneTest, SLimFinderRunsTable runsTable, JPanel resultCards, SLiMFinderOptionsPanel sLiMFinderOptionsPanel) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 0;
		add(tabbedPane, gbc_tabbedPane);
		
		simpleSplitPaneTest.setPanes(runSLiMFinderPanel, runsTable, resultCards);
		
		
		tabbedPane.addTab("Run SLiMFinder", simpleSplitPaneTest);
		tabbedPane.addTab("Options", sLiMFinderOptionsPanel);

		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub

	}
}
