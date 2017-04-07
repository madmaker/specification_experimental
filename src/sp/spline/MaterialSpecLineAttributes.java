package sp.spline;

import sp.spline.attributes.SLKits;

public class MaterialSpecLineAttributes extends SPLineAttributes
{
	private SLKits kits;

	public MaterialSpecLineAttributes()
	{

	}

	public void addKit(String id, String name, double qty)
	{
		if (kits == null)
			this.kits = new SLKits();
		this.kits.addKit(id, name, (int)qty);
	}

	public void addKit(SLKits kit)
	{
		if (kits == null)
			this.kits = new SLKits();
		this.kits.addKits(kit);
	}

	public SLKits getKits()
	{
		return this.kits;
	}
}
