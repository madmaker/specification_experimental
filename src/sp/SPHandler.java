package sp;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.ui.handlers.HandlerUtil;

import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCSession;

import reports.BlockType;
import reports.EnumBlockType;
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
		
		specification.init();
		specification.readData();
		
		readSettings(specification.report.blockList);
		
		SPDialog spDialog = new SPDialog(HandlerUtil.getActiveShell(event).getShell(), SWT.CLOSE, specification);
		spDialog.open();
		
		if (!SPSettings.isOKPressed) { return null; }
		
		saveSettings(specification.report.blockList);
//		
//		specification.buildXmlFile();
//		specification.buildReportFile();

		return null;
	}
	
	void readSettings(SPBlockList blockList){
		String settingsString = SPSettings.blockSettings;
		System.out.println("BLOCK_SETTINGS"+settingsString);
		for(String blockProps:settingsString.split("&")){
			System.out.println(blockProps);
			String[] props = blockProps.split(":");
			if(props.length!=4) continue;
			System.out.println(props[0]);
			SPBlock block = blockList.getBlock(EnumBlockType.values()[Character.getNumericValue(props[0].charAt(0))], Character.getNumericValue(props[0].charAt(1)));
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
			SPBlock block = (SPBlock) blockList.get(i);
			settingsString+=block.attributes.contentType.ordinal()+block.attributes.type+del+block.attributes.reservePosCount+del+block.attributes.reserveLinesCount+del+block.attributes.intervalPosCount;
			settingsString+="&";
		}
		if(settingsString.endsWith("&")){
			settingsString = settingsString.substring(0, settingsString.length()-1);
		}
		SPSettings.blockSettings = settingsString;
	}
}
