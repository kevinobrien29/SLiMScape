package vis.workers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import vis.data.Constants;
import vis.data.attribute.AggregateSLim;
import vis.data.attribute.Attribute;
import vis.data.attribute.CustomAttribute;
import vis.tools.BinarySearchParser;
import vis.ui.TableDisplay;
import cytoscape.CyNetwork;
import cytoscape.CyNode;
import cytoscape.Cytoscape;
import cytoscape.data.CyAttributes;

public class ImportCustomAttributes extends Thread {
	String[] titles = null;
	List<String[]> slims = null;
	TableDisplay customAttributePanel = null;
	boolean isSuccess = true;
	

	public ImportCustomAttributes(String[] titles,
			List<String[]> slims,
			TableDisplay customAttributePanel) {
		this.titles = titles;
		this.slims = slims;
		this.customAttributePanel = customAttributePanel;
	}

	public void loadSLiMs() {
		
		ArrayList<Attribute> customAttributes = new ArrayList<Attribute>();
		for (String[] row : this.slims) {
				HashSet<String> proteins = new HashSet<String>();
				
				
				proteins.add(row[0]);
				HashMap<String, String> attributes = new HashMap<String, String>();
				for (int i = 0; i < row.length; i++)
				{
					attributes.put(titles[i + 1], row[i]);
				}
				CustomAttribute customAttribute = new CustomAttribute(titles, attributes, proteins);
				customAttributes.add(customAttribute);
				
				
		}
		
		customAttributePanel.replaceModelData(titles, customAttributes);
	}
	
	public boolean isSuccess()
	{
		return this.isSuccess;
	}

	@Override
	public void run() {
		try
		{
			loadSLiMs();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			isSuccess = false;
		}
		
	}
}