package vis.slims.ui;

import java.awt.event.MouseEvent;

import vis.slimsearch.ui.RunSLiMSearchPanel;
import vis.tools.CustomImportHelper;
import vis.ui.TableDisplay;

public class SLiMAggregateTableDisplay extends TableDisplay {

	RunSLiMSearchPanel sLiMSearchPanel = null;

	public SLiMAggregateTableDisplay(CustomImportHelper customImportHelper) {
		super(customImportHelper.getSLiMTitles(),
				"evalue" , null);
	}

	public void showMenu(MouseEvent e) {
		/*
		 * if (e.isPopupTrigger()) { final JTable source = (JTable)
		 * e.getSource(); final int row = source.rowAtPoint(e.getPoint()); int
		 * column = source.columnAtPoint(e.getPoint());
		 * 
		 * final RunSLiMSearchPanel z = this.sLiMSearchPanel; if
		 * (!source.isRowSelected(row)) source.changeSelection(row, column,
		 * false, false);
		 * 
		 * JPopupMenu popupMewnu = new JPopupMenu(); JMenuItem menuItem = new
		 * JMenuItem("SLiMSearch on selected nodes");
		 * 
		 * menuItem.addActionListener(new ActionListener() { public void
		 * actionPerformed(ActionEvent e) { HashSet<Integer>
		 * highlightedNodeIndices =
		 * NetworkDataCollector.getSelectedNodesIndices();
		 * z.runSLiMSearch(highlightedNodeIndices,
		 * ((SLiMFinderResult)((AttributeModel
		 * )source.getModel()).getAttribute(row)).getRegExp()); } });
		 * popupMewnu.add(menuItem);
		 * 
		 * 
		 * menuItem = new JMenuItem("SLiMSearch on all nodes");
		 * 
		 * menuItem.addActionListener(new ActionListener() { public void
		 * actionPerformed(ActionEvent e) { HashSet<Integer>
		 * highlightedNodeIndices = NetworkDataCollector.getAllNodeIndices();
		 * z.runSLiMSearch(highlightedNodeIndices,
		 * ((SLiMFinderResult)((AttributeModel
		 * )source.getModel()).getAttribute(row)).getRegExp()); } });
		 * 
		 * popupMewnu.add(menuItem); popupMewnu.show(e.getComponent(), e.getX(),
		 * e.getY()); }
		 */
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

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

}