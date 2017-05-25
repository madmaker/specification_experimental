package sp.spline.attributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import sp.SP;
import sp.SPSettings;

public class _SLFormat
{
	public boolean				isMultirow	= false;
	private ArrayList<String>	formatsList	= new ArrayList<String>(1);

	static class DocFormatComparator implements Comparator<String>
	{
		@Override
		public int compare(String s0, String s1)
		{
			int arg0len, arg1len;

			arg0len = s0.length();
			arg1len = s1.length();

			if (Integer.valueOf(s0.charAt(1)) == Integer.valueOf(s1.charAt(1)))
			{
				if (arg0len > arg1len)
					return 1;
				if (arg0len < arg1len)
					return -1;
				if (Integer.valueOf(s0.charAt(3)) > Integer.valueOf(s1.charAt(3)))
					return 1;
				if (Integer.valueOf(s0.charAt(3)) < Integer.valueOf(s1.charAt(3)))
					return -1;
			} else
			{
				return (Integer.valueOf(s0.charAt(1)) > Integer.valueOf(s1.charAt(1))) ? -1 : 1;
			}
			return 0;
		}

	}

	public _SLFormat(String format)
	{
		if (!format.equals("*)"))
		{
			if (format.length() > SPSettings.columnLengths.get(SP.FormField.FORMAT) - 1)
				isMultirow = true;
			for (String f : format.split(","))
			{
				if (!containsFormat(f.trim()))
					formatsList.add(f.trim());
			}
		} else
		{
			formatsList.add(format);
		}
	}

	public ArrayList<String> toStringList()
	{
		return formatsList;
	}

	public boolean containsFormat(String format)
	{
		boolean result = formatsList.contains(format);
		return result;
	}

	private boolean isMultiline(String format)
	{
		return (format.trim().equals("*)"));
	}

	@Override
	public String toString()
	{
		Collections.sort(formatsList, new DocFormatComparator());
		Iterator<String> it = formatsList.iterator();
		StringBuilder sb = new StringBuilder();
		if (isMultirow && it.hasNext())
			sb.append("*) " + it.next() + (it.hasNext() ? ", " : ""));
		while (it.hasNext())
		{
			sb.append(it.next() + (it.hasNext() ? ", " : ""));
		}
		return sb.toString().trim();
	}
}
