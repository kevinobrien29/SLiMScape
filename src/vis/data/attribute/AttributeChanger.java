package vis.data.attribute;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import vis.data.Constants;
import vis.tools.CytoscapeHelper;
import cytoscape.CyEdge;
import cytoscape.CyNetwork;
import cytoscape.CyNode;
import cytoscape.Cytoscape;
import cytoscape.data.CyAttributes;
import cytoscape.view.CyNetworkView;
import cytoscape.visual.CalculatorCatalog;
import cytoscape.visual.VisualMappingManager;
import cytoscape.visual.VisualStyle;

public class AttributeChanger {
	
	CyAttributes cyNodeAttrs = Cytoscape.getNodeAttributes();

	
	VisualMappingManager manager = Cytoscape.getVisualMappingManager();
	
	CalculatorCatalog catalog = manager.getCalculatorCatalog();
	
	
	
	public void activateAttribute(String nodeId, String attributeTitle,
			String type) {
		String activityLevelName = "";
		String activeAttributeName = "";

		if (type == Constants.getSlims()) {
			activityLevelName = Constants.getSlimActivityLevel();
			activeAttributeName = Constants.getActiveSLiMs();

			ArrayList<String> currentValues = (ArrayList<String>) cyNodeAttrs
					.getListAttribute(nodeId, activeAttributeName);
			if (currentValues == null) {
				currentValues = new ArrayList<String>();
			}
			currentValues.add(attributeTitle);
			cyNodeAttrs.setListAttribute(nodeId, activeAttributeName,
					currentValues);
		} else if (type == Constants.getDomains()) {
			activityLevelName = Constants.getDomainActivityLevel();
			activeAttributeName = Constants.getActiveDomains();

			ArrayList<String> currentValues = (ArrayList<String>) cyNodeAttrs
					.getListAttribute(nodeId, activeAttributeName);
			if (currentValues == null) {
				currentValues = new ArrayList<String>();
			}
			currentValues.add(attributeTitle);
			cyNodeAttrs.setListAttribute(nodeId, activeAttributeName,
					currentValues);
		} else if (type == Constants.getSecondary()) {
			activityLevelName = Constants.getSecondaryActivity();
		}

		Integer currentLevel = cyNodeAttrs.getIntegerAttribute(nodeId,
				activityLevelName);

		if (currentLevel == null) {
			currentLevel = 0;
		}
		currentLevel = currentLevel + 1;
		cyNodeAttrs.setAttribute(nodeId, activityLevelName, currentLevel);

		recalculateActivityLevel(nodeId);
		Constants.generateLabel(nodeId);

		// refresh();
	}

	public void deActivateAttribute(String nodeId, String attributeTitle,
			String type) {
		String activityLevelName = "";
		String activeAttributeName = "";

		if (type == Constants.getSlims()) {
			activityLevelName = Constants.getSlimActivityLevel();
			activeAttributeName = Constants.getActiveSLiMs();

			ArrayList<String> currentValues = (ArrayList<String>) cyNodeAttrs
					.getListAttribute(nodeId, activeAttributeName);

			currentValues.remove(attributeTitle);

			cyNodeAttrs.setListAttribute(nodeId, activeAttributeName,
					currentValues);

			if (currentValues.size() == 0) {
				cyNodeAttrs.deleteAttribute(nodeId, activeAttributeName);
			}
		} else if (type == Constants.getDomains()) {
			activityLevelName = Constants.getDomainActivityLevel();
			activeAttributeName = Constants.getActiveDomains();

			ArrayList<String> currentValues = (ArrayList<String>) cyNodeAttrs
					.getListAttribute(nodeId, activeAttributeName);

			currentValues.remove(attributeTitle);

			cyNodeAttrs.setListAttribute(nodeId, activeAttributeName,
					currentValues);

			if (currentValues.size() == 0) {
				cyNodeAttrs.deleteAttribute(nodeId, activeAttributeName);
			}
		} else if (type == Constants.getSecondary()) {
			activityLevelName = Constants.getSecondaryActivity();
		}

		Integer currentLevel = cyNodeAttrs.getIntegerAttribute(nodeId,
				activityLevelName);
		
		if (currentLevel != null)
		{
			currentLevel = currentLevel - 1;
			cyNodeAttrs.setAttribute(nodeId, activityLevelName, currentLevel);
		}
		
		if (currentLevel == 0) {
			cyNodeAttrs.deleteAttribute(nodeId, activityLevelName);
			cyNodeAttrs.setAttribute(nodeId, activityLevelName, new Integer(0));
		}
		recalculateActivityLevel(nodeId);
		Constants.generateLabel(nodeId);

		// refresh();
	}

