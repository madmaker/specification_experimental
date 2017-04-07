package sp;

import java.io.File;

public class Report
{
	public enum ReportType {PDF, XLS}
	
	public String name;
	public File data;
	public File report;
	private ReportConfiguration configuration;

	public Report(String name)
	{
		this.name = name;
	}

	public void setConfiguration(ReportConfiguration configuration)
	{
		this.configuration = configuration;
	}

	public ReportConfiguration getConfiguration()
	{
		return this.configuration;
	}

	public void isDataValid()
	{
		if (data == null)
			throw new RuntimeException("Построение отчёта невозможно: отсутствуют данные.");
	}
}
