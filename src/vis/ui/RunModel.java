package vis.ui;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.table.AbstractTableModel;

import vis.data.run.Run;
import cytoscape.Cytoscape;

public class RunModel extends AbstractTableModel {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	private String[] columnNames = null;
	private ArrayList<Run> data = new ArrayList<Run>();
	int switchIndex;

	public RunModel(String[] columnNames, int switchIndex) {
		super();
		this.switchIndex = switchIndex;
		this.columnNames = columnNames;
	}
	
	

	public Run getRun(int row) {
		return data.get(row);
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
			Run Run = data.get(0);
			return Run.getValueAt(c).getClass();
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.exit(1);
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
	
	public ArrayList<Run> getModelData() {
		return this.data;
	}

	@Override
	public Object getValueAt(int row, int col) {
		Run Run = data.get(row);

		return Run.getValueAt(col);
	}
	
	public Object getStringValueAt(int row, int col) {
		Run Run = data.get(row);

		return Run.getStringValueAt(col);
	}

	/*
	 * Don't need to implement this method unless your table's editable.
	 */
	@Override
	public boolean isCellEditable(int row, int col) {
		// Note that the data/cell address is constant,
		// no matter where the cell appears onscreen.
		if (col == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public void clearData()
	{
		data = new ArrayList<Run>();
		this.fireTableDataChanged();
	}
	
	public void removeRun(Run run)
	{
		this.data.remove(run);
		this.fireTableDataChanged();
		this.fireTableStructureChanged();
	}

	public void replaceModelData(ArrayList<Run> data) {
		String y = "";
		for (String x : this.columnNames) {
			y = y + "    " + x;
		}
		this.data = data;
		Collections.sort(this.data);
		if (data.size() > 0) {
			this.fireTableRowsInserted(0, data.size() - 1);
			this.fireTableStructureChanged();
		}
	}

	public void replaceModelData(String[] titles, ArrayList<Run> data) {
		this.columnNames = titles;
		this.data = data;
		Collections.sort(this.data);
		if (data.size() > 0) {
			this.fireTableRowsInserted(0, data.size() - 1);
		}
		this.fireTableDataChanged();
		this.fireTableStructureChanged();
	}

	public void addRow(Run Run) {
		data.add(Run);
		this.fireTableDataChanged();
	}

	public Run getRun(String name) {

		for (Run Run : data) {
			if (Run.getId().equals(name)) {
				return Run;
			}
		}
		return null;
	}

	/*
	 * Don't need to implement this method unless your table's data can change.
	 */
	@Override
	public void setValueAt(Object value, int row, int col) {

		Run existingRun = data.get(row);

		if (col == existingRun.getSwitchIndex()) {
			if (((Boolean) existingRun.isActive()) == false) {
				existingRun.activate();
			} else {
				existingRun.deActivate();
			}
			//existingRun.setActive((Boolean) value);
			Cytoscape.getCurrentNetworkView().updateView();
		}
	}
}