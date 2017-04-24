package vp;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.teamcenter.rac.aif.kernel.AIFComponentContext;
import com.teamcenter.rac.kernel.TCComponentBOMLine;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCException;

import util.ItemUtil;

public class VPValidator
{
	public ArrayList<String> errorList;
	private TCComponentBOMLine topBOMLine;
	private TCComponentItemRevision topIR;
	private ReportsItemUtils itemUtils = ReportsItemUtils.getReportsItemUtils();
	
	public VPValidator(TCComponentBOMLine topBomLine)
	{
		this.topBOMLine = topBomLine;
	}
	
	public ArrayList<String> validate()
	{
		errorList = new ArrayList<String>();
		try {
			topIR = topBOMLine.getItemRevision();
			if (topIR.getProperty("pm8_Designation").trim().equals("")) {
				errorList.add("У изделия, для которого строится ВП, не задано обозначение.");
			}
			TCComponentItemRevision kdRev = ReportsItemUtils.getVpRev(topBOMLine);
			if ((kdRev != null)	&& ItemUtil.isComponentHasReleasedStatus(kdRev))
			{
				errorList.add("У данной ревизии уже есть утверждённая ВП");
			}
			if ((kdRev != null) && ReportsItemUtils.isVpDatasetBlocked(kdRev))
			{
				errorList.add("Текущий набор данных заблокирован.\nЗакройте PDF файл и повторите попытку.");
			}
		} catch (TCException ex) {
			ex.printStackTrace();
			errorList.add("Возникла непредвиденная ошибка. Пожалуйста, обратитесь к разработчику.");
		}
		return errorList;
	}
}
