package specline;

import com.teamcenter.rac.kernel.TCComponentBOMLine;

public abstract class DetailSpecLine extends SpecLine
{
	public DetailSpecLine()
	{
		this.attributes = new DetailSpecLineAttributes();
	}

	public void init()
	{

	}

	public void build()
	{

	}

	public String getUid()
	{
    
	}

	private SpecLine newLine(TCComponentBOMLine bomLine)
	{
	};
}
