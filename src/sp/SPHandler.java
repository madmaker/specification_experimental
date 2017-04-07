package sp;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.handlers.HandlerUtil;

import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCSession;

public class SPHandler extends AbstractHandler
{
	public static TCSession session = (TCSession) AIFUtility.getCurrentApplication().getSession();

	public SPHandler()
	{
	};

	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		ProgressMonitorDialog pd = new ProgressMonitorDialog(HandlerUtil.getActiveShell(event).getShell());
		
		SP specification = new SP();
		specification.progressMonitor = pd;
		
		specification.buildXmlFile();
		specification.buildReportFile();

		return null;
	}
}
