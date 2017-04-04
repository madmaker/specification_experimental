package spec;

import java.io.File;

public class Specification
{
	public StampData stampData;
	public static enum FormField {FORMAT, ZONE, POSITION, ID, NAME, QUANTITY, REMARK};

	public Specification()
	{
		stampData = new StampData();
	}

	public void buildXmlFile()
	{
		XmlBuilderConfiguration xmlBuilderConfiguration = new XmlBuilderConfiguration(26, 32);
		xmlBuilderConfiguration.MaxWidthGlobalRemark = 474;

		XmlBuilder xmlBuilder = new XmlBuilder(xmlBuilderConfiguration);
	}

	public void buildReportFile()
	{
		ReportConfiguration configuration = new ReportConfiguration();
		Report report = new Report(configuration);
	}
}
