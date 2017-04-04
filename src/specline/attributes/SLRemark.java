package specline.attributes;

import java.util.ArrayList;
import java.util.List;

import spec.Specification;
import util.LineUtil;

public class SLRemark
{
	private List<String> remark = new ArrayList<String>(2);

	public SLRemark()
	{
		remark = new ArrayList<String>();
	}

	public SLRemark(String remark)
	{
		this.remark.add(remark);
	}

	public String get(int i)
	{
		return remark.get(i);
	}

	public ArrayList<String> getAll()
	{
		return (ArrayList<String>) remark;
	}

	public void set(ArrayList<String> values)
	{
		remark = values;
	}

	public boolean containsRemarkLine(String remark)
	{
		boolean result = this.remark.contains(remark);
		return result;
	}

	public void insert(String value)
	{
		if (!this.containsRemarkLine(value))
			remark.add(value);
	}

	public void insert(ArrayList<String> values)
	{
		for (String value : values)
		{
			insert(value);
		}
	}

	public void insertAt(int i, String value)
	{
		remark.add(i, value);
	}

	public int size()
	{
		return remark.size();
	}

	public void build()
	{
		ArrayList<String> tempRemark = new ArrayList<String>();
		for (String string : this.remark)
		{
			tempRemark
					.addAll(LineUtil.getFittedLines(string, SpecificationSettings.columnLengths.get(Specification.FormField.REMARK)));
		}
		this.remark = tempRemark;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for (String string : remark)
		{
			sb.append(string + "---");
		}
		return sb.toString();
	}
}
