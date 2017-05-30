package vp;

import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.services.rac.core.DataManagementService;

import sp.SPHandler;

public class ReportUploader
{
	public TCSession session;
	public DataManagementService dmService;
	
	public ReportUploader()
	{
		session = SPHandler.session;
		dmService = DataManagementService.getService(session);
	}

	public void addToTeamcenter()
	{
		
	}
}
