package vis.domains.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

import vis.data.Constants;
import vis.data.attribute.Attribute;
import vis.data.attribute.AttributeModel;
import vis.data.attribute.SLiMFinderResult;
import vis.slimfinder.ui.NetworkDataCollector;
import vis.slimsearch.ui.RunSLiMSearchPanel;
import vis.tools.CytoscapeHelper;
import vis.ui.AttributeTable;
import cytoscape.CyNetwork;

public class DomainAttributeTable extends AttributeTable
{
	CyNetwork network = null;
	public DomainAttributeTable(CyNetwork network, ArrayList<Attribute> SLiMs) {
		super(Constants.getDomainTableTitles());
		this.network = network;
		//this.replaceModelData(SLiMs);
		// TODO Auto-generated constructor stub
		setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
	}

	@Override
	public void createExtraOptions(final JTable source, JPopupMenu popupMewnu,
			final int row, int column) {
	}
	
}