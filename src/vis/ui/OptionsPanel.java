package vis.ui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class OptionsPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public OptionsPanel() {
		setLayout(null);
		
		JPanel visualOptions = new JPanel();
		visualOptions.setBorder(new TitledBorder(null, "Visual Options", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		visualOptions.setBounds(12, 28, 325, 108);
		add(visualOptions);
		visualOptions.setLayout(null);
		
		JLabel lblLabelAttribute = new JLabel("Label Attribute:");
		lblLabelAttribute.setBounds(12, 31, 120, 22);
		visualOptions.add(lblLabelAttribute);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(175, 31, 138, 23);
		visualOptions.add(comboBox);
		
		JButton btnNewButton = new JButton("Apply");
		btnNewButton.setBounds(196, 71, 117, 25);
		visualOptions.add(btnNewButton);

	}
}
