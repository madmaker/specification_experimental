package sp.spblock;

import java.util.LinkedList;
import java.util.ListIterator;

import sp.spblock.SPBlock.BlockContentType;
import sp.spblock.SPBlock.BlockType;

public class SPBlockList
{
	private LinkedList<SPBlock> list;

	public SPBlockList()
	{
		this.list = new LinkedList<SPBlock>();
	}

	public void addBlock(SPBlock block)
	{
		list.addLast(block);
	}

	public SPBlock getBlock(BlockContentType contentType)
	{
		for (SPBlock block : list)
		{
			if (block.attributes.contentType == contentType)
				return block;
		}
		return null;
	}
	
	public SPBlock getBlock(BlockContentType contentType, BlockType type)
	{
		for (SPBlock block : list)
		{
			if (block.attributes.contentType == contentType && block.attributes.type == type)
				return block;
		}
		return null;
	}

	public void remove(int i)
	{
		list.remove(i);
	}
	
	public SPBlock get(int i)
	{
		return list.get(i);
	}
	
	public int size()
	{
		return list.size();
	}
	
	public SPBlock getLast()
	{
		return list.getLast();
	}
	
	public ListIterator<SPBlock> iterator()
	{
		return list.listIterator();
	}
}
