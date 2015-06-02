package vis.dbfetch;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.biojava3.core.sequence.ProteinSequence;
import org.biojava3.core.sequence.io.FastaReaderHelper;

public class dbfetch {

	public static String getDataForMultiUniprot(String[] IDs, String format) {
		// Parameters for dbfetch call
		String dbName = "uniprot"; // Database name (e.g. UniProtKB)

		String idall = ""; // Entry identifier, name or accession
		for (String ID : IDs) {
			idall = idall + ID + ",";
		}

		// Construct the dbfetch URL
		// dbfetch document style base URL
		String dbfetchBaseUrl = "http://www.ebi.ac.uk/Tools/dbfetch/dbfetch/";
		// Add the database name, identifiers and format to the URL
		String dbfetchUrl = dbfetchBaseUrl + dbName + "/" + idall + "/"
				+ format;
		// Get the page and print it.
		System.out.println(dbfetchUrl);
		return getHttpUrl(dbfetchUrl);
	}

	/**
	 * Get a web page using HTTP GET.
	 * 
	 * @param urlStr
	 *            The URL of the page to be retrieved as a string.
	 * @return A string containing the page data.
	 */
	private static String getHttpUrl(String urlStr) {
		URL website = null;
		try {
			website = new URL(urlStr);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		
		// Return the response data
		try {
			return IOUtils.toString(website.openStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static HashMap<String, String> getFastaHashMapFromString(
			String results) {
		LinkedHashMap<String, ProteinSequence> a = null;
		try {
			InputStream is = new ByteArrayInputStream(results.getBytes());
			a = FastaReaderHelper.readFastaProteinSequence(is);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// FastaReaderHelper.readFastaDNASequence for DNA sequences
		HashMap<String, String> map = new HashMap<String, String>();

		for (Entry<String, ProteinSequence> entry : a.entrySet()) {
			map.put(entry.getValue().getOriginalHeader().toString()
					.replace("|", "_").split("_")[1], entry.getValue()
					.getOriginalHeader()
					+ "\n"
					+ entry.getValue().getSequenceAsString());
		}

		return map;
	}

	public static HashMap<String, String> getDatHashMapFromString(String[] IDs,
			String results) {
		String[] entries = results.split("\n//");
		// FastaReaderHelper.readFastaDNASequence for DNA sequences
		HashMap<String, String> map = new HashMap<String, String>();
		System.out.println(entries.length);
		for (String entry : entries) {
			for (String line : entry.split("\n")) {
				if (line.length() > 5) {
					if (line.substring(0, 2).equals("AC")) {
						for (String id : IDs) {
							if (line.contains(id.trim())) {
								map.put(id.trim(), entry + "\n//");
							}
						}
					}
				}
			}
			// map.put(entry);
		}
		return map;
	}

	/**
	 * Execution entry point
	 * 
	 * @param args
	 *            Command-line arguments
	 * @return Exit status
	 */
	public static void main(String[] args) {
		String[] all = { "Q15139", "P05106", "Q76MZ3", "Q6SZW1", "Q3KRB6",
				"Q15654", "Q15475", "Q13562", "Q12874", "Q04917", "Q04725",
				"Q04724", "Q00978", "P61421" }; // Entry identifier, name or //
												// accession
		String results1 = getDataForMultiUniprot(all, "");
		System.out.println(getDatHashMapFromString(all, results1).size());
		String results2 = getDataForMultiUniprot(all, "fasta");
		System.out.println(getFastaHashMapFromString(results2).size());
	}
}
