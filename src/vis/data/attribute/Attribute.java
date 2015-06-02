package vis.data.attribute;

import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public abstract class Attribute implements Comparable<Attribute> {

	UUID uniqueKey = null;
	HashMap<String, Object> attributesMap = new HashMap<String, Object>();
	HashMap<Integer, String> indicesToName = new HashMap<Integer, String>();
	static int switchIndex = 0;
	Color backgroundColor = null;

	HashSet<String> proteins = new HashSet<String>();

	public Attribute() {
		this.uniqueKey = UUID.randomUUID();
	}

	public String[] getTitles()
	{
		return null;
	}

	public Class getClass(int i) {
		return this.getValueAt(i).getClass();
	}

	public abstract void activate(Double eValueCutoff);
	
	public abstract void deActivate(Double eValueCutoff);

	public void addAttributes(String[] orderedTitles,
			HashMap<String, String> extras) {
		int count = 2;
		for (String name : orderedTitles) {
			this.attributesMap.put(name, extras.get(name));
			this.indicesToName.put(count, name);
			count++;
		}
	}

	public void addAttributes2(String[] orderedTitles,
			HashMap<String, String> extras) {
		for (String name : orderedTitles) {
			this.attributesMap.put(name, extras.get(name));
			this.indicesToName.put(indicesToName.size(), name);
		}
	}

	@Override
	public int compareTo(Attribute attribute) {
		return this.getName().compareTo(attribute.getName());
	}

	

	public Object getAttribute(String attributeName) {
		return attributesMap.get(attributeName);
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public String getId() {
		return uniqueKey.toString();
	}

	public String getName() {
		return (String) this.attributesMap.get("name");
	}

	public HashSet<String> getProteins() {
		return proteins;
	}

	public int getSize() {
		return attributesMap.size();
	}

	public static int getSwitchIndex() {
		return switchIndex;
	}

	public Object getValueAt(int col) {
		return this.attributesMap.get(this.indicesToName.get(col));
	}
	
	public synchronized void setActive(boolean activity) {
		this.attributesMap.put(this.indicesToName.get(this.switchIndex),
				activity);
	}

	public synchronized boolean isActive() {
		return (Boolean) this.attributesMap.get(this.indicesToName.get(this.switchIndex));
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public void setProteins(HashSet<String> proteins) {
		this.proteins = proteins;
	}

	public void setSwitchIndex(int switchIndex) {
		this.switchIndex = switchIndex;
	}

	public void setValueAt(int col, Object obj) {
		this.attributesMap.put(this.indicesToName.get(col), obj);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return (String) this.attributesMap.get("name");
	}

}
