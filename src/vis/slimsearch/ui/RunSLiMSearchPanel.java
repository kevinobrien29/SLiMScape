package vis.slimsearch.ui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vis.data.Constants;
import vis.data.run.SLiMFinderRun;
import vis.data.run.SLiMSearchRun;
import vis.exec.WorkQueue;
import vis.root.Variables;
import vis.slimfinder.exec.SLiMFinderThread;
import vis.slimfinder.ui.NetworkDataCollector;
import vis.slimsearch.exec.SLiMSearchThread;
import vis.tools.CytoscapeHelper;
import vis.ui.RunsTable;
import vis.ui.TripleSplitPane;
import cytoscape.CyNetwork;
import java.awt.GridLayout;

public class RunSLiMSearchPanel extends JPanel implements ItemListener,
		ActionListener {
	Logger log = LoggerFactory.getLogger(RunSLiMSearchPanel.class);
	private JPanel resultCards = null;
	private RunsTable runsTable = null;
	String[] columnNames = { "Status_", "Percentage" };
	JTextArea motifTextArea = null;
	TripleSplitPane resultsPanel = null;
	WorkQueue workQueue = null;
	SLiMSearchOptionsPanel sLiMSearchOptionsPanel = null;
	CyNetwork network = null;
	Variables variables = null;
	JComboBox comboBox = null;

	String[] options = { "All Nodes", "Selected Nodes" };

	/**
	 * Create the panel.
	 */
	public RunSLiMSearchPanel(final CyNetwork network, Variables variables,
			SLiMSearchOptionsPanel sLiMSearchOptionsPanel,
			TripleSplitPane resultsPanel, WorkQueue workQueue) {
		this.network = network;
		this.sLiMSearchOptionsPanel = sLiMSearchOptionsPanel;
		this.variables = variables;
		this.workQueue = workQueue;
		this.resultsPanel = resultsPanel;
		setBackground(new Color(238, 238, 238));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 558, 0 };
		gridBagLayout.rowHeights = new int[] { 208, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JPanel runSLiMFinderPanel = new JPanel();
		runSLiMFinderPanel.setBorder(new TitledBorder(null, "Run SLiMSearch",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagLayout gbl_runSLiMFinderPanel = new GridBagLayout();
		gbl_runSLiMFinderPanel.columnWidths = new int[] { 466, 0 };
		gbl_runSLiMFinderPanel.rowHeights = new int[] { 25, 110, 0, 0, 0 };
		gbl_runSLiMFinderPanel.columnWeights = new double[] { 1.0,
				Double.MIN_VALUE };
		gbl_runSLiMFinderPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		runSLiMFinderPanel.setLayout(gbl_runSLiMFinderPanel);
		GridBagConstraints gbc_runSLiMFinderPanel = new GridBagConstraints();
		gbc_runSLiMFinderPanel.fill = GridBagConstraints.BOTH;
		gbc_runSLiMFinderPanel.gridx = 0;
		gbc_runSLiMFinderPanel.gridy = 0;
		add(runSLiMFinderPanel, gbc_runSLiMFinderPanel);
		JButton runSLiMSearchButton = new JButton("RunSLiMSearch");

		runSLiMSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (comboBox.getSelectedItem().equals("Selected Nodes")) {
					HashSet<Integer> highlightedNodeIndices = NetworkDataCollector
							.getSelectedNodesIndices(network);
					if (highlightedNodeIndices.size() > 0) {
						String motif = motifTextArea.getText();
						runSLiMSearch(highlightedNodeIndices, motif);
					} else {
						log.info("Not enough nodes in selection.");
					}
				} else if (comboBox.getSelectedItem().equals("All Nodes")) {
					HashSet<Integer> highlightedNodeIndices = NetworkDataCollector
							.getAllNodeIndices(network);
					String motif = motifTextArea.getText();
					runSLiMSearch(highlightedNodeIndices, motif);
				}
			}

		});

		GridBagConstraints gbc_runSLiMSearchButton = new GridBagConstraints();
		gbc_runSLiMSearchButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_runSLiMSearchButton.insets = new Insets(0, 0, 5, 0);
		gbc_runSLiMSearchButton.gridx = 0;
		gbc_runSLiMSearchButton.gridy = 0;
		runSLiMFinderPanel.add(runSLiMSearchButton, gbc_runSLiMSearchButton);

		JPanel slimSearchOptionsPanel = new JPanel();
		slimSearchOptionsPanel.setBorder(new TitledBorder(new LineBorder(
				new Color(184, 207, 229)), "Parameters", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(51, 51, 51)));
		GridBagConstraints gbc_slimSearchOptionsPanel = new GridBagConstraints();
		gbc_slimSearchOptionsPanel.insets = new Insets(0, 0, 5, 0);
		gbc_slimSearchOptionsPanel.fill = GridBagConstraints.BOTH;
		gbc_slimSearchOptionsPanel.gridx = 0;
		gbc_slimSearchOptionsPanel.gridy = 1;
		runSLiMFinderPanel.add(slimSearchOptionsPanel,
				gbc_slimSearchOptionsPanel);
		GridBagLayout gbl_slimSearchOptionsPanel = new GridBagLayout();
		gbl_slimSearchOptionsPanel.columnWidths = new int[] { 0, 0 };
		gbl_slimSearchOptionsPanel.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_slimSearchOptionsPanel.columnWeights = new double[] { 1.0,
				Double.MIN_VALUE };
		gbl_slimSearchOptionsPanel.rowWeights = new double[] { 1.0, 0.0, 1.0,
				Double.MIN_VALUE };
		slimSearchOptionsPanel.setLayout(gbl_slimSearchOptionsPanel);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		slimSearchOptionsPanel.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblSelectionType = new JLabel("Run Configuration:");
		GridBagConstraints gbc_lblSelectionType = new GridBagConstraints();
		gbc_lblSelectionType.insets = new Insets(0, 0, 0, 5);
		gbc_lblSelectionType.gridx = 0;
		gbc_lblSelectionType.gridy = 0;
		panel.add(lblSelectionType, gbc_lblSelectionType);

		comboBox = new JComboBox(options);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.anchor = GridBagConstraints.WEST;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 0;
		panel.add(comboBox, gbc_comboBox);
		comboBox.addActionListener(this);

		JLabel motifLabel = new JLabel("Motifs:");
		GridBagConstraints gbc_motifLabel = new GridBagConstraints();
		gbc_motifLabel.anchor = GridBagConstraints.WEST;
		gbc_motifLabel.insets = new Insets(0, 0, 5, 5);
		gbc_motifLabel.gridx = 0;
		gbc_motifLabel.gridy = 1;
		slimSearchOptionsPanel.add(motifLabel, gbc_motifLabel);

		motifTextArea = new JTextArea();
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.insets = new Insets(0, 0, 0, 5);
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 0;
		gbc_textArea.gridy = 2;
		slimSearchOptionsPanel.add(motifTextArea, gbc_textArea);

	}

	public void runSLiMSearch(HashSet<Integer> highlightedNodes, String motif) {
		if (highlightedNodes.size() > 0) {
			String fasta = NetworkDataCollector.getFastaForNodeIndices(
					this.network, highlightedNodes);
			String id = CytoscapeHelper.LocalExec.getUniqueFolderId(Constants
					.getsLiMSearchRunDir());
			SLiMSearchThread thread = new SLiMSearchThread(this.network,
					variables, sLiMSearchOptionsPanel.getSLiMFinderOptions(),
					id, fasta, motif, resultsPanel, highlightedNodes);

			SLiMSearchRun sLiMSearch = new SLiMSearchRun(this.network,
					variables, sLiMSearchOptionsPanel.getSLiMFinderOptions(),
					id, fasta, motif, resultsPanel, highlightedNodes, motif,
					highlightedNodes.size(), thread);
			resultsPanel.initialiseRun(sLiMSearch);
			workQueue.executePriority(sLiMSearch);
		} else {
			log.info("Not enough nodes in selection.");
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}
