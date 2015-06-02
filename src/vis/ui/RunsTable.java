package vis.ui;

import java.awt.CardLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import vis.data.attribute.Attribute;
import vis.data.run.Run;
import vis.exec.WorkQueue;
import vis.root.Variables;
import cytoscape.CyNetwork;
import cytoscape.CyNode;
import cytoscape.view.CyNetworkView;

public abstract class RunsTable extends JTable implements MouseListener,
		HierarchyListener {
	private RunModel model = null;
	private JPanel resultCards = null;
	private WorkQueue workQueue = null;
	private final RunsTable tmp = this;
	private Integer[] minColumnWidths;
	private String[] columnNames = null;
	private ColumnSizeMaker columnSizeMaker = null;
	private CyNetwork network = null;
	private CyNetworkView networkView = null;
	public Variables variables = null;
	int switchIndex = 0;
	AttributeTable attributeTable = null;

	public RunsTable(CyNetwork network, CyNetworkView networkView,
			Variables variables, String[] columnNames, WorkQueue workQueue,
			JPanel resultCards) {
		super();
		this.networkView = networkView;
		this.network = network;
		this.variables = variables;
		this.columnNames = columnNames;
		model = new RunModel(columnNames, switchIndex);
		this.setModel(model);
		addMouseListener(this);
		this.resultCards = resultCards;

		TableRowSorter sorter = new TableRowSorter<RunModel>(model);
		this.setRowSorter(sorter);
		sorter.setSortsOnUpdates(true);
		this.workQueue = workQueue;

		this.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

		columnSizeMaker = new ColumnSizeMaker(this, columnNames, switchIndex);
		columnSizeMaker.initialiseColLengthTracker();
		columnSizeMaker.applyColLengths();

		// this.setPreferredSize(new Dimension(this.getPreferredSize().width,
		// this.getRowHeight()*10));

		// this.getColumnModel().getColumn(1).setCellRenderer(new
		// AttributeTableCellRenderer());
	}

	private void colorColumns() {
		for (int i = 0; i < this.getColumnModel().getColumnCount(); i++) {
			TableColumn col = this.getColumnModel().getColumn(i);
			col.setCellRenderer(new RunTableCellRenderer());
		}
	}

	public void setColumnWidths() {
		int count = 0;
		for (Integer width : minColumnWidths) {
			this.getColumnModel().getColumn(count).setMaxWidth(width);
			count++;
		}
	}

	public void removeInvalidRuns() {
		ArrayList<Run> originalData = model.getModelData();
		ArrayList<Run> newData = new ArrayList<Run>();
		for (Run originalRun : originalData) {
			if (!originalRun.isToBeRemoved()) {
				newData.add(originalRun);
			} else {
				if (originalRun.isActive()) {
					originalRun.deActivate();
				}
				originalRun.deactivateChildAttributes();
			}

		}
		model.replaceModelData(newData);
		this.clearSelection();
		colorColumns();
		columnSizeMaker.revaluateColSizes();
		columnSizeMaker.applyColLengths();
	}

	public Run getRun(int row) {
		return this.model.getRun(row);
	}

	public void addRow(Run run) {
		model.addRow(run);
		colorColumns();
		columnSizeMaker.revaluateColSizes();
		columnSizeMaker.applyColLengths();
		model.fireTableDataChanged();
		// new TablePacker(TablePacker.VISIBLE_ROWS, true).pack(this);
	}

	public void markFailedResult(String id, String message) {
		model.getRun(id).failed(message);
		colorColumns();
		columnSizeMaker.revaluateColSizes();
		columnSizeMaker.applyColLengths();
		model.fireTableDataChanged();
		// new TablePacker(TablePacker.VISIBLE_ROWS, true).pack(this);
	}

	public void startRun(String id) {
		model.getRun(id).begin();
		colorColumns();
		columnSizeMaker.revaluateColSizes();
		columnSizeMaker.applyColLengths();
		model.fireTableDataChanged();
		// new TablePacker(TablePacker.VISIBLE_ROWS, true).pack(this);
	}

	public void addResults(String id, AttributeTable slimPanel,
			int numberOfInputNodes, int numberOfSLiMS, double minSig,
			ArrayList<Attribute> childAttributes) {
		model.getRun(id).success(numberOfInputNodes, numberOfSLiMS, minSig,
				childAttributes);
		colorColumns();
		columnSizeMaker.revaluateColSizes();
		columnSizeMaker.applyColLengths();
		model.fireTableDataChanged();

		// new TablePacker(TablePacker.VISIBLE_ROWS, true).pack(this);
	}

	public void createDefaultOptions(final CyNetwork network,
			final CyNetworkView networkView, JTable source,
			JPopupMenu popupMewnu, final int row, int column) {
		if (!source.isRowSelected(row))
			source.changeSelection(row, column, false, false);

		JMenuItem menuItem = new JMenuItem("Add nodes to selection");

		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CyNode[] nodes = new CyNode[model.getRun(row).getNodeIndices()
						.size()];

				int i = 0;
				for (Integer nodeIndex : model.getRun(row).getNodeIndices()) {
					nodes[i] = (CyNode) network.getNode(nodeIndex);
					i++;
				}
				networkView.setSelected(nodes);
				networkView.updateView();
			}
		});
		popupMewnu.add(menuItem);

		menuItem = new JMenuItem("Cancel/Mark for deletion");

		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				workQueue.abort(model.getRun(row));
				columnSizeMaker.revaluateColSizes();
				columnSizeMaker.applyColLengths();
				model.fireTableDataChanged();
			}
		});
		popupMewnu.add(menuItem);

		menuItem = new JMenuItem("Purge Results");

		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Run run = ((RunModel) tmp.getModel()).getRun(row);
				if (run.isActive()) {
					run.deActivate();
				}
				removeInvalidRuns();
				columnSizeMaker.revaluateColSizes();
				columnSizeMaker.applyColLengths();

				CardLayout cl = (CardLayout) (resultCards.getLayout());
				cl.show(resultCards, "Default");

				// new TablePacker(TablePacker.VISIBLE_ROWS, true).pack(tmp);
			}
		});
		popupMewnu.add(menuItem);
	}

	public void showMenu(MouseEvent e) {
		try {
			// if (e.isPopupTrigger()) {
			JPopupMenu popupMenu = new JPopupMenu();
			final JTable source = (JTable) e.getSource();
			final int row = source.rowAtPoint(e.getPoint());
			int modelRow = this.getRowSorter().convertRowIndexToModel(row);
			int column = source.columnAtPoint(e.getPoint());

			createDefaultOptions(network, networkView, source, popupMenu,
					modelRow, column);
			createExtraOptions(source, popupMenu, modelRow, column);
			popupMenu.show(e.getComponent(), e.getX(), e.getY());

			// get the coordinates of the mouse click
			Point p = e.getPoint();

			// get the row index that contains that coordinate
			int rowNumber = rowAtPoint(p);

			// Get the ListSelectionModel of the JTable
			ListSelectionModel model = getSelectionModel();

			// set the selected interval of rows. Using the "rowNumber"
			// variable for the beginning and end selects only that one row.
			model.setSelectionInterval(rowNumber, rowNumber);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		// }
	}

	public abstract void createExtraOptions(JTable source,
			JPopupMenu popupMewnu, final int row, int column);

	public void mouseClicked(MouseEvent e) {

		if (SwingUtilities.isLeftMouseButton(e)) {
			JTable target = (JTable) e.getSource();
			int row = target.getSelectedRow();
			if (row != -1) {
				int modelRow = this.getRowSorter().convertRowIndexToModel(row);
				CardLayout cl = (CardLayout) (resultCards.getLayout());
				cl.show(resultCards,
						(((RunsTable) target).getRun(modelRow).getId()));
			}
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if (SwingUtilities.isRightMouseButton(e)) {
			showMenu(e);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		// showMenu(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void addNotify() {
		super.addNotify();
		addHierarchyListener(this);
	}

	public void removeNotify() {
		removeHierarchyListener(this);
		super.removeNotify();
	}

	public void hierarchyChanged(HierarchyEvent e) {
		if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0) {
			if (isShowing()) {
				// new TablePacker(TablePacker.VISIBLE_ROWS, true).pack(tmp);
				// colorColumns();
				columnSizeMaker.revaluateColSizes();
				columnSizeMaker.applyColLengths();
			}
		}
	}

}
