package specline;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import spec.Specification;
import spec.Specification.FormField;
import specline.attributes.SLFormat;
import specline.attributes.SLRemark;
import specline.attributes.SLZone;
import util.LineUtil;

public abstract class SpecLineAttributes
{
	private SLFormat			format;
	private SLZone				zone;
	private SLRemark			remark;
	private String				position;
	private String				id;
	private ArrayList<String>	name;
	private BigDecimal			quantity;

	public SpecLineAttributes()
	{
	};

	public void setFormat(String format)
	{
		this.format = new SLFormat(format);
	}

	public void setZone(String zone)
	{
		if (this.zone == null)
		{
			this.zone = new SLZone(zone);
		}
		else
		{
			this.zone.addZone(zone);
		}
	}

	public void setRemark(String remark)
	{
		if (this.remark == null)
			this.remark = new SLRemark();
		this.remark.insert(remark);
	}

	public void setPosition(String position)
	{
		this.position = position;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public void setName(String name)
	{
		this.name = LineUtil.getFittedLines(name, SpecificationSettings.columnLengths.get(Specification.FormField.NAME));
	}

	public void setQuantity(String quantity)
	{
		this.quantity = new BigDecimal("".equals(quantity) ? "1" : quantity);
	}

	public void addQuantity(String quantity)
	{
		this.quantity = this.quantity.add(new BigDecimal("".equals(quantity) ? "1" : quantity));
	}

	public SLFormat getFormat()
	{
		return format;
	}

	public SLZone getZone()
	{
		return zone;
	}

	public String getPosition()
	{
		return position;
	}

	public String getId()
	{
		return id;
	}

	public ArrayList<String> getName()
	{
		return name;
	}

	public BigDecimal getQuantity()
	{
		return quantity;
	}

	public SLRemark getRemark()
	{
		return remark;
	}

	public String getStringValueFromField(FormField field)
	{
		switch (field)
			{
			case FORMAT:
				return format.toString();
			case ZONE:
				return zone.toString();
			case POSITION:
				return position;
			case ID:
				return id;
			case NAME:
				return Arrays.toString(name.toArray());
			case QUANTITY:
				return quantity.compareTo(new BigDecimal(0))==0?" ":quantity.stripTrailingZeros().toString();
			case REMARK:
				return remark.toString();
			}
		return "";
	}
}
