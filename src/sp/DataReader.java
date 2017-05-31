package sp;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CancellationException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;

import com.teamcenter.rac.aif.kernel.AIFComponentContext;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentBOMLine;
import com.teamcenter.rac.kernel.TCComponentDataset;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.util.MessageBox;
import com.teamcenter.services.rac.cad.StructureManagementService;
import com.teamcenter.services.rac.cad._2007_01.StructureManagement.ExpandPSData;
import com.teamcenter.services.rac.cad._2007_01.StructureManagement.ExpandPSOneLevelInfo;
import com.teamcenter.services.rac.cad._2007_01.StructureManagement.ExpandPSOneLevelOutput;
import com.teamcenter.services.rac.cad._2007_01.StructureManagement.ExpandPSOneLevelPref;
import com.teamcenter.services.rac.cad._2007_01.StructureManagement.ExpandPSOneLevelResponse;

import reports.EnumBlockType;
import reports.Error;
import sp.spline.SPLine;
import sp.spline.SPLineDocument;
import util.DateUtil;

public class DataReader
{
	StructureManagementService		smsService		= StructureManagementService.getService(SPHandler.session);
	private DocumentTypes			documentTypes	= new DocumentTypes();
	private SP						specification;
	private ProgressMonitorDialog	pd;

	public DataReader(SP specification)
	{
		this.specification = specification;
		pd = specification.progressMonitor;
	}

