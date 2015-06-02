package vis.root;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vis.data.Constants;
import vis.domains.ui.AggregateDomainAttributeTable;
import vis.domains.ui.DomainAttributeTable;
import vis.launch.LaunchSlimDomain;
import vis.slims.ui.SLiMTableDisplay;
import vis.tools.CustomImportHelper;
import vis.tools.CytoscapeHelper;
import vis.tools.Utilities;
import vis.tools.VisualStyleBuilder;
import vis.ui.AttributeTable;
import vis.ui.ProgressBar;
import vis.ui.TableDisplay;
import vis.ui.TabsPanel;
import vis.workers.ImportCustomAttributes;
import vis.workers.ImportDomainsHTTPJavaWorker;
import vis.workers.UniprotSequenceImporter;
import cytoscape.CyNetwork;
import cytoscape.Cytoscape;
import cytoscape.layout.CyLayoutAlgorithm;
import cytoscape.layout.CyLayouts;
import cytoscape.view.CyNetworkView;
import cytoscape.view.cytopanels.CytoPanelImp;
import cytoscape.visual.CalculatorCatalog;
import cytoscape.visual.VisualMappingManager;
import cytoscape.visual.VisualStyle;

public class SetupWizardJFrame extends JFrame {

	Logger log = LoggerFactory.getLogger(SetupWizardJFrame.class);

	private JPanel contentPane;
	private JCheckBox existingNetworkRetrieveDomainsCheckBox;
	private JComboBox networkComboBox;
	private JComboBox uniprotComboBox;
	private VariablesPanel variablesPanel;
	private JTabbedPane tabbedPane;

	private LaunchSlimDomain launchSlimDomain;
	private JTextField customSLiMsTextField;
	private JButton customSLiMsbutton;
	private JLabel nodeLabelLabel;
	private JComboBox nodeLabelComboBox;

	final JCheckBox customSLiMsCheckBox = new JCheckBox(
			"Import Custom Attributes");

	private TabsPanel rootPanel = null;

	class WindowEventHandler extends WindowAdapter {
		public void windowClosing(WindowEvent evt) {
			launchSlimDomain.setRunning(false);
		}
	}

	/**
	 * Create the frame.
	 */
	public SetupWizardJFrame(LaunchSlimDomain launchSlimDomain) {
		log.info("Launching root pane");
		this.launchSlimDomain = launchSlimDomain;
		this.launchSlimDomain.setRunning(true);

		ArrayList<String> networkNames = new ArrayList<String>();
		for (CyNetwork network : Cytoscape.getNetworkSet()) {
			networkNames.add(network.getTitle());
		}

		this.addWindowListener(new WindowEventHandler());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setBounds(100, 100, 725, 471);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		contentPane.setLayout(new BorderLayout(0, 0));

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane);

		JPanel panel = new JPanel();
		tabbedPane.addTab("Import Network", null, panel, null);

		variablesPanel = new VariablesPanel();
		JScrollPane variablesPanelScrollPane = new JScrollPane(variablesPanel,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		tabbedPane.addTab("Options", null, variablesPanelScrollPane, null);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 610, 0 };
		gbl_panel.rowHeights = new int[] { 24, 234, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel label = new JLabel("Import Network");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.NORTHWEST;
		gbc_label.insets = new Insets(0, 0, 5, 0);
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		panel.add(label, gbc_label);
		label.setFont(new Font("Dialog", Font.BOLD, 20));

		JPanel existingNetworkPanel = new JPanel();
		GridBagConstraints gbc_existingNetworkPanel = new GridBagConstraints();
		gbc_existingNetworkPanel.fill = GridBagConstraints.BOTH;
		gbc_existingNetworkPanel.gridx = 0;
		gbc_existingNetworkPanel.gridy = 1;
		panel.add(existingNetworkPanel, gbc_existingNetworkPanel);
		existingNetworkPanel.setLayout(null);
		existingNetworkPanel.setBorder(new TitledBorder(new LineBorder(
				new Color(184, 207, 229)), "Use Existing Network",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51,
						51)));

		JLabel networkLabel = new JLabel("Network:");
		networkLabel.setBounds(12, 31, 96, 15);
		existingNetworkPanel.add(networkLabel);

