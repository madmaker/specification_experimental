package sp;

public class SPSettings
{
	public static boolean isOKPressed;
	public static boolean canRenumerize;
	public static boolean canUseReservePositions;
	public static boolean canReadPrevRevPositions;
	public static boolean doRenumerize;
	public static boolean doUseReservePositions;
	public static boolean doReadPrevRevPositions;
	public static boolean doShowAdditionalForm;
	public static String additionalText;
	public static String blockSettings;
	
	public static String[] nonbreakableWords;
	
	static
	{
		reset();
	}
	
	public static void reset()
	{
		isOKPressed = false;
		canRenumerize = false;
		canUseReservePositions = false;
		canReadPrevRevPositions = false;
		doRenumerize = false;
		doUseReservePositions = false;
		doReadPrevRevPositions = false;
		doShowAdditionalForm = false;
		additionalText = "";
		blockSettings = "";
		if(nonbreakableWords.length > 0)
			nonbreakableWords = new String[]{};
	}
}
