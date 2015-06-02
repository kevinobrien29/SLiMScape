package vis.slimfinder.exec;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import vis.data.attribute.Attribute;
import vis.data.attribute.SLiMFinderResult;
import vis.exec.CommandRunner;
import vis.root.Variables;
import vis.slimfinder.SLiMFinderImportHelper;
import vis.slimfinder.ui.SLiMFinderAttributeTable;
import vis.slimfinder.ui.SLiMFinderOptions;
import vis.slimsearch.ui.RunSLiMSearchPanel;
import vis.tools.CytoscapeHelper;
import vis.ui.TripleSplitPane;
import cytoscape.CyNetwork;

public class SLiMFinderThread extends SLiMThread {

	private String SLiMFinderPath = null;;
	private String runDir = null;;
	private String fasta = null;
	private String fastapath = null;
	// private LogScreen progressBarWithText = null;
	private TripleSplitPane tripleSplitPane;
	private HashSet<String> nodeIds = null;
	private HashSet<String> nodeIdsSet = null;
	private String id = null;
	private RunSLiMSearchPanel slimSearchPanel = null;
	private SLiMFinderOptions sLiMFinderOptions = null;
	private CyNetwork network = null;
	private Variables variables;
	private String pythonExec = null;
	private CommandRunner commandRunner = null;
	private boolean cancel = false;

	private Logger log = LoggerFactory.getLogger("exec");

	public SLiMFinderThread(CyNetwork network, Variables variables,
			SLiMFinderOptions sLiMFinderOptions, String id, String fasta,
			TripleSplitPane resultsPanel, HashSet<String> nodeIds,
			RunSLiMSearchPanel slimSearchPanel) {
		super();
		this.network = network;
		this.variables = variables;
		this.id = id;
		this.fasta = fasta;
		this.tripleSplitPane = resultsPanel;
		this.slimSearchPanel = slimSearchPanel;
		this.sLiMFinderOptions = sLiMFinderOptions;
		this.SLiMFinderPath = variables.getSlimsuiteHome()
				+ "/tools/slimfinder.py";
		this.runDir = variables.getRunsDir() + "/slimfinder";
		this.pythonExec = variables.getPythonExec();

		nodeIdsSet = new HashSet<String>();
		for (String nodeId : nodeIds) {
			nodeIdsSet.add(nodeId);
		}
		this.nodeIds = nodeIds;

		// progressBarWithText = new LogScreen("Running SLiMFinder");
		commandRunner = new CommandRunner(log, variables);
	}

	public void cancel() {
		this.cancel = true;
		commandRunner.cancel();
	}

	@Override
	public void run() {

		MDC.put("thread", id);
		MDC.put("type", "slimfinder");

		log.info("SlimFinder Thread running");
		boolean isUsingConservation = sLiMFinderOptions.isUsingConservation();
		tripleSplitPane.startRun(id, nodeIdsSet);
		// String resultsDir = runDir + "/" + id;
		String resultsDir = CytoscapeHelper.LocalExec.createFolder(runDir, id);
		String logPath = resultsDir + "/slimfinder.log";

		fastapath = resultsDir + "/" + "input.fas";

		try {
			CytoscapeHelper.LocalExec.createFile(fastapath, fasta);
			String resultsFile = resultsDir + "/results.csv";
			if (resultsDir != null) {

				String command = " i=-1 seqin=\"%s\" resdir=\"%s\" resfile=\"%s\" "
						+ sLiMFinderOptions.getOptionsString();
				command = String.format(command, fastapath, resultsDir,
						resultsFile);
				log.info(command);

				// this.progressBarWithText.setVisible(true);

				File log = new File(logPath);
				log.createNewFile();
				commandRunner.runCommand(resultsDir, this.pythonExec,
						SLiMFinderPath, command);
			} else {
				throw new Exception();
			}

			File tmp = new File(resultsDir + "/input.occ.csv");
			if (tmp.exists() && !this.cancel) {
				SLiMFinderImportHelper SLiMFinderImportHelper = new SLiMFinderImportHelper(
						resultsDir + "/input.occ.csv", resultsFile);

				ArrayList<Attribute> SLiMs = new ArrayList<Attribute>();

				Double minSig = null;
				for (LinkedHashMap<String, Object> row : SLiMFinderImportHelper
						.getAllDataHashMap()) {
					SLiMFinderResult slim = new SLiMFinderResult(row,
							isUsingConservation);
					SLiMs.add(slim);

					if (minSig == null) {
						minSig = slim.getSig();
					} else if (minSig > slim.getSig()) {
						minSig = slim.getSig();
					}
				}
				SLiMFinderAttributeTable slimPanel = new SLiMFinderAttributeTable(
						network, this.slimSearchPanel, SLiMs);
				// slimPanel.replaceModelData(SLiMs);

				int numberOfInputNodes = this.nodeIds.size();
				int numberOfSLiMS = SLiMs.size();

				tripleSplitPane.addResults(id, slimPanel, slimPanel, numberOfInputNodes,
						numberOfSLiMS, minSig, SLiMs);
			} else {
				if (cancel) {
					tripleSplitPane.markFailedResult(id, logPath, commandRunner.getStdOutLog(),commandRunner.getStdErrLog(), "Cancelled");
					log.error("gracefully terminated after cancel");
				} else {
					tripleSplitPane.markFailedResult(id, logPath, commandRunner.getStdOutLog(),commandRunner.getStdErrLog(), "No results found");
					log.error("result not found");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			tripleSplitPane.markFailedResult(id, logPath, commandRunner.getStdOutLog(),commandRunner.getStdErrLog(), "error");
		}
		log.info("SlimFinder Thread ran to completion");

		MDC.remove("thread");
		MDC.remove("type");
	}

}
