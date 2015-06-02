package vis.data.attribute;

import java.util.HashSet;

public class AggregateSLim extends Attribute {
	String Name;
	String Pattern;

	public AggregateSLim(String name, HashSet<String> proteins) {
		super();
		this.proteins = proteins;
		
		this.attributesMap.put("status", false);
		this.attributesMap.put("name", name);
		
		this.indicesToName.put(0,"status");
		this.indicesToName.put(1,"name");
		
		
		this.switchIndex = 0;
		
		//this.addAttributes(extras);
	}

	@Override
	public void activate(Double eValueCutoff) {
		AttributeChanger AttributeChanger = new AttributeChanger();
		for (String activatedNodeID : this.getProteins()) {
				AttributeChanger.activateAttribute(activatedNodeID, (String)this.attributesMap.get("name"), "slim");
			}
		AttributeChanger.refresh();
	}

	@Override
	public void deActivate(Double eValueCutoff) {
		AttributeChanger AttributeChanger = new AttributeChanger();
		for (String nodeId : this.getProteins()) {

			AttributeChanger.deActivateAttribute(nodeId, (String)this.attributesMap.get("name"), "slim");
		}
		AttributeChanger.refresh();
	}

	@Override
	public String[] getTitles() {
		// TODO Auto-generated method stub
		return null;
	}
}
