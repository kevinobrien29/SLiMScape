package vis.root;

import java.io.File;

public class Variables {
	boolean iupredReady;
	boolean conservationReady;

	private String pythonExec;
	private String slimsuiteHome;
	private String runsDir;
	private String iUPredExecutable;
	private String orthDB;
	private String blastpath;
	private String muscle;
	private String clustalw;

	public Variables() {
		super();
	}

	public Variables(String pythonExec, String slimsuiteHome, String runsDir,
			String iUPredPath, String orthDB, String blastpath, String muscle,
			String clustalw) {
		super();
		this.pythonExec = pythonExec;
		this.slimsuiteHome = slimsuiteHome;
		this.runsDir = runsDir;
		this.iUPredExecutable = iUPredPath;
		this.orthDB = orthDB;
		this.blastpath = blastpath;
		this.muscle = muscle;
		this.clustalw = clustalw;
	}

	public String getSlimsuiteHome() {
		return slimsuiteHome;
	}

	public void setSlimsuiteHome(String slimsuiteHome) {
		this.slimsuiteHome = slimsuiteHome;
	}

	public String getPythonExec() {
		if (this.pythonExec == null)
		{
			this.pythonExec = "";
		}
		return pythonExec;
	}

	public void setpythonExec(String pythonExec) {
		this.pythonExec = pythonExec;
	}

	public String getRunsDir() {
		return runsDir;
	}

	public void setRunsDir(String runsDir) {
		this.runsDir = runsDir;
	}

	public String getiUPredPath() {
		return iUPredExecutable;
	}

	public void setiUPredPath(String iUPredPath) {
		this.iUPredExecutable = iUPredPath;
	}

	public String getOrthDB() {
		return orthDB;
	}

	public void setOrthDB(String orthDB) {
		this.orthDB = orthDB;
	}

	public String getBlastpath() {
		return blastpath;
	}

	public void setBlastpath(String blastpath) {
		this.blastpath = blastpath;
	}

	public String getMuscle() {
		return muscle;
	}

	public void setMuscle(String muscle) {
		this.muscle = muscle;
	}

	public String getClustalw() {
		return clustalw;
	}

	public void setClustalw(String clustalw) {
		this.clustalw = clustalw;
	}

	public boolean iupredIsReady() {
		File nww = new File(this.iUPredExecutable);
		if (nww.exists() && nww.isFile())
		{
			return true;
		}
		return false;
	}

	public boolean conservationIsReady() {
		File nww = new File(this.orthDB);
		if (nww.exists() && nww.isFile())
		{
			return true;
		}
		return false;
	}
	
	public String getGopherDir()
	{
		return this.runsDir + "/gopher";
	}

	public String getOptionsString() {

		return "orthdb=\"" + orthDB + "\" iupath=\"" + iUPredExecutable
				+ "\" blastpath=\"" + blastpath + "\" muscle=\"" + muscle
				+ "\" clustalw=\"" + clustalw + "\"";
	}

	public String[] getOptionsArray() {

		String[] result = { "orthdb=", orthDB, "orthdb=", orthDB, "iupath=",
				iUPredExecutable, "blastpath", blastpath, "muscle=", muscle,
				"clustalw=", clustalw };
		return result;
	}

}
