package vis.data.attribute;

import java.util.ArrayList;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;

import vis.data.Constants;
import cytoscape.Cytoscape;

public class AttributeModel extends AbstractTableModel implements
		ChangeListener {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	private String[] columnNames = null;
	private ArrayList<Attribute> data = new ArrayList<Attribute>();
	private Double evalueCutoff = 99999999.0;

	public AttributeModel(String[] columnNames) {
		super();
		this.columnNames = columnNames;
	}

	public void addRow(Attribute attribute) {

		String y = "";
		for (String x : this.columnNames) {
			y = y + "    " + x;
		}

		String a = "";
		for (int i = 0; i < attribute.getSize(); i++) {
			a = a + "    " + attribute.getValueAt(i);
		}

		data.add(attribute);
		this.fireTableDataChanged();
	}

	public Attribute getAttribute(int row) {
		return data.get(row);
	}

	public Attribute getAttribute(String name) {

		for (Attribute attribute : data) {
			if (attribute.getName().equals(name)) {
				return attribute;
			}
		}
		return null;
	}

	/*
	 * JTable uses this method to determine the default renderer/ editor for
	 * each cell. If we didn't implement this method, then the last column would
	 * contain text ("true"/"false"), rather than a check box.
	 */
	@Override
	public Class getColumnClass(int c) {
		try {

			if (data.size() == 0) {
				return null;
			}
			Attribute attribute = data.get(0);
			return attribute.getValueAt(c).getClass();
		} catch (NullPointerException e) {
			e.printStackTrace();
			// System.exit(1);
		}
		return null;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Attribute attribute = data.get(row);

		return attribute.getValueAt(col);
	}

	/*
	 * Don't need to implement this method unless your table's editable.
	 */
	@Override
	public boolean isCellEditable(int row, int col) {
		// Note that the data/cell address is constant,
		// no matter where the cell appears onscreen.
		if (col == Attribute.getSwitchIndex()) {
			return true;
		} else {
			return false;
		}
	}
	
	public ArrayList<Attribute> getData() {
		return data;
	}

	public void replaceModelData(ArrayList<Attribute> data) {
		//String y = "";
		//for (String x : this.columnNames) {
		//	y = y + "    " + x;
		//}
		this.data = data;
		// Collections.sort(this.data);
		if (data.size() > 0) {
			this.fireTableRowsInserted(0, data.size() - 1);
		}
		this.fireTableDataChanged();
		this.fireTableStructureChanged();
	}

	public void replaceModelData(String[] titles, ArrayList<Attribute> data) {
		this.columnNames = titles;
		System.out.println(this.columnNames.length);
		this.data = data;
		// Collections.sort(this.data);
		if (data.size() > 0) {
			this.fireTableRowsInserted(0, data.size() - 1);
		}
		//this.fireTableDataChanged();
		this.fireTableStructureChanged();
	}

	public ArrayList<Attribute> getModelData() {
		return data;
	}

	/*
	 * Don't need to implement this method unless your table's data can change.
	 */
	@Override
	public void setValueAt(Object value, int row, int col) {

		//synchronized (this.getClass()) {

			Attribute existingAttribute = data.get(row);

			if (col == existingAttribute.getSwitchIndex()) {
				if (existingAttribute.isActive() == false) {
					existingAttribute.setActive(true);
					existingAttribute.activate(evalueCutoff);
				} else {
					existingAttribute.setActive(false);
					existingAttribute.deActivate(evalueCutoff);
				}
				
				Cytoscape.getCurrentNetworkView().updateView();
			}
		//}
	}

	public void deactivateActiveNodes() {

		for (Attribute attribute : data) {
			if (attribute.isActive()) {
				attribute.deActivate(0.0);
			}
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		final JSlider source = (JSlider) e.getSource();
		if (!source.getValueIsAdjusting()) {
			// TODO Auto-generated method stub
			JSlider slider = (JSlider) e.getSource();
			evalueCutoff = ((double) slider.getValue()) / Constants.divider;
		}
	}
}