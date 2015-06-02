package vis.slimfinder.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

import vis.data.attribute.Attribute;
import vis.data.attribute.AttributeModel;
import vis.data.attribute.SLiMFinderResult;
import vis.slimsearch.ui.RunSLiMSearchPanel;
import vis.tools.CytoscapeHelper;
import vis.ui.AttributeTable;
import cytoscape.CyNetwork;

public class SLiMFinderAttributeTable extends AttributeTable
{
	CyNetwork network = null;
	RunSLiMSearchPanel sLiMSearchPanel = null;
	public SLiMFinderAttributeTable(CyNetwork network, RunSLiMSearchPanel sLiMSearchPanel, ArrayList<Attribute> SLiMs) {
		super(CytoscapeHelper.General.concat(new String [] {"Status", "Pattern"}, SLiMs.get(0).getTitles()));
		this.sLiMSearchPanel = sLiMSearchPanel;
		this.network = network;
		this.replaceModelData(SLiMs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createExtraOptions(final JTable source, JPopupMenu popupMewnu,
			final int row, int column) {
		JMenuItem menuItem = new JMenuItem("SLiMSearch: selected nodes");

		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HashSet<Integer> highlightedNodeIndices = NetworkDataCollector.getSelectedNodesIndices(network);
				sLiMSearchPanel.runSLiMSearch(highlightedNodeIndices, ((SLiMFinderResult)((AttributeModel)source.getModel()).getAttribute(convertRowIndexToModel(row))).getRegExp());
			}
		});
		popupMewnu.add(menuItem);
		
		
		menuItem = new JMenuItem("SLiMSearch: all nodes");

		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HashSet<Integer> highlightedNodeIndices = NetworkDataCollector.getAllNodeIndices(network);
				sLiMSearchPanel.runSLiMSearch(highlightedNodeIndices, ((SLiMFinderResult)((AttributeModel)source.getModel()).getAttribute(convertRowIndexToModel(row))).getRegExp());
			}
		});
		
		popupMewnu.add(menuItem);
		// TODO Auto-generated method stub
		
	}
	
}