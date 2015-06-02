package vis.ui;

import java.awt.CardLayout;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import vis.domains.ui.AggregateDomainAttributeTable;
import vis.exec.WorkQueue;
import vis.root.Variables;
import vis.slimfinder.ui.RootSLiMFinderBuilderPanel;
import vis.slimfinder.ui.RunSLiMFinderDisplayPanel;
import vis.slimfinder.ui.SLiMFinderOptionsPanel;
import vis.slimfinder.ui.SLimFinderRunsTable;
import vis.slimsearch.ui.RootSLiMSearchPanel;
import vis.slimsearch.ui.RunSLiMSearchPanel;
import vis.slimsearch.ui.SLiMSearchOptionsPanel;
import vis.slimsearch.ui.SLiMSearchRunsTable;
import cytoscape.CyNetwork;
import cytoscape.view.CyNetworkView;

public class TabsPanel extends JPanel {

	TableDisplay slimPanel = null;
	AttributeTable domainPanel = null;
	AttributeTable domainPanelAggregate = null;

	public TabsPanel(CyNetwork network, CyNetworkView networkView,
			Variables variables, TableDisplay slimPanel, AttributeTable domainPanel,
			AggregateDomainAttributeTable domainPanelAggregate) {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.slimPanel = slimPanel;
		this.domainPanel = domainPanel;
		this.domainPanelAggregate = domainPanelAggregate;

		// Dimension dimension = new Dimension(1000, 1000);
		// this.setPreferredSize(dimension);

		WorkQueue workQueue = new WorkQueue(0);

		JTabbedPane tabbedPane = new JTabbedPane();

		TripleSplitPane slimSearchTripleSplitPane = new TripleSplitPane();
		SLiMSearchOptionsPanel sLiMSearchOptionsPanel = new SLiMSearchOptionsPanel(
				variables);
		RunSLiMSearchPanel runSLiMSearchPanel = new RunSLiMSearchPanel(network,
				variables, sLiMSearchOptionsPanel, slimSearchTripleSplitPane,
				workQueue);
		
		JPanel slimSearchResultCards = new JPanel();
		slimSearchResultCards.setLayout(new CardLayout(0, 0));
		slimSearchResultCards.add(new JPanel(), "Default");
		
		SLiMSearchRunsTable slimsearchRunsTable = new SLiMSearchRunsTable(
				network, networkView, variables, workQueue,
				slimSearchResultCards);
		RootSLiMSearchPanel sLiMSearchPanel = new RootSLiMSearchPanel(
				runSLiMSearchPanel, slimSearchTripleSplitPane,
				slimsearchRunsTable, slimSearchResultCards,
				sLiMSearchOptionsPanel);

		TripleSplitPane simpleSplitPaneTest = new TripleSplitPane();
		SLiMFinderOptionsPanel sLiMFinderOptionsPanel = new SLiMFinderOptionsPanel(
				variables);
		RunSLiMFinderDisplayPanel runSLiMFinderPanel = new RunSLiMFinderDisplayPanel(
				network, variables, sLiMFinderOptionsPanel,
				simpleSplitPaneTest, workQueue, runSLiMSearchPanel, domainPanelAggregate);
		
		domainPanelAggregate.registerRunSLiMFinderDisplayPanel(runSLiMFinderPanel);
		
		JPanel slimFinderResultCards = new JPanel();
		slimFinderResultCards.setLayout(new CardLayout(0, 0));
		slimFinderResultCards.add(new JPanel(), "Default");
		
		SLimFinderRunsTable slimFinderRunsTable = new SLimFinderRunsTable(
				network, networkView, variables, workQueue,
				slimFinderResultCards);
		RootSLiMFinderBuilderPanel sLiMFinderPanel = new RootSLiMFinderBuilderPanel(
				runSLiMFinderPanel, simpleSplitPaneTest, slimFinderRunsTable,
				slimFinderResultCards, sLiMFinderOptionsPanel);

		tabbedPane.addTab("SLiMFinder", null, sLiMFinderPanel, "");
		tabbedPane.addTab("SLiMSearch", null, sLiMSearchPanel, "");

		if (slimPanel != null) {
			tabbedPane.addTab("SLiM Panel", null, slimPanel, "");
		}

		if (domainPanel != null) {
			JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrollPane.setViewportView(domainPanel);
			tabbedPane.addTab("Domain Panel", null, scrollPane, "");
		}
		
		if (domainPanel != null) {
			JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrollPane.setViewportView(this.domainPanelAggregate);
			tabbedPane.addTab("Aggregate Domain Panel", null,
					scrollPane, "");
		}
		tabbedPane.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Add the tabbed pane to this panel.
		add(tabbedPane);

		// The following line enables to use scrolling tabs.
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

		this.updateUI();
	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = TabsPanel.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
}