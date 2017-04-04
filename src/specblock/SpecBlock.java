package specblock;

import java.util.ArrayList;

import specline.SpecLine;

public abstract class SpecBlock
{
	public String				title;
	public SpecBlockAttributes	attributes;
	private ArrayList<SpecLine>	lines;

	public SpecBlock(String title, SpecBlockAttributes attributes)
	{
		this.title = title;
		this.attributes = attributes;
		this.lines = new ArrayList<SpecLine>();
	}

	public final ArrayList<SpecLine> getLines()
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

	public abstract void addLine(SpecLine line);

	public abstract void updateLine(SpecLine target, SpecLine source);
}
