package vis.data;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;

import vis.data.attribute.AttributeChanger;
import cytoscape.CyNetwork;
import cytoscape.CyNode;
import cytoscape.Cytoscape;
import cytoscape.data.CyAttributes;

public class Constants {
	public static String pluginName = "SLiMScape";
	public static Double divider = 100000.0;

	public static String domains = pluginName + ".domains";
	public static String slims = pluginName + ".slim";
	private static String secondary = pluginName + ".secondary";

	public static String activeDomains = pluginName + ".activeDomains";
	public static String activeSLiMs = pluginName + ".activeSlims";

	public static String slimActivityLevel = pluginName + ".slimActivityLevel";
	public static String domainActivityLevel = pluginName + ".domainActivityLevel";
	public static String activityLevel = pluginName + ".activityLevel";
	private static String secondaryActivity = pluginName + ".secondaryActivity";
	
	private static String labelAttribute = "";

	public static String uniprotTxt = pluginName + ".uniprot.txt";
	public static String fasta = pluginName + ".fasta";
	public static String sequence = pluginName + ".sequence";

	public static String type = pluginName + ".type";
	public static String label = pluginName + ".label";
	public static String visualStyleName = pluginName;

	private static Color runningColor = new Color(255, 140, 105);
	private static Color failedColor = new Color(220, 20, 60);
	private static Color successColor = new Color(100, 149, 237);

	private static String[] domainTableTitles = { "", "Name", "Source",
			"Node ID", "e-value" };

	private static String[] sLiMFinderResultTitles = new String[] { "Desc",
			"Sig", "Seq", "Match", "Variant", "MisMatch", "Chance", "IC",
			"Occ", "Support", "UP", "ExpUP", "Prob", "Cloud", "CloudSeq",
			"CloudUP", "ExpUP" };

	private static String[] sLiMFinderResultTitlesConservation = new String[] {
			"Desc", "Masking", "Sig", "Seq", "Match", "Variant", "MisMatch",
			"Chance", "IC", "Occ", "Support", "UP", "ExpUP", "Prob", "Cloud",
			"CloudSeq", "CloudUP", "ExpUP", "Cons", "Cons_mean" };

	private static String[] sLiMSearchResultTitles = new String[] { "Masking",
			"Motif", "Seq", "Match", "Variant", "MisMatch", "Desc", "SeqNum",
			"UPNum", "AANum", "IC", "N_Occ", "E_Occ", "p_Occ", "pUnd_Occ",
			"N_Seq", "E_Seq", "p_Seq", "pUnd_Seq", "N_UPC", "E_UPC", "p_UPC",
			"pUnd_UPC" };

	private static String[] sLiMSearchResultTitlesConservation = new String[] {
			"Masking", "Motif", "Seq", "Match", "Variant", "MisMatch", "Desc",
			"SeqNum", "UPNum", "AANum", "IC", "N_Occ", "E_Occ", "p_Occ",
			"pUnd_Occ", "N_Seq", "E_Seq", "p_Seq", "pUnd_Seq", "N_UPC",
			"E_UPC", "p_UPC", "pUnd_UPC" };

	private static String[] sLiMFinderRunsTableTitles = new String[] { "name",
			"message", "lowest e-value" };

	private static String[] domainTableAggregateTitles = { "Domain Name", "Source",
			"Active", "Count", "Proteins", "min_evalue", "max_evalue" };

	private static String sLiMSearchRunDir = "";

	private static String sLiMFinderRunDir = "";

	public static String getVisualStyleName() {
		return visualStyleName;
	}

	public static String getLabelAttribute() {
		return labelAttribute;
	}

	public static void setLabelAttribute(String labelAttribute) {
		Constants.labelAttribute = labelAttribute;
	}
	
	public static String getLabel() {
		return label;
	}

	public static String getType() {
		return type;
	}

	public static String getSlimActivityLevel() {
		return slimActivityLevel;
	}

	public static String getDomainActivityLevel() {
		return domainActivityLevel;
	}

	public static void createAllAttributesForNetwork(CyNetwork network) {
		for (int nodeId : network.getNodeIndicesArray()) {
			CyNode node = (CyNode) network.getNode(nodeId);
			setAttributesForNode(node.getIdentifier());
		}
	}

