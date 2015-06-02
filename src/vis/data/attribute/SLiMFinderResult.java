package vis.data.attribute;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;

import vis.data.Constants;

public class SLiMFinderResult extends Attribute {

	ArrayList<String> titles = new ArrayList<String>();
	public SLiMFinderResult(LinkedHashMap<String, Object> allMappings, boolean conservation) {
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
		//System.out.println("size " + this.getSize());
	}
	
	public String [] getTitles()
	{
		String [] tmp = new String [this.titles.size()];
		return this.titles.toArray(tmp);
	}
	
	@Override
	public void activate(Double eValueCutoff) {
		AttributeChanger AttributeChanger = new AttributeChanger();
		for (String activatedNodeID : this.getProteins()) {
				AttributeChanger.activateAttribute(activatedNodeID, (String)this.attributesMap.get("Pattern"), Constants.getSlims());
			}
		AttributeChanger.refresh();
	}
	
	@Override
	public void deActivate(Double eValueCutoff) {
		AttributeChanger AttributeChanger = new AttributeChanger();
		for (String nodeId : this.getProteins()) {

			AttributeChanger.deActivateAttribute(nodeId, (String)this.attributesMap.get("Pattern"), Constants.getSlims());
		}
		AttributeChanger.refresh();
	}
	

	public String getRegExp()
	{
		return (String)this.attributesMap.get("Pattern");
	}

	public Double getSig()
	{
		return (Double) this.attributesMap.get("occ_Sig");
	}
	
	@Override
	public String getName()
	{
		return (String)this.attributesMap.get("Pattern");
	}
}
