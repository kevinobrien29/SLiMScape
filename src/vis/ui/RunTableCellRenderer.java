package vis.ui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class RunTableCellRenderer extends DefaultTableCellRenderer {
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int col) {
		try {

			int modelRow = table.getRowSorter().convertRowIndexToModel(row);

			Color s = ((RunModel) table.getModel()).getRun(modelRow)
					.getBackgroundColor();
			setBackground(s);

			if (col == 0) {
				boolean marked = (Boolean) value;
				JCheckBox rendererComponent = new JCheckBox();
				if (marked) {
					rendererComponent.setSelected(true);
				}
				rendererComponent.setOpaque(true);
				rendererComponent.setBackground(s);
				rendererComponent.setForeground(s);

				if (isSelected) {
					rendererComponent.setBackground(table
							.getSelectionBackground());

				}
				return rendererComponent;
			} else if (col == 4) {
				String _formattedValue = null;

				Double _value = (Double) value;
				if (_value == -1.0) {
					value = "none";
				} 
				return super.getTableCellRendererComponent(table, value,
						isSelected, hasFocus, modelRow, col);
			}
			else
			{
				return super.getTableCellRendererComponent(table, value,
						isSelected, hasFocus, modelRow, col);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
}