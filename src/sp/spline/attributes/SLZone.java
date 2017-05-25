package sp.spline.attributes;

import java.util.ArrayList;
import java.util.Iterator;

public class SLZone implements SPLineAttribute
{
	private ArrayList<String> zonesList = new ArrayList<String>(1);
	
	@Override
	public String getStringValue()
	{
		Iterator<String> it = zonesList.iterator();
		StringBuilder sb = new StringBuilder();
		while (it.hasNext()) {
			sb.append(it.next() + (it.hasNext() ? "\n" : ""));
		}
		return sb.toString().trim();
	}

	@Override
	public void setValue(String value)
	{
		clearValue();
		for (String f : value.split(","))
		{
			if (!containsZone(f.trim()))
				zonesList.add(f.trim());
		}
	}

	@Override
	public void updateValueWith(String value)
	{
		for (String f : value.split(","))
		{
			if (!containsZone(f.trim()))
				zonesList.add(f.trim());
		}
	}

	@Override
	public void updateValueWith(SPLineAttribute value)
	{
		for (String f : value.getStringValue().split("\n"))
		{
			if (!containsZone(f.trim()))
				zonesList.add(f.trim());
		}
	}

	@Override
	public void clearValue()
	{
		zonesList.clear();
	}
	
	public boolean containsZone(String zone)
	{
		boolean result = zonesList.contains(zone);
		return result;
	}
}
