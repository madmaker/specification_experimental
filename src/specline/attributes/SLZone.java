package specline.attributes;

import java.util.ArrayList;
import java.util.Iterator;

import spec.Specification;

public class SLZone
{
	public boolean exceedsLimit = false;
	private ArrayList<String> docZone = new ArrayList<String>(1);

	public SLZone(String zone)
	{
		if(zone.isEmpty()) return;
		if(!zone.equals("*)")){
			if(zone.length() > SpecificationSettings.columnLengths.get(Specification.FormField.ZONE)-1) exceedsLimit = true;
			for(String f : zone.split(",")){
				if(!containsZone(f.trim())) docZone.add(f.trim());
			}
		} else {
			docZone.add(zone);
		}
	}

	public void addZone(String zone)
	{
		if(zone.isEmpty()) return;
		if(zone.equals("*)")) {
			docZone.clear();
			docZone.add("*)");
			return;
		}
		if(zone.length() > SpecificationSettings.columnLengths.get(Specification.FormField.ZONE)-1) exceedsLimit = true;
		for(String f : zone.split(",")){
			if(!containsZone(f.trim())) docZone.add(f.trim());
		}
		if(docZone.size()>1) exceedsLimit = true;
	}
	public void addZone(SLZone blzone)
	{
		for(String zone:blzone.toStringList()){
			addZone(zone);
		}
	}

	public ArrayList<String> toStringList()
	{
		return docZone;
	}

	public boolean containsZone(String zone)
	{
		boolean result = docZone.contains(zone);
		return result;
	}

	@Override
	public String toString()
	{
		if(docZone.size()==1 && docZone.get(0).equals("*)")) return "*)";
		Iterator<String> it = docZone.iterator();
		StringBuilder sb = new StringBuilder();
		if(exceedsLimit && it.hasNext()) sb.append("*) " + it.next() + (it.hasNext()?", ":""));
		while(it.hasNext()){
			sb.append(it.next() + (it.hasNext()?", ":""));
		}
		return sb.toString().trim();
	}
}
