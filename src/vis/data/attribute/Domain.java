package vis.data.attribute;

import java.util.HashSet;

import vis.data.Constants;

public class Domain extends Attribute {

	public Domain(String name, String source, String start, String end, String evalue,
			String nodeId) {
		
		this.attributesMap.put("name", name);
		this.attributesMap.put("source", source);
		this.attributesMap.put("status", false);
		this.attributesMap.put("nodeid", nodeId);
		
		Double eValueDouble = Double.parseDouble(evalue);
		this.attributesMap.put("evalue", eValueDouble);
		
		
		this.indicesToName.put(0,"status");
		this.indicesToName.put(1,"name");
		this.indicesToName.put(2,"source");
		this.indicesToName.put(3,"nodeid");
		this.indicesToName.put(4,"evalue");
		
		
		this.switchIndex = 0;
		
	}

	@Override
	public void activate(Double eValueCutoff) {
		AttributeChanger AttributeChanger = new AttributeChanger();
		AttributeChanger.activateAttribute((String) attributesMap.get("nodeid"), this.getName(), Constants.getDomains());
		AttributeChanger.refresh();
	}

	@Override
	public void deActivate(Double eValueCutoff) {
		AttributeChanger AttributeChanger = new AttributeChanger();

		for (String nodeId : this.getProteins()) {

			AttributeChanger.deActivateAttribute(nodeId, this.getName(), Constants.getDomains());
		}
		AttributeChanger.refresh();
	}

	public Double getEvalue()
	{
		return Double.parseDouble("" + attributesMap.get("evalue"));
	}
	
	@Override
	public String getName() {
		return (String) attributesMap.get("name");
	}

	@Override
	public HashSet<String> getProteins() {
		// TODO Auto-generated method stub

		HashSet<String> proteins = new HashSet<String>();
		proteins.add((String) attributesMap.get("nodeid"));
		return proteins;
	}
	
	public String getSource()
	{
		return (String) attributesMap.get("source");
	}

	@Override
	public void setProteins(HashSet<String> proteins) {
		// TODO Auto-generated method stub
		this.attributesMap.put("nodeid", proteins.toArray()[0]);
	}

	@Override
	public String[] getTitles() {
		// TODO Auto-generated method stub
		return null;
	}
}