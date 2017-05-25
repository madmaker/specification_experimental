package sp.spline.attributes;

public class SLId implements SPLineAttribute
{
	private String id = "";
	
	public SLId(String id)
	{
		this.id = id;
	}

	@Override
	public String getStringValue()
	{
		return id;
	}

	@Override
	public void setValue(String value)
	{
		this.id = value;
	}

	@Override
	public void updateValueWith(String value)
	{
		this.id = value;
	}

	@Override
	public void updateValueWith(SPLineAttribute value)
	{
		this.id = value.getStringValue();
	}

	@Override
	public void clearValue()
	{
		id = "";
	}
}