	public void readData()
	{
		try
		{
			pd.run(true, true, new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException
				{
					monitor.beginTask("Чтение данных", 100);
					readDocumentsData(monitor);
					readBOMData(monitor);
					monitor.done();
				}
			});
		}
		catch (InvocationTargetException | InterruptedException e)
		{
			e.printStackTrace();
		}
		catch (CancellationException ex)
		{
			SPSettings.isCancelled = true;
			System.out.println(ex.getMessage());
		}
	}

	private void readDocumentsData(IProgressMonitor monitor)
	{
		TCComponent[] documents = getDocumentsRelatedToItemRevision(SP.topBOMLineIR);
		monitor.beginTask("Чтение данных связанной документации", documents.length);
		for (TCComponent document : documents)
		{
			try
			{
				readDocumentData((TCComponentItem) document);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
			finally
			{
				monitor.worked(1);
				checkIfMonitorIsCancelled(monitor);
			}
		}
	}

	private void readDocumentData(TCComponentItem documentItem) throws TCException
	{
		TCComponentItemRevision documentItemRevision = documentItem.getLatestItemRevision();
		String topBOMLineItemId = SP.topBOMLineIR.getProperty("item_id");
		String documentItemId = documentItem.getProperty("item_id");

		if (documentItemId.equals(topBOMLineItemId))
		{
			SP.spIR = documentItemRevision;
			readSPDocumentRevisionData(documentItemRevision);
			readStampDataFromGeneralNoteForm(documentItemRevision);
			readBlockSettings(documentItemRevision);
		}
		else
		{
			createSPLineFromDocumentItemRevision(documentItemRevision);
		}
	}

	private void createSPLineFromDocumentItemRevision(TCComponentItemRevision documentItemRevision)
	{
		try
		{
			String documentItemId = documentItemRevision.getProperty("item_id");
			String topBOMLineItemId = SP.topBOMLineIR.getProperty("item_id");
			DocumentType documentType = documentTypes.getType(documentItemId);
			String uid = documentItemRevision.getUid();
			String format = documentItemRevision.getProperty("oc9_Format");
			String object_name = documentItemRevision.getProperty("object_name");
			String name = "";
			boolean isBaseDoc = true;
			boolean gostNameIsFalse;

			if (documentType.type == EnumBlockType.DOCUMENTS)
			{
				gostNameIsFalse = documentItemRevision.getProperty("oc9_GOSTName").equalsIgnoreCase("нет");
				isBaseDoc = documentItemId.substring(0, documentItemId.lastIndexOf(" ")).equals(topBOMLineItemId);
				if (gostNameIsFalse || !isBaseDoc)
				{
					name += object_name;
				}
				if (!gostNameIsFalse)
				{
					name += "\n" + documentType.longName;
				}

				SPLineDocument resultBlockLine = new SPLineDocument();
				resultBlockLine.attributes.format.setValue(format);
				resultBlockLine.attributes.id.setValue(documentItemId);
				resultBlockLine.attributes.name.setValue(name);
				resultBlockLine.attributes.quantity.setValue("-1");
				resultBlockLine.documentType = documentType;
				resultBlockLine.build();
				specification.report.blockList.getBlock(documentType.type).addLine(resultBlockLine);
			}
			else if (documentType.type == EnumBlockType.KITS)
			{
				gostNameIsFalse = documentItemRevision.getProperty("oc9_GOSTName").equalsIgnoreCase("нет");
				isBaseDoc = documentItemId.substring(0, documentItemId.lastIndexOf(" ")).equals(topBOMLineItemId);
				name += object_name;
				if (!gostNameIsFalse)
				{
					name += "\n" + documentType.longName;
				}

				SPLineDocument resultBlockLine = new SPLineDocument();
				resultBlockLine.attributes.format.setValue(format);
				resultBlockLine.attributes.id.setValue(documentItemId);
				resultBlockLine.attributes.name.setValue(name);
				resultBlockLine.attributes.quantity.setValue("-1");
				resultBlockLine.documentType = documentType;
				resultBlockLine.build();
				specification.report.blockList.getBlock(documentType.type).addLine(resultBlockLine);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	private void readSPDocumentRevisionData(TCComponentItemRevision documentItemRevision)
	{
		try
		{
			readStampDataFromExistingSPDocument(documentItemRevision);
			checkIfSPDatasetIsCheckedOut(documentItemRevision);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	private void checkIfSPDatasetIsCheckedOut(TCComponentItemRevision documentItemRevision) throws Exception
	{
		for (AIFComponentContext compContext : documentItemRevision.getChildren())
		{
			if ((compContext.getComponent() instanceof TCComponentDataset)
					&& compContext.getComponent().getProperty("object_desc").equals("Спецификация"))
			{
				if (((TCComponent) compContext.getComponent()).isCheckedOut())
				{
					specification.errorList.storeError(new Error("Набор данных заблокирован."));
				}
			}
		}
	}

	private void readStampDataFromExistingSPDocument(TCComponentItemRevision documentItemRevision) throws Exception
	{
		specification.stampData.litera1 = SP.spIR.getProperty("oc9_Litera1");
		specification.stampData.litera2 = SP.spIR.getProperty("oc9_Litera2");
		specification.stampData.litera3 = SP.spIR.getProperty("oc9_Litera3");
		specification.stampData.pervPrim = SP.spIR.getItem().getProperty("oc9_PrimaryApp");
		specification.stampData.invNo = SP.spIR.getItem().getProperty("oc9_InvNo");
	}

	private TCComponent[] getDocumentsRelatedToItemRevision(TCComponentItemRevision itemRevision)
	{
		try
		{
			return itemRevision.getRelatedComponents("Oc9_DocRel");
		}
		catch (TCException ex)
		{
			ex.printStackTrace();
			return new TCComponent[] {};
		}
	}

	private void readBOMData(IProgressMonitor monitor)
	{
		try
		{
			ExpandPSOneLevelInfo levelInfo = new ExpandPSOneLevelInfo();
			ExpandPSOneLevelPref levelPref = new ExpandPSOneLevelPref();

			levelInfo.parentBomLines = new TCComponentBOMLine[] { SP.topBOMLine };
			levelInfo.excludeFilter = "None";
			levelPref.expItemRev = true;

			ExpandPSOneLevelResponse levelResp = smsService.expandPSOneLevel(levelInfo, levelPref);

			if (levelResp.output.length > 0)
			{
				for (ExpandPSOneLevelOutput levelOut : levelResp.output)
				{
					monitor.beginTask("Чтение данных структуры сборки", levelOut.children.length);
					for (ExpandPSData psData : levelOut.children)
					{
						monitor.worked(1);
						/*** Для отладки ***/
						System.out.println(psData.bomLine.getProperty("bl_line_name"));
						try
						{
							Thread.sleep(100);
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
						}
						/*** Для отладки ***/
						checkIfMonitorIsCancelled(monitor);
					}
				}
				monitor.done();
			}
		}
		catch (TCException ex)
		{
			ex.printStackTrace();
		}
	}

	private void readStampDataFromGeneralNoteForm(TCComponentItemRevision documentItemRevision)
	{
		try
		{
			TCComponent signForm = documentItemRevision.getRelatedComponent("Oc9_SignRel");
			if (signForm != null)
			{
				specification.stampData.design = signForm.getProperty("oc9_Designer");
				specification.stampData.check = signForm.getProperty("oc9_Check");
				specification.stampData.techCheck = signForm.getProperty("oc9_TCheck");
				specification.stampData.normCheck = signForm.getProperty("oc9_NCheck");
				specification.stampData.approve = signForm.getProperty("oc9_Approver");
				String designDate = DateUtil.parseDateFromTC(signForm.getProperty("oc9_DesignDate"));
				String checkDate = DateUtil.parseDateFromTC(signForm.getProperty("oc9_CheckDate"));
				String tCheckDate = DateUtil.parseDateFromTC(signForm.getProperty("oc9_TCheckDate"));
				String nCheckDate = DateUtil.parseDateFromTC(signForm.getProperty("oc9_NCheckDate"));
				String approveDate = DateUtil.parseDateFromTC(signForm.getProperty("oc9_ApproveDate"));
				specification.stampData.designDate = designDate;
				specification.stampData.checkDate = checkDate;
				specification.stampData.techCheckDate = tCheckDate;
				specification.stampData.normCheckDate = nCheckDate;
				specification.stampData.approveDate = approveDate;
			}
		}
		catch (TCException ex)
		{
			ex.printStackTrace();
		}
	}

	private void readBlockSettings(TCComponentItemRevision documentItemRevision)
	{
		try
		{
			TCComponent masterForm = documentItemRevision.getRelatedComponent("IMAN_master_form_rev");
			if (masterForm != null)
			{
				SPSettings.blockSettings = masterForm.getProperty("object_desc");
			}
		}
		catch (TCException ex)
		{
			ex.printStackTrace();
		}
	}

	private void checkIfMonitorIsCancelled(IProgressMonitor monitor)
	{
		if (monitor.isCanceled())
		{
			throw new CancellationException("Чтение данных было отменено");
		}
	}
}
