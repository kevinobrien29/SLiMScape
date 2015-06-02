package vis.ui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

import sun.swing.DefaultLookup;

public class DoubleRenderer extends DefaultTableCellRenderer {

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		
		String _formattedValue = null;
		
		Double _value = (Double) value;
		if (_value == 99999.0) {
			_formattedValue = "none";
		} else if (_value == 0)
		{
			_formattedValue = "0";
		} else if (_value == 1)
		{
			_formattedValue = "1";
		}
		else {
			_formattedValue = "" + _value;
		}
		
		
		if (table == null) {
            return this;
        }

        Color fg = null;
        Color bg = null;

        JTable.DropLocation dropLocation = table.getDropLocation();
        if (dropLocation != null
                && !dropLocation.isInsertRow()
                && !dropLocation.isInsertColumn()
                && dropLocation.getRow() == row
                && dropLocation.getColumn() == column) {

            fg = DefaultLookup.getColor(this, ui, "dropCellForeground");
            bg = DefaultLookup.getColor(this, ui, "dropCellBackground");

            isSelected = true;
        }

        if (isSelected) {
            super.setForeground(fg == null ? table.getSelectionForeground()
                                           : fg);
            super.setBackground(bg == null ? table.getSelectionBackground()
                                           : bg);
        }
        else
        {
        	super.setForeground(null);
        	super.setBackground(null);
        }

        setFont(table.getFont());

        if (hasFocus) {
            Border border = null;
            if (isSelected) {
                border = DefaultLookup.getBorder(this, ui, "Table.focusSelectedCellHighlightBorder");
            }
            if (border == null) {
                border = DefaultLookup.getBorder(this, ui, "Table.focusCellHighlightBorder");
            }
            setBorder(border);

            if (!isSelected && table.isCellEditable(row, column)) {
                Color col;
                col = DefaultLookup.getColor(this, ui, "Table.focusCellForeground");
                if (col != null) {
                    super.setForeground(col);
                }
                col = DefaultLookup.getColor(this, ui, "Table.focusCellBackground");
                if (col != null) {
                    super.setBackground(col);
                }
            }
        } else {
            setBorder(null);
        }

        setValue(_formattedValue);
        return this;
		
	}
}