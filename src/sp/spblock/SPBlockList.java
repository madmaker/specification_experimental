package sp.spblock;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import reports.BlockList;
import reports.EnumBlockType;
import sp.spline.SPLine;

public class SPBlockList implements BlockList<SPBlock>
{
	protected List<SPBlock> list;
	private SPBlock errorBlock;
	
	public SPBlockList()
	{
		list = new LinkedList<SPBlock>();
		errorBlock = new SPBlock(EnumBlockType.NONE);
	}

	public SPBlock getBlock(EnumBlockType contentType)
	{
		for (SPBlock block : list)
		{
			if (block.attributes.contentType == contentType)
				return block;
		}
		return errorBlock;
	}
	
	public SPBlock getBlock(EnumBlockType contentType, int type)
	{
		for (SPBlock block : list)
		{
			if (block.attributes.contentType == contentType && block.attributes.type == type)
				return block;
		}
		return errorBlock;
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
		if(list.size()>i && i>=0)
			return list.get(i);
		else
			return errorBlock;
	}

	@Override
	public SPBlock getLast()
	{
		if(list.size()>0)
			return list.get(list.size()-1);
		else
			return errorBlock;
	}

	@Override
	public void remove(SPBlock block)
	{
		// TODO Auto-generated method stub
		
	}
}
