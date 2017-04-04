package spec;

import java.io.File;
import java.io.IOException;

import util.FileUtil;

public class OceanosReportBuilder
{
	public File buildReport()
	{
		checkData();
		try
		{
			FileUtil.copy(OceanosReportBuilderMethod.class.getResourceAsStream("/icons/iconOceanos.jpg"),
					new File(specification.getXmlFile().getParentFile().getAbsolutePath() + "\\iconOceanos.jpg"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		pdfBuilder.passSourceFile(specification.getXmlFile(), this);
		synchronized (this)
		{
			try
			{
				this.wait();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		report = pdfBuilder.getReport();
		return report;
	}

	public File buildReportStatic()
	{
		checkData();
		try
		{
			copy(OceanosReportBuilderMethod.class.getResourceAsStream("/icons/iconOceanos.jpg"),
					new File(specification.getXmlFile().getParentFile().getAbsolutePath() + "\\iconOceanos.jpg"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		report = PDFBuilder.xml2pdf(data, configuration.getTemplateStream(), configuration.getConfigStream());
		return report;
	}
}