	public CyNode getCyNode(String network, String nodeId) {
		CyNetwork current_network = Cytoscape.getNetwork(network);

		Iterator<CyNode> it = current_network.nodesIterator();
		while (it.hasNext()) {
			CyNode node = it.next();
			if (node.getIdentifier().equals(nodeId)) {
				return node;
			}
		}
		return null;
	}

	public void recalculateActivityLevel(String nodeID) {
		CyAttributes cyNodeAttrs = Cytoscape.getNodeAttributes();
		Integer slimLevel = cyNodeAttrs.getIntegerAttribute(nodeID,
				Constants.getSlimActivityLevel());

		Integer domainLevel = cyNodeAttrs.getIntegerAttribute(nodeID,
				Constants.getDomainActivityLevel());

		Integer secondaryLevel = cyNodeAttrs.getIntegerAttribute(nodeID,
				Constants.getSecondaryActivity());

		if (slimLevel == null)
			slimLevel = 0;

		if (domainLevel == null)
			domainLevel = 0;

		if (secondaryLevel == null)
			secondaryLevel = 0;

		if (slimLevel == 0 && domainLevel == 0 && secondaryLevel == 0) {
			cyNodeAttrs.setAttribute(nodeID, Constants.getActivityLevel(), 0);
			cyNodeAttrs.setAttribute(nodeID, Constants.getType(), "none");
		} else if (slimLevel > 0 && domainLevel == 0) {
			cyNodeAttrs.setAttribute(nodeID, Constants.getActivityLevel(), 1);
			cyNodeAttrs.setAttribute(nodeID, Constants.getType(), "slim");
		} else if (slimLevel == 0 && domainLevel > 0) {
			cyNodeAttrs.setAttribute(nodeID, Constants.getActivityLevel(), 2);
			cyNodeAttrs.setAttribute(nodeID, Constants.getType(), "domain");
		} else if (slimLevel > 0 && domainLevel > 0) {
			cyNodeAttrs.setAttribute(nodeID, Constants.getActivityLevel(), 3);
			cyNodeAttrs.setAttribute(nodeID, Constants.getType(), "both");
			
		}
		
		if (secondaryLevel > 0) {
			cyNodeAttrs.setAttribute(nodeID, Constants.getActivityLevel(), 4);
		}
	}

	public void recalculateSecondaryActivityLevel(ArrayList<String> nodeIDs) {
		for (String nodeID : nodeIDs) {
			CyAttributes cyNodeAttrs = Cytoscape.getNodeAttributes();

			Integer secondaryActivityLevel = cyNodeAttrs.getIntegerAttribute(
					nodeID, Constants.getSecondaryActivity());

			if (secondaryActivityLevel == null)
				secondaryActivityLevel = 0;
			else if (secondaryActivityLevel == 0) {
				cyNodeAttrs.setAttribute(nodeID, Constants.getActivityLevel(), 0);
			} else if (secondaryActivityLevel == 1) {
				cyNodeAttrs.setAttribute(nodeID, Constants.getActivityLevel(), 4);
			} else if (secondaryActivityLevel >= 2) {
				cyNodeAttrs.setAttribute(nodeID, Constants.getActivityLevel(), 5);
			}
		}
	}

	public void refresh() {
		Cytoscape.firePropertyChange(Cytoscape.ATTRIBUTES_CHANGED, null, null);

		VisualStyle vs = catalog.getVisualStyle(Constants.visualStyleName);
		if (vs != null) {
			CyNetworkView cnv1 = CytoscapeHelper.getNetworkView("network1");
			if (cnv1 != null) {
				cnv1.applyVizmapper(vs);
				cnv1.redrawGraph(true, true);
			}
		}

	}

}
