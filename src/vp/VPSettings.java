package vp;

public class VPSettings
{
	public static boolean isOKPressed;
	public static boolean isCancelled;
	
	static
	{
		reset();
	}
	
	public static void reset()
	{
		isOKPressed = false;
		isCancelled = false;
	}
}
