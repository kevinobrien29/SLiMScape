package vis.slimsearch.ui;

import java.util.ArrayList;

import javax.swing.JPopupMenu;
import javax.swing.JTable;

import vis.data.attribute.Attribute;
import vis.tools.CytoscapeHelper;
import vis.ui.AttributeTable;

public class SLiMSearchAttributeTable extends AttributeTable{

	ArrayList<Attribute> sLiMs = null;
	public SLiMSearchAttributeTable(ArrayList<Attribute> sLiMs) {
		super(CytoscapeHelper.General.concat(new String [] {"Status","Pattern"}, sLiMs.get(0).getTitles()));
		this.sLiMs = sLiMs;
		this.replaceModelData(sLiMs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createExtraOptions(JTable source, JPopupMenu popupMewnu,
			int row, int column) {
		// TODO Auto-generated method stub
		
	}

	
	

}