		JLabel lblInstances = new JLabel("Uniprot Attribute:");
		lblInstances.setBounds(12, 125, 144, 15);
		existingNetworkPanel.add(lblInstances);

		JButton button_6 = new JButton("Import");
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (variablesPanel.checkValid())
				{
					variablesPanel.getCurrentPropereties();
					Variables variables = variablesPanel.getVariables();
					useExistingNetwork(variables, variables.getSlimsuiteHome(),
							variables.getRunsDir(), variables.getiUPredPath(),
							customSLiMsCheckBox);
					variablesPanel.loadProperties(tabbedPane);
				}
			}
		});
		button_6.setBounds(487, 327, 107, 25);
		existingNetworkPanel.add(button_6);

		JPanel slimsearchOptionsPanel = new JPanel();
		slimsearchOptionsPanel.setBorder(new TitledBorder(null, "Options",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		slimsearchOptionsPanel.setBounds(12, 177, 582, 113);
		existingNetworkPanel.add(slimsearchOptionsPanel);
		slimsearchOptionsPanel.setLayout(null);

		existingNetworkRetrieveDomainsCheckBox = new JCheckBox(
				"Retrieve Domains");
		existingNetworkRetrieveDomainsCheckBox.setBounds(8, 18, 185, 23);
		existingNetworkRetrieveDomainsCheckBox.setSelected(true);
		slimsearchOptionsPanel.add(existingNetworkRetrieveDomainsCheckBox);

		customSLiMsCheckBox.setSelected(false);
		customSLiMsCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (customSLiMsCheckBox.isSelected()) {
					customSLiMsTextField.setEnabled(true);
					customSLiMsbutton.setEnabled(true);
				} else {
					customSLiMsTextField.setEnabled(false);
					customSLiMsbutton.setEnabled(false);
				}

			}
		});
		customSLiMsCheckBox.setBounds(8, 45, 286, 23);
		slimsearchOptionsPanel.add(customSLiMsCheckBox);

		customSLiMsTextField = new JTextField();
		customSLiMsTextField.setEnabled(false);
		customSLiMsTextField.setBounds(75, 76, 368, 22);
		slimsearchOptionsPanel.add(customSLiMsTextField);
		customSLiMsTextField.setText("/home/kevin/Desktop/PCNA/slims.csv");
		customSLiMsTextField.setColumns(10);

		JLabel label_1 = new JLabel("SLiMs");
		label_1.setBounds(18, 76, 70, 15);
		slimsearchOptionsPanel.add(label_1);

		customSLiMsbutton = new JButton("Select");
		customSLiMsbutton.setEnabled(false);
		customSLiMsbutton.setBounds(455, 74, 107, 25);
		slimsearchOptionsPanel.add(customSLiMsbutton);
		customSLiMsbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updatePath(customSLiMsTextField);
			}
		});

		networkComboBox = new JComboBox(networkNames.toArray());
		networkComboBox.setBounds(174, 26, 208, 24);
		existingNetworkPanel.add(networkComboBox);

		uniprotComboBox = new JComboBox(Cytoscape.getNodeAttributes()
				.getAttributeNames());
		uniprotComboBox.setBounds(174, 120, 208, 24);
		existingNetworkPanel.add(uniprotComboBox);
		
		

		for (int i = 0; i < uniprotComboBox.getItemCount(); i++) {
			String entry = (String) uniprotComboBox.getItemAt(i);
			{
				if (entry.matches("[ A-Za-z0-9]*[uU][nN][iI][pP][rR][oO][tT][ A-Za-z0-9]*")) {
					uniprotComboBox.setSelectedIndex(i);
					break;
				}
			}
		}
		
		nodeLabelLabel = new JLabel("Node Label Attribute:");
		nodeLabelLabel.setBounds(12, 80, 161, 15);
		existingNetworkPanel.add(nodeLabelLabel);
		
		
		nodeLabelComboBox = new JComboBox(Cytoscape.getNodeAttributes()
				.getAttributeNames());
		nodeLabelComboBox.setBounds(174, 75, 208, 24);
		existingNetworkPanel.add(nodeLabelComboBox);
		nodeLabelComboBox.setSelectedIndex(0);
		
		
		for (int i = 0; i < nodeLabelComboBox.getItemCount(); i++) {
			String entry = (String) nodeLabelComboBox.getItemAt(i);
			{
				if (entry.matches("canonicalName")) {
					nodeLabelComboBox.setSelectedIndex(i);
					break;
				}
			}
		}

		this.variablesPanel.loadProperties(this.tabbedPane);
	}

	public void updatePath(JTextField textField) {
		final JFileChooser fc = new JFileChooser();
		// In response to a button click:
		int returnVal = fc.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			try {
				textField.setText(file.getCanonicalPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void invokeLater(Thread thread) {
		try {
			SwingUtilities.invokeAndWait(thread);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void applyVisualStyle(CyNetwork cyNetwork, CyNetworkView cNV) {

		javax.swing.JInternalFrame f = Cytoscape.getDesktop()
				.getNetworkViewManager().getInternalFrame(cNV);
		try {
			f.setMaximum(true);
		} catch (Exception ex) {
		}
		cNV.fitContent();

		// get the VisualMappingManager and CalculatorCatalog
		VisualMappingManager manager = Cytoscape.getVisualMappingManager();
		CalculatorCatalog catalog = manager.getCalculatorCatalog();

		// check to see if a visual style with this name already exists
		VisualStyle vs = catalog.getVisualStyle(Constants.getVisualStyleName());
		if (vs == null) {
			// if not, create it and add it to the catalog
			vs = VisualStyleBuilder.createPrimaryVisualStyle(cyNetwork);
			catalog.addVisualStyle(vs);
		}

		cNV.applyVizmapper(vs);

		//CyLayoutAlgorithm gridLayout = CyLayouts.getLayout("force-directed");
		//gridLayout.doLayout(cNV);
		cNV.redrawGraph(true, true);
	}

	public void applyLayout(CyNetwork cyNetwork) {
		String networkView = "SDV";
		CyNetworkView cNV = Cytoscape.createNetworkView(cyNetwork, networkView);
		CyLayoutAlgorithm gridLayout = CyLayouts.getLayout("force-directed");
		gridLayout.doLayout(cNV);
		cNV.redrawGraph(true, true);
	}

	public void reset() {
		CytoPanelImp ctrlPanel = (CytoPanelImp) Cytoscape.getDesktop()
				.getCytoPanel(SwingConstants.WEST);
		ctrlPanel.remove(rootPanel);
	}

	public void launchPlugin(CyNetwork network, CyNetworkView networkView,
			Variables variables, SetupWizardJFrame parent,
			TableDisplay slimPanel,	AttributeTable domainPanel, AggregateDomainAttributeTable domainPanelAggregate) {

		try {

			CytoPanelImp ctrlPanel = (CytoPanelImp) Cytoscape.getDesktop()
					.getCytoPanel(SwingConstants.WEST);

			// OptionsPanel optionsPanel = new OptionsPanel();
			rootPanel = new TabsPanel(network, networkView, variables,
					slimPanel, domainPanel,
					domainPanelAggregate);
			Dimension dimension = new Dimension(1000, 1000);
			rootPanel.setPreferredSize(dimension);

			ctrlPanel.add("SLiM Domain Panel", rootPanel);
			int indexInCytoPanel = ctrlPanel
					.indexOfComponent("SLiM Domain Panel");
			ctrlPanel.setSelectedIndex(indexInCytoPanel);

			parent.setVisible(false);
			ctrlPanel.updateUI();
		} catch (Exception e) {
			e.printStackTrace();
			parent.setEnabled(true);
		}
		parent.setEnabled(true);
	}

	public void useExistingNetwork(final Variables variables,
			final String slimsuiteHomeValue, final String runDirValue,
			final String iUPredPathVAlue,
			final JCheckBox customAttributesCheckBox) {

		log.info(variables.getOptionsString().replace(" ", "\n"));
		final SetupWizardJFrame tmp = this;
		Thread thread = new Thread(new Runnable() {
			public void run() {
				log.info("Preparing " + Constants.pluginName);

				boolean cancelled = false;
				boolean customAttributeSuccess = true;

				tmp.setEnabled(false);

				SLiMTableDisplay slimPanel = null;
				//SLiMTableDisplay slimPanelAggregate = null;
				CustomImportHelper customImportHelper = null;
				ImportCustomAttributes importCustomAttributes = null;

				if (customAttributesCheckBox.isSelected()) {
					File slims = new File(customSLiMsTextField.getText());
					if (slims.exists()) {
						customImportHelper = new CustomImportHelper(
								customSLiMsTextField.getText());
						if (customImportHelper.loadSLiMs()) {
							slimPanel = new SLiMTableDisplay(customImportHelper.getSLiMTitles());
							//slimPanelAggregate = new SLiMTableDisplay();

							importCustomAttributes = new ImportCustomAttributes(
									customImportHelper.getSLiMTitles(),
									customImportHelper.getSLiMs(), slimPanel);

							importCustomAttributes.start();
							try {
								importCustomAttributes.join();
								tmp.setEnabled(true);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								customAttributeSuccess = false;
							}

							if (importCustomAttributes.isSuccess()) {
							} else {
								Utilities.showMessage(tmp,
										"Could not import Custom Attributes");
								customAttributeSuccess = false;
							}
						} else {
							Utilities.showMessage(tmp,
									"Could not import Custom Attributes");
							tmp.setEnabled(true);
							customAttributeSuccess = false;
						}

					} else {
						customAttributeSuccess = false;
						Utilities.showMessage(tmp,
								"Could not find Custom Attributes file");
						tmp.setEnabled(true);
					}
				}

				if (customAttributeSuccess) {

					CyNetwork network = CytoscapeHelper
							.getNetwork((String) networkComboBox
									.getSelectedItem());
					String networkViewName = "SDV_" + network.getTitle()
							+ "_view";
					String uniprotAttribute = (String) uniprotComboBox
							.getSelectedItem();
					
					Constants.setLabelAttribute((String) nodeLabelComboBox.getSelectedItem());

					boolean domainSuccess = true;
					DomainAttributeTable domainPanel = null;
					AggregateDomainAttributeTable aggregateDomainPanel = null;
					if (existingNetworkRetrieveDomainsCheckBox.isSelected()) {
						
						

						ProgressBar progressBar = new ProgressBar(
								"Import Sequences");
						UniprotSequenceImporter importSequencesFromWeb = new UniprotSequenceImporter(
								uniprotAttribute, progressBar, network);
						progressBar.addCencelListener(importSequencesFromWeb);
						importSequencesFromWeb.start();
						try {
							importSequencesFromWeb.join();
							if (importSequencesFromWeb.isCancelled()) {
								cancelled = true;
							}
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							progressBar.dispose();
							e.printStackTrace();
							domainSuccess = false;
						}

						progressBar.dispose();

						if (domainSuccess && !cancelled) {
							progressBar = new ProgressBar("Import Domains");

							aggregateDomainPanel = new AggregateDomainAttributeTable(network, null);
							domainPanel = new DomainAttributeTable(network, null);
							ImportDomainsHTTPJavaWorker importDomainsFromWebWorkerThread = new ImportDomainsHTTPJavaWorker(
									network, domainPanel, aggregateDomainPanel,
									progressBar);
							progressBar
									.addCencelListener(importDomainsFromWebWorkerThread);
							importDomainsFromWebWorkerThread.start();
							try {
								importDomainsFromWebWorkerThread.join();
								if (importDomainsFromWebWorkerThread
										.isCancelled()) {
									cancelled = true;
								}
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								progressBar.dispose();
								domainSuccess = false;
							}
							progressBar.dispose();
						} else if (!cancelled) {
							Utilities.showMessage(tmp,
									"Error importing sequences");
							tmp.setEnabled(true);
						}

						if (domainSuccess && !cancelled) {
							CyNetworkView networkView = Cytoscape
									.createNetworkView(network, networkViewName);

							Constants.generateAllLabels(network);
							Constants.createAllAttributesForNetwork(network);

							launchPlugin(network, networkView, variables, tmp,
									slimPanel, domainPanel,
									aggregateDomainPanel);

							applyVisualStyle(network, networkView);

							tmp.setVisible(false);
						} else if (!cancelled) {
							Utilities.showMessage(tmp,
									"Error importing domains");
							tmp.setEnabled(true);
						}
					}

					if (cancelled) {
						tmp.setEnabled(true);
					}
				} else {
					tmp.setEnabled(true);
				}
			}
		});
		thread.start();
	}
}
