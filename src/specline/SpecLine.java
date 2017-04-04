package specline;

import com.teamcenter.rac.kernel.TCComponentBOMLine;

import specblock.BlockList.BlockContentType;
import specblock.BlockList.BlockType;

public abstract class SpecLine
{
	public SpecLineAttributes	attributes;
	public BlockContentType		blockContentType;
	public BlockType			blockType;
	public boolean				isSubstitute;
	private String				uid;

	protected SpecLine()
	{
	};

	public abstract SpecLine newLine(TCComponentBOMLine bomLine);

	public abstract void init();

	public abstract void build();

	public abstract String getUid();
}
