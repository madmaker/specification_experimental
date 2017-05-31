package vp;

import java.io.IOException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.ui.handlers.HandlerUtil;

import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;

import vp.gui.ErrorListDialog;
import vp.gui.VPDialog;

public class VPHandler extends AbstractHandler
{
	public static TCSession session = (TCSession) AIFUtility.getCurrentApplication().getSession();
	VP vp;
	
	public VPHandler()
	{
	};

	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		ProgressMonitorDialog pd = new ProgressMonitorDialog(HandlerUtil.getActiveShell(event).getShell());
		
		vp = new VP();
		vp.progressMonitor = pd;
		vp.init();
		
		VPDialog vpDialog = new VPDialog(HandlerUtil.getActiveShell(event).getShell(), SWT.CLOSE, vp);
		vpDialog.open();
		
		if (!VPSettings.isOKPressed) { return null; }
		
		VPValidator vpValidator = new VPValidator(VP.topBOMLine);
		vpValidator.validate();
		if(vpValidator.errorList.size()>0){
			ErrorListDialog eld = new ErrorListDialog(HandlerUtil.getActiveShell(event).getShell(), vpValidator.errorList);
		} else {
			try {
				vp.init();
				VPDialog dialog = new VPDialog(HandlerUtil.getActiveShell(event).getShell(), 1, vp);
				dialog.open();			
			} catch (TCException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		setProgressBarStage("Анализ структуры",1);

		EngineVP2G.getListOfBoughts(BuildVP2G.topBomLine, 1, 0);
		if (EngineVP2G.arrListErrorElements.size() == 0) {
			EngineVP2G.makeAndSortBlocks();
			int result = EngineVP2G.exportXML();
			System.out.println("XML exporting answer ->" + result);
			System.out.println("Export to XML!");
			setProgressBarStage("Добавление в Teamcenter", 5);
			EngineVP2G.addToTeamcenter();
			System.out.println("Added to Teamcenter!");
		}
		
		System.out.println("ERROR size: " + EngineVP2G.arrListErrorElements.size());
		
		String out = "\n";
		if (EngineVP2G.arrListErrorElements.size() > 0) {
			for (String currString: EngineVP2G.arrListErrorElements) {
				out += (currString + "\n");
			}
			new InformMessageBox("Формирование ВП невозможно так как для следующих компонентов указана различная размерность:" + out, SWT.ICON_ERROR);
		}
		
		return null;
	}
}
