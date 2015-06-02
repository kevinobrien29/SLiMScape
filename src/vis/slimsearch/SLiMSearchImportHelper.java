package vis.slimsearch;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import vis.opencsv.CSVReader;


public class SLiMSearchImportHelper {
	String slims = null;
	String summary = null;
	
	String[] includedOccColumns = { "Motif","Pattern","Match","Desc","Cons" };
	String[] includedResultColumns = { "Masking","UPNum","IC","N_Occ","E_Occ","p_Occ","pUnd_Occ","N_Seq","E_Seq","p_Seq","pUnd_Seq","Cons_mean" };

	HashMap<String, Class> occTypes = new HashMap<String, Class>();
	HashMap<String, Class> resultTypes = new HashMap<String, Class>();
	ArrayList<String> priority = new ArrayList<String>();

	int result_Dataset = 0;
	int result_Motif = 3;

	int summary_Dataset = 0;
	int summary_Motif = 7;

	List<String[]> allSLiMs = null;
	List<String[]> allSummary = null;

	public SLiMSearchImportHelper(String slims, String summary) {
		super();
		this.slims = slims;
		this.summary = summary;
		
		occTypes.put("Motif", String.class);//
		occTypes.put("Pattern", String.class);//
		occTypes.put("Match", String.class);//
		occTypes.put("Desc", String.class);//
		occTypes.put("Cons", Double.class);//

		resultTypes.put("Masking", String.class);//
		resultTypes.put("UPNum", Integer.class);
		resultTypes.put("IC", Double.class);//
		resultTypes.put("N_Occ", Integer.class);
		resultTypes.put("E_Occ", Double.class);
		resultTypes.put("p_Occ", Double.class);
		resultTypes.put("pUnd_Occ", Double.class);
		resultTypes.put("N_Seq", Integer.class);
		resultTypes.put("E_Seq", Double.class);
		resultTypes.put("p_Seq", Double.class);
		resultTypes.put("pUnd_Seq", Double.class);
		resultTypes.put("Cons_mean", Double.class);
		
		
		
		priority.add("occ_Motif");
		priority.add("occ_Pattern");
		priority.add("occ_Match");
		priority.add("occ_Desc");
		priority.add("occ_Cons");
		priority.add("result_IC");
		priority.add("occ_HomNum");
		priority.add("result_Masking");
		priority.add("result_UPNum");
		priority.add("result_N_Occ");
		priority.add("result_E_Occ");
		priority.add("result_p_Occ");
		priority.add("result_pUnd_Occ");
		priority.add("result_N_Seq");
		priority.add("result_E_Seq");
		priority.add("result_p_Seq");
		priority.add("result_pUnd_Seq");
		priority.add("result_CloudUP");
		priority.add("result_Cons_mean");

		CSVReader csvReader = null;
		try {
			csvReader = new CSVReader(new FileReader(this.slims), ',');
			allSLiMs = csvReader.readAll();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			csvReader = new CSVReader(new FileReader(this.summary), ',');
			allSummary = csvReader.readAll();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<HashMap<String, Object>> getAllDataHashMap()
	{
		ArrayList<HashMap<String, Object>> mappings = new ArrayList<HashMap<String, Object>>();
		String[] occTitles = this.allSLiMs.get(0);
		String[] resultTitles = this.allSummary.get(0);
		for (String [] occ: this.allSLiMs.subList(1, allSLiMs.size()))
		{
			for (String [] result: this.allSummary.subList(1, allSummary.size()))
			{
				String occRank = occ[this.result_Dataset];
				String summaryDataset = result[this.summary_Dataset];
				
				String occMotif = occ[this.result_Motif];
				String summaryMotif = result[this.summary_Motif];
			    if (occRank.equals(summaryDataset) && occMotif.equals(summaryMotif))
			    {
			    	HashMap<String, Object> map = new HashMap<String, Object>();
			    	for (int i = 0; i < occ.length; i++)
			    	{
			    		if (Arrays.asList(includedOccColumns).contains(
								occTitles[i])) {

							if (occTypes.get(occTitles[i]).equals(Double.class)) {
								map.put("occ_" + occTitles[i],
										Double.parseDouble(occ[i]));
							} else if (occTypes.get(occTitles[i]).equals(
									Integer.class)) {
								map.put("occ_" + occTitles[i],
										Integer.parseInt(occ[i]));

							} else if (occTypes.get(occTitles[i]).equals(
									String.class)) {
								map.put("occ_" + occTitles[i], occ[i]);
							}
						}
			    	}
			    	for (int i = 0; i < result.length; i++)
			    	{
			    		if (Arrays.asList(includedResultColumns).contains(
								resultTitles[i])) {
							if (resultTypes.get(resultTitles[i]).equals(
									Double.class)) {
								map.put("result_" + resultTitles[i],
										Double.parseDouble(result[i]));
							} else if (resultTypes.get(resultTitles[i]).equals(
									Integer.class)) {
								map.put("result_" + resultTitles[i],
										Integer.parseInt(result[i]));

							} else if (resultTypes.get(resultTitles[i]).equals(
									String.class)) {
								map.put("result_" + resultTitles[i], result[i]);
							}
						}
			    	}
			    	
			    	LinkedHashMap<String, Object> sortedMap = new LinkedHashMap<String, Object>();
					for (String entry: priority)
					{
						Object move = map.get(entry);
						if (move!=null)
						{
							sortedMap.put(entry, move);
						}
						
					}
					mappings.add(sortedMap);
			    }
			}
		}
		return mappings;
	}
	public static void main(String [] args)
	{
		String a = "/home/kevin/Desktop/slim_domain_vis/slimsuite/runs/slimsearch/705dc1e1-405f-4618-83a6-647d21aee543/results.csv";
		String b = "/home/kevin/Desktop/slim_domain_vis/slimsuite/runs/slimsearch/705dc1e1-405f-4618-83a6-647d21aee543/results.summary.csv";
		SLiMSearchImportHelper sLiMSearchImportHelper = new SLiMSearchImportHelper(a, b);
		System.out.println(sLiMSearchImportHelper.getAllDataHashMap().size());
	}
}