package vis.exec;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import vis.ui.LogScreen;

class LogGobbler extends Thread {
	String path;
	LogScreen progressBarWithText = null;
	boolean running = true;

	LogGobbler(String path, LogScreen progressBarWithText) {
		this.path = path;
		this.progressBarWithText = progressBarWithText;
	}

	public void run() {
		try {
			BufferedInputStream reader = new BufferedInputStream( 
			    new FileInputStream(path) );

			    while( running ) {
			        if( reader.available() > 0 ) {
			        	progressBarWithText.appendText("" + (char)reader.read());
			        }
			        else {
			            try {
			                sleep( 500 );
			            }
			            catch( InterruptedException ex )
			            {
			                running = false;
			            }
			        }
			    }
			    reader.close();
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}
	}
}