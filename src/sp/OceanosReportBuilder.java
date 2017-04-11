package sp;

import java.io.File;
import java.io.IOException;

import ru.idealplm.xml2pdf.handlers.PDFBuilder;
import util.FileUtil;

public class OceanosReportBuilder
{
	private PDFBuilder pdfBuilder;
	private Report report;
	
	public OceanosReportBuilder(Report report)
	{
		this.report = report;
	}
	
	public void buildReport()
	{
		report.isDataValid();
		try
		{
			FileUtil.copy(OceanosReportBuilder.class.getResourceAsStream("/icons/iconOceanos.jpg"),
					new File(report.data.getParentFile().getAbsolutePath() + "\\iconOceanos.jpg"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		pdfBuilder.passSourceFile(report.data, this);
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
		report.report = pdfBuilder.getReport();
	}

	public void buildReportStatic()
	{
		report.isDataValid();
		try
		{
			FileUtil.copy(OceanosReportBuilder.class.getResourceAsStream("/icons/iconOceanos.jpg"),
					new File(report.data.getParentFile().getAbsolutePath() + "\\iconOceanos.jpg"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		report.report = PDFBuilder.xml2pdf(report.data, ((PDFBuilderConfiguration)report.configuration).getTemplateStream(), ((PDFBuilderConfiguration)report.configuration).getConfigStream());
	}
}
