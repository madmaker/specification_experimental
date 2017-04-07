package vp;

public class VPSettings
{
	public static boolean isOKPressed;
	
	static
	{
		reset();
	}
	
	public static void reset()
	{
		isOKPressed = false;
	}
}
