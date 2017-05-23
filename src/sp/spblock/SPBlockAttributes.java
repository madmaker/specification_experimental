package sp.spblock;

import reports.BlockAttributes;
import reports.BlockContentType;
import reports.BlockType;
import reports.EnumBlockType;

public class SPBlockAttributes implements BlockAttributes
{
	public int				reserveLinesCount;
	public int				reservePosCount;
	public int				intervalPosCount;
	public int				type;
	public EnumBlockType	contentType;

	public SPBlockAttributes()
	{
		this.reserveLinesCount = 0;
		this.reservePosCount = 0;
		this.intervalPosCount = 0;
		this.type = BlockType.DEFAULT;
		this.contentType = EnumBlockType.NONE;
	}

	public boolean isValid()
	{
		int errors = 0;
		if (reserveLinesCount < 0)
			errors++;
		if (reservePosCount < 0)
			errors++;
		if (intervalPosCount < 0)
			errors++;
		if (contentType == EnumBlockType.NONE)
			errors++;
		return (errors == 0);
	}
}
