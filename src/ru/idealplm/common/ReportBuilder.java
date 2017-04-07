package ru.idealplm.common;

import java.io.File;

public interface ReportBuilder
{
	File getReport();
	void passSourceFile(File sourceFile, Object lock);
}
