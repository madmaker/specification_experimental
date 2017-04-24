package vp;

import org.eclipse.jface.dialogs.ProgressMonitorDialog;

import com.teamcenter.rac.kernel.TCComponentBOMLine;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.pse.plugin.Activator;

import sp.Report;
import vp.vpblock.VPBlockList;

public class VP
{
	static enum FormField {NUMBER, FORMAT, ZONE, ID, NAME, QTY, REMARK}

	public static TCComponentBOMLine topBOMLine;
	public static TCComponentItem topBOMLineI;
	public static TCComponentItemRevision topBOMLineIR;
	public static TCComponentItemRevision vpIR;
	
	public ProgressMonitorDialog progressMonitor;
	private Report report;
	
	public VP()
	{
		report = new Report("My VP Report");
		report.blockList = new VPBlockList();
	}
	
	public void init()
	{
		try {
			//TODO topBOMLine = Activator.getPSEService().getTopBOMLine();
			topBOMLine = new TCComponentBOMLine();
			topBOMLineI = topBOMLine.getItem();
			topBOMLineIR = topBOMLine.getItemRevision();
		} catch (TCException ex) {
			ex.printStackTrace();
		}
	}
}
