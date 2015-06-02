package vis.slimfinder.ui;

import giny.model.Edge;
import giny.model.Node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import vis.data.Constants;
import vis.data.attribute.Attribute;
import vis.ui.AttributeTable;
import vis.ui.TableDisplay;
import cytoscape.CyNetwork;
import cytoscape.CyNode;
import cytoscape.Cytoscape;
import cytoscape.data.CyAttributes;

public class NetworkDataCollector {

	public static String getFastaForNodeIndices(CyNetwork cyNetwork,
			HashSet<Integer> nodeIndices) {

		CyAttributes cyNodeAttrs = Cytoscape.getNodeAttributes();
		String result = "";
		for (int nodeIndex : nodeIndices) {
			String singleFasta = (String) cyNodeAttrs.getAttribute(cyNetwork
					.getNode(nodeIndex).getIdentifier(), Constants.uniprotTxt);
			result = result + singleFasta;
		}
		if (result.substring(0, 1).contains("\n"))
		{
			result = result.replaceFirst("\n", "");
		}
		return result;
	}

	public static HashSet<String> getNodeNames(CyNetwork cyNetwork,
			HashSet<Integer> nodeIndices) {

		HashSet<String> nodeNames = new HashSet<String>();
		for (int index : nodeIndices) {
			nodeNames.add(cyNetwork.getNode(index).getIdentifier());
		}
		return nodeNames;
	}

	public static HashSet<Integer> getSelectedNodesIndices(CyNetwork cyNetwork) {

		Set<CyNode> selectedNodes = cyNetwork.getSelectedNodes();

		HashSet<Integer> nodeIds = new HashSet<Integer>();
		for (CyNode aNode : selectedNodes) {
			nodeIds.add(aNode.getRootGraphIndex());
		}
		return nodeIds;
	}

	public static HashSet<Integer> getInteractionIndicesForNodeIndex(
			CyNetwork cyNetwork, int hubNodeIndex, boolean includeHub) {

		HashSet<Integer> nodeIndices = new HashSet<Integer>();
		if (includeHub) {
			nodeIndices.add(hubNodeIndex);
		}
		int[] edgeIndices = cyNetwork.getAdjacentEdgeIndicesArray(hubNodeIndex,
				true, true, true);
		for (int edgeIndex : edgeIndices) {
			Edge edge = cyNetwork.getEdge(edgeIndex);
			Node source = edge.getSource();
			Node target = edge.getTarget();
			if (hubNodeIndex != source.getRootGraphIndex()) {
				nodeIndices.add(source.getRootGraphIndex());
			}
			if (hubNodeIndex != target.getRootGraphIndex()) {
				nodeIndices.add(target.getRootGraphIndex());
			}
		}
		return nodeIndices;
	}

	public static HashSet<Integer> getAllNodeIndices(CyNetwork cyNetwork) {

		HashSet<Integer> nodeIds = new HashSet<Integer>();
		for (int nodeId : cyNetwork.getNodeIndicesArray()) {
			CyNode node = (CyNode) cyNetwork.getNode(nodeId);
			nodeIds.add(node.getRootGraphIndex());
		}
		return nodeIds;
	}

	public static HashMap<String, HashSet<Integer>> getNodeIndicesForAttribute(
			CyNetwork cyNetwork, String attributeName) {
		CyAttributes attributes = Cytoscape.getNodeAttributes();
		HashMap<String, HashSet<Integer>> results = new HashMap<String, HashSet<Integer>>();
		for (int nodeIndex : cyNetwork.getNodeIndicesArray()) {
			Node node = cyNetwork.getNode(nodeIndex);
			String attributeValue = ""
					+ attributes.getAttribute(node.getIdentifier(),
							attributeName);

			HashSet<Integer> current = results.get(attributeValue);
			if (current == null) {
				current = new HashSet<Integer>();
				results.put(attributeValue, current);
			}
			current.add(nodeIndex);
		}
		return results;
	}

	public static HashMap<String, HashSet<Integer>> getBatchInteractionsNodeIds(
			CyNetwork cyNetwork, boolean includeHubs) {

		HashMap<String, HashSet<Integer>> results = new HashMap<String, HashSet<Integer>>();
		for (int hubNodeIndex : cyNetwork.getNodeIndicesArray()) {
			results.put(
					cyNetwork.getNode(hubNodeIndex).getIdentifier(),
					getInteractionIndicesForNodeIndex(cyNetwork, hubNodeIndex,
							includeHubs));
		}
		return results;
	}

