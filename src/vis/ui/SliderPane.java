package vis.ui;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeListener;

import vis.data.Constants;

public class SliderPane extends JPanel {
	private JTextField textField = null;
	private JSlider slider = null;
	JComboBox comboBox = null;
	private static Dimension customSize = new Dimension(500, 80);

	/**
	 * Create the panel.
	 */
	public SliderPane(String attribute, double min, double max, ChangeListener changeListener, ActionListener actionListener) {
		setLayout(null);
		this.setPreferredSize(customSize);
		this.setMinimumSize(customSize);
		
		
		slider = new JSlider(SwingConstants.HORIZONTAL, (int)(min * Constants.divider),
				(int)(max * Constants.divider), (int)(max * Constants.divider));
		slider.setBounds(153, 23, 175, 27);
		slider.setName(attribute + " cutoff:");
		slider.addChangeListener(changeListener);
		
		Hashtable labelTable = new Hashtable();
		labelTable.put(min, new JLabel("" + min));
		labelTable.put(max/2, new JLabel("" + (max/2)));
		labelTable.put(max, new JLabel("" + max));
		
		slider.setLabelTable(labelTable);
		
		
		
		JLabel attributeName = new JLabel(attribute);
		attributeName.setBounds(12, 23, 78, 27);
		this.add(attributeName);
		
		this.add(slider);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(340, 23, 90, 28);
		textField.setText("" + max);
		this.add(textField);
		textField.setColumns(10);

	}

	public static Dimension getCustomSize() {
		return customSize;
	}

	public JTextField getTextField() {
		return textField;
	}
	
	public String getComparisonType() {
		return (String)comboBox.getSelectedItem();
	}

	public JSlider getSlider() {
		return slider;
	}
}
