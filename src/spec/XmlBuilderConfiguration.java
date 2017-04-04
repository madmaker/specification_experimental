package spec;

public class XmlBuilderConfiguration
{
	public int	MaxLinesOnFirstPage;
	public int	MaxLinesOnOtherPage;
	public int	MaxWidthGlobalRemark;

	public XmlBuilderConfiguration()
	{
		MaxLinesOnFirstPage = 1;
		MaxLinesOnOtherPage = 1;
		MaxWidthGlobalRemark = 1;
	}

	public XmlBuilderConfiguration(int MaxLinesOnFirstPage, int MaxLinesOnOtherPage)
	{
		this.MaxLinesOnFirstPage = MaxLinesOnFirstPage;
		this.MaxLinesOnOtherPage = MaxLinesOnOtherPage;
	}
}
