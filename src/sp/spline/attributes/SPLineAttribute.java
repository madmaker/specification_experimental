package sp.spline.attributes;

public interface SPLineAttribute
{
	public String getStringValue();
	public void setValue(String value);
	public void updateValueWith(String value);
	public void updateValueWith(SPLineAttribute value);
	public void clearValue();
}
