package vp;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CancellationException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;

import com.teamcenter.rac.kernel.TCComponentBOMLine;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.services.rac.cad.StructureManagementService;
import com.teamcenter.services.rac.cad._2007_01.StructureManagement.ExpandPSData;
import com.teamcenter.services.rac.cad._2007_01.StructureManagement.ExpandPSOneLevelInfo;
import com.teamcenter.services.rac.cad._2007_01.StructureManagement.ExpandPSOneLevelOutput;
import com.teamcenter.services.rac.cad._2007_01.StructureManagement.ExpandPSOneLevelPref;
import com.teamcenter.services.rac.cad._2007_01.StructureManagement.ExpandPSOneLevelResponse;

import sp.SP;
import sp.SPHandler;

public class DataReader
{
	StructureManagementService smsService = StructureManagementService.getService(SPHandler.session);
	private VP vp;
	private ProgressMonitorDialog pd;

	public DataReader(VP vp)
	{
		this.vp = vp;
		pd = vp.progressMonitor;
	}

	public void readData()
	{
		try {
			pd.run(true, true, new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException
				{
					monitor.beginTask("Чтение данных", 100);
					readBOMData(monitor);
					monitor.done();
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		} catch (CancellationException ex) {
			VPSettings.isCancelled = true;
			System.out.println(ex.getMessage());
		}
	}
	
	private void readBOMData(IProgressMonitor monitor)
	{
		try {
			ExpandPSOneLevelInfo levelInfo = new ExpandPSOneLevelInfo();
			ExpandPSOneLevelPref levelPref = new ExpandPSOneLevelPref();

			levelInfo.parentBomLines = new TCComponentBOMLine[] { SP.topBOMLine };
			levelInfo.excludeFilter = "None";
			levelPref.expItemRev = true;

			ExpandPSOneLevelResponse levelResp = smsService.expandPSOneLevel(levelInfo, levelPref);

			if (levelResp.output.length > 0) {
				for (ExpandPSOneLevelOutput levelOut : levelResp.output) {
					monitor.beginTask("Чтение данных структуры сборки", levelOut.children.length);
					for (ExpandPSData psData : levelOut.children) {
						monitor.worked(1);
						System.out.println(psData.bomLine.getProperty("bl_line_name"));
						//TODO Обработка бомлайна
						checkIfMonitorIsCancelled(monitor);
					}
				}
				monitor.done();
			}
		} catch (TCException ex) {
			ex.printStackTrace();
		}
	}
	
	private void checkIfMonitorIsCancelled(IProgressMonitor monitor)
	{
		if (monitor.isCanceled()) {
			throw new CancellationException("Чтение данных было отменено");
		}
	}
}
