package sp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.teamcenter.rac.aif.kernel.AIFComponentContext;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCComponentItemType;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.services.rac.core.DataManagementService;
import com.teamcenter.services.rac.core._2008_06.DataManagement.ReviseInfo;
import com.teamcenter.services.rac.core._2008_06.DataManagement.ReviseOutput;
import com.teamcenter.services.rac.core._2008_06.DataManagement.ReviseResponse2;

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
	
	private TCComponentItemRevision createNextRevisionBasedOn(TCComponentItemRevision itemRev)
	{
		TCComponentItemRevision out = null;
		
		ReviseInfo revInfo = new ReviseInfo();
		revInfo.baseItemRevision = itemRev;
		ReviseResponse2 response = dmService.revise2(new ReviseInfo[] {revInfo});
		
		Iterator it = response.reviseOutputMap.entrySet().iterator();
		if (it.hasNext())
		{
			Map.Entry entry = (Entry) it.next();
			out = ((ReviseOutput)entry.getValue()).newItemRev;
		}
		
		return out;
	}
	
	private void removeSPItemsRelatedToCompanyPartRevision(TCComponentItemRevision rev) throws TCException
	{
		ArrayList<TCComponentItemRevision> list4Removing = new ArrayList<TCComponentItemRevision>();
		AIFComponentContext[] itemRev4Delete = rev.getItem().getRelated("Oc9_DocRel");
		for (AIFComponentContext currContext : itemRev4Delete)
		{
			String documentRevisionItemId = ((TCComponentItemRevision)currContext.getComponent()).getProperty("item_id");
			String companyPartItemId = rev.getItem().getProperty("item_id");
			
			if (documentRevisionItemId.equals(companyPartItemId))
			{
				list4Removing.add((TCComponentItemRevision)currContext.getComponent());
			}
		}
		rev.remove("Oc9_DocRel", list4Removing);
	}
	
	private TCComponentItem findKDDocItem() throws TCException
	{
		TCComponentItem result = null;
		TCComponentItemType itemType = (TCComponentItemType) session.getTypeComponent("Oc9_KD");
		String criteria = SP.topBOMLineIR.getProperty("item_id");
		TCComponentItem[] items = itemType.findItems(criteria);
		if (items != null && items.length > 0)
		{
			for(TCComponentItem item : items){
				System.out.println("Found item " + item.getProperty("item_id") + " of type " + item.getType());
				if(item.getType().equals("Oc9_KD")){
					result = item;
					break;
				}
			}
		}
		
		return result;
	}
	
	private TCComponentItem findSPItemForCompanyPartRevision(TCComponentItemRevision itemRevision) throws TCException
	{
		TCComponent[] documents = itemRevision.getRelatedComponents("Oc9_DocRel");
		String topBOMLineItemID = itemRevision.getItem().getProperty("item_id");
		String documentItemID = "";
		for(TCComponent document : documents)
		{
			documentItemID = document.getProperty("item_id");
			if(topBOMLineItemID.equals(documentItemID))
			{
				return (TCComponentItem) document;
			}
		}
		return null;
	}
}
