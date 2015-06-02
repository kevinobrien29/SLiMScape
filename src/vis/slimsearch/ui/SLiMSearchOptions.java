package vis.slimsearch.ui;

import vis.root.Variables;

public class SLiMSearchOptions {
	private boolean dismask;
	private double cutoff = 0.1;
	private String customParameters = "";
	private boolean consmask; 
	private Variables variables;
	
	public SLiMSearchOptions(Variables variables) {
		super();
		this.variables = variables;
	}

	public boolean isDismask() {
		return dismask;
	}

	public void setDismask(boolean dismask) {
		this.dismask = dismask;
	}

	public double getCutoff() {
		return cutoff;
	}
	
	public boolean isUsingConservation() {
		return consmask;
	}
	
	public void setConsmask(boolean consmask) {
		this.consmask = consmask;
	}

	public void setCutoff(double cutoff) {
		this.cutoff = cutoff;
	}
	
	

	public String getCustomParameters() {
		return customParameters;
	}

	public void setCustomParameters(String customParameters) {
		this.customParameters = customParameters;
	}
	
	public String getOptionsString() {
		String disorderParams = " iupath=\"" + variables.getiUPredPath() + "\""
				+ " dismask=T";
		String coservationParams = " slimcalc=Cons" + " usegopher=T" + " gopherdir=\"" + variables.getGopherDir() + "\""
				+ " usealn=T" + " orthdb=\"" + variables.getOrthDB() + "\""
				+ " consmask=T" + " conscore=rel ";
		String commonParams = " blastpath=\"" + variables.getBlastpath() + "\""
				+ " muscle=\"" + variables.getMuscle() + "\"" + " clustalw=\""
				+ variables.getClustalw() + "\" ";

		String result = "";
		if (dismask == true) {
			result = result + disorderParams;
		}
		if (consmask == true) {
			result = result + coservationParams;
		}
		result = result + commonParams + " maxsize=1000000 " + this.customParameters;
		return result;
	}
}