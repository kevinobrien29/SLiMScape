package vis.workers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vis.data.Constants;
import vis.data.attribute.AggregateDomain;
import vis.data.attribute.Attribute;
import vis.data.attribute.Domain;
import vis.domains.http.client.sequential.HTTPSMARTClient;
import vis.tools.CytoscapeHelper;
import vis.ui.AttributeTable;
import vis.ui.ProgressBar;
import vis.ui.TableDisplay;
import cytoscape.CyNetwork;
import cytoscape.CyNode;
import cytoscape.Cytoscape;
import cytoscape.data.CyAttributes;

public class ImportDomainsHTTPJavaWorker extends Thread implements
		ActionListener {
	AttributeTable domainPanel = null;
	AttributeTable domainPanelAggregate = null;
	ProgressBar progressBar = null;
	int numberOfNodes = 0;
	CyNetwork current_network = null;
	boolean cancelled = false;

	Logger log = LoggerFactory.getLogger(ImportDomainsHTTPJavaWorker.class);

	public ImportDomainsHTTPJavaWorker(CyNetwork current_network,
			AttributeTable domainPanel, AttributeTable domainPanelAggregate,
			ProgressBar progressBar) {
		this.current_network = current_network;
		this.progressBar = progressBar;
		this.domainPanel = domainPanel;
		this.numberOfNodes = current_network.getNodeCount();
		this.domainPanelAggregate = domainPanelAggregate;

		progressBar.setVisible(true);
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public ArrayList<Attribute> createAggregateDomains(
			ArrayList<Attribute> domains) {
		ArrayList<Attribute> aggregateDomains = new ArrayList<Attribute>();

		HashMap<String, ArrayList<Attribute>> domainMap = new HashMap<String, ArrayList<Attribute>>();
		HashMap<String, HashSet<String>> domainProteinMap = new HashMap<String, HashSet<String>>();
		for (Attribute attribute : domains) {
			Domain domain = (Domain) attribute;
			ArrayList<Attribute> currentDomains = domainMap.get(domain
					.getName().trim() + "_slimscape_"+ domain.getSource());
			if (currentDomains == null) {
				currentDomains = new ArrayList<Attribute>();
			}
			currentDomains.add(domain);
			domainMap.put(domain.getName().trim() + "_slimscape_"+ domain.getSource(), currentDomains);

			HashSet<String> currentProteins = domainProteinMap.get(domain
					.getName().trim() + "_slimscape_"+ domain.getSource());
			if (currentProteins == null) {
				currentProteins = new HashSet<String>();
			}
			currentProteins.addAll(domain.getProteins());
			domainProteinMap.put(domain
					.getName().trim() + "_slimscape_"+ domain.getSource(), currentProteins);
		}

		for (String domainName : domainMap.keySet()) {

			HashSet<String> proteins = domainProteinMap.get(domainName);
			ArrayList<Attribute> currentDomains = domainMap.get(domainName);
			AggregateDomain aggregateDomain = new AggregateDomain(domainName.split("_slimscape_")[0], domainName.split("_slimscape_")[1],
					currentDomains, proteins);
			aggregateDomains.add(aggregateDomain);
		}
		return aggregateDomains;
	}

	@Override
	public void run() {
		Iterator<CyNode> it = current_network.nodesIterator();
		int numberOfNodes = current_network.getNodeCount();
		int remainingNodes = numberOfNodes;
		HTTPSMARTClient sMARTClient = new HTTPSMARTClient();

		ArrayList<Attribute> allDomains = new ArrayList<Attribute>();
		CyAttributes cyNodeAttrs = Cytoscape.getNodeAttributes();
		while (it.hasNext() && !cancelled) {
			try {

				CyNode node = it.next();
				log.debug("Getting domains for " + node.getIdentifier());
				if (node != null) {

					String sequence = (String) cyNodeAttrs.getAttribute(
							node.getIdentifier(), Constants.sequence);

					if (sequence != null) {
						if (sequence.length() > 0) {

							// String sequence = uniprot.getFasta(uniprotID);
							ArrayList<Domain> domainsForSequence = sMARTClient
									.getDomains(node.getIdentifier(), sequence);
							if (domainsForSequence != null) {
								ArrayList<String> allDomainNames = new ArrayList<String>();
								for (Domain domain : domainsForSequence) {
									allDomainNames.add(domain.getName());
								}

								if (allDomains.size() > 0) {

									if (node != null) {
										cyNodeAttrs = Cytoscape
												.getNodeAttributes();
										cyNodeAttrs.setListAttribute(
												node.getIdentifier(),
												Constants.getDomains(),
												allDomainNames);
									}

								}

								allDomains.addAll(domainsForSequence);

								cyNodeAttrs.setAttribute(node.getIdentifier(),
										Constants.sequence, sequence);
							}

						}
					}
				}
				progressBar.setProgress(100 - (int) (((double) remainingNodes)
						/ ((double) numberOfNodes) * 100));
				remainingNodes--;
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		ArrayList<Attribute> aggregateDomains = this
				.createAggregateDomains(allDomains);
		domainPanelAggregate.replaceModelData(aggregateDomains);

		domainPanel.replaceModelData(allDomains);

		progressBar.dispose();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		this.cancelled = true;
		log.info("cancelled");
	}
}