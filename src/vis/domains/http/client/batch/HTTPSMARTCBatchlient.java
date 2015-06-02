package vis.domains.http.client.batch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import vis.data.attribute.Domain;
import vis.domains.http.client.ClientHttpRequest;

public class HTTPSMARTCBatchlient {

	String mainURL = "http://smart.embl.de/smart/show_motifs.pl";

	String statusURL = "http://smart.embl.de/smart/job_status.pl";
	int waitLoop = 20000;
	
	public static void main(String[] args) {

		String fasta = ">sp|P49023|PAXI_HUMAN Paxillin OS=Homo sapiens GN=PXN PE=1 SV=3\n"
				+ "MDDLDALLADLESTTSHISKRPVFLSEETPYSYPTGNHTYQEIAVPPPVPPPPSSEALNG\n"
				+ "TILDPLDQWQPSSSRFIHQQPQSSSPVYGSSAKTSSVSNPQDSVGSPCSRVGEEEHVYSF\n"
				+ "PNKQKSAEPSPTVMSTSLGSNLSELDRLLLELNAVQHNPPGFPADEANSSPPLPGALSPL\n"
				+ "YGVPETNSPLGGKAGPLTKEKPKRNGGRGLEDVRPSVESLLDELESSVPSPVPAITVNQG\n"
				+ "EMSSPQRVTSTQQQTRISASSATRELDELMASLSDFKIQGLEQRADGERCWAAGWPRDGG\n"
				+ "RSSPGGQDEGGFMAQGKTGSSSPPGGPPKPGSQLDSMLGSLQSDLNKLGVATVAKGVCGA\n"
				+ "CKKPIAGQVVTAMGKTWHPEHFVCTHCQEEIGSRNFFERDGQPYCEKDYHNLFSPRCYYC\n"
				+ "NGPILDKVVTALDRTWHPEHFFCAQCGAFFGPEGFHEKDGKAYCRKDYFDMFAPKCGGCA\n"
				+ "RAILENYISALNTLWHPECFVCRECFTPFVNGSFFEHDGQPYCEVHYHERRGSLCSGCQK\n"
				+ "PITGRCITAMAKKFHPEHFVCAFCLKQLNKGTFKEQNDKPYCQNCFLKLFC\n"
				+ ">sp|P49023|PAXI_HUMAN\n"
				+ "MDDLDALLADLESTTSHISKRPVFLSEETPYSYPTGNHTYQEIAVPPPVPPPPSSEALNG\n"
				+ "TILDPLDQWQPSSSRFIHQQPQSSSPVYGSSAKTSSVSNPQDSVGSPCSRVGEEEHVYSF\n"
				+ "PNKQKSAEPSPTVMSTSLGSNLSELDRLLLELNAVQHNPPGFPADEANSSPPLPGALSPL\n"
				+ "YGVPETNSPLGGKAGPLTKEKVGFFKRSLLDELESSVPSPVPAITVNQG\n"
				+ "EMSSPQRVTSTQQQTRISASSATRELDELMASLSDFKIQGLEQRADGERCWAAGWPRDGG\n"
				+ "RSSPGGQDEGGFMAQGKTGSSSPPGGPPKPGSQLDSMLGSLQSDLNKLGVATVAKGVCGA\n"
				+ "CKKPIAGQVVTAMGKTWHPEHFVCTHCQEEIGSRNFFERDGQPYCEKDYHNLFSPRCYYC\n"
				+ "NGPILDKVVTALDRTWHPEHFFCAQCGAFFGPEGFHEKDGKAYCRKDYFDMFAPKCGGCA\n"
				+ "RAILENYISALNTLWHPECFVCRECFTPFVNGSFFEHDGQPYCEVHYHERRGSLCSGCQK\n"
				+ "PITGRCITAMAKKFHPEHFVCAFCLKQLNKGTFKEQNDKPYCQNCFLKLFC";

		HTTPSMARTCBatchlient client = new HTTPSMARTCBatchlient();
		System.out.println(client.getDomains("P49023", fasta));
	}

	public String getDataAtURL(String uRL, String argument) {
		String result = "";
		URL oracle = null;
		try {
			oracle = new URL(uRL + "?jobid=" + argument);
			URLConnection yc = oracle.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					yc.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null)
				result = result + inputLine + "\n";
			in.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public ArrayList<Domain> getDomains(String nodeId, String fasta) {
		//System.out.println(nodeId);

		String result1 = this.postContent(mainURL, new Object[] { "SEQUENCE",
				fasta, "TEXTONLY", "1", "DO_PFAM", "DO_PFAM", });
		// got result
		if (result1.split("\n")[0].contains("-- SMART RESULT")) {
			return parseResults(result1, nodeId);
		} else
		// got job id or error
		{
			String jobID = "";
			for (String line : result1.split("\n")) {
				if (line.contains("job_status.pl?jobid=")) {
					int start = line.indexOf("job_status.pl?jobid=")
							+ "job_status.pl?jobid=".length();
					int end = line.substring(start, line.length() - 1).indexOf(
							"\"")
							+ start;

					if (start < end) {
						jobID = line.substring(start, end);
					}
				}
			}
			// got jobId
			if (jobID.length() > 0) {
				String result2 = "SMART: Request is still in the queue";
				int timer = 0;
				while (result2.contains("SMART: Request is still in the queue") && timer < waitLoop) {
					result2 = this.getDataAtURL(statusURL, jobID);
					try {
						Thread.sleep(5);
						timer = timer + 1;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (result2.split("\n")[0].contains("-- SMART RESULT")) {
					return parseResults(result2, nodeId);
				} else {
					return null;
				}
			} else {
				return null;
			}
		}
	}

	public ArrayList<Domain> parseResults(String data, String nodeId) {
		ArrayList<Domain> domainsList = new ArrayList<Domain>();
		for (String line : data.split("\n\n")) {
			String name = "";
			String evalue = "";
			String start = "";
			String end = "";
			if (line.indexOf("DOMAIN=") != -1 & line.indexOf("EVALUE=") != -1) {
				int nameStart = line.indexOf("DOMAIN=") + 7;
				int nameEnd = line.indexOf("\n", nameStart);
				if (nameStart < nameEnd) {
					name = line.substring(nameStart, nameEnd);
				}

				int evalueStart = line.indexOf("EVALUE=") + 7;
				int evalueEnd = line.indexOf("\n", evalueStart);
				if (evalueStart < evalueEnd) {
					evalue = line.substring(evalueStart, evalueEnd);
				}

				int startStart = line.indexOf("START=") + 6;
				int startEnd = line.indexOf("\n", startStart);
				if (startStart < startEnd) {
					start = line.substring(startStart, startEnd);
				}

				int endStart = line.indexOf("END=") + 4;
				int endEnd = line.indexOf("\n", endStart);
				if (endStart < endEnd) {
					end = line.substring(endStart, endEnd);
				}

				Domain domain = new Domain(name, "", start, end, evalue, nodeId);
				domainsList.add(domain);

			}
		}
		return domainsList;

	}

	public String postContent(String uRL, Object[] data) {
		String result = null;
		try {
			InputStream serverInput = ClientHttpRequest.post(new java.net.URL(
					uRL), data);
			ArrayList<Byte> bytes = new ArrayList<Byte>();
			while (serverInput.available() > 0) {
				Byte byte_ = (byte) serverInput.read();
				bytes.add(byte_);
			}

			byte[] bytesArray = new byte[bytes.size()];
			for (int i = 0; i < bytes.size(); i++) {
				bytesArray[i] = bytes.get(i);
			}

			result = new String(bytesArray);
			return result;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
