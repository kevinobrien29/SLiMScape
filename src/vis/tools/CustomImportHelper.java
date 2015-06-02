package vis.tools;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import vis.opencsv.CSVReader;


public class CustomImportHelper {

	private String interactionDataPath = null;
	private String slimDataPath = null;
	private String attributeDataPath = null;
	private HashMap<String, Integer> titleIndexMappings = null;
	private List<String[]> allInteractions = null;
	private List<String[]> allSLiMs = null;
	private List<String[]> allAttributes = null;

	public CustomImportHelper(String interactionDataPath, String slimDataPath,
			String attributeDataPath) {
		super();
		this.interactionDataPath = interactionDataPath;
		this.slimDataPath = slimDataPath;
		this.attributeDataPath = attributeDataPath;
		titleIndexMappings = new HashMap<String, Integer>();
		
	}
	
	public CustomImportHelper(String slimDataPath) {
		super();
		this.slimDataPath = slimDataPath;
		titleIndexMappings = new HashMap<String, Integer>();
		
	}
	
	public boolean loadAll()
	{
		try {
			CSVReader csvReader = new CSVReader(new FileReader(this.interactionDataPath),
					',');
			allInteractions = csvReader.readAll();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		try {
			CSVReader csvReader = new CSVReader(new FileReader(this.slimDataPath), ',');
			allSLiMs = csvReader.readAll();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		try {
			CSVReader csvReader = new CSVReader(new FileReader(this.attributeDataPath),
					',');
			allAttributes = csvReader.readAll();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean loadSLiMs()
	{
		try {
			CSVReader csvReader = new CSVReader(new FileReader(this.slimDataPath), ',');
			allSLiMs = csvReader.readAll();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public ArrayList<String[]> getInteractions() {

		ArrayList<String[]> allTrimmed = new ArrayList<String[]>();

		for (int i = 1; i < this.allInteractions.size(); i++) {
			allTrimmed.add(new String[] { this.allInteractions.get(i)[0],
					this.allInteractions.get(i)[1] });
		}
		return allTrimmed;
	}
	
	public String[] getSLiMTitles() {
		
		String[] titles = new String[this.allSLiMs.get(0).length + 1];
		titles[0] = "";
		titles[1] = "name";
		for (int i = 1; i < this.allSLiMs.get(0).length;i++)
		{
			titles[i + 1] = this.allSLiMs.get(0)[i];
		}
		return titles;
	}

	public List<String[]> getSLiMs() {
		return this.allSLiMs.subList(1, this.allSLiMs.size());
	}

	public List<String[]> getAttributes() {
		return this.allAttributes.subList(1, this.allAttributes.size());
	}
	
	public String[] getAttributesTitles() {
		return this.allAttributes.get(0);
	}
}
