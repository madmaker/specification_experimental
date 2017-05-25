package sp.spline.attributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class SLFormat implements SPLineAttribute
{
	private ArrayList<String> formatsList = new ArrayList<String>(1);

	@Override
	public String getStringValue()
	{
		Collections.sort(formatsList, new FormatComparator());
		Iterator<String> it = formatsList.iterator();
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
			if (!containsFormat(f.trim()))
				formatsList.add(f.trim());
		}
	}

	@Override
	public void updateValueWith(String value)
	{
		for (String f : value.split(","))
		{
			if (!containsFormat(f.trim()))
				formatsList.add(f.trim());
		}
	}

	@Override
	public void updateValueWith(SPLineAttribute value)
	{
		for (String f : value.getStringValue().split("\n"))
		{
			if (!containsFormat(f.trim()))
				formatsList.add(f.trim());
		}
	}

	@Override
	public void clearValue()
	{
		formatsList.clear();
	}

	static class FormatComparator implements Comparator<String>
	{
		@Override
		public int compare(String s0, String s1)
		{
			int arg0len, arg1len;

			arg0len = s0.length();
			arg1len = s1.length();

			if (Integer.valueOf(s0.charAt(1)) == Integer.valueOf(s1.charAt(1))) {
				if (arg0len > arg1len)
					return 1;
				if (arg0len < arg1len)
					return -1;
				if (Integer.valueOf(s0.charAt(3)) > Integer.valueOf(s1.charAt(3)))
					return 1;
				if (Integer.valueOf(s0.charAt(3)) < Integer.valueOf(s1.charAt(3)))
					return -1;
			} else {
				return (Integer.valueOf(s0.charAt(1)) > Integer.valueOf(s1.charAt(1))) ? -1 : 1;
			}
			return 0;
		}
	}

	public boolean containsFormat(String format)
	{
		boolean result = formatsList.contains(format);
		return result;
	}
}
