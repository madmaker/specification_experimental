package sp.spblock;

import java.util.ArrayList;

import reports.Block;
import reports.EnumBlockType;
import sp.spline.SPLine;

public class SPBlock extends Block
{
	public SPBlockAttributes	attributes;
	private ArrayList<SPLine>	lines;
	
	public SPBlock(EnumBlockType type)
	{
		this.attributes = new SPBlockAttributes();
		this.attributes.contentType = type;
		this.lines = new ArrayList<SPLine>();
	}

	public SPBlock(SPBlockAttributes attributes)
	{
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
	
	public String getTitle()
	{
		return attributes.contentType.title();
	}

	public void addLine(SPLine line){};

	public void updateLine(SPLine target, SPLine source){};
}
