package vp;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.ui.handlers.HandlerUtil;

import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCSession;

public class VPHandler extends AbstractHandler
{
	public static TCSession session = (TCSession) AIFUtility.getCurrentApplication().getSession();
	
	public VPHandler()
	{
	};

	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		ProgressMonitorDialog pd = new ProgressMonitorDialog(HandlerUtil.getActiveShell(event).getShell());
		
		VP vp = new VP();
		vp.progressMonitor = pd;
		
		return null;
	}
}
