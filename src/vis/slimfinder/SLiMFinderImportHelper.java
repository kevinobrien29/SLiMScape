package vis.slimfinder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import vis.opencsv.CSVReader;

public class SLiMFinderImportHelper {

	String[] includedOccColumns = { "Rank", "Pattern", "Sig", "Cons", "Desc",
			"Match"};
	String[] includedResultColumns = { "Masking", "UPNum", "MotNum", "Rank",
			"Sig", "IC", "Occ", "Prob", "Cloud", "CloudUP",
			"Cons_mean" };

	HashMap<String, Class> occTypes = new HashMap<String, Class>();
	HashMap<String, Class> resultTypes = new HashMap<String, Class>();
	ArrayList<String> priority = new ArrayList<String>();

	String slims = null;
	String results = null;
	int slims_Rank = 1;
	int result_Rank = 10;

	List<String[]> allSLiMs = null;
	List<String[]> allResults = null;

	public SLiMFinderImportHelper(String slims, String results) {
		super();
		this.slims = slims;
		this.results = results;

		occTypes.put("Rank", Integer.class);
		occTypes.put("Pattern", String.class);
		occTypes.put("Sig", Double.class);
		occTypes.put("Cons", Double.class);
		occTypes.put("Desc", String.class);
		occTypes.put("Match", String.class);

		resultTypes.put("Masking", String.class);
		resultTypes.put("UPNum", Integer.class);
		resultTypes.put("MotNum", Integer.class);
		resultTypes.put("Rank", Integer.class);
		resultTypes.put("Sig", Double.class);
		resultTypes.put("IC", Double.class);
		resultTypes.put("Occ", Integer.class);
		resultTypes.put("Prob", Double.class);
		resultTypes.put("Cloud", String.class);
		resultTypes.put("CloudUP", String.class);
		resultTypes.put("Cons_mean", Double.class);
		
		
		
		priority.add("occ_Pattern");
		priority.add("occ_Sig");
		priority.add("occ_Cons");
		priority.add("occ_Desc");
		priority.add("result_IC");
		priority.add("result_Sig");
		priority.add("result_MotNum");
		priority.add("result_Masking");
		priority.add("result_UPNum");
		priority.add("occ_Rank");
		priority.add("result_Rank");
		priority.add("occ_Match");
		priority.add("result_Occ");
		priority.add("result_Prob");
		priority.add("result_Cloud");
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
			csvReader = new CSVReader(new FileReader(this.results), ',');
			allResults = csvReader.readAll();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<LinkedHashMap<String, Object>> getAllDataHashMap() {
		ArrayList<LinkedHashMap<String, Object>> mappings = new ArrayList<LinkedHashMap<String, Object>>();
		String[] occTitles = this.allSLiMs.get(0);
		String[] resultTitles = this.allResults.get(0);
		for (String[] occ : this.allSLiMs.subList(1, allSLiMs.size())) {

			for (String[] result : this.allResults
					.subList(1, allResults.size())) {
				String occRank = occ[this.slims_Rank];
				String resultRank = result[this.result_Rank];
				if (occRank.equals(resultRank)) {

					LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
					for (int i = 0; i < occ.length; i++) {
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
					for (int i = 0; i < result.length; i++) {
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
}
