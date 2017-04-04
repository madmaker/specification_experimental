package spec;

public class XmlBuilder
{
	private XmlBuilderConfiguration configuration;

	public XmlBuilder(XmlBuilderConfiguration configuration)
	{
		this.configuration = configuration;
	}

	public void setConfiguration(XmlBuilderConfiguration configuration)
	{
		this.configuration = configuration;
	}
}
