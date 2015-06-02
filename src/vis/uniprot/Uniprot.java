package vis.uniprot;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Uniprot {

	Logger log = LoggerFactory.getLogger(Uniprot.class);

	public Uniprot() {
		// TODO Auto-generated constructor stub
	}

	public String getUniprotTxt(String uniprotId, String node_id) {
		URL u;
		InputStream is = null;
		DataInputStream dis;
		String s = "";
		String result = "";
		String finalUniprot = "";

		try {

			u = new URL("http://www.uniprot.org/uniprot/" + uniprotId + ".txt");

			log.debug("fetched " + uniprotId);
			is = u.openStream();

			dis = new DataInputStream(new BufferedInputStream(is));

			while ((s = dis.readLine()) != null) {
				result = result + s + "\n";
			}

			String markedFasta = "";
			String[] SplitResult = result.split("\n");
			for (int i = 0; i < SplitResult.length; i++) {
				if (SplitResult[i].substring(0, 2).equals("DE")) {
					SplitResult[i] = "";
				}
			}

			for (int i = 0; i < SplitResult.length; i++) {
				if (SplitResult[i].equals("")) {
					SplitResult[i] = "DE  ";
					SplitResult[i] = SplitResult[i] + " " + node_id;
					break;
				}
			}

			for (int i = 0; i < SplitResult.length; i++) {
				if (SplitResult[i].equals("")) {
				} else {
					finalUniprot = finalUniprot + SplitResult[i] + "\n";
				}
			}

		} catch (MalformedURLException mue) {

			log.error(mue.getMessage());

		} catch (IOException ioe) {

			log.error(ioe.getMessage());
		} catch (Exception e) {

			log.error(e.getMessage());

		} finally {

			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}

		}
		return finalUniprot;
	}

	public String getFasta(String uniprotId) throws Exception {
		URL url;
		InputStream in = null;
		BufferedReader dis;
		String s = "";
		String result = "";

		try {

			url = new URL("http://www.uniprot.org/uniprot/" + uniprotId
					+ ".fasta");

			URLConnection con = url.openConnection();
			con.connect();
			con.setReadTimeout(15000);
			in = con.getInputStream();

			log.debug("fetched " + uniprotId);

			dis = new BufferedReader(new InputStreamReader(in));

			while ((s = dis.readLine()) != null) {
				result = result + s + "\n";
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;

		} finally {

			try {
				in.close();
			} catch (IOException ioe) {
				log.error(ioe.getMessage());
			}

		}
		return result;
	}

	public String getSequence(String uniprotId) {
		URL u;
		InputStream is = null;
		DataInputStream dis;
		String s = "";
		String result = "";

		try {

			u = new URL("http://www.uniprot.org/uniprot/" + uniprotId
					+ ".fasta");

			log.debug("fetched " + uniprotId);
			is = u.openStream();

			dis = new DataInputStream(new BufferedInputStream(is));

			dis.readLine();
			while ((s = dis.readLine()) != null) {
				result = result + s;
			}

		} catch (Exception e) {

			log.error(e.getMessage());

		} finally {

			try {
				is.close();
			} catch (IOException e) {
				log.error(e.getMessage());
			}

		}
		return result;
	}

	public static void main(String[] args) {
		Uniprot uniprot = new Uniprot();
		try {
			System.out.println(uniprot.getFasta("Q04656z"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
