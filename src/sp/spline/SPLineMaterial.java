package sp.spline;

import com.teamcenter.rac.kernel.TCComponentBOMLine;

public class SPLineMaterial extends SPLine<MaterialSpecLineAttributes>
{
	public SPLineMaterial()
	{
		this.attributes = new MaterialSpecLineAttributes();
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
	
	@Override
	public void setUid(String uid)
	{
		this.uid = uid;
	};
}
