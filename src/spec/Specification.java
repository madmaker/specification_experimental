package spec;

import java.io.File;
import java.io.InputStream;

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
		InputStream template = Specification.class.getResourceAsStream("/pdf/OceanosSpecPDFTemplate.xsl");
		InputStream config = Specification.class.getResourceAsStream("/pdf/OceanosUserconfig.xsl");
		ReportConfiguration configuration = new ReportConfiguration(template, config);
		
		Report report = new Report(configuration);
	}
}
