package sp.spblock;

import java.util.LinkedList;
import java.util.ListIterator;

import reports.Block;
import reports.BlockList;

public class SPBlockList implements BlockList
{
	protected LinkedList<SPBlock> list;
	
	public SPBlockList()
	{
		list = new LinkedList<SPBlock>();
	}

	public SPBlock getBlock(int contentType)
	{
		for (SPBlock block : list)
		{
			if ((block).attributes.contentType == contentType)
				return block;
		}
		return null;
	}
	
	public SPBlock getBlock(int contentType, int type)
	{
		for (SPBlock block : list)
		{
			if (block.attributes.contentType == contentType && block.attributes.type == type)
				return block;
		}
		return null;
	}
	
	public void addBlock(SPBlock block)
	{
		list.addLast(block);
	}
	
	public void remove(int i)
	{
		list.remove(i);
	}
	
	public int size()
	{
		return list.size();
	}
	
	public ListIterator<SPBlock> iterator()
	{
		return list.listIterator();
	}
}
