package vp;

import org.eclipse.jface.dialogs.ProgressMonitorDialog;

import com.teamcenter.rac.kernel.TCComponentBOMLine;
import com.teamcenter.rac.kernel.TCComponentItemRevision;

import sp.Report;

public class VP
{
	static enum FormField {NUMBER, FORMAT, ZONE, ID, NAME, QTY, REMARK}

	public static TCComponentBOMLine topBOMLine;
	public static TCComponentItemRevision vpIR;
	
	public ProgressMonitorDialog progressMonitor;
	private Report report;
	
	public VP()
	{
		report = new Report("My VP Report");
	}
}
