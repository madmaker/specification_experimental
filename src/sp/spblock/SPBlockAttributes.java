package sp.spblock;

import sp.spblock.SPBlock.BlockContentType;
import sp.spblock.SPBlock.BlockType;

public class SPBlockAttributes
{
	public int				reserveLinesCount;
	public int				reservePosCount;
	public int				intervalPosCount;
	public BlockType		type;
	public BlockContentType	contentType;

	public SPBlockAttributes()
	{
		this.reserveLinesCount = 0;
		this.reservePosCount = 0;
		this.intervalPosCount = 0;
		this.type = BlockType.DEFAULT;
		this.contentType = BlockContentType.NONE;
	}

	public boolean areValid()
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
