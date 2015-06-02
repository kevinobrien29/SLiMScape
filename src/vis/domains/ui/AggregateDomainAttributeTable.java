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
import vis.data.attribute.Domain;
import vis.slimfinder.ui.NetworkDataCollector;
import vis.slimfinder.ui.RunSLiMFinderDisplayPanel;
import vis.ui.AttributeTable;
import cytoscape.CyNetwork;

public class AggregateDomainAttributeTable extends AttributeTable {
	CyNetwork network = null;
	RunSLiMFinderDisplayPanel runSLiMFinderDisplayPanel = null;

	public AggregateDomainAttributeTable(CyNetwork network,
			ArrayList<Attribute> SLiMs) {
		super(Constants.getDomainTableAggregateTitles());
		this.network = network;

		// this.replaceModelData(SLiMs);
		// TODO Auto-generated constructor stub
		setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
	}

	public void registerRunSLiMFinderDisplayPanel(
			RunSLiMFinderDisplayPanel runSLiMFinderDisplayPanel) {
		this.runSLiMFinderDisplayPanel = runSLiMFinderDisplayPanel;
	}

	@Override
	public void createExtraOptions(final JTable source, JPopupMenu popupMewnu,
			final int row, int column) {
		JMenuItem menuItem = new JMenuItem("SLiMFinder: Single domain");

		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Attribute attribute = ((Attribute) ((AttributeModel)source.getModel()).getAttribute(convertRowIndexToModel(row)));;
				String[] proteins = new String[attribute.getProteins().size()];
				runSLiMFinderDisplayPanel.runSLiMFinder("Single domain",
						attribute.getProteins().toArray(proteins),
						attribute.getName());
			}
		});
		popupMewnu.add(menuItem);

		menuItem = new JMenuItem("SLiMFinder: Single domain interactors");

		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Attribute attribute = ((Attribute) ((AttributeModel)source.getModel()).getAttribute(convertRowIndexToModel(row)));
				String[] proteins = new String[attribute.getProteins().size()];
				runSLiMFinderDisplayPanel.runSLiMFinder(
						"Single domain interactors", attribute.getProteins()
								.toArray(proteins), attribute.getName());
			}
		});

		popupMewnu.add(menuItem);
		// TODO Auto-generated method stub

	}

}