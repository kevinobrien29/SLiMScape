package vis.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class NoResultsPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public NoResultsPanel(String logPath, String stdout, String stderr,
			String message) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblNoResults = new JLabel("No Results");
		GridBagConstraints gbc_lblNoResults = new GridBagConstraints();
		gbc_lblNoResults.anchor = GridBagConstraints.WEST;
		gbc_lblNoResults.insets = new Insets(0, 0, 5, 0);
		gbc_lblNoResults.gridx = 1;
		gbc_lblNoResults.gridy = 1;
		add(lblNoResults, gbc_lblNoResults);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 2;
		add(scrollPane, gbc_scrollPane);

		FileInputStream inMessage = null;
		try {
			inMessage = new FileInputStream(logPath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Get the object of DataInputStream
		DataInputStream in = new DataInputStream(inMessage);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		String strLine;
		StringBuilder sb = new StringBuilder();
		// Read File Line By Line
		try {
			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				sb.append(strLine + "\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String result = sb.toString().trim();
		if (result == null) {
			result = stdout + "\n" + stderr;
		}
		else
		{
			if (result.length() < 5)
			{
				result = stdout + "\n" + stderr;
			}
		}
		JTextArea textArea = new JTextArea(result);
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);

		
	}

}
