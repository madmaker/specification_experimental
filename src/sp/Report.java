package sp;

import java.io.File;

import reports.BlockList;
import reports.ReportConfiguration;
import sp.spblock.SPBlockList;

public class Report
{
	public enum ReportType {PDF, XLS}
	
	public String name;
	public SPBlockList blockList;
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
