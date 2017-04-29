package sp.spblock;

import reports.BlockAttributes;
import reports.BlockContentType;
import reports.BlockType;

public class SPBlockAttributes implements BlockAttributes
{
	public int				reserveLinesCount;
	public int				reservePosCount;
	public int				intervalPosCount;
	public int				type;
	public int				contentType;

	public SPBlockAttributes()
	{
		this.reserveLinesCount = 0;
		this.reservePosCount = 0;
		this.intervalPosCount = 0;
		this.type = BlockType.DEFAULT;
		this.contentType = BlockContentType.NONE;
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
		if (contentType == BlockContentType.NONE)
			errors++;
		return (errors == 0);
	}
}
