package sp.spline.attributes;

import java.math.BigDecimal;

public class SLQuantity implements SPLineAttribute
{
	public BigDecimal quantity;
	
	public SLQuantity(String quantity)
	{
		this.quantity = new BigDecimal(quantity);
	}
	
	@Override
	public String getStringValue()
	{
		return quantity.toPlainString();
	}

	@Override
	public void setValue(String value)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateValueWith(String value)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateValueWith(SPLineAttribute value)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearValue()
	{
		quantity = new BigDecimal(0);
	}

}
