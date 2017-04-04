package specline;

public class MaterialSpecLineAttributes extends SpecLineAttributes
{
  private SLKits kits;

  public MaterialSpecLineAttributes()
  {

  }

  public void addKit(String id, String name, double qty)
  {
		if(kits==null) this.kits = new BLKits();
		this.kits.addKit(id, name, qty);
	}

	public void addKit(SLKits kit)
  {
    if(kits==null) this.kits = new SLKits();
		this.kits.addKits(kit);
	}

	public SLKits getKits()
  {
		return this.kits;
	}
}
