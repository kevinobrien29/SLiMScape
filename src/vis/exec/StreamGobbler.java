package vis.exec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class StreamGobbler extends Thread {
	InputStream is;
	String type;
	boolean loop = true;
	String log = "";

	StreamGobbler(InputStream is, String type) {
		this.is = is;
		this.type = type;
	}
	
	public synchronized String getLog()
	{
		return log;
	}
	
	public synchronized void addToLog(String logLine)
	{
		log = log + logLine + "\n";
	}
	
	private synchronized boolean checkContinueRunning()
	{
		return this.loop;
	}
	
	public synchronized void cancel()
	{
		this.loop = false;
	}

	public void run() {
		try {
			InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line=null;
            while ( (line = br.readLine()) != null && this.checkContinueRunning())
            {
            	addToLog(line);
            }
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}