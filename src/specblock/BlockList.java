package specblock;

import java.util.LinkedList;

public class BlockList
{
	public static enum BlockContentType {
		NONE, DOCS, COMPLEXES, ASSEMBLIES, DETAILS, STANDARDS, OTHERS, MATERIALS, KITS
	};

	public static enum BlockType {
		DEFAULT
	};

	private LinkedList<SpecBlock> list;

	public BlockList()
	{
		this.list = new LinkedList<SpecBlock>();
	}

	public void addBlock(SpecBlock block)
	{
		list.addLast(block);
	}

	public SpecBlock getBlock(BlockContentType contentType)
	{
		for (SpecBlock block : list)
		{
			if (block.attributes.contentType == contentType)
				return block;
		}
		return null;
	}

	public void removeBlock(int i)
	{
		list.remove(i);
	}
}
