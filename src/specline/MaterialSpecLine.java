package specline;

public class MaterialSpecLine extends SpecLine
{
  public MaterialSpecLine()
  {
    this.attributes = new MaterialSpecLineAttributes();
  }

  public abstract void init()
  {

  }

  public abstract void build()
  {

  }

  public abstract String getUid()
  {
    
  }

  private SpecLine newLine(TCComponentBOMLine bomLine){};
}
