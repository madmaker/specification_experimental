package sp;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.ui.handlers.HandlerUtil;

import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCSession;

import sp.gui.SPDialog;
import sp.spblock.SPBlock;
import sp.spblock.SPBlockList;

public class SPHandler extends AbstractHandler
{
	public static TCSession session = (TCSession) AIFUtility.getCurrentApplication().getSession();
	SP specification;

	public SPHandler()
	{
	};

	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		ProgressMonitorDialog pd = new ProgressMonitorDialog(HandlerUtil.getActiveShell(event).getShell());
		
		specification = new SP();
		specification.progressMonitor = pd;
		
		readSettings((SPBlockList)specification.report.blockList);
		
		SPDialog spDialog = new SPDialog(HandlerUtil.getActiveShell(event).getShell(), SWT.CLOSE, specification);
		spDialog.open();
		
		if (!SPSettings.isOKPressed) { return null; }
		
		saveSettings((SPBlockList)specification.report.blockList);
		
		specification.buildXmlFile();
		specification.buildReportFile();

		return null;
	}
	
	void readSettings(SPBlockList blockList){
		String settingsString = SPSettings.blockSettings;
		System.out.println("BLOCK_SETTINGS"+settingsString);
		if(settingsString==null || settingsString.isEmpty()) return;
		for(String blockProps:settingsString.split("&")){
			System.out.println(blockProps);
			String[] props = blockProps.split(":");
			if(props.length!=4) continue;
			System.out.println(props[0]);
			SPBlock block = blockList.getBlock(BlockContentType.values()[Character.getNumericValue(props[0].charAt(0))], props[0].charAt(1)=='0'?BlockType.DEFAULT:BlockType.ME);
			if(block!=null){
				block.attributes.reservePosCount = Integer.parseInt(props[1]);
				block.attributes.reserveLinesCount = Integer.parseInt(props[2]);
				block.attributes.intervalPosCount = Integer.parseInt(props[3]);
			}
		}
	}
	
	void saveSettings(SPBlockList blockList){
		String settingsString = "";
		String del = ":";
		for(int i = 0; i < blockList.size(); i++){
			settingsString+=blockList.get(i).attributes.contentType.ordinal()+(blockList.get(i).attributes.type==BlockType.DEFAULT?"0":"1")+del+blockList.get(i).attributes.reservePosCount+del+blockList.get(i).attributes.reserveLinesCount+del+blockList.get(i).attributes.intervalPosCount;
			settingsString+="&";
		}
		if(settingsString.endsWith("&")){
			settingsString = settingsString.substring(0, settingsString.length()-1);
		}
		SPSettings.blockSettings = settingsString;
	}
}
