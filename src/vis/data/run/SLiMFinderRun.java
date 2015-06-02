package vis.data.run;

import java.util.ArrayList;
import java.util.HashSet;

import com.sun.org.apache.bcel.internal.classfile.Attribute;

import vis.data.Constants;
import vis.data.attribute.AttributeChanger;
import vis.root.Variables;
import vis.slimfinder.ui.SLiMFinderOptions;
import vis.slimsearch.ui.RunSLiMSearchPanel;
import vis.ui.LogScreen;
import vis.ui.TripleSplitPane;

public class SLiMFinderRun extends Run {

	String SLiMFinderPath = "/home/kevin/Desktop/slim_domain_vis/slimsuite/tools/slimfinder.py";
	String runDir = "/home/kevin/Desktop/slim_domain_vis/slimsuite/runs/slimfinder";
	String fasta = null;
	String fastapath = null;
	LogScreen progressBarWithText = null;
	TripleSplitPane resultsPanel;
	HashSet<String> nodeIdsSet = null;
	RunSLiMSearchPanel slimSearchPanel = null;
	SLiMFinderOptions sLiMFinderOptions = null;
	String iUPredPath = null;
	String gopherDir = null;
	Variables variables;

	public SLiMFinderRun(Variables variables, String id,
			String fasta, TripleSplitPane resultsPanel,
			HashSet<String> nodeIds, RunSLiMSearchPanel slimSearchPanel,
			String name, int datasetSize, HashSet<String> proteins, HashSet<Integer> nodeIndices,
			Thread thread) {
		super(id, thread, null);
		this.variables = variables;
		this.name = name;
		this.nodeIds = nodeIds;
		this.datasetSize = datasetSize;
		this.isActive = false;
		this.percentageComplete = "0";
		this.nodeIndices = nodeIndices;
		this.runDir = variables.getRunsDir() + "/slimfinder";
		this.backgroundColor = Constants.getRunningColor();

		nodeIdsSet = new HashSet<String>();
		for (String nodeId : nodeIds) {
			nodeIdsSet.add(nodeId);
		}
	}

	public static String[] getTitles() {
		String[] z = { "", "ID", "Status", "Number of Nodes", "Min Sig" };
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

	public void failed() {
		setMessage("No results found");
		setPercentageComplete("100");
		setBackgroundColor(Constants.getFailedColor());
		setToBeRemoved(true);
		// resultsPanel.getRunsTable().dataChanged();
	}

	@Override
	public void begin() {
		setMessage("running");
		setPercentageComplete("0");
		setBackgroundColor(Constants.getRunningColor());
		// resultsPanel.getRunsTable().dataChanged();
	}

	@Override
	public void activate() {
		AttributeChanger AttributeChanger = new AttributeChanger();
		for (String activatedNodeID : this.getNodeIds()) {
			AttributeChanger.activateAttribute(activatedNodeID, name,
					Constants.getSecondary());
		}
		AttributeChanger.refresh();
		this.setActive(true);
	}

	@Override
	public void deActivate() {
		AttributeChanger AttributeChanger = new AttributeChanger();
		for (String nodeId : this.getNodeIds()) {

			AttributeChanger.deActivateAttribute(nodeId, name,
					Constants.getSecondary());
		}
		AttributeChanger.refresh();
		this.setActive(false);
	}
}
