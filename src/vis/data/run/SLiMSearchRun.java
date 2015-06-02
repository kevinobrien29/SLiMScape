package vis.data.run;

import java.util.ArrayList;
import java.util.HashSet;

import com.sun.org.apache.bcel.internal.classfile.Attribute;

import vis.data.Constants;
import vis.data.attribute.AttributeChanger;
import vis.root.Variables;
import vis.slimfinder.ui.NetworkDataCollector;
import vis.slimsearch.ui.SLiMSearchOptions;
import vis.ui.LogScreen;
import vis.ui.TripleSplitPane;
import cytoscape.CyNetwork;

public class SLiMSearchRun extends Run {

	String SLiMSearchPath = "/home/kevin/Desktop/slim_domain_vis/slimsuite/tools/slimsearch.py";
	String runDir = Constants.getsLiMSearchRunDir();
	String fasta = null;
	String fastapath = null;
	String motifs = null;
	String motifPath = null;
	LogScreen progressBarWithText = null;
	TripleSplitPane resultsPanel;;
	SLiMSearchOptions sLiMSearchOptions = null;
	String iUPredPath = null;
	Variables variables;

	public SLiMSearchRun(CyNetwork network, Variables variables, SLiMSearchOptions sLiMSearchOptions, String id,
			String fasta, String motifs, TripleSplitPane resultsPanel,
			HashSet<Integer> nodeIndices, String name, int datasetSize,
			Thread thread) {
		super(id, thread, null);
		this.variables = variables;
		this.type = "SLiMSearch";
		this.name = name;
		this.name = this.name.replace("\n", ", ");
		
		this.datasetSize = datasetSize;
		this.isActive = false;
		this.percentageComplete = "0";
		this.switchIndex = 0;
		this.backgroundColor = Constants.getRunningColor();
		this.SLiMSearchPath = this.variables.getSlimsuiteHome() + "/tools/slimsearch.py";
		this.runDir = this.variables.getRunsDir() + "/slimsearch";
		this.sLiMSearchOptions = sLiMSearchOptions;
		this.fasta = fasta;
		this.resultsPanel = resultsPanel;
		this.motifs = motifs;
		this.nodeIds = NetworkDataCollector.getNodeNames(network, nodeIndices);
	}

	public static String[] getTitles() {
		String[] z = { "", "ID", "Status", "# of nodes", "Min P Occ" };
		return z;
	}

	@Override
	public Object getValueAt(int col) {
		if (col == 1) {
			return this.getName();
		} else if (col == 0) {
			return this.isActive;
		} else if (col == 2) {
			return this.message;
		} else if (col == 3) {
			return this.datasetSize;
		} else if (col == 4) {
			return this.minSig;
		}
		return null;
	}

	@Override
	public void setValueAt(int col, Object obj) {
		if (col == 1) {
			this.name = (String) obj;
		} else if (col == 0) {
			this.isActive = !this.isActive;
		} else if (col == 2) {
			this.message = (String) obj;
		} else if (col == 3) {
			this.datasetSize = (Integer) obj;
		} else if (col == 4) {
			this.minSig = (Double) obj;
		}

	}
	
	public String getStringValueAt(int col) {
		if (col == 1) {
			return this.getName();
		} else if (col == 0) {
			if (this.isActive) {
				return "T";
			}
			return "F";
		} else if (col == 2) {
			return this.message;
		} else if (col == 3) {
			return "" + this.datasetSize;
		} else if (col == 4) {
			return "" + this.minSig;
		}
		return null;
	}


	public void failed() {
		setMessage("No Results Found");
		setPercentageComplete("100");
		setBackgroundColor(Constants.getFailedColor());
		setToBeRemoved(true);
	}

	@Override
	public void begin() {
		setMessage("running");
		setPercentageComplete("0");
		setBackgroundColor(Constants.getRunningColor());
	}

	@Override
	public void activate() {
		AttributeChanger AttributeChanger = new AttributeChanger();
		for (String activatedNodeID : this.getNodeIds()) {
			AttributeChanger.activateAttribute(activatedNodeID, this.name,
					Constants.getSecondary());
		}
		AttributeChanger.refresh();
		this.setActive(true);
	}

	@Override
	public void deActivate() {
		AttributeChanger AttributeChanger = new AttributeChanger();
		for (String nodeId : this.getNodeIds()) {

			AttributeChanger.deActivateAttribute(nodeId, this.name,
					Constants.getSecondary());
		}
		AttributeChanger.refresh();
		this.setActive(false);
	}
}
