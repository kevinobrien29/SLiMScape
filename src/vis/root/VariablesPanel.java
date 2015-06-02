package vis.root;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import vis.data.Constants;
import vis.tools.Tuple;
import vis.tools.Utilities;

public class VariablesPanel extends JPanel {
	private String configFilePath = System.getProperty("user.dir")
			+ "/slimscape.properties.txt";
	Variables variables = null;
	final JCheckBox pythonExecutableCheckBox = new JCheckBox(
			"Detect Automatically (Must be in PATH)");
	private JButton pythonHomeButton = new JButton("Select");
	private JTextField slimsuiteHomeTextField;
	private JTextField runsDirTextField;
	private JTextField iUPredPathTextField;
	private JTextField orthDBTextField;
	private JTextField blastpathTextField;
	private JTextField muscleTextField;
	private JTextField clustalwTextField;
	private JTextField pythonExecutable;

	/**
	 * Create the panel.
	 */
	public VariablesPanel() {

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 146, 416, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 88, 52, 51, 55, 48, 48, 52, 51,
				35, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel pythonExecLabel = new JLabel("Python Executable:");
		GridBagConstraints gbc_pythonExecLabel = new GridBagConstraints();
		gbc_pythonExecLabel.anchor = GridBagConstraints.EAST;
		gbc_pythonExecLabel.insets = new Insets(0, 0, 5, 5);
		gbc_pythonExecLabel.gridx = 0;
		gbc_pythonExecLabel.gridy = 0;
		add(pythonExecLabel, gbc_pythonExecLabel);

		JPanel pythonExecutablePanel = new JPanel();
		GridBagConstraints gbc_pythonExecutablePanel = new GridBagConstraints();
		gbc_pythonExecutablePanel.anchor = GridBagConstraints.SOUTH;
		gbc_pythonExecutablePanel.insets = new Insets(0, 0, 5, 5);
		gbc_pythonExecutablePanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_pythonExecutablePanel.gridx = 1;
		gbc_pythonExecutablePanel.gridy = 0;
		add(pythonExecutablePanel, gbc_pythonExecutablePanel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		pythonExecutablePanel.setLayout(gbl_panel);

		GridBagConstraints gbc_pythonExecutableCheckBox = new GridBagConstraints();
		gbc_pythonExecutableCheckBox.anchor = GridBagConstraints.WEST;
		gbc_pythonExecutableCheckBox.insets = new Insets(0, 0, 5, 0);
		gbc_pythonExecutableCheckBox.gridx = 0;
		gbc_pythonExecutableCheckBox.gridy = 0;
		pythonExecutablePanel.add(pythonExecutableCheckBox,
				gbc_pythonExecutableCheckBox);

		pythonExecutable = new JTextField();
		pythonExecutable.setText("");
		pythonExecutable.setColumns(10);
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 0;
		gbc_textField_1.gridy = 1;
		pythonExecutablePanel.add(pythonExecutable, gbc_textField_1);

		GridBagConstraints gbc_pythonHomeButton = new GridBagConstraints();
		gbc_pythonHomeButton.anchor = GridBagConstraints.SOUTH;
		gbc_pythonHomeButton.insets = new Insets(0, 0, 5, 0);
		gbc_pythonHomeButton.gridx = 2;
		gbc_pythonHomeButton.gridy = 0;
		add(pythonHomeButton, gbc_pythonHomeButton);

		pythonHomeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updatePath(pythonExecutable, false);
			}
		});

		pythonExecutableCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (pythonExecutableCheckBox.isSelected()) {
					pythonExecutable.setEnabled(false);
					pythonHomeButton.setEnabled(false);
				} else {
					pythonExecutable.setEnabled(true);
					pythonHomeButton.setEnabled(true);
				}

			}
		});
		pythonExecutableCheckBox.setSelected(true);
		pythonExecutable.setEnabled(false);
		pythonHomeButton.setEnabled(false);

		JLabel label = new JLabel("SLiMSuite Home:");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 1;
		add(label, gbc_label);

		slimsuiteHomeTextField = new JTextField();
		slimsuiteHomeTextField.setText("");
		slimsuiteHomeTextField.setColumns(10);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 1;
		add(slimsuiteHomeTextField, gbc_textField);

		JButton slimsuiteHomeButton = new JButton("Select");
		GridBagConstraints gbc_slimsuiteHomeButton = new GridBagConstraints();
		gbc_slimsuiteHomeButton.insets = new Insets(0, 0, 5, 0);
		gbc_slimsuiteHomeButton.gridx = 2;
		gbc_slimsuiteHomeButton.gridy = 1;
		add(slimsuiteHomeButton, gbc_slimsuiteHomeButton);

		slimsuiteHomeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updatePath(slimsuiteHomeTextField, true);
			}
		});

		JLabel label_1 = new JLabel("Output Directory:");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.anchor = GridBagConstraints.EAST;
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 0;
		gbc_label_1.gridy = 2;
		add(label_1, gbc_label_1);

		runsDirTextField = new JTextField();
		runsDirTextField.setText("");
		runsDirTextField.setColumns(10);
		GridBagConstraints gbc_runsDirTextField = new GridBagConstraints();
		gbc_runsDirTextField.insets = new Insets(0, 0, 5, 5);
		gbc_runsDirTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_runsDirTextField.gridx = 1;
		gbc_runsDirTextField.gridy = 2;
		add(runsDirTextField, gbc_runsDirTextField);

		JButton runsDirTextButton = new JButton("Select");
		GridBagConstraints gbc_runsDirTextButton = new GridBagConstraints();
		gbc_runsDirTextButton.insets = new Insets(0, 0, 5, 0);
		gbc_runsDirTextButton.gridx = 2;
		gbc_runsDirTextButton.gridy = 2;
		add(runsDirTextButton, gbc_runsDirTextButton);

		runsDirTextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updatePath(runsDirTextField, true);
			}
		});

		JLabel lblIupredBinary = new JLabel("IUPred Binary:");
		GridBagConstraints gbc_lblIupredBinary = new GridBagConstraints();
		gbc_lblIupredBinary.anchor = GridBagConstraints.EAST;
		gbc_lblIupredBinary.insets = new Insets(0, 0, 5, 5);
		gbc_lblIupredBinary.gridx = 0;
		gbc_lblIupredBinary.gridy = 3;
		add(lblIupredBinary, gbc_lblIupredBinary);

		iUPredPathTextField = new JTextField();
		iUPredPathTextField.setText("");
		iUPredPathTextField.setColumns(10);
		GridBagConstraints gbc_iUPredTextField = new GridBagConstraints();
		gbc_iUPredTextField.insets = new Insets(0, 0, 5, 5);
		gbc_iUPredTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_iUPredTextField.gridx = 1;
		gbc_iUPredTextField.gridy = 3;
		add(iUPredPathTextField, gbc_iUPredTextField);

		JButton iUPredPathTextButton = new JButton("Select");
		GridBagConstraints gbc_iUPredPathTextButton = new GridBagConstraints();
		gbc_iUPredPathTextButton.insets = new Insets(0, 0, 5, 0);
		gbc_iUPredPathTextButton.gridx = 2;
		gbc_iUPredPathTextButton.gridy = 3;
		add(iUPredPathTextButton, gbc_iUPredPathTextButton);

		iUPredPathTextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updatePath(iUPredPathTextField, false);
			}
		});

		JLabel orthDBLabel = new JLabel("OrthDB:");
		GridBagConstraints gbc_orthDBLabel = new GridBagConstraints();
		gbc_orthDBLabel.anchor = GridBagConstraints.EAST;
		gbc_orthDBLabel.insets = new Insets(0, 0, 5, 5);
		gbc_orthDBLabel.gridx = 0;
		gbc_orthDBLabel.gridy = 4;
		add(orthDBLabel, gbc_orthDBLabel);

		orthDBTextField = new JTextField();
		orthDBTextField.setText("");
		orthDBTextField.setColumns(10);
		GridBagConstraints gbc_orthDBTextField = new GridBagConstraints();
		gbc_orthDBTextField.insets = new Insets(0, 0, 5, 5);
		gbc_orthDBTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_orthDBTextField.gridx = 1;
		gbc_orthDBTextField.gridy = 4;
		add(orthDBTextField, gbc_orthDBTextField);

		JButton orthDBButton = new JButton("Select");
		orthDBButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updatePath(orthDBTextField, false);
			}
		});

		GridBagConstraints gbc_orthDBButton = new GridBagConstraints();
		gbc_orthDBButton.insets = new Insets(0, 0, 5, 0);
		gbc_orthDBButton.gridx = 2;
		gbc_orthDBButton.gridy = 4;
		add(orthDBButton, gbc_orthDBButton);

		JLabel lblastpathLabel = new JLabel("Blast bin directory:");
		GridBagConstraints gbc_lblastpathLabel = new GridBagConstraints();
		gbc_lblastpathLabel.anchor = GridBagConstraints.EAST;
		gbc_lblastpathLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblastpathLabel.gridx = 0;
		gbc_lblastpathLabel.gridy = 5;
		add(lblastpathLabel, gbc_lblastpathLabel);

		blastpathTextField = new JTextField();
		blastpathTextField.setText("");
		blastpathTextField.setColumns(10);
		GridBagConstraints gbc_blastpathTextField = new GridBagConstraints();
		gbc_blastpathTextField.insets = new Insets(0, 0, 5, 5);
		gbc_blastpathTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_blastpathTextField.gridx = 1;
		gbc_blastpathTextField.gridy = 5;
		add(blastpathTextField, gbc_blastpathTextField);

		JButton blastpathButton = new JButton("Select");
		GridBagConstraints gbc_blastpathButton = new GridBagConstraints();
		gbc_blastpathButton.insets = new Insets(0, 0, 5, 0);
		gbc_blastpathButton.gridx = 2;
		gbc_blastpathButton.gridy = 5;
		add(blastpathButton, gbc_blastpathButton);

		blastpathButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updatePath(blastpathTextField, true);
			}
		});

		JLabel muscleLabel = new JLabel("Muscle Binary:");
		GridBagConstraints gbc_muscleLabel = new GridBagConstraints();
		gbc_muscleLabel.anchor = GridBagConstraints.EAST;
		gbc_muscleLabel.insets = new Insets(0, 0, 5, 5);
		gbc_muscleLabel.gridx = 0;
		gbc_muscleLabel.gridy = 6;
		add(muscleLabel, gbc_muscleLabel);

		muscleTextField = new JTextField();
		muscleTextField.setText("");
		muscleTextField.setColumns(10);
		GridBagConstraints gbc_muscleTextField = new GridBagConstraints();
		gbc_muscleTextField.insets = new Insets(0, 0, 5, 5);
		gbc_muscleTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_muscleTextField.gridx = 1;
		gbc_muscleTextField.gridy = 6;
		add(muscleTextField, gbc_muscleTextField);

		JButton muscleButton = new JButton("Select");
		GridBagConstraints gbc_muscleButton = new GridBagConstraints();
		gbc_muscleButton.insets = new Insets(0, 0, 5, 0);
		gbc_muscleButton.gridx = 2;
		gbc_muscleButton.gridy = 6;
		add(muscleButton, gbc_muscleButton);

		muscleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updatePath(muscleTextField, false);
			}
		});

		JLabel clustalwLabel = new JLabel("ClustalW Binary:");
		GridBagConstraints gbc_clustalwLabel = new GridBagConstraints();
		gbc_clustalwLabel.anchor = GridBagConstraints.EAST;
		gbc_clustalwLabel.insets = new Insets(0, 0, 5, 5);
		gbc_clustalwLabel.gridx = 0;
		gbc_clustalwLabel.gridy = 7;
		add(clustalwLabel, gbc_clustalwLabel);

		clustalwTextField = new JTextField();
		clustalwTextField.setText("");
		clustalwTextField.setColumns(10);
		GridBagConstraints gbc_clustalwTextField = new GridBagConstraints();
		gbc_clustalwTextField.insets = new Insets(0, 0, 5, 5);
		gbc_clustalwTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_clustalwTextField.gridx = 1;
		gbc_clustalwTextField.gridy = 7;
		add(clustalwTextField, gbc_clustalwTextField);

		JButton clustalwButton = new JButton("Select");
		GridBagConstraints gbc_clustalwButton = new GridBagConstraints();
		gbc_clustalwButton.insets = new Insets(0, 0, 5, 0);
		gbc_clustalwButton.gridx = 2;
		gbc_clustalwButton.gridy = 7;
		add(clustalwButton, gbc_clustalwButton);

		clustalwButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updatePath(clustalwTextField, false);
			}
		});

		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveProperties();
			}
		});
		GridBagConstraints gbc_saveButton = new GridBagConstraints();
		gbc_saveButton.gridx = 2;
		gbc_saveButton.gridy = 8;
		add(saveButton, gbc_saveButton);

	}

	public Variables getVariables() {
		return variables;
	}

	public void saveProperties() {
		Properties prop = new Properties();

		try {

			Boolean detectPythonAutomatically = this.pythonExecutableCheckBox
					.isSelected();
			String pythonHome = this.pythonExecutable.getText();
			String slimSuiteHome = this.slimsuiteHomeTextField.getText();
			String runDir = this.runsDirTextField.getText();
			String iUPredPath = this.iUPredPathTextField.getText();
			String orthDB = this.orthDBTextField.getText();
			String blastpath = this.blastpathTextField.getText();
			String musclepath = this.muscleTextField.getText();
			String clustalwpath = this.clustalwTextField.getText();

			// set the properties value
			if (pythonExecutableCheckBox.isSelected()) {
				prop.setProperty("pythonhome", "");
			} else {
				prop.setProperty("pythonhome", pythonHome);
			}
			prop.setProperty("detectPythonAutomatically", ""
					+ detectPythonAutomatically);
			prop.setProperty("slimsuitehome", slimSuiteHome);
			prop.setProperty("runsdirectory", runDir);
			prop.setProperty("iupredpath", iUPredPath);
			prop.setProperty("orthdbpath", orthDB);
			prop.setProperty("blastpath", blastpath);
			prop.setProperty("musclepath", musclepath);
			prop.setProperty("clustalwpath", clustalwpath);

			// save properties to project root folder
			prop.store(new FileOutputStream(this.configFilePath), null);

			/*
			 * this.variables = new Variables(); if
			 * (isValid(iUPredPathDefaultValue)) {
			 * variables.setIupredReady(true); } else {
			 * variables.setIupredReady(false); } if (isValid(orthDB)) {
			 * variables.setConservationReady(true); } else {
			 * variables.setConservationReady(false); } if
			 * (isValid(slimSuiteHome)) { if (isValid(runDir)) {
			 * 
			 * } }
			 */

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public boolean checkValid() {
		// String pythonHome = this.pythonExecutable.getText();

		String slimSuiteHome = this.slimsuiteHomeTextField.getText();
		String runDir = this.runsDirTextField.getText();
		String iUPredPath = this.iUPredPathTextField.getText();
		String orthDB = this.orthDBTextField.getText();
		String blastpath = this.blastpathTextField.getText();
		String musclepath = this.muscleTextField.getText();
		String clustalwpath = this.clustalwTextField.getText();

		Tuple<String, String>[] tuples = new Tuple[7];
		tuples[0] = new Tuple<String, String>("Slimsuite home", slimSuiteHome);
		tuples[1] = new Tuple<String, String>("Run dir", runDir);
		tuples[2] = new Tuple<String, String>("iUPred Path", iUPredPath);
		tuples[3] = new Tuple<String, String>("OrthDB", orthDB);
		tuples[4] = new Tuple<String, String>("Blast path", blastpath);
		tuples[5] = new Tuple<String, String>("Muscle path", musclepath);
		tuples[6] = new Tuple<String, String>("Clustalw path", clustalwpath);

		for (Tuple<String, String> tuple : tuples) {
			File tmp = new File(tuple.y);
			if (!tmp.exists()) {
				Object[] options = { "Yes", "No" };
				int n = JOptionPane
						.showOptionDialog(
								this,
								tuple.x
										+ " does not exist. Are you sure you want to continue?",
								"", JOptionPane.YES_NO_CANCEL_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options,
								options[0]);
				if (n == 0) {
					return true;
				} else {
					return false;
				}

			}
		}
		return true;
	}

	public boolean isValid(String path) {
		File tmp = new File(path);
		if (tmp.exists()) {
			if (tmp.canRead()) {
				if (tmp.canWrite()) {
					return true;
				}
			}
		}
		return false;
	}

	public void loadProperties(JTabbedPane tabbedPane) {
		if (new File(configFilePath).exists()) {
			Properties prop = new Properties();
			try {
				prop.load(new FileInputStream(configFilePath));

				if (prop.getProperty("detectPythonAutomatically") != null) {
					String detectPythonAutomaticallyString = prop
							.getProperty("detectPythonAutomatically");
					if (detectPythonAutomaticallyString.equals("true")) {
						pythonExecutableCheckBox.setSelected(true);
						pythonExecutable.setEnabled(false);
					} else {
						pythonExecutableCheckBox.setSelected(false);
						pythonExecutable.setEnabled(true);
						pythonHomeButton.setEnabled(true);
					}
				} else {
					pythonExecutableCheckBox.setSelected(false);
					pythonExecutable.setEnabled(true);
					pythonHomeButton.setEnabled(true);
				}

				String pythonHome = prop.getProperty("pythonhome");
				String slimSuiteHome = prop.getProperty("slimsuitehome");
				String runDir = prop.getProperty("runsdirectory");
				String iUPredPathDefaultValue = prop.getProperty("iupredpath");
				String orthDB = prop.getProperty("orthdbpath");
				String blastpath = prop.getProperty("blastpath");
				String musclepath = prop.getProperty("musclepath");
				String clustalwpath = prop.getProperty("clustalwpath");

				this.pythonExecutable.setText(pythonHome);
				this.slimsuiteHomeTextField.setText(slimSuiteHome);
				this.runsDirTextField.setText(runDir);
				this.iUPredPathTextField.setText(iUPredPathDefaultValue);
				this.orthDBTextField.setText(orthDB);
				this.blastpathTextField.setText(blastpath);
				this.muscleTextField.setText(musclepath);
				this.clustalwTextField.setText(clustalwpath);

				this.variables = new Variables(pythonHome, slimSuiteHome,
						runDir, iUPredPathDefaultValue, orthDB, blastpath,
						musclepath, clustalwpath);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JOptionPane
						.showMessageDialog(this, "properties file not found");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "properties file IO error");
			}
		} else {
			JOptionPane
					.showMessageDialog(
							this,
							"This appears to be the first time you have run "
									+ Constants.pluginName
									+ ". Please fill in the options on the options screen.");
			tabbedPane.setSelectedIndex(1);
		}

	}

	public Variables getCurrentPropereties() {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(configFilePath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String pythonHome = prop.getProperty("pythonhome");
		String detectPythonAutomaticallyString = prop
				.getProperty("detectPythonAutomatically");

		if (detectPythonAutomaticallyString != null) {
			if (detectPythonAutomaticallyString.equals("true")) {
				pythonHome = "";
			}
		}

		String slimSuiteHome = slimsuiteHomeTextField.getText();
		String runDir = runsDirTextField.getText();
		String iUPredPathDefaultValue = iUPredPathTextField.getText();
		String orthDB = orthDBTextField.getText();
		String blastpath = blastpathTextField.getText();
		String musclepath = muscleTextField.getText();
		String clustalwpath = clustalwTextField.getText();

		this.variables = new Variables(pythonHome, slimSuiteHome, runDir,
				iUPredPathDefaultValue, orthDB, blastpath, musclepath,
				clustalwpath);
		return variables;

	}

	private void updatePath(JTextField textField, boolean folder) {
		final JFileChooser fc = new JFileChooser();
		if (folder) {
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		}
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
}