	private static void setAttributesForNode(String nodeId) {
		CyAttributes cyNodeAttrs = Cytoscape.getNodeAttributes();

		cyNodeAttrs.setAttribute(nodeId, Constants.getType(), "none");

		// Create a node attribute
		ArrayList<String> slimsList = new ArrayList<String>();
		cyNodeAttrs.setListAttribute(nodeId, getSlims(), slimsList);

		
		
		
		// Create a node attribute
		ArrayList<String> domainsList = new ArrayList<String>();
		cyNodeAttrs.setListAttribute(nodeId, Constants.getDomains(),
				domainsList);

		
		
		
		// Create a node attribute
		ArrayList<String> activeSlimsList = new ArrayList<String>();
		if (cyNodeAttrs.getAttribute(nodeId, Constants.getActiveSLiMs()) != null)
		{
			cyNodeAttrs.deleteAttribute(nodeId, Constants.getActiveSLiMs());
		}
		
		
		cyNodeAttrs.setListAttribute(nodeId, Constants.getActiveSLiMs(),
				activeSlimsList);

		
		
		
		// Create a node attribute
		ArrayList<String> activeDomainsList = new ArrayList<String>();
		
		if (cyNodeAttrs.getAttribute(nodeId, Constants.getActiveDomains()) != null)
		{
			cyNodeAttrs.deleteAttribute(nodeId, Constants.getActiveDomains());
		}
		
		cyNodeAttrs.setListAttribute(nodeId, Constants.getActiveDomains(),
				activeDomainsList);

		
		
		
		
		// Create a node attribute to indicate if it should be highlighted as a
		// slim
		cyNodeAttrs = Cytoscape.getNodeAttributes();
		Integer slimActive = new Integer(0);
		// Save
		cyNodeAttrs.setAttribute(nodeId, Constants.getSlimActivityLevel(),
				slimActive);

		// Create a node attribute to indicate if it should be highlighted as a
		// domain
		cyNodeAttrs = Cytoscape.getNodeAttributes();
		Integer domainActive = new Integer(0);
		// Save
		cyNodeAttrs.setAttribute(nodeId, Constants.getDomainActivityLevel(),
				domainActive);

		// Create a node attribute to indicate if the node should be
		// highligheted based on nodes and slims
		cyNodeAttrs = Cytoscape.getNodeAttributes();
		Integer nodeActive = new Integer(0);
		// Save
		cyNodeAttrs.setAttribute(nodeId, Constants.getActivityLevel(),
				nodeActive);
		
		Integer secondaryActivityLevel = new Integer(0);
		
		if (cyNodeAttrs.getAttribute(nodeId, Constants.getSecondaryActivity()) != null)
		{
			cyNodeAttrs.deleteAttribute(nodeId, Constants.getSecondaryActivity());
		}
		cyNodeAttrs.setAttribute(nodeId, Constants.getSecondaryActivity(),
				secondaryActivityLevel);

		generateLabel(nodeId);
	}
	
	public static void generateAllLabels(CyNetwork cyNetwork) {

		HashSet<Integer> nodeIds = new HashSet<Integer>();
		for (int nodeId : cyNetwork.getNodeIndicesArray()) {
			CyNode node = (CyNode) cyNetwork.getNode(nodeId);
			generateLabel(node.getIdentifier());
		}
	}
	
	public static void generateLabel(String nodeId) {
		CyAttributes cyNodeAttrs = Cytoscape.getNodeAttributes();
		String Id = cyNodeAttrs.getStringAttribute(nodeId, labelAttribute);

		String label = Id + "\n";

		ArrayList<String> slims = (ArrayList<String>) cyNodeAttrs
				.getListAttribute(nodeId, Constants.getActiveSLiMs());

		ArrayList<String> domains = (ArrayList<String>) cyNodeAttrs
				.getListAttribute(nodeId, Constants.getActiveDomains());

		if (slims != null) {
			if (slims.size() > 0) {
				label = label + "Slims:" + "\n";
				for (Object slim : slims) {
					label = label + slim + "\n";
				}
				label = label + "\n";
			}
		}

		if (domains != null) {
			if (domains.size() > 0) {
				label = label + "Domains:" + "\n";
				for (Object domain : domains) {
					label = label + domain + "\n";
				}
			}
		}
		cyNodeAttrs.setAttribute(nodeId, Constants.getLabel(), label);
	}

	public static String[] getDomainTableAggregateTitles() {
		return domainTableAggregateTitles;
	}

	public static String[] getsLiMFinderRunsTableTitles() {
		return sLiMFinderRunsTableTitles;
	}

	public static String[] getDomainTableTitles() {
		return domainTableTitles;
	}

	public static String getActiveDomains() {
		return activeDomains;
	}

	public static String getActiveSLiMs() {
		return activeSLiMs;
	}

	public static String[] getsLiMFinderResultTitles(boolean usingConservation) {
		if (usingConservation) {
			return sLiMFinderResultTitlesConservation;
		}
		return sLiMFinderResultTitles;
	}

	public static void setsLiMFinderResultTitles(String[] sLiMFinderResultTitles) {
		Constants.sLiMFinderResultTitles = sLiMFinderResultTitles;
	}

	public static String[] getsLiMSearchResultTitles(boolean conservation) {
		if (conservation) {
			return sLiMSearchResultTitlesConservation;
		} else {
			return sLiMSearchResultTitles;
		}
	}

	public static String getActivityLevel() {
		return activityLevel;
	}

	public static void setsLiMSearchResultTitles(String[] sLiMSearchResultTitles) {
		Constants.sLiMSearchResultTitles = sLiMSearchResultTitles;
	}

	public static void setsLiMFinderRunsTableTitles(
			String[] sLiMFinderRunsTableTitles) {
		Constants.sLiMFinderRunsTableTitles = sLiMFinderRunsTableTitles;
	}

	public static String sLiMFinderRunDir() {
		return sLiMFinderRunDir;
	}

	public static String getsLiMSearchRunDir() {
		return sLiMSearchRunDir;
	}

	public static Color getRunningColor() {
		return runningColor;
	}

	public static Color getFailedColor() {
		return failedColor;
	}

	public static Color getSuccessColor() {
		return successColor;
	}

	public static String getSecondaryActivity() {
		return secondaryActivity;
	}

	public static String getUniprotTxt() {
		return uniprotTxt;
	}

	public static String getSecondary() {
		return secondary;
	}

	public static String getFasta() {
		return fasta;
	}

	public static String getSequence() {
		return sequence;
	}

	public static String getDomains() {
		return domains;
	}

	public static String getSlims() {
		return slims;
	}

}
