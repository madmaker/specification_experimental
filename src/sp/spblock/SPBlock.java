package sp.spblock;

import java.util.ArrayList;

import sp.spline.SPLine;

public abstract class SPBlock
{
	public String				title;
	public SPBlockAttributes	attributes;
	private ArrayList<SPLine>	lines;
	
	public static enum BlockContentType {
		NONE, DOCS, COMPLEXES, ASSEMBLIES, DETAILS, STANDARDS, OTHERS, MATERIALS, KITS
	};

	public static enum BlockType {
		DEFAULT
	};

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
		return (size() > 0 && attributes.areValid());
	}

	public int size()
	{
		return lines.size();
	};

	public abstract void addLine(SPLine line);

	public abstract void updateLine(SPLine target, SPLine source);
}
