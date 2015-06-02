package vis.ui;

import java.awt.FontMetrics;

import javax.swing.JTable;

public class ColumnSizeMaker {

	JTable table = null;
	Integer[] minColumnWidths;
	String[] columnNames = null;
	int checkboxColumn = 1;

	public ColumnSizeMaker(JTable table, String[] exampleRow, int checkboxColumn) {
		this.table = table;
		this.minColumnWidths = new Integer[exampleRow.length];
		this.columnNames = exampleRow;
		this.checkboxColumn = checkboxColumn;
	}

	private int getWidth() {
		int max = 0;
		for (int i : minColumnWidths) {
			max = max + i;
		}
		return max;
	}

	private int getHeight() {
		return table.getRowHeight() * table.getRowCount();
	}

	public void initialiseColLengthTracker() {
		minColumnWidths = new Integer[columnNames.length];
		int i = 0;
		FontMetrics metrics = table.getFontMetrics(table.getFont());
		for (String colName : columnNames) {
			int n = metrics.stringWidth(colName);
			minColumnWidths[i] = n;
			i++;
		}
	}

	public void applyColLengths() {
		for (int j = 0; j < table.getModel().getColumnCount() - 1; j++) {
			if (j != checkboxColumn) {
				table.getColumnModel().getColumn(j)
						.setPreferredWidth(minColumnWidths[j] + 20);
				table.getColumnModel().getColumn(j)
						.setMinWidth(minColumnWidths[j] + 20);
				table.getColumnModel().getColumn(j)
						.setMaxWidth(minColumnWidths[j] * 2);
			} else {
				table.getColumnModel().getColumn(j)
						.setPreferredWidth(minColumnWidths[j]);
				table.getColumnModel().getColumn(j)
						.setMinWidth(minColumnWidths[j]);
				table.getColumnModel().getColumn(j)
						.setMaxWidth(minColumnWidths[j]);
			}

		}

		table.getColumnModel()
				.getColumn(table.getModel().getColumnCount() - 1)
				.setPreferredWidth(
						minColumnWidths[table.getModel().getColumnCount() - 1]);
		table.getColumnModel()
				.getColumn(table.getModel().getColumnCount() - 1)
				.setMinWidth(
						minColumnWidths[table.getModel().getColumnCount() - 1]);
	}

	public void revaluateColSizes() {
		for (int i = 0; i < table.getModel().getColumnCount(); i++) {
			minColumnWidths[i] = 0;
		}

		FontMetrics metrics = table.getFontMetrics(table.getFont());
		for (int i = 0; i < table.getModel().getRowCount(); i++) {
			for (int j = 0; j < table.getModel().getColumnCount(); j++) {
				if (j != checkboxColumn) {
					Integer n = metrics.stringWidth(""
							+ table.getModel().getValueAt(i, j));
					if (n != null) {
						if (n > minColumnWidths[j]) {
							minColumnWidths[j] = n;
						}
					}
				} else {
					minColumnWidths[checkboxColumn] = 20;
				}
			}
		}

		int counter = 0;
		for (String colName : columnNames) {
			int n = metrics.stringWidth(colName);
			if (n > minColumnWidths[counter]) {
				if (counter != checkboxColumn) {
					minColumnWidths[counter] = n + 5;
				}
			}
			counter++;
		}
	}
}
