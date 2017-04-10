package sp;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CancellationException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;

import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentBOMLine;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.services.rac.cad.StructureManagementService;
import com.teamcenter.services.rac.cad._2007_01.StructureManagement.ExpandPSData;
import com.teamcenter.services.rac.cad._2007_01.StructureManagement.ExpandPSOneLevelInfo;
import com.teamcenter.services.rac.cad._2007_01.StructureManagement.ExpandPSOneLevelOutput;
import com.teamcenter.services.rac.cad._2007_01.StructureManagement.ExpandPSOneLevelPref;
import com.teamcenter.services.rac.cad._2007_01.StructureManagement.ExpandPSOneLevelResponse;

import util.DateUtil;

public class DataReader
{
	DocumentTypes documentTypes = new DocumentTypes();
	StructureManagementService smsService = StructureManagementService.getService(SPHandler.session);
	private SP specification;
	private ProgressMonitorDialog pd;
	
	public DataReader(SP specification)
	{
		this.specification = specification;
		pd = specification.progressMonitor;
	}
	
	public void readData()
	{
		try {
			pd.run(true, true, new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					monitor.beginTask("Чтение данных", 100);
					readGeneralNoteForm();
					readBOMData(monitor);
					monitor.done();
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		} catch (CancellationException ex) {
			SPSettings.isCancelled = true;
			System.out.println(ex.getMessage());
		}
	}
	
	private void readBOMData(IProgressMonitor monitor)
	{
		try{
			ExpandPSOneLevelInfo levelInfo = new ExpandPSOneLevelInfo();
			ExpandPSOneLevelPref levelPref = new ExpandPSOneLevelPref();
	
			levelInfo.parentBomLines = new TCComponentBOMLine[] { SP.topBOMLine };
			levelInfo.excludeFilter = "None";
			levelPref.expItemRev = true;
	
			ExpandPSOneLevelResponse levelResp = smsService.expandPSOneLevel(levelInfo, levelPref);
	
			if (levelResp.output.length > 0) {
				monitor.beginTask("Чтение данных структуры сборки", levelResp.output.length);
				for (ExpandPSOneLevelOutput levelOut : levelResp.output) {
					System.out.println(levelResp.output.length + " : child lines");
					monitor.worked(1);
					for (ExpandPSData psData : levelOut.children) {
						System.out.println(psData.bomLine.getProperty("bl_line_name"));
						//expandBOMLines(psData.bomLine);
					}
					if(monitor.isCanceled())
					{
						throw new CancellationException("Чтение данных структуры сборки было отменено");
					}
				}
				monitor.done();
			}
		} catch (TCException ex) {
			ex.printStackTrace();
		}
	}
	
	private void readGeneralNoteForm()
	{
		try{
			TCComponentItemRevision spIR = specification.spIR;
			TCComponent signForm;
			if(spIR!=null){
				if((signForm = spIR.getRelatedComponent("Oc9_SignRel"))!=null){
					specification.stampData.design = signForm.getProperty("oc9_Designer");
					specification.stampData.check = signForm.getProperty("oc9_Check");
					specification.stampData.techCheck = signForm.getProperty("oc9_TCheck");
					specification.stampData.normCheck = signForm.getProperty("oc9_NCheck");
					specification.stampData.approve = signForm.getProperty("oc9_Approver");
					String designDate = signForm.getProperty("oc9_DesignDate").equals("")?null:DateUtil.parseDateFromTC(signForm.getProperty("oc9_DesignDate"));
					String checkDate = signForm.getProperty("oc9_CheckDate").equals("")?null:DateUtil.parseDateFromTC(signForm.getProperty("oc9_CheckDate"));
					String tCheckDate = signForm.getProperty("oc9_TCheckDate").equals("")?null:DateUtil.parseDateFromTC(signForm.getProperty("oc9_TCheckDate"));
					String nCheckDate = signForm.getProperty("oc9_NCheckDate").equals("")?null:DateUtil.parseDateFromTC(signForm.getProperty("oc9_NCheckDate"));
					String approveDate = signForm.getProperty("oc9_ApproveDate").equals("")?null:DateUtil.parseDateFromTC(signForm.getProperty("oc9_ApproveDate"));
					specification.stampData.designDate = designDate;
					specification.stampData.checkDate = checkDate;
					specification.stampData.techCheckDate = tCheckDate;
					specification.stampData.normCheckDate= nCheckDate;
					specification.stampData.approveDate= approveDate;
				}
				if(spIR.getRelatedComponent("IMAN_master_form_rev")!=null){
					SPSettings.blockSettings = spIR.getRelatedComponent("IMAN_master_form_rev").getProperty("object_desc");
				}
			}
		} catch (TCException ex){
			ex.printStackTrace();
		}
	}
}
