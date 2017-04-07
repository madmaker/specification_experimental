package util;

import com.teamcenter.rac.aif.kernel.AIFComponentContext;
import com.teamcenter.rac.kernel.TCComponentBOMLine;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.services.rac.cad.StructureManagementService;
import com.teamcenter.services.rac.cad._2007_01.StructureManagement.ExpandPSAllLevelsInfo;
import com.teamcenter.services.rac.cad._2007_01.StructureManagement.ExpandPSAllLevelsOutput;
import com.teamcenter.services.rac.cad._2007_01.StructureManagement.ExpandPSAllLevelsPref;
import com.teamcenter.services.rac.cad._2007_01.StructureManagement.ExpandPSAllLevelsResponse;
import com.teamcenter.services.rac.cad._2007_01.StructureManagement.ExpandPSData;
import com.teamcenter.services.rac.cad._2007_01.StructureManagement.ExpandPSOneLevelInfo;
import com.teamcenter.services.rac.cad._2007_01.StructureManagement.ExpandPSOneLevelOutput;
import com.teamcenter.services.rac.cad._2007_01.StructureManagement.ExpandPSOneLevelPref;
import com.teamcenter.services.rac.cad._2007_01.StructureManagement.ExpandPSOneLevelResponse;

import sp.SPHandler;

public class BOMUtil
{
	private static StructureManagementService getSMService()
	{
		return StructureManagementService.getService(SPHandler.session);
	}
	// Упаковывает все линии, входящие в состав переданной как аргумент
	public static void packLines(TCComponentBOMLine bomLineToPack) throws TCException
	{
		AIFComponentContext[] childBOMLines = bomLineToPack.getChildren();
		for (AIFComponentContext currBOMLineContext : childBOMLines) {
			TCComponentBOMLine bomLine = (TCComponentBOMLine) currBOMLineContext.getComponent();
			bomLine.pack();
		}
		bomLineToPack.refresh();
	}

	// Распаковывает все линии, входящие в состав переданной как аргумент
	public static void unpackLines(TCComponentBOMLine bomLineToUnPack) throws TCException
	{
		AIFComponentContext[] childBOMLines = bomLineToUnPack.getChildren();
		for (AIFComponentContext currBOMLineContext : childBOMLines) {
			TCComponentBOMLine bomLine = (TCComponentBOMLine) currBOMLineContext.getComponent();
			bomLine.unpack();
		}
		bomLineToUnPack.refresh();
	}

	private void expandBOMLines(final TCComponentBOMLine bomLine)
	{
		ExpandPSOneLevelInfo levelInfo = new ExpandPSOneLevelInfo();
		ExpandPSOneLevelPref levelPref = new ExpandPSOneLevelPref();

		levelInfo.parentBomLines = new TCComponentBOMLine[] { bomLine };
		levelInfo.excludeFilter = "None";
		levelPref.expItemRev = true;

		ExpandPSOneLevelResponse levelResp = getSMService().expandPSOneLevel(levelInfo, levelPref);

		try {
			System.out.println("BOMLine: " + bomLine.getTCProperty("bl_line_name").getStringValue());
		} catch (TCException e) {
			e.printStackTrace();
		}

		if (levelResp.output.length > 0) {
			for (ExpandPSOneLevelOutput levelOut : levelResp.output) {
				for (ExpandPSData psData : levelOut.children) {
					expandBOMLines(psData.bomLine);
				}
			}
		}
	}

	private void expandBOMAllLines(final TCComponentBOMLine bomLine)
	{
		ExpandPSAllLevelsInfo info = new ExpandPSAllLevelsInfo();
		ExpandPSAllLevelsPref pref = new ExpandPSAllLevelsPref();

		info.parentBomLines = new TCComponentBOMLine[] { bomLine };
		info.excludeFilter = "None";
		pref.expItemRev = false;

		ExpandPSAllLevelsResponse resp = getSMService().expandPSAllLevels(info, pref);

		try {
			for (ExpandPSAllLevelsOutput data : resp.output) {
				TCComponentItemRevision parentItemRevision = data.parent.bomLine.getItemRevision();
				String parentUID = parentItemRevision.getUid();

				System.out.println("Parent: " + data.parent.bomLine.getTCProperty("bl_line_name").getStringValue()
						+ " UID:" + parentUID);

				if (data.children.length > 0) {
					for (ExpandPSData child : data.children) {
						TCComponentItemRevision itemRev = child.bomLine.getItemRevision();
						String uid = itemRev.getUid();

						System.out.println("\tChild: " + child.bomLine.getTCProperty("bl_line_name").getStringValue()
								+ " UID:" + uid);
					}
				} else
					System.out.println("\tChildren: none");
			}
		} catch (TCException e) {
			e.printStackTrace();
		}
	}
}
