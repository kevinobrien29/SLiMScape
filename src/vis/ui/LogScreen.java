package vis.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

public class LogScreen extends JPanel {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			LogScreen dialog = new LogScreen("");

			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private JTextArea textArea = null;
	String previousLine = "";
	int count = 0;
	JScrollPane scrollPane = null;

	/**
	 * Create the dialog.
	 */
	public LogScreen(String display) {
		this.setSize(720, 300);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 551, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 217, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblNewLabel = new JLabel(display);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		add(lblNewLabel, gbc_lblNewLabel);

		JSeparator separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.insets = new Insets(0, 0, 5, 5);
		gbc_separator.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator.gridx = 1;
		gbc_separator.gridy = 2;
		add(separator, gbc_separator);

		scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		textArea = new JTextArea();
		DefaultCaret caret = (DefaultCaret) textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		scrollPane.setViewportView(textArea);

		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 3;
		add(scrollPane, gbc_scrollPane);
	}

	public synchronized void appendText(String line) {
		
		try {
			if (count == 0) {
				//line = line + "\n";
				textArea.setText(line);
				previousLine = line;
				
			} else if (line.length() > 0){
				//line = line + "\n";
				if (!line.contains("%") && !line.equals(previousLine)) {
					textArea.append("\n" + line);
					previousLine = line;
				} else if (line.contains("%") && !previousLine.contains("%")) {
					textArea.append("\n" + line);
					previousLine = line;
				} else if (line.contains("%")) {
					previousLine = line;
				}
			}
			count++;
			//scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void appendSingleLine(final String line) {
		try {
			textArea.append(line + "\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized void appendChar(final String line) {
		try {
			textArea.append(line);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
