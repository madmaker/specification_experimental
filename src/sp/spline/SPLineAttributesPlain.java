package sp.spline;

import java.math.BigDecimal;
import java.util.ArrayList;

import sp.spline.attributes.SLFormat;
import sp.spline.attributes.SLRemark;
import sp.spline.attributes.SLZone;

public class SPLineAttributesPlain
{
	private SLFormat			format;
	private SLZone				zone;
	private SLRemark			remark;
	private String				position;
	private String				id;
	private ArrayList<String>	name;
	private BigDecimal			quantity;
	
	public SPLineAttributesPlain()
	{
		format = new SLFormat("");
		zone = new SLZone("");
		remark = new SLRemark("");
		position = "";
		id = "";
		name = new ArrayList<String>();
		quantity = new BigDecimal(0);
	}
}
