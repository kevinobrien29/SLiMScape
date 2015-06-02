package vis.workers;

import java.util.Iterator;
import java.util.List;

import cytoscape.CyNetwork;
import cytoscape.CyNode;
import cytoscape.Cytoscape;
import cytoscape.data.CyAttributes;

public class ImportAttributes extends Thread {
	List<String[]> attributes = null;
	String[] titles = null;
	CyNetwork network = null;

	public ImportAttributes(CyNetwork network, String[] titles, List<String[]> attributes) {
		this.titles = titles;
		this.attributes = attributes;
		this.network = network;
	}

	public void addAttributesToNode(CyNetwork network, String node_ID, String[] titles,
			String[] nodeAttributes) {
		CyNode node = null;

		Iterator<CyNode> it = network.nodesIterator();
		while (it.hasNext()) {
			CyNode node1 = it.next();
			if (node1.getIdentifier().equals(node_ID)) {
				node = node1;
				break;
			}
		}

		// if node exists
		if (node != null) {

			for (int i = 0; i < titles.length; i++) {
				// Create a node attribute
				CyAttributes cyNodeAttrs = Cytoscape.getNodeAttributes();
				cyNodeAttrs.setAttribute(node_ID, titles[i], nodeAttributes[i]);
				//cyNodeAttrs.setAttribute(node_ID + "_2", titles[i], nodeAttributes[i]);

			}
		}
	}

	public void loadAttributes(CyNetwork network) {
		for (String[] row : attributes) {
			this.addAttributesToNode(network, row[0], titles, row);
		}
	}

	@Override
	public void run() {
		loadAttributes(network);
	}
}