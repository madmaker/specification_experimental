package sp.spline;

import sp.spline.attributes.SLFormat;
import sp.spline.attributes.SLId;
import sp.spline.attributes.SLName;
import sp.spline.attributes.SLPosition;
import sp.spline.attributes.SLQuantity;
import sp.spline.attributes.SLRemark;
import sp.spline.attributes.SLZone;

public class SPLineAttributesPlain
{
	public SLFormat format;
	public SLZone zone;
	public SLRemark remark;
	public SLPosition position;
	public SLId id;
	public SLName name;
	public SLQuantity quantity;

	public SPLineAttributesPlain()
	{
		format = new SLFormat();
		zone = new SLZone();
		remark = new SLRemark();
		position = new SLPosition("");
		id = new SLId("");
		name = new SLName("");
		quantity = new SLQuantity("0");
	}
}
