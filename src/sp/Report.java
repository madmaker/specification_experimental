package sp;

import java.io.File;

import reports.BlockList;
import reports.ReportConfiguration;

public class Report
{
	public enum ReportType {PDF, XLS}
	
	public String name;
	public BlockList blockList;
	public File data;
	public File report;
	public ReportConfiguration configuration;

	public Report(String name)
	{
		this.name = name;
	}

	public void isDataValid()
	{
		if (data == null)
			throw new RuntimeException("Построение отчёта невозможно: отсутствуют данные.");
	}
}
