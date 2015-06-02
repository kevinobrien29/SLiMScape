package vis.slimfinder.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import vis.data.Constants;
import vis.data.run.SLiMFinderRun;
import vis.exec.WorkQueue;
import vis.root.Variables;
import vis.slimfinder.exec.SLiMFinderThread;
import vis.slimsearch.ui.RunSLiMSearchPanel;
import vis.tools.CytoscapeHelper;
import vis.ui.AttributeTable;
import vis.ui.TableDisplay;
import vis.ui.TripleSplitPane;
import cytoscape.CyNetwork;
import cytoscape.Cytoscape;

public class RunSLiMFinderDisplayPanel extends JPanel implements ItemListener,
		ActionListener {
	String[] columnNames = { "Status_", "Percentage" };

	String[] options = {
			"Selected Nodes",
			"Selected Node Interactors (spokes + hub)",
			"Batch Interactions (spokes + hub)",
			"Batch Interactors (spokes)", "Selected Node Interactors (spokes)",
			"Batch Domain", "Batch Domain Interactors",
			"Merge Selected Node Interactors (spokes)",
			"Merge Selected Node Interactors (spokes + hub)",
			"Attribute" };
	JComboBox comboBox = null;
	JComboBox attributeNameCombobox = null;
	CyNetwork network = null;
	Variables variables = null;
	SLiMFinderOptionsPanel sLiMFinderOptionsPanel = null;
	TripleSplitPane resultsPanel = null;
	WorkQueue workQueue = null;
	RunSLiMSearchPanel slimSearchPanel = null;
	AttributeTable domainPanelAggregate = null;

	/**
	 * Create the panel.
	 */
	public RunSLiMFinderDisplayPanel(CyNetwork network, Variables variables,
			SLiMFinderOptionsPanel sLiMFinderOptionsPanel,
			TripleSplitPane resultsPanel, WorkQueue workQueue,
			RunSLiMSearchPanel slimSearchPanel,
			AttributeTable domainPanelAggregate) {
		this.network = network;
		this.variables = variables;
		this.sLiMFinderOptionsPanel = sLiMFinderOptionsPanel;
		this.resultsPanel = resultsPanel;
		this.workQueue = workQueue;
		this.slimSearchPanel = slimSearchPanel;
		this.domainPanelAggregate = domainPanelAggregate;

		setBorder(null);
		int height = 170;
		setBackground(new Color(238, 238, 238));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 629, 0 };
		gridBagLayout.rowHeights = new int[] { height, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);
		setPreferredSize(new Dimension(476, 167));

		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		// add(resultsPanel, gbc_panel_1);

		JPanel sLiMFinderPanel = new JPanel();
		sLiMFinderPanel.setBorder(BorderFactory.createCompoundBorder(
				new TitledBorder("Run SLiMFinder"),
				new EmptyBorder(0, 0, 0, 20)));
		GridBagConstraints gbc_sLiMFinderPanel = new GridBagConstraints();
		gbc_sLiMFinderPanel.fill = GridBagConstraints.BOTH;
		gbc_sLiMFinderPanel.gridx = 0;
		gbc_sLiMFinderPanel.gridy = 0;
		add(sLiMFinderPanel, gbc_sLiMFinderPanel);
		GridBagLayout gbl_sLiMFinderPanel = new GridBagLayout();
		gbl_sLiMFinderPanel.columnWidths = new int[] { 497, 0 };
		gbl_sLiMFinderPanel.rowHeights = new int[] { 25, 87, 0 };
		gbl_sLiMFinderPanel.columnWeights = new double[] { 1.0,
				Double.MIN_VALUE };
		gbl_sLiMFinderPanel.rowWeights = new double[] { 0.0, 0.0,
				Double.MIN_VALUE };
		sLiMFinderPanel.setLayout(gbl_sLiMFinderPanel);
		JButton runSLiMFinderButton = new JButton("RunSLiMFinder");
		GridBagConstraints gbc_runSLiMFinderButton = new GridBagConstraints();
		gbc_runSLiMFinderButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_runSLiMFinderButton.insets = new Insets(0, 0, 5, 0);
		gbc_runSLiMFinderButton.gridx = 0;
		gbc_runSLiMFinderButton.gridy = 0;
		sLiMFinderPanel.add(runSLiMFinderButton, gbc_runSLiMFinderButton);

		JPanel SLiMFinderOptionsPanel = new JPanel();
		GridBagConstraints gbc_SLiMFinderOptionsPanel = new GridBagConstraints();
		gbc_SLiMFinderOptionsPanel.insets = new Insets(0, 0, 0, 2);
		gbc_SLiMFinderOptionsPanel.fill = GridBagConstraints.BOTH;
		gbc_SLiMFinderOptionsPanel.gridx = 0;
		gbc_SLiMFinderOptionsPanel.gridy = 1;
		sLiMFinderPanel.add(SLiMFinderOptionsPanel, gbc_SLiMFinderOptionsPanel);
		SLiMFinderOptionsPanel.setBorder(new TitledBorder(new LineBorder(
				new Color(184, 207, 229)), "Parameters", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(51, 51, 51)));
		GridBagLayout gbl_SLiMFinderOptionsPanel = new GridBagLayout();
		gbl_SLiMFinderOptionsPanel.columnWidths = new int[] { 109, 373, 0 };
		gbl_SLiMFinderOptionsPanel.rowHeights = new int[] { 24, 24, 0 };
		gbl_SLiMFinderOptionsPanel.columnWeights = new double[] { 0.0, 1.0,
				Double.MIN_VALUE };
		gbl_SLiMFinderOptionsPanel.rowWeights = new double[] { 0.0, 0.0,
				Double.MIN_VALUE };
		SLiMFinderOptionsPanel.setLayout(gbl_SLiMFinderOptionsPanel);

		comboBox = new JComboBox(options);
		comboBox.addActionListener(this);

		JLabel lblSelectionType = new JLabel("Run Configuration:");
		GridBagConstraints gbc_lblSelectionType = new GridBagConstraints();
		gbc_lblSelectionType.anchor = GridBagConstraints.WEST;
		gbc_lblSelectionType.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelectionType.gridx = 0;
		gbc_lblSelectionType.gridy = 0;
		SLiMFinderOptionsPanel.add(lblSelectionType, gbc_lblSelectionType);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.anchor = GridBagConstraints.NORTHWEST;
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 0;
		SLiMFinderOptionsPanel.add(comboBox, gbc_comboBox);

		JLabel AbbtrubteNameLAbel = new JLabel("Attribute:");
		GridBagConstraints gbc_AbbtrubteNameLAbel = new GridBagConstraints();
		gbc_AbbtrubteNameLAbel.anchor = GridBagConstraints.EAST;
		gbc_AbbtrubteNameLAbel.insets = new Insets(0, 0, 0, 5);
		gbc_AbbtrubteNameLAbel.gridx = 0;
		gbc_AbbtrubteNameLAbel.gridy = 1;
		SLiMFinderOptionsPanel.add(AbbtrubteNameLAbel, gbc_AbbtrubteNameLAbel);

		attributeNameCombobox = new JComboBox(new Object[] {});
		attributeNameCombobox.setEnabled(false);
		GridBagConstraints gbc_attributeNameCombobox = new GridBagConstraints();
		gbc_attributeNameCombobox.anchor = GridBagConstraints.NORTHWEST;
		gbc_attributeNameCombobox.gridx = 1;
		gbc_attributeNameCombobox.gridy = 1;
		SLiMFinderOptionsPanel.add(attributeNameCombobox,
				gbc_attributeNameCombobox);

		runSLiMFinderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runSLiMFinder((String) comboBox.getSelectedItem(), null, null);
			}
		});
	}

	public void runSLiMFinder(String selection, String[] proteins, String domain) {
		RunBuilderWorker runBuilderWorker = new RunBuilderWorker(network,
				variables, sLiMFinderOptionsPanel, resultsPanel, workQueue,
				slimSearchPanel, domainPanelAggregate, selection,
				attributeNameCombobox, proteins, domain);
		runBuilderWorker.execute();
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (comboBox.getSelectedItem().equals("Attribute")) {
			this.attributeNameCombobox.setEnabled(true);
			this.attributeNameCombobox.removeAllItems();
			for (String item : Cytoscape.getNodeAttributes()
					.getAttributeNames()) {
				attributeNameCombobox.addItem(item);
			}

		} else {
			this.attributeNameCombobox.setEnabled(false);
		}
	}
}

