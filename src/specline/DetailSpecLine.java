package specline;

public abstract class DetailSpecLine extends SpecLine
{
	public DetailSpecLine()
	{
		this.attributes = new DetailSpecLineAttributes();
	}

	public abstract void init()
	{

	}

	public abstract void build()
	{

	}

	public abstract String getUid()
	{
    
	}

	private SpecLine newLine(TCComponentBOMLine bomLine)
	{
	};
}
