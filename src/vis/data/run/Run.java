package vis.data.run;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;

import vis.data.Constants;
import vis.data.attribute.Attribute;

public abstract class Run implements Comparable<Run> {
	// valid
	private ArrayList<Attribute> childAttributes = null;
	String uniqueId = null;
	boolean isToBeRemoved = false;
	Thread thread = null;
	HashSet<String> nodeIds = null;
	String message = "";
	Double minSig = new Double(-1.0);
	Color backgroundColor = null;
	String percentageComplete = "";

	// may not be valid
	int switchIndex = 0;

	String name = "";
	boolean isActive = false;
	int datasetSize = 0;

	int numberOfInputNodes;
	int numberOfSLiMS;
	int numberOfNodesWithSLims;
	HashSet<Integer> nodeIndices;
	String type = null;
	int size = 4;

	public Run(String id, Thread thread, ArrayList<Attribute> childAttributes) {
		super();
		this.thread = thread;
		uniqueId = id;
		this.message = "Queued";
		// TODO Auto-generated constructor stub
	}

	public HashSet<String> getNodeIds() {
		return nodeIds;
	}

	public void deactivateChildAttributes() {
		if (getChildAttributes() != null) {
			for (Attribute attribute : getChildAttributes()) {
				if (attribute.isActive()) {
					attribute.deActivate(0.0);
				}
			}
		}
	}

	public Thread getThread() {
		return thread;
	}

	public void failed(String message) {
		setMessage(message);
		setPercentageComplete("100");
		setBackgroundColor(Constants.getFailedColor());
		setToBeRemoved(true);
	}

	public void begin() {
		setMessage("running");
		setPercentageComplete("0");
		setBackgroundColor(Constants.getRunningColor());
	}

	public synchronized void setChildAttributes(
			ArrayList<Attribute> childAttributes) {
		this.childAttributes = childAttributes;
	}

	public synchronized ArrayList<Attribute> getChildAttributes() {
		return childAttributes;
	}

	public void success(int numberOfInputNodes, int numberOfSLiMS,
			double minSig, ArrayList<Attribute> childAttributes) {
		setChildAttributes(childAttributes);
		setMessage("ready");
		setPercentageComplete("100");
		setMinSig(minSig);
		setBackgroundColor(Constants.getSuccessColor());
	}

	public boolean isToBeRemoved() {
		return isToBeRemoved;
	}

	public void setToBeRemoved(boolean isToBeRemoved) {
		this.isToBeRemoved = isToBeRemoved;
	}

	public String getType() {
		return type;
	}

	public String getId() {
		return uniqueId;
	}

	public int getDatasetSize() {
		return datasetSize;
	}

	public void setDatasetSize(int datasetSize) {
		this.datasetSize = datasetSize;
	}

	public void setId(String id) {
		this.uniqueId = id;
	}

	public int getNumberOfInputNodes() {
		return numberOfInputNodes;
	}

	public void setNumberOfInputNodes(int numberOfInputNodes) {
		this.numberOfInputNodes = numberOfInputNodes;
	}

	public int getNumberOfSLiMS() {
		return numberOfSLiMS;
	}

	public void setNumberOfSLiMS(int numberOfSLiMS) {
		this.numberOfSLiMS = numberOfSLiMS;
	}

	public int getNumberOfNodesWithSLims() {
		return numberOfNodesWithSLims;
	}

	public void setNumberOfNodesWithSLims(int numberOfNodesWithSLims) {
		this.numberOfNodesWithSLims = numberOfNodesWithSLims;
	}

	public HashSet<Integer> getNodeIndices() {
		return nodeIndices;
	}

	public void setNodeIndices(HashSet<Integer> nodeIndices) {
		this.nodeIndices = nodeIndices;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Double getMinSig() {
		return minSig;
	}

	public void setMinSig(Double minSig) {
		this.minSig = minSig;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPercentageComplete() {
		return percentageComplete;
	}

	public void setPercentageComplete(String percentageComplete) {
		this.percentageComplete = percentageComplete;
	}

	public int getSize() {
		return this.size;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public abstract void activate();

	public abstract void deActivate();

	public int getSwitchIndex() {
		return switchIndex;
	}

	public void setSwitchIndex(int switchIndex) {
		this.switchIndex = switchIndex;
	}

	public int compareTo(Run run) {
		return this.name.compareTo(run.getName());
	}

	public boolean isActive() {
		return isActive;
	}

	public abstract Object getValueAt(int col);

	public abstract void setValueAt(int col, Object obj);

	public abstract String getStringValueAt(int col);

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return (String) this.getName();
	}

}