class RunBuilderWorker extends SwingWorker<Void, Void> {
	CyNetwork network = null;
	Variables variables = null;
	SLiMFinderOptionsPanel sLiMFinderOptionsPanel = null;
	TripleSplitPane resultsPanel = null;
	WorkQueue workQueue = null;
	RunSLiMSearchPanel slimSearchPanel = null;
	AttributeTable domainPanelAggregate = null;
	String selection = null;
	JComboBox attributeNameCombobox = null;
	String[] proteins = null;
	String domain = null;

	public RunBuilderWorker(CyNetwork network, Variables variables,
			SLiMFinderOptionsPanel sLiMFinderOptionsPanel,
			TripleSplitPane resultsPanel, WorkQueue workQueue,
			RunSLiMSearchPanel slimSearchPanel,
			AttributeTable domainPanelAggregate, String selection,
			JComboBox attributeNameCombobox, String[] proteins, String domain) {
		super();
		this.network = network;
		this.variables = variables;
		this.sLiMFinderOptionsPanel = sLiMFinderOptionsPanel;
		this.resultsPanel = resultsPanel;
		this.workQueue = workQueue;
		this.slimSearchPanel = slimSearchPanel;
		this.domainPanelAggregate = domainPanelAggregate;
		this.selection = selection;
		this.attributeNameCombobox = attributeNameCombobox;
		this.proteins = proteins;
		this.domain = domain;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Void doInBackground() throws Exception {

		if (selection.equals("Batch Interactions (spokes + hub)")) {
			HashMap<String, HashSet<Integer>> mappings = NetworkDataCollector
					.getBatchInteractionsNodeIds(network, true);

			for (String hub : mappings.keySet()) {
				HashSet<Integer> nodeIds = mappings.get(hub);
				if (nodeIds.size() > 2) {
					String fasta = NetworkDataCollector.getFastaForNodeIndices(
							network, nodeIds);
					String id = CytoscapeHelper.LocalExec
							.getUniqueFolderId(Constants.getsLiMSearchRunDir());
					HashSet<String> highlightedNodeNames = NetworkDataCollector
							.getNodeNames(network, nodeIds);

					SLiMFinderThread thread = new SLiMFinderThread(network,
							variables,
							sLiMFinderOptionsPanel.getSLiMFinderOptions(), id,
							fasta, resultsPanel, highlightedNodeNames,
							slimSearchPanel);

					SLiMFinderRun sLiMFinder = new SLiMFinderRun(variables, id,
							fasta, resultsPanel, highlightedNodeNames,
							slimSearchPanel,
							"Batch Interactions (spokes + hub): " + hub,
							highlightedNodeNames.size(), highlightedNodeNames,
							nodeIds, thread);

					resultsPanel.initialiseRun(sLiMFinder);
					workQueue.execute(sLiMFinder);
				}
			}
		} else if (selection.equals("Single domain")) {
			HashSet<Integer> mappings = NetworkDataCollector
					.getSingleDomainNodeIds(network, true, proteins,
							domainPanelAggregate);

			HashSet<Integer> nodeIds = mappings;
			if (nodeIds.size() >= 1) {
				String fasta = NetworkDataCollector.getFastaForNodeIndices(
						network, nodeIds);
				String id = CytoscapeHelper.LocalExec
						.getUniqueFolderId(Constants.getsLiMSearchRunDir());
				HashSet<String> highlightedNodeNames = NetworkDataCollector
						.getNodeNames(network, nodeIds);

				SLiMFinderThread thread = new SLiMFinderThread(network,
						variables,
						sLiMFinderOptionsPanel.getSLiMFinderOptions(), id,
						fasta, resultsPanel, highlightedNodeNames,
						slimSearchPanel);

				SLiMFinderRun sLiMFinder = new SLiMFinderRun(variables, id,
						fasta, resultsPanel, highlightedNodeNames,
						slimSearchPanel, "Single domain: " + domain,
						highlightedNodeNames.size(), highlightedNodeNames,
						nodeIds, thread);

				resultsPanel.initialiseRun(sLiMFinder);
				workQueue.execute(sLiMFinder);
			}
		}  else if (selection.equals("Single domain interactors")) {
			HashSet<Integer> mappings = NetworkDataCollector
					.getSingleDomainInteractorNodeIds(network, true, proteins,
							domainPanelAggregate);

			HashSet<Integer> nodeIds = mappings;
			if (nodeIds.size() >= 1) {
				String fasta = NetworkDataCollector.getFastaForNodeIndices(
						network, nodeIds);
				String id = CytoscapeHelper.LocalExec
						.getUniqueFolderId(Constants.getsLiMSearchRunDir());
				HashSet<String> highlightedNodeNames = NetworkDataCollector
						.getNodeNames(network, nodeIds);

				SLiMFinderThread thread = new SLiMFinderThread(network,
						variables,
						sLiMFinderOptionsPanel.getSLiMFinderOptions(), id,
						fasta, resultsPanel, highlightedNodeNames,
						slimSearchPanel);

				SLiMFinderRun sLiMFinder = new SLiMFinderRun(variables, id,
						fasta, resultsPanel, highlightedNodeNames,
						slimSearchPanel, "Single domain interactors: " + domain,
						highlightedNodeNames.size(), highlightedNodeNames,
						nodeIds, thread);

				resultsPanel.initialiseRun(sLiMFinder);
				workQueue.execute(sLiMFinder);
			}
		} else if (selection.equals("Batch Domain")) {
			HashMap<String, HashSet<Integer>> mappings = NetworkDataCollector
					.getBatchDomainNodeIds(network, true, domainPanelAggregate);

			for (String hub : mappings.keySet()) {
				HashSet<Integer> nodeIds = mappings.get(hub);
				if (nodeIds.size() > 2) {
					String fasta = NetworkDataCollector.getFastaForNodeIndices(
							network, nodeIds);
					String id = CytoscapeHelper.LocalExec
							.getUniqueFolderId(Constants.getsLiMSearchRunDir());
					HashSet<String> highlightedNodeNames = NetworkDataCollector
							.getNodeNames(network, nodeIds);

					SLiMFinderThread thread = new SLiMFinderThread(network,
							variables,
							sLiMFinderOptionsPanel.getSLiMFinderOptions(), id,
							fasta, resultsPanel, highlightedNodeNames,
							slimSearchPanel);

					SLiMFinderRun sLiMFinder = new SLiMFinderRun(variables, id,
							fasta, resultsPanel, highlightedNodeNames,
							slimSearchPanel, "Batch Domain: " + hub,
							highlightedNodeNames.size(), highlightedNodeNames,
							nodeIds, thread);

					resultsPanel.initialiseRun(sLiMFinder);
					workQueue.execute(sLiMFinder);
				}
			}
		}

		else if (selection.equals("Batch Domain Interactors")) {
			HashMap<String, HashSet<Integer>> mappings = NetworkDataCollector
					.getBatchDomainInteractorNodeIds(network, true,
							domainPanelAggregate);

			for (String hub : mappings.keySet()) {
				HashSet<Integer> nodeIds = mappings.get(hub);
				if (nodeIds.size() > 2) {
					String fasta = NetworkDataCollector.getFastaForNodeIndices(
							network, nodeIds);
					String id = CytoscapeHelper.LocalExec
							.getUniqueFolderId(Constants.getsLiMSearchRunDir());
					HashSet<String> highlightedNodeNames = NetworkDataCollector
							.getNodeNames(network, nodeIds);

					SLiMFinderThread thread = new SLiMFinderThread(network,
							variables,
							sLiMFinderOptionsPanel.getSLiMFinderOptions(), id,
							fasta, resultsPanel, highlightedNodeNames,
							slimSearchPanel);

					SLiMFinderRun sLiMFinder = new SLiMFinderRun(variables, id,
							fasta, resultsPanel, highlightedNodeNames,
							slimSearchPanel,
							"Batch Domain Interactors: " + hub,
							highlightedNodeNames.size(), highlightedNodeNames,
							nodeIds, thread);

					resultsPanel.initialiseRun(sLiMFinder);
					workQueue.execute(sLiMFinder);
				}
			}
		} else if (selection.equals("Batch Interactors (spokes)")) {

			HashMap<String, HashSet<Integer>> mappings = NetworkDataCollector
					.getBatchInteractionsNodeIds(network, false);

			for (String hub : mappings.keySet()) {
				HashSet<Integer> nodeIds = mappings.get(hub);
				if (nodeIds.size() > 2) {
					String fasta = NetworkDataCollector.getFastaForNodeIndices(
							network, nodeIds);
					String id = CytoscapeHelper.LocalExec
							.getUniqueFolderId(Constants.getsLiMSearchRunDir());

					HashSet<String> highlightedNodeNames = NetworkDataCollector
							.getNodeNames(network, nodeIds);

					SLiMFinderThread thread = new SLiMFinderThread(network,
							variables,
							sLiMFinderOptionsPanel.getSLiMFinderOptions(), id,
							fasta, resultsPanel, highlightedNodeNames,
							slimSearchPanel);

					SLiMFinderRun sLiMFinder = new SLiMFinderRun(variables, id,
							fasta, resultsPanel, highlightedNodeNames,
							slimSearchPanel, "Batch Interactions (spokes): "
									+ hub, highlightedNodeNames.size(),
							highlightedNodeNames, nodeIds, thread);

					resultsPanel.initialiseRun(sLiMFinder);
					workQueue.execute(sLiMFinder);
				}
			}
		} else if (selection.equals("Selected Nodes")) {
			HashSet<Integer> nodeIds = NetworkDataCollector
					.getSelectedNodesIndices(network);
			if (nodeIds.size() > 2) {
				String fasta = NetworkDataCollector.getFastaForNodeIndices(
						network, nodeIds);
				String id = CytoscapeHelper.LocalExec
						.getUniqueFolderId(Constants.getsLiMSearchRunDir());

				HashSet<String> highlightedNodeNames = NetworkDataCollector
						.getNodeNames(network, nodeIds);

				String allNames = "";
				for (String name : highlightedNodeNames) {
					allNames = allNames + name + ", ";
				}

				SLiMFinderThread thread = new SLiMFinderThread(network,
						variables,
						sLiMFinderOptionsPanel.getSLiMFinderOptions(), id,
						fasta, resultsPanel, highlightedNodeNames,
						slimSearchPanel);

				SLiMFinderRun sLiMFinder = new SLiMFinderRun(variables, id,
						fasta, resultsPanel, highlightedNodeNames,
						slimSearchPanel, allNames, highlightedNodeNames.size(),
						highlightedNodeNames, nodeIds, thread);

				resultsPanel.initialiseRun(sLiMFinder);
				workQueue.execute(sLiMFinder);
			}
		} else if (selection.equals("Selected Node Interactors (spokes + hub)")) {

			HashMap<String, HashSet<Integer>> mappings = NetworkDataCollector
					.getSelectedNodeInteractionsNodeIds(network, true);

			for (String hub : mappings.keySet()) {
				HashSet<Integer> nodeIds = mappings.get(hub);
				if (nodeIds.size() > 2) {
					String fasta = NetworkDataCollector.getFastaForNodeIndices(
							network, nodeIds);
					String id = CytoscapeHelper.LocalExec
							.getUniqueFolderId(Constants.getsLiMSearchRunDir());

					HashSet<String> highlightedNodeNames = NetworkDataCollector
							.getNodeNames(network, nodeIds);

					SLiMFinderThread thread = new SLiMFinderThread(network,
							variables,
							sLiMFinderOptionsPanel.getSLiMFinderOptions(), id,
							fasta, resultsPanel, highlightedNodeNames,
							slimSearchPanel);

					SLiMFinderRun sLiMFinder = new SLiMFinderRun(variables, id,
							fasta, resultsPanel, highlightedNodeNames,
							slimSearchPanel,
							"Selected Node Interactors (spokes + hub):" + hub,
							highlightedNodeNames.size(), highlightedNodeNames,
							nodeIds, thread);

					resultsPanel.initialiseRun(sLiMFinder);
					workQueue.execute(sLiMFinder);
				}
			}
		} else if (selection.equals("Selected Node Interactors (spokes)")) {
			HashMap<String, HashSet<Integer>> mappings = NetworkDataCollector
					.getSelectedNodeInteractionsNodeIds(network, false);

			for (String hub : mappings.keySet()) {
				HashSet<Integer> nodeIds = mappings.get(hub);
				if (nodeIds.size() > 2) {
					String fasta = NetworkDataCollector.getFastaForNodeIndices(
							network, nodeIds);
					String id = CytoscapeHelper.LocalExec
							.getUniqueFolderId(Constants.getsLiMSearchRunDir());

					HashSet<String> highlightedNodeNames = NetworkDataCollector
							.getNodeNames(network, nodeIds);

					SLiMFinderThread thread = new SLiMFinderThread(network,
							variables,
							sLiMFinderOptionsPanel.getSLiMFinderOptions(), id,
							fasta, resultsPanel, highlightedNodeNames,
							slimSearchPanel);

					SLiMFinderRun sLiMFinder = new SLiMFinderRun(variables, id,
							fasta, resultsPanel, highlightedNodeNames,
							slimSearchPanel,
							"Selected Node Interactors (spokes):" + hub,
							highlightedNodeNames.size(), highlightedNodeNames,
							nodeIds, thread);

					resultsPanel.initialiseRun(sLiMFinder);
					workQueue.execute(sLiMFinder);
				}
			}
		} else if (selection.equals(

		"Merge Selected Node Interactors (spokes)")) {

			HashMap<String, HashSet<Integer>> mappings = NetworkDataCollector
					.getSelectedNodeInteractionsNodeIds(network, false);

			String id = CytoscapeHelper.LocalExec.getUniqueFolderId(Constants
					.getsLiMSearchRunDir());
			String hubs = "";

			HashSet<Integer> allNodeIds = new HashSet<Integer>();
			for (String hub : mappings.keySet()) {
				hubs = hubs + hub + ", ";
				allNodeIds.addAll(mappings.get(hub));
			}
			if (allNodeIds.size() > 2) {

				String fasta = NetworkDataCollector.getFastaForNodeIndices(
						network, allNodeIds);
				HashSet<String> highlightedNodeNames = NetworkDataCollector
						.getNodeNames(network, allNodeIds);

				SLiMFinderThread thread = new SLiMFinderThread(network,
						variables,
						sLiMFinderOptionsPanel.getSLiMFinderOptions(), id,
						fasta, resultsPanel, highlightedNodeNames,
						slimSearchPanel);

				SLiMFinderRun sLiMFinder = new SLiMFinderRun(variables, id,
						fasta, resultsPanel, highlightedNodeNames,
						slimSearchPanel,
						"Merge Selected Node Interactors (spokes):" + hubs,
						highlightedNodeNames.size(), highlightedNodeNames,
						allNodeIds, thread);

				resultsPanel.initialiseRun(sLiMFinder);
				workQueue.execute(sLiMFinder);
			}
		} else if (selection
				.equals("Merge Selected Node Interactors (spokes + hub)")) {
			HashMap<String, HashSet<Integer>> mappings = NetworkDataCollector
					.getSelectedNodeInteractionsNodeIds(network, true);

			String id = CytoscapeHelper.LocalExec.getUniqueFolderId(Constants
					.getsLiMSearchRunDir());
			String hubs = "";

			HashSet<Integer> allNodeIds = new HashSet<Integer>();
			for (String hub : mappings.keySet()) {
				hubs = hubs + hub + ", ";
				allNodeIds.addAll(mappings.get(hub));
			}
			if (allNodeIds.size() > 2) {

				String fasta = NetworkDataCollector.getFastaForNodeIndices(
						network, allNodeIds);
				HashSet<String> highlightedNodeNames = NetworkDataCollector
						.getNodeNames(network, allNodeIds);

				SLiMFinderThread thread = new SLiMFinderThread(network,
						variables,
						sLiMFinderOptionsPanel.getSLiMFinderOptions(), id,
						fasta, resultsPanel, highlightedNodeNames,
						slimSearchPanel);

				SLiMFinderRun sLiMFinder = new SLiMFinderRun(variables, id,
						fasta, resultsPanel, highlightedNodeNames,
						slimSearchPanel,
						"Merge Selected Node Interactors (spokes + hub):"
								+ hubs, highlightedNodeNames.size(),
						highlightedNodeNames, allNodeIds, thread);

				resultsPanel.initialiseRun(sLiMFinder);
				workQueue.execute(sLiMFinder);
			}
		} else if (selection.equals("Attribute")) {

			String attributeName = (String) attributeNameCombobox
					.getSelectedItem();

			HashMap<String, HashSet<Integer>> mappings = NetworkDataCollector
					.getNodeIndicesForAttribute(network, attributeName);

			for (String attribute : mappings.keySet()) {
				HashSet<Integer> nodeIds = mappings.get(attribute);
				if (nodeIds.size() > 2) {
					String fasta = NetworkDataCollector.getFastaForNodeIndices(
							network, nodeIds);
					String id = CytoscapeHelper.LocalExec
							.getUniqueFolderId(Constants.getsLiMSearchRunDir());

					HashSet<String> highlightedNodeNames = NetworkDataCollector
							.getNodeNames(network, nodeIds);

					SLiMFinderThread thread = new SLiMFinderThread(network,
							variables,
							sLiMFinderOptionsPanel.getSLiMFinderOptions(), id,
							fasta, resultsPanel, highlightedNodeNames,
							slimSearchPanel);

					SLiMFinderRun sLiMFinder = new SLiMFinderRun(variables, id,
							fasta, resultsPanel, highlightedNodeNames,
							slimSearchPanel, "Attribute: " + attribute,
							highlightedNodeNames.size(), highlightedNodeNames,
							nodeIds, thread);

					resultsPanel.initialiseRun(sLiMFinder);
					workQueue.execute(sLiMFinder);
				}
			}
		}
		return null;
	}
}
