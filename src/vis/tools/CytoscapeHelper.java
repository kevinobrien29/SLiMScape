package vis.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import cytoscape.CyNetwork;
import cytoscape.Cytoscape;
import cytoscape.view.CyNetworkView;

public class CytoscapeHelper {

	public static class General {
		public static String[] concat(String[] A, String[] B) {
			String[] C = new String[A.length + B.length];
			System.arraycopy(A, 0, C, 0, A.length);
			System.arraycopy(B, 0, C, A.length, B.length);
			return C;
		}
	}

	public static class LocalExec {
		
		public static String getUniqueFolderId(String root) {
			//TO DO - check if folder exists already and celan if necessary
			String result = UUID.randomUUID().toString();
			return result;
		}
		
		public static String createFolder(String root, String id) {
			String result = root + "/" + id;
			deleteDir(new File(result));
			boolean success = (new File(result)).mkdirs();
			if (!success) {
				return null;
			}
			return result;
		}

		public static void createFile(String dest, String data) {
			try {
				// Create file
				FileWriter fstream = new FileWriter(dest);
				BufferedWriter out = new BufferedWriter(fstream);
				out.write(data);
				// Close the output stream
				out.close();
			} catch (Exception e) {// Catch exception if any
				System.err.println("Error: " + e.getMessage());
			}
		}

		public static String readFile(String fileName) {

			File file = new File(fileName);

			char[] buffer = null;

			try {
				BufferedReader bufferedReader = new BufferedReader(
						new FileReader(file));

				buffer = new char[(int) file.length()];

				int i = 0;
				int c = bufferedReader.read();

				while (c != -1) {
					buffer[i++] = (char) c;
					c = bufferedReader.read();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return new String(buffer);
		}

		// Deletes all files and subdirectories under dir.
		// Returns true if all deletions were successful.
		// If a deletion fails, the method stops attempting to delete and
		// returns false.
		public static boolean deleteDir(File dir) {
			if (dir.isDirectory()) {
				String[] children = dir.list();
				for (int i = 0; i < children.length; i++) {
					boolean success = deleteDir(new File(dir, children[i]));
					if (!success) {
						return false;
					}
				}
			}

			// The directory is now empty so delete it
			return dir.delete();
		}
	}

	public static CyNetwork getNetwork(String networkName) {
		Set<CyNetwork> networks = Cytoscape.getNetworkSet();

		for (CyNetwork network : networks) {
			if (network.getTitle().equals(networkName)) {
				return network;
			}
		}
		return null;
	}

	public static CyNetworkView getNetworkView(String networkName) {
		Map<String, CyNetworkView> networks = Cytoscape.getNetworkViewMap();

		for (String network : networks.keySet()) {
			if (networks.get(network).getTitle().equals(networkName)) {
				return networks.get(network);
			}
		}
		return null;
	}
}
