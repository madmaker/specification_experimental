package vp;

public class VPSettings
{
	public static boolean isOKPressed;
	public static boolean isCancelled;
	
	public static String[] nonbreakableWords;
	
	static
	{
		reset();
	}
	
	public static void reset()
	{
		isOKPressed = false;
		isCancelled = false;
		if(nonbreakableWords.length > 0)
			nonbreakableWords = new String[]{};
	}
}
