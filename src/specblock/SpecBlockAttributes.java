package specblock;

import specblock.BlockList.BlockContentType;
import specblock.BlockList.BlockType;

public class SpecBlockAttributes
{
	public int				reserveLinesCount;
	public int				reservePosCount;
	public int				intervalPosCount;
	public BlockType		type;
	public BlockContentType	contentType;

	public SpecBlockAttributes()
	{
		this.reserveLinesCount = 0;
		this.reservePosCount = 0;
		this.intervalPosCount = 0;
		this.type = BlockType.DEFAULT;
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
		if (type == BlockType.DEFAULT)
			errors++;
		return (errors == 0);
	}
}
