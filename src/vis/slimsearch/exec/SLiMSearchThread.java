package vis.slimsearch.exec;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import vis.data.Constants;
import vis.data.attribute.Attribute;
import vis.data.attribute.SLiMSearchResult;
import vis.exec.CommandRunner;
import vis.root.Variables;
import vis.slimfinder.exec.SLiMThread;
import vis.slimfinder.ui.NetworkDataCollector;
import vis.slimsearch.SLiMSearchImportHelper;
import vis.slimsearch.ui.SLiMSearchAttributeTable;
import vis.slimsearch.ui.SLiMSearchOptions;
import vis.tools.CytoscapeHelper;
import vis.ui.LogScreen;
import vis.ui.TripleSplitPane;
import cytoscape.CyNetwork;

public class SLiMSearchThread extends SLiMThread{

	private String SLiMSearchPath;
	private String runDir = Constants.getsLiMSearchRunDir();
	private String fasta = null;
	private String fastapath = null;
	private String motifs = null;
	private String motifPath = null;
	private LogScreen progressBarWithText = null;
	private TripleSplitPane tripleSplitPane;;
	private HashSet<String> nodeIds = null;
	private String id = null;
	private SLiMSearchOptions sLiMSearchOptions = null;
	private String pythonExec = null;
	private String gopherDir = null;
	private boolean cancel = false;
	CommandRunner commandRunner = null;
	private Logger log = LoggerFactory.getLogger("exec");

	public SLiMSearchThread(CyNetwork network, Variables variables, SLiMSearchOptions sLiMSearchOptions, String id,
			String fasta, String motifs, TripleSplitPane resultsPanel,
			HashSet<Integer> nodeIds) {
		super();
		this.SLiMSearchPath = variables.getSlimsuiteHome() + "/tools/slimsearch.py";
		this.runDir = variables.getRunsDir() + "/slimsearch";
		this.sLiMSearchOptions = sLiMSearchOptions;
		this.id = id;
		this.fasta = fasta;
		this.tripleSplitPane = resultsPanel;
		this.motifs = motifs;
		this.nodeIds = NetworkDataCollector.getNodeNames(network, nodeIds);
		this.pythonExec = variables.getPythonExec();
		this.gopherDir = variables.getRunsDir() + "/gopher";
		
		progressBarWithText = new LogScreen("Running SLiMSearch");
		commandRunner = new CommandRunner(log, variables);
	}
	
	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		this.cancel = false;
		commandRunner.cancel();
	}

	@Override
	public void run() {
		MDC.put("thread", id);
		MDC.put("type", "slimfinder");
		
		log.info("SlimSearch Thread running");
		
		tripleSplitPane.startRun(id, nodeIds);
		// String resultsDir = runDir + "/" + id;
		String resultsDir = CytoscapeHelper.LocalExec.createFolder(runDir, id);
		fastapath = resultsDir + "/" + "input.fas";
		motifPath = resultsDir + "/" + "motifs";
		CytoscapeHelper.LocalExec.createFile(fastapath, fasta);
		CytoscapeHelper.LocalExec.createFile(motifPath, motifs);

		String logPath = resultsDir + "/slimsearch.log";
		String resultsFile = resultsDir + "/results.csv";
		try
		{
			if (resultsDir != null) {
				String command = " i=-1 seqin=\"%s\" motifs=\"%s\" resdir=\"%s\" resfile=\"%s\" "
						+ sLiMSearchOptions.getOptionsString();
				command = String.format(command, fastapath,
						motifPath, resultsDir, resultsFile);

				log.info(command);
				// this.progressBarWithText.setVisible(true);
				
				commandRunner.runCommand(resultsDir, this.pythonExec, SLiMSearchPath, command);
			} else {
				throw new Exception();
			}

			File tmp = new File(resultsFile);
			if (tmp.exists() && !this.cancel) {
				SLiMSearchImportHelper SLiMSearchImportHelper = new SLiMSearchImportHelper(
						resultsDir + "/results.csv", resultsDir
								+ "/results.summary.csv");

				ArrayList<HashMap<String, Object>> slims = SLiMSearchImportHelper
						.getAllDataHashMap();
				if (slims.size() > 0) {

					Double minPOcc = 0.0;
					
					ArrayList<Attribute> SLiMs = new ArrayList<Attribute>();
					for (HashMap<String, Object> row : slims) {
						SLiMSearchResult slim = new SLiMSearchResult(row);
						SLiMs.add(slim);
						if (minPOcc == null) {
							minPOcc = slim.getPOcc();
						} else if (minPOcc > slim.getPOcc()) {
							minPOcc = slim.getPOcc();
						}
					}
					SLiMSearchAttributeTable slimPanelAggregate = new SLiMSearchAttributeTable(SLiMs);
					slimPanelAggregate.replaceModelData(SLiMs);

					int numberOfInputNodes = this.nodeIds.size();
					int numberOfSLiMS = slims.size();

					tripleSplitPane.addResults(id, slimPanelAggregate, slimPanelAggregate,
							numberOfInputNodes, numberOfSLiMS, minPOcc, SLiMs);
				} else {
					tripleSplitPane.markFailedResult(id, logPath, commandRunner.getStdOutLog(),commandRunner.getStdErrLog(), "No results found");
					log.error("gracefully terminated after cancel");
				}

			} else {
				if (cancel) {
					tripleSplitPane.markFailedResult(id, logPath, commandRunner.getStdOutLog(),commandRunner.getStdErrLog(), "Cancelled");
					log.error("gracefully terminated after cancel");
				} else {
					tripleSplitPane.markFailedResult(id, logPath, commandRunner.getStdOutLog(),commandRunner.getStdErrLog(), "No results found");
					log.error("result not found");
				}
				
				
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error(e.getMessage());
			tripleSplitPane.markFailedResult(id, logPath, commandRunner.getStdOutLog(),commandRunner.getStdErrLog(), "error");
		}
		
		log.info("SlimFinder Thread ran to completion");
		
		MDC.remove("thread");
		MDC.remove("type");
		
	}

}
