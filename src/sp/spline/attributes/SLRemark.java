package sp.spline.attributes;

import java.util.ArrayList;
import java.util.Iterator;

public class SLRemark implements SPLineAttribute
{
	private ArrayList<String> remarksList = new ArrayList<String>(2);
	
	@Override
	public String getStringValue()
	{
		Iterator<String> it = remarksList.iterator();
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
			remarksList.add(f.trim());
		}
	}

	@Override
	public void updateValueWith(String value)
	{
		for (String f : value.split(","))
		{
			remarksList.add(f.trim());
		}
	}

	@Override
	public void updateValueWith(SPLineAttribute value)
	{
		for (String f : value.getStringValue().split("\n"))
		{
			remarksList.add(f.trim());
		}
	}

	@Override
	public void clearValue()
	{
		remarksList.clear();
	}

}
