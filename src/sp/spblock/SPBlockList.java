package sp.spblock;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import reports.Block;
import reports.BlockList;

public class SPBlockList implements BlockList<SPBlock>
{
	protected List<SPBlock> list;
	
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

	@Override
	public void add(SPBlock block)
	{
		list.add(block);
	}

	@Override
	public SPBlock get(int i)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SPBlock getLast()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(SPBlock block)
	{
		// TODO Auto-generated method stub
		
	}
}
