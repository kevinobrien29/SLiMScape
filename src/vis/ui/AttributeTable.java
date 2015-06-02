package vis.ui;

import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.TableRowSorter;

import vis.data.attribute.Attribute;
import vis.data.attribute.AttributeModel;

public abstract class AttributeTable extends JTable implements MouseListener, HierarchyListener {
	private AttributeModel model = null;
	final AttributeTable tmp = this;
	ColumnSizeMaker columnSizeMaker = null;
	ArrayList<Attribute> slims = null;

	public AttributeTable(String[] columnNames) {
		super();
		model = new AttributeModel(columnNames);
		columnNames[0] = "";
		this.setModel(model);
		setBounds(149, 74, 1, 1);
		addMouseListener(this);
		this.getColumnModel().getColumn(1).setMaxWidth(20);
		this.getColumnModel().getColumn(1).setPreferredWidth(20);

		TableRowSorter sorter = new TableRowSorter<AttributeModel>(model);
		this.setRowSorter(sorter);
		sorter.setSortsOnUpdates(true);
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		columnSizeMaker = new ColumnSizeMaker(this, columnNames, 0);
		columnSizeMaker.initialiseColLengthTracker();
		columnSizeMaker.applyColLengths();
		
		DoubleRenderer _renderer = new DoubleRenderer();
		this.setDefaultRenderer(Double.class, _renderer);
		
	}
	
	@Override
	public boolean getScrollableTracksViewportWidth()
    {
        return getPreferredSize().width < getParent().getWidth();
    }
	
	public ArrayList<Attribute> getModelData()
	{
		return slims;
	}

	public Attribute getAttribute(int row) {
		return this.model.getAttribute(row);
	}
	
	public void replaceModelData(final ArrayList<Attribute> slims) {
		this.slims = slims;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				model.replaceModelData(slims);
				tmp.getColumnModel().getColumn(0).setMaxWidth(20);
				tmp.getColumnModel().getColumn(0).setPreferredWidth(20);
				//new TablePacker(TablePacker.VISIBLE_ROWS, true).pack(tmp);
				columnSizeMaker.revaluateColSizes();
				columnSizeMaker.applyColLengths();
			}

		});
	}
	
	public void replaceModelData(final String [] titles, final ArrayList<Attribute> slims) {
		this.slims = slims;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				model.replaceModelData(titles, slims);
				tmp.getColumnModel().getColumn(0).setMaxWidth(20);
				tmp.getColumnModel().getColumn(0).setPreferredWidth(20);
				new TablePacker(TablePacker.VISIBLE_ROWS, true).pack(tmp);
				columnSizeMaker.revaluateColSizes();
				columnSizeMaker.applyColLengths();
			}

		});
	}

	public void createDefaultOptions(JTable source, JPopupMenu popupMewnu,
			final int row, int column) {
		if (!source.isRowSelected(row))
			source.changeSelection(row, column, false, false);
	}

	public void showMenu(MouseEvent e) {
		if (e.isPopupTrigger()) {
			JPopupMenu popupMenu = new JPopupMenu();
			final JTable source = (JTable) e.getSource();
			final int row = source.rowAtPoint(e.getPoint());
			int column = source.columnAtPoint(e.getPoint());

			createDefaultOptions(source, popupMenu, row, column);
			createExtraOptions(source, popupMenu, row, column);

			popupMenu.show(e.getComponent(), e.getX(), e.getY());
		}
	}
	
	public abstract void createExtraOptions(JTable source, JPopupMenu popupMewnu,
			final int row, int column);

	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		showMenu(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		showMenu(e);
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
				columnSizeMaker.revaluateColSizes();
				columnSizeMaker.applyColLengths();
				//new TablePacker(TablePacker.VISIBLE_ROWS, true).pack(tmp);
			}
		}
	}
	
	public void deactivateActiveAttributes()
	{
		model.deactivateActiveNodes();
	}

}
