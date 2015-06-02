package vis.slimsearch.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;

import vis.ui.TripleSplitPane;

public class RootSLiMSearchPanel extends JPanel implements ItemListener {
	/**
	 * Create the panel.
	 */
	public RootSLiMSearchPanel(RunSLiMSearchPanel runSLiMSearchPanel, TripleSplitPane simpleSplitPaneTest, SLiMSearchRunsTable runsTable, JPanel resultCards,  SLiMSearchOptionsPanel sLiMSearchOptionsPanel) {
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
		
		
		simpleSplitPaneTest.setPanes(runSLiMSearchPanel, runsTable, resultCards);
		
		tabbedPane.addTab("Run SLiMSearch", simpleSplitPaneTest);
		tabbedPane.addTab("Options", sLiMSearchOptionsPanel);

		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;

		int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
		int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub

	}
}
