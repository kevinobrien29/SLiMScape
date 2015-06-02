package vis.workers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vis.data.Constants;
import vis.dbfetch.dbfetch;
import vis.ui.ProgressBar;
import vis.uniprot.Uniprot;
import cytoscape.CyNetwork;
import cytoscape.CyNode;
import cytoscape.Cytoscape;
import cytoscape.data.CyAttributes;

public class UniprotSequenceImporter extends Thread implements ActionListener {

	private String uniprotAttributeName = "";
	ProgressBar progressBar = null;
	CyNetwork current_network = null;
	boolean cancelled = false;
	Logger log = LoggerFactory.getLogger(UniprotSequenceImporter.class);

	public UniprotSequenceImporter(String uniprotAttributeName,
			ProgressBar progressBar, CyNetwork current_network) {
		this.current_network = current_network;
		this.uniprotAttributeName = uniprotAttributeName;
		this.progressBar = progressBar;
		progressBar.setVisible(true);
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void runNew() {

		log.info("Retrieving Uniprot Sequences");
		Iterator<CyNode> it = current_network.nodesIterator();
		final int numberOfNodes = current_network.getNodeCount();
		CyAttributes cyNodeAttrs = Cytoscape.getNodeAttributes();
		Integer remainingNodes = numberOfNodes;

		HashSet<String> uniprots = new HashSet<String>(); // Entry
																// identifier,
																// name or //
																// accession

		while (it.hasNext() && !this.cancelled) {
			try
			{
				CyNode node = it.next();
				String uniprotID = (String) cyNodeAttrs.getAttribute(
						node.getIdentifier().trim(), uniprotAttributeName);
				uniprots.add(uniprotID.trim());
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		String[] uniprotsArray = new String[uniprots.size()];
		uniprots.toArray(uniprotsArray);
		String result1 = dbfetch.getDataForMultiUniprot(uniprotsArray, "");
		HashMap<String, String> uniprotTxtMap = dbfetch
				.getDatHashMapFromString(uniprotsArray, result1);
		//System.out.println(uniprotsArray.length + "  " + uniprotTxtMap.keySet().size());
		
		String result2 = dbfetch.getDataForMultiUniprot(uniprotsArray, "fasta");
		HashMap<String, String> uniprotFastaMap = dbfetch.getFastaHashMapFromString(result2);
		//System.out.println(uniprotsArray.length + "  " + uniprotFastaMap.keySet().size());

		Iterator<CyNode> it2 = current_network.nodesIterator();
		while (it2.hasNext() && !this.cancelled) {
			try {
				CyNode node = it2.next();
				String uniprotID = (String) cyNodeAttrs.getAttribute(
						node.getIdentifier(), uniprotAttributeName);
				if (uniprotID != null) {
					if (uniprotID.length() > 1) {
						String uniprotTxt = null;
						if (uniprotTxtMap.containsKey(uniprotID.trim())) {
							uniprotTxt = uniprotTxtMap.get(uniprotID.trim());

							cyNodeAttrs.setAttribute(node.getIdentifier(),
									Constants.uniprotTxt, uniprotTxt);
						}

						String uniprotFasta = null;
						String sequence = "";
						if (uniprotFastaMap.containsKey(uniprotID.trim())) {
							uniprotFasta = uniprotFastaMap.get(uniprotID.trim());
							String[] lines = uniprotFasta.split("\n");
							for (int i = 1; i < lines.length; i++) {
								sequence = sequence + lines[i].trim();
							}
							cyNodeAttrs.setAttribute(node.getIdentifier(),
									Constants.sequence, sequence);
							cyNodeAttrs.setAttribute(node.getIdentifier(),
									Constants.fasta, uniprotFasta);
						}

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}

			final int tmpRemainingNodes = remainingNodes;
			SwingUtilities.invokeLater(new Runnable() {

				public void run() {

					progressBar
							.setProgress(100 - (int) (((double) tmpRemainingNodes)
									/ ((double) numberOfNodes) * 100));

				}

			});

			remainingNodes = remainingNodes - 1;
		}

	}

	@Override
	public void run() {
		Uniprot uniprot = new Uniprot();

		log.info("Retrieving Uniprot Sequences");
		Iterator<CyNode> it = current_network.nodesIterator();
		final int numberOfNodes = current_network.getNodeCount();
		CyAttributes cyNodeAttrs = Cytoscape.getNodeAttributes();
		Integer remainingNodes = numberOfNodes;
		while (it.hasNext() && !this.cancelled) {

			CyNode node = it.next();
			String uniprotID = (String) cyNodeAttrs.getAttribute(
					node.getIdentifier(), uniprotAttributeName);
			try {

				if (uniprotID != null) {
					if (uniprotID.length() > 1) {
						String uniprotTxt = uniprot.getUniprotTxt(uniprotID,
								node.getIdentifier());
						String fasta = uniprot.getFasta(uniprotID.trim());
						String sequence = "";
						String[] lines = fasta.split("\n");
						for (int i = 1; i < lines.length; i++) {
							sequence = sequence + lines[i].trim();
						}

						if (sequence.length() > 0) {
							cyNodeAttrs.setAttribute(node.getIdentifier(),
									Constants.sequence, sequence);
							cyNodeAttrs.setAttribute(node.getIdentifier(),
									Constants.fasta, fasta);
						}
						cyNodeAttrs.setAttribute(node.getIdentifier(),
								Constants.uniprotTxt, uniprotTxt);

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("failed to retrieve sequence for: " + uniprotID);
				log.error(e.getMessage());
			}

			final int tmpRemainingNodes = remainingNodes;
			SwingUtilities.invokeLater(new Runnable() {

				public void run() {

					progressBar
							.setProgress(100 - (int) (((double) tmpRemainingNodes)
									/ ((double) numberOfNodes) * 100));

				}

			});

			remainingNodes = remainingNodes - 1;
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		this.cancelled = true;
		log.info("cancelled");
	}
}