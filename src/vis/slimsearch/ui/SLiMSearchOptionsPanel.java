package vis.slimsearch.ui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import vis.root.Variables;

public class SLiMSearchOptionsPanel extends JPanel {
	private JTextField probabililtyCutoffTextField = null;
	private JCheckBox disorderMakingCheckBox = null;
	private JTextArea customParametersTextArea = null;
	private JCheckBox conservationCheckBox = null;
	private Variables variables;
	
	
	/**
	 * Create the panel.
	 */
	public SLiMSearchOptionsPanel(Variables variables) {
		this.variables = variables;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{450, 0};
		gridBagLayout.rowHeights = new int[]{97, 93, 97, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JPanel maskingPanel = new JPanel();
		maskingPanel.setBorder(new TitledBorder(null, "Masking", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_maskingPanel = new GridBagConstraints();
		gbc_maskingPanel.fill = GridBagConstraints.BOTH;
		gbc_maskingPanel.insets = new Insets(0, 0, 5, 0);
		gbc_maskingPanel.gridx = 0;
		gbc_maskingPanel.gridy = 0;
		add(maskingPanel, gbc_maskingPanel);
		GridBagLayout gbl_maskingPanel = new GridBagLayout();
		gbl_maskingPanel.columnWidths = new int[]{0, 0, 0};
		gbl_maskingPanel.rowHeights = new int[]{0, 0, 0};
		gbl_maskingPanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_maskingPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		maskingPanel.setLayout(gbl_maskingPanel);
		
		this.disorderMakingCheckBox = new JCheckBox("Disorder Masking");
		this.disorderMakingCheckBox.setSelected(true);
		GridBagConstraints gbc_disorderMakingCheckBox = new GridBagConstraints();
		gbc_disorderMakingCheckBox.anchor = GridBagConstraints.WEST;
		gbc_disorderMakingCheckBox.insets = new Insets(0, 0, 5, 5);
		gbc_disorderMakingCheckBox.gridx = 0;
		gbc_disorderMakingCheckBox.gridy = 0;
		maskingPanel.add(this.disorderMakingCheckBox, gbc_disorderMakingCheckBox);
		
		this.disorderMakingCheckBox = new JCheckBox("Disorder Masking");
		if (variables.iupredIsReady())
		{
			this.disorderMakingCheckBox.setSelected(true);
		}
		else
		{
			this.disorderMakingCheckBox.setSelected(false);
			disorderMakingCheckBox.setEnabled(false);
		}
		
		conservationCheckBox = new JCheckBox("Conservation Masking");
		conservationCheckBox.setSelected(true);
		GridBagConstraints gbc_conservationCheckBox = new GridBagConstraints();
		gbc_conservationCheckBox.anchor = GridBagConstraints.WEST;
		gbc_conservationCheckBox.insets = new Insets(0, 0, 0, 5);
		gbc_conservationCheckBox.gridx = 0;
		gbc_conservationCheckBox.gridy = 1;
		maskingPanel.add(conservationCheckBox, gbc_conservationCheckBox);
		
		if (variables.conservationIsReady())
		{
			this.conservationCheckBox.setSelected(true);
		}
		else
		{
			this.conservationCheckBox.setSelected(false);
			conservationCheckBox.setEnabled(false);
		}
		
		JPanel SLiMChance = new JPanel();
		SLiMChance.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "SLiM Chance", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		GridBagConstraints gbc_SLiMChance = new GridBagConstraints();
		gbc_SLiMChance.fill = GridBagConstraints.BOTH;
		gbc_SLiMChance.insets = new Insets(0, 0, 5, 0);
		gbc_SLiMChance.gridx = 0;
		gbc_SLiMChance.gridy = 1;
		add(SLiMChance, gbc_SLiMChance);
		GridBagLayout gbl_SLiMChance = new GridBagLayout();
		gbl_SLiMChance.columnWidths = new int[]{0, 0, 0};
		gbl_SLiMChance.rowHeights = new int[]{0, 0};
		gbl_SLiMChance.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_SLiMChance.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		SLiMChance.setLayout(gbl_SLiMChance);
		
		JLabel labelProbabilityCutOff = new JLabel("Probability cut-off:");
		labelProbabilityCutOff.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblProbabilityCutOff = new GridBagConstraints();
		gbc_lblProbabilityCutOff.anchor = GridBagConstraints.EAST;
		gbc_lblProbabilityCutOff.insets = new Insets(0, 0, 0, 5);
		gbc_lblProbabilityCutOff.gridx = 0;
		gbc_lblProbabilityCutOff.gridy = 0;
		SLiMChance.add(labelProbabilityCutOff, gbc_lblProbabilityCutOff);
		
		probabililtyCutoffTextField = new JTextField();
		probabililtyCutoffTextField.setHorizontalAlignment(SwingConstants.LEFT);
		probabililtyCutoffTextField.setText("1");
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.anchor = GridBagConstraints.WEST;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		SLiMChance.add(probabililtyCutoffTextField, gbc_textField);
		probabililtyCutoffTextField.setColumns(10);
		
		JPanel customPanel = new JPanel();
		customPanel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Custom Parameters", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		GridBagConstraints gbc_customPanel = new GridBagConstraints();
		gbc_customPanel.fill = GridBagConstraints.BOTH;
		gbc_customPanel.gridx = 0;
		gbc_customPanel.gridy = 2;
		add(customPanel, gbc_customPanel);
		GridBagLayout gbl_customPanel = new GridBagLayout();
		gbl_customPanel.columnWidths = new int[]{0, 0};
		gbl_customPanel.rowHeights = new int[]{0, 0};
		gbl_customPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_customPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		customPanel.setLayout(gbl_customPanel);
		
		customParametersTextArea = new JTextArea();
		GridBagConstraints gbc_customParametersTextArea = new GridBagConstraints();
		gbc_customParametersTextArea.fill = GridBagConstraints.BOTH;
		gbc_customParametersTextArea.gridx = 0;
		gbc_customParametersTextArea.gridy = 0;
		customPanel.add(customParametersTextArea, gbc_customParametersTextArea);
	}
	
	public SLiMSearchOptions getSLiMFinderOptions()
	{
		SLiMSearchOptions sLiMFinderOptions = new SLiMSearchOptions(variables);
		sLiMFinderOptions.setDismask(disorderMakingCheckBox.isEnabled());
		sLiMFinderOptions.setCutoff(Double.parseDouble(probabililtyCutoffTextField.getText()));
		sLiMFinderOptions.setCustomParameters(customParametersTextArea.getText());
		sLiMFinderOptions.setConsmask(conservationCheckBox.isSelected());
		return sLiMFinderOptions;
	}

}


