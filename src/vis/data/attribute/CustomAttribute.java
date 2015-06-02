package vis.data.attribute;

import java.util.HashMap;
import java.util.HashSet;

import vis.data.Constants;


public class CustomAttribute extends Attribute {

	public CustomAttribute(String [] titles, HashMap<String, String> allMappings, HashSet<String> proteins) {
		super();
		this.proteins = proteins;
		
		this.attributesMap.put("status", false);
		this.attributesMap.put("name", allMappings.get(titles[1]));
		
		this.indicesToName.put(0,"status");
		this.indicesToName.put(1,"name");
		
		
		this.switchIndex = 0;
		
		for (int i = 2; i < titles.length; i++)
		{
			this.attributesMap.put(titles[i], allMappings.get(titles[i]));
			this.indicesToName.put(indicesToName.size(),titles[i]);
		}
	}
	

	@Override
	public void activate(Double eValueCutoff) {
		AttributeChanger AttributeChanger = new AttributeChanger();
		for (String activatedNodeID : this.getProteins()) {
				AttributeChanger.activateAttribute(activatedNodeID, (String)this.attributesMap.get("name"), Constants.getSlims());
			}
		AttributeChanger.refresh();
	}

	@Override
	public void deActivate(Double eValueCutoff) {
		AttributeChanger AttributeChanger = new AttributeChanger();
		for (String nodeId : this.getProteins()) {

			AttributeChanger.deActivateAttribute(nodeId, (String)this.attributesMap.get("name"), Constants.getSlims());
		}
		AttributeChanger.refresh();
	}
}
