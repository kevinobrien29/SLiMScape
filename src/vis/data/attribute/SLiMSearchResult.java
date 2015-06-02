package vis.data.attribute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import vis.data.Constants;

public class SLiMSearchResult extends Attribute {

	ArrayList<String> titles = new ArrayList<String>();
	public SLiMSearchResult(HashMap<String, Object> allMappings) {
		super();

		this.proteins = new HashSet<String>();
		this.attributesMap.put("Protein", (String)allMappings.get("occ_Desc"));
		proteins.add((String)allMappings.get("occ_Desc"));
		allMappings.remove("occ_Desc");
		
		this.attributesMap.put("status", false);
		this.attributesMap.put("Pattern", allMappings.get("occ_Pattern"));
		allMappings.remove("occ_Pattern");
		
		
		this.indicesToName.put(0,"status");
		this.indicesToName.put(1,"Pattern");
		this.indicesToName.put(2,"Protein");
		
		titles.add("Protein");
		
		
		for (String title: allMappings.keySet())
		{
			this.attributesMap.put(title, allMappings.get(title));
			this.indicesToName.put(indicesToName.size(),title);
			titles.add(title);
		}

		this.switchIndex = 0;
	}
	
	@Override
	public void activate(Double eValueCutoff) {
		AttributeChanger AttributeChanger = new AttributeChanger();
		for (String activatedNodeID : this.getProteins()) {
			AttributeChanger.activateAttribute(activatedNodeID,
					(String) this.attributesMap.get("Pattern"),
					Constants.getSlims());
		}
		AttributeChanger.refresh();
	}

	@Override
	public void deActivate(Double eValueCutoff) {
		AttributeChanger AttributeChanger = new AttributeChanger();
		for (String nodeId : this.getProteins()) {

			AttributeChanger.deActivateAttribute(nodeId,
					(String) this.attributesMap.get("Pattern"),
					Constants.getSlims());
		}
		AttributeChanger.refresh();
	}

	public Double getPOcc()
	{
		return (Double) this.attributesMap.get("result_p_Occ");
	}

	@Override
	public String[] getTitles() {
		String [] tmp = new String [this.titles.size()];
		return this.titles.toArray(tmp);
	}
}
