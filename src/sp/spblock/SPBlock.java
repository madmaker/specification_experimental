package sp.spblock;

import java.util.ArrayList;

import reports.Block;
import sp.spline.SPLine;

public class SPBlock extends Block
{
	public String				title;
	public SPBlockAttributes	attributes;
	private ArrayList<SPLine>	lines;

	public SPBlock(String title, SPBlockAttributes attributes)
	{
		this.title = title;
		this.attributes = attributes;
		this.lines = new ArrayList<SPLine>();
	}

	public final ArrayList<SPLine> getLines()
	{
		return this.lines;
	};

	public boolean isValid()
	{
		return (size() > 0 && attributes.isValid());
	}

	public int size()
	{
		return lines.size();
	};

	public void addLine(SPLine line){};

	public void updateLine(SPLine target, SPLine source){};
}
