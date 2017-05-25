package sp.spline;

import com.teamcenter.rac.kernel.TCComponentBOMLine;

public class SPLineDetail extends SPLine
{
	public SPLineDetail()
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
		return this.uid;
	}

	public SPLine newLine(TCComponentBOMLine bomLine)
	{
		return null;
	}

	@Override
	public void updateWith(SPLine line)
	{
	};
}
