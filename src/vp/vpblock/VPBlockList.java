package vp.vpblock;

import java.util.LinkedList;
import java.util.ListIterator;

import reports.BlockList;
import reports.EnumBlockType;

public class VPBlockList implements BlockList<VPBlock>
{
	private LinkedList<VPBlock> list;

	public VPBlockList()
	{
		this.list = new LinkedList<VPBlock>();
	}

	public void addBlock(VPBlock block)
	{
		list.addLast(block);
	}

	public VPBlock getBlock(EnumBlockType contentType)
	{
		for (VPBlock block : list)
		{
			if (block.attributes.contentType == contentType)
				return block;
		}
		return null;
	}

	public void remove(int i)
	{
		list.remove(i);
	}
	
	public VPBlock get(int i)
	{
		return list.get(i);
	}
	
	public int size()
	{
		return list.size();
	}
	
	public ListIterator<VPBlock> iterator()
	{
		return list.listIterator();
	}

	@Override
	public void add(VPBlock block)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public VPBlock getLast()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(VPBlock block)
	{
		// TODO Auto-generated method stub
		
	}
}