	public static HashMap<String, HashSet<Integer>> getBatchDomainNodeIds(
			CyNetwork cyNetwork, boolean includeHubs,
			AttributeTable domainPanelAggregate) {
		HashMap<String, HashSet<Integer>> results = new HashMap<String, HashSet<Integer>>();

		HashMap<String, Integer> mappings = new HashMap<String, Integer>();
		Iterator it = cyNetwork.nodesIterator();
		while (it.hasNext()) {
			CyNode node = (CyNode) it.next();
			mappings.put(node.getIdentifier(), node.getRootGraphIndex());
		}

		for (Attribute attribute : domainPanelAggregate.getModelData()) {
			for (String protein : attribute.getProteins()) {
				HashSet<Integer> proteins = results.get(attribute.getName());
				if (proteins != null) {
					proteins.add(mappings.get(protein));
					results.put(attribute.getName(), proteins);
				} else {
					proteins = new HashSet<Integer>();
					proteins.add(mappings.get(protein));
					results.put(attribute.getName(), proteins);
				}
			}
		}
		return results;
	}

	public static HashSet<Integer> getSingleDomainNodeIds(CyNetwork cyNetwork,
			boolean includeHubs, String[] proteins,
			AttributeTable domainPanelAggregate) {

		// get indicews for node names
		HashMap<String, Integer> indicesForNodeNames = new HashMap<String, Integer>();
		Iterator it = cyNetwork.nodesIterator();
		while (it.hasNext()) {
			CyNode node = (CyNode) it.next();
			indicesForNodeNames.put(node.getIdentifier(),
					node.getRootGraphIndex());
		}

		HashSet<Integer> result = new HashSet<Integer>();
		for (String protein : proteins) {
			result.add(indicesForNodeNames.get(protein));
		}

		return result;
	}

	public static HashSet<Integer> getSingleDomainInteractorNodeIds(
			CyNetwork cyNetwork, boolean includeHubs, String[] proteins,
			AttributeTable domainPanelAggregate) {
		HashSet<Integer> results = new HashSet<Integer>();

		// get indicews for node names
		HashMap<String, Integer> indicesForNodeNames = new HashMap<String, Integer>();
		Iterator it = cyNetwork.nodesIterator();
		while (it.hasNext()) {
			CyNode node = (CyNode) it.next();
			indicesForNodeNames.put(node.getIdentifier(),
					node.getRootGraphIndex());
		}

		for (String protein : proteins) {
			results.addAll(getInteractionIndicesForNodeIndex(cyNetwork,
					indicesForNodeNames.get(protein), includeHubs));
		}
		return results;
	}

	public static HashMap<String, HashSet<Integer>> getBatchDomainInteractorNodeIds(
			CyNetwork cyNetwork, boolean includeHubs,
			AttributeTable domainPanelAggregate) {
		HashMap<String, HashSet<Integer>> results = new HashMap<String, HashSet<Integer>>();

		// get indicews for node names
		HashMap<String, Integer> indicesForNodeNames = new HashMap<String, Integer>();
		Iterator it = cyNetwork.nodesIterator();
		while (it.hasNext()) {
			CyNode node = (CyNode) it.next();
			indicesForNodeNames.put(node.getIdentifier(),
					node.getRootGraphIndex());
		}

		for (Attribute attribute : domainPanelAggregate.getModelData()) {
			HashSet<Integer> result = new HashSet<Integer>();
			for (String protein : attribute.getProteins()) {
				result.addAll(getInteractionIndicesForNodeIndex(cyNetwork,
						indicesForNodeNames.get(protein), includeHubs));
			}
			results.put(attribute.getName(), result);
		}
		return results;
	}

	public static HashMap<String, HashSet<Integer>> getSelectedNodeInteractionsNodeIds(
			CyNetwork cyNetwork, boolean includeHubs) {
		HashMap<String, HashSet<Integer>> results = new HashMap<String, HashSet<Integer>>();
		for (Object obj : cyNetwork.getSelectedNodes()) {
			CyNode node = (CyNode) obj;
			results.put(
					node.getIdentifier(),
					getInteractionIndicesForNodeIndex(cyNetwork,
							node.getRootGraphIndex(), includeHubs));
		}
		return results;
	}

}
