package spec;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Report
{
	private ReportConfiguration	configuration;
	private File				data;
	private File				report;

	public Report(ReportConfiguration configuration)
	{
		this.configuration = configuration;
	}

	public void setConfiguration(ReportConfiguration configuration)
	{
		this.configuration = configuration;
	}

	public void setData(File data)
	{
		this.data = data;
	}

	public void isDataValid()
	{
		if (data == null)
			throw new RuntimeException("Построение отчёта невозможно: отсутствуют данные.");
	}
}
