package vis.exec;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

import vis.root.Variables;
import vis.tools.OSValidator;
import vis.ui.LogScreen;

public class CommandRunner {
	LogScreen progressBarWithText = null;
	Process proc = null;
	Variables variables;
	StreamGobbler errorGobbler = null;
	StreamGobbler outputGobbler = null;
	private Logger log;

	public CommandRunner(Logger log, Variables variables) {
		super();
		this.log = log;
		this.variables = variables;
	}

	public void cancel() {
		try {
			proc.destroy();
			errorGobbler.cancel();
			outputGobbler.cancel();
		} catch (NullPointerException e) {
			log.error(e.getMessage());
		}

	}
	
	public static String[] split(String str) {
	    str += " "; // To detect last token when not quoted...
	    ArrayList<String> strings = new ArrayList<String>();
	    boolean inQuote = false;
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < str.length(); i++) {
	        char c = str.charAt(i);
	        if (c == '\"' || c == ' ' && !inQuote) {
	            if (c == '\"')
	                inQuote = !inQuote;
	            if (!inQuote && sb.length() > 0) {
	                strings.add(sb.toString());
	                sb.delete(0, sb.length());
	            }
	        } else
	            sb.append(c);
	    }
	    return strings.toArray(new String[strings.size()]);
	}
	
	public String getStdOutLog()
	{
		return outputGobbler.getLog();
	}
	
	public String getStdErrLog()
	{
		return errorGobbler.getLog();
	}
	

	public void runCommand(String runDir, String pythonExec, String scriptPath,
			String command) {
		if (OSValidator.isWindows()) {
			if (pythonExec.length() < 1 || pythonExec == null) {
				pythonExec = "python.exe";
				command = command + " win32=T";
			}
			scriptPath = "\"" + scriptPath + "\"";
		} else {
			if (pythonExec.length() < 1 || pythonExec == null) {
				pythonExec = "python";
				command = command + " win32=F";
			}
			scriptPath = scriptPath.replace(" ", "\\");
		}
		File wd = new File(runDir);
		Runtime rt = Runtime.getRuntime();

		final Map<String, String> systemEnvs = new HashMap<String, String>();
		try {
			String fullCommand = (pythonExec + " " + scriptPath + " " + command);
			log.info(fullCommand);

			ProcessBuilder processBuilder = new ProcessBuilder(split(fullCommand));

			File tmp = new File(variables.getiUPredPath());
			Map env = processBuilder.environment();
			env.put("IUPred_PATH", tmp.getParent());
			env.putAll(System.getenv());
			
			
			processBuilder.directory(wd);
			proc = processBuilder.start();

			errorGobbler = new StreamGobbler(proc.getErrorStream(), "ERR");

			outputGobbler = new StreamGobbler(proc.getInputStream(), "OUT");

			errorGobbler.start();
			outputGobbler.start();

			// any error???
			try {
				int exitVal = proc.waitFor();
			} catch (InterruptedException e) {
				log.error(e.getMessage());
			}

		} catch (IOException e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			throw new RuntimeException();
		}
		
	}
}
