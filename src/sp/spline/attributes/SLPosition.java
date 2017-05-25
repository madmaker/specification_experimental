package sp.spline.attributes;

public class SLPosition implements SPLineAttribute
{
	private String position = "";
	
	public SLPosition(String value)
	{
		setValue(value);
	}
	
	@Override
	public String getStringValue()
	{
		return position;
	}

	@Override
	public void setValue(String value)
	{
		this.position = value;
	}

	@Override
	public void updateValueWith(String value)
	{
		this.position = value;
	}

	@Override
	public void updateValueWith(SPLineAttribute value)
	{
		this.position = value.getStringValue();
	}

	@Override
	public void clearValue()
	{
		this.position = "";
	}
}
