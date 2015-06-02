package vis.data.attribute;

import java.util.ArrayList;
import java.util.HashSet;

import vis.data.Constants;

public class AggregateDomain extends Attribute {
	String name;
	ArrayList<Double> evalues = new ArrayList<Double>();
	ArrayList<Attribute> domains = null;
	boolean isActive;
	HashSet<String> activeProteins = new HashSet<String>();

	public AggregateDomain(String name, String source, ArrayList<Attribute> domains, HashSet<String> proteins) {
		super();
		this.domains = domains;
		this.proteins = proteins;
		
		Double minEvalue = null;
		Double maxEvalue = null;
		for (Attribute domain: domains)
		{
			Domain tmp = ((Domain)domain);
			Double currentValue = tmp.getEvalue();
			if (minEvalue == null || maxEvalue == null)
			{
				minEvalue = currentValue;
				maxEvalue = currentValue;
			}
			else if (minEvalue > currentValue)
			{
				minEvalue = currentValue;
			}
			else if  (maxEvalue < currentValue)
			{
				maxEvalue = currentValue;
			}
		}
		
		this.attributesMap.put("status", false);
		this.attributesMap.put("name", name);
		this.attributesMap.put("source", source);
		this.attributesMap.put("domainCount", domains.size());
		this.attributesMap.put("proteinCount", proteins.size());
		this.attributesMap.put("evalue", minEvalue);
		this.attributesMap.put("max-evalue", maxEvalue);
		
		this.indicesToName.put(0,"status");
		this.indicesToName.put(1,"name");
		this.indicesToName.put(2,"source");
		this.indicesToName.put(3,"domainCount");
		this.indicesToName.put(4,"proteinCount");
		this.indicesToName.put(5,"evalue");
		this.indicesToName.put(6,"max-evalue");
		
		this.switchIndex = 0;
		
	}
	
	@Override
	public void activate(Double eValueCutoff) {
		AttributeChanger AttributeChanger = new AttributeChanger();
		
		for (Attribute attribute: domains)
		{
			Domain domain = (Domain) attribute;
			if (eValueCutoff >= domain.getEvalue())
			{
				activeProteins.addAll(domain.getProteins());
			}
			
		}
		for (String nodeId : activeProteins) {

			AttributeChanger.activateAttribute(nodeId, this.getName(), Constants.getDomains());
		}
		AttributeChanger.refresh();
	}

	@Override
	public void deActivate(Double eValueCutoff) {
		AttributeChanger AttributeChanger = new AttributeChanger();
		
		for (String nodeId : this.activeProteins) {

			AttributeChanger.deActivateAttribute(nodeId, this.getName(), Constants.getDomains());
		}
		
		AttributeChanger.refresh();
	}

	@Override
	public String[] getTitles() {
		// TODO Auto-generated method stub
		return null;
	}
}