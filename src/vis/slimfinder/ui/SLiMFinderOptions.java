package vis.slimfinder.ui;

import vis.root.Variables;

public class SLiMFinderOptions {
	private Variables variables;

	private boolean dismask;
	private double cutoff;
	private String walltime;
	private boolean consmask;
	boolean featuremask;

	private String customParameters = "";

	public SLiMFinderOptions(Variables variables) {
		super();
		this.variables = variables;
	}

	public boolean isUsingConservation() {
		return consmask;
	}

	public void setUsingConservation(boolean usingConservation) {
		this.consmask = usingConservation;
	}

	public void setFeaturemask(boolean featuremask) {
		this.featuremask = featuremask;
	}
	
	public void setDismask(boolean dismaskBoolean) {
		this.dismask = dismaskBoolean;
	}

	public double getCutoff() {
		return cutoff;
	}

	public void setCutoff(double cutoff) {
		this.cutoff = cutoff;
	}

	public void setWalltime(String walltime) {
		this.walltime = walltime;
	}

	public void setConsmask(boolean consmask) {
		this.consmask = consmask;
	}

	public void setCustomParameters(String customParameters) {
		this.customParameters = customParameters;
	}

	private String booleanToString(boolean input) {
		if (input == true) {
			return "T";
		} else if (input == false) {
			return "F";
		}
		return null;
	}

	public String getOptionsString() {
		String disorderParams = " iupath=\"" + variables.getiUPredPath() + "\""
				+ " dismask=T";
		String coservationParams = " slimcalc=Cons" + " usegopher=T" + " gopherdir=\"" + variables.getGopherDir() + "\""
				+ " usealn=T" + " orthdb=\"" + variables.getOrthDB() + "\""
				+ " consmask=T" + " conscore=rel ";
		String featureParams = " ftmask=T";
		String commonParams = " probcut=" + cutoff + " walltime=" + walltime
				+ " blastpath=\"" + variables.getBlastpath() + "\""
				+ " muscle=\"" + variables.getMuscle() + "\"" + " clustalw=\""
				+ variables.getClustalw() + "\" ";

		String result = "";
		if (dismask == true) {
			result = result + disorderParams;
		}
		if (consmask == true) {
			result = result + coservationParams;
		}
		if (featuremask == true) {
			result = result + featureParams;
		}
		result = result + commonParams + " " + customParameters;
		return result;
	}
}